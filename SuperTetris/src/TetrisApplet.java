
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.sql.*;
import java.util.Calendar;
import java.util.concurrent.locks.ReentrantLock;
import javax.swing.JOptionPane;

public class TetrisApplet extends JApplet implements KeyListener, ActionListener {

    private JPanel _contenu = new JPanel();
    private JPanel _contenuInfo = new JPanel();
    private JPanel _contenuButtonInfo = new JPanel();
    private JButton _startButton = new JButton("Jouer");
    private JButton _pauseButton = new JButton("Pause");
    private JLabel _scoreLabel = new JLabel("Score : 0");
    private JLabel _niveauLabel = new JLabel("Niveau : 1");
    private JLabel _pieceSuivanteLabel = new JLabel("Piece Suivante : ");
    public static Grille _grille;
    private ReentrantLock _mutexGrille;
    public GrillePanel _grillePanel;
    public PiecePanel _pieceSuivantePanel = new PiecePanel();
    private GridBagConstraints _contraintes;
    private String _idJoueur = "2";
    private String _idConnexion = "SuperTetris";
    private String _mdpConnexion = "supertetris";
    private String _URLConnexion = "judgeay.servegame.com";
    private String _nomBDD = "tetris";
    private MonThread _monProcess;
    private int _minSpeed = 300;

    @Override
    public void init() {
        _idJoueur = this.getParameter("idJoueur");

        this._mutexGrille = new ReentrantLock();
        _contenuInfo.setLayout(new GridBagLayout());
        _contenu.setLayout(new GridBagLayout());


        _contraintes = new GridBagConstraints();
        _contraintes.anchor = GridBagConstraints.WEST;
        _contraintes.insets = new Insets(3, 3, 3, 3);

        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _pauseButton.addActionListener(this);
        _pauseButton.setFocusable(false);
        _pauseButton.setEnabled(false);
        _contenuButtonInfo.add(_pauseButton, _contraintes);
        _contraintes.gridx = 1;
        _contraintes.gridy = 1;
        _startButton.addActionListener(this);
        _startButton.setFocusable(false);
        _contenuButtonInfo.add(_startButton, _contraintes);

        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _contenuButtonInfo.setBackground(new Color(221,221,221));
        _contenuInfo.add(_contenuButtonInfo, _contraintes);

        _contraintes.gridx = 0;
        _contraintes.gridy = 2;
        _contraintes.insets = new Insets(3, 6, 3, 3);
        _contenuInfo.add(_scoreLabel, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 3;
        _contenuInfo.add(_niveauLabel, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 4;
        _contenuInfo.add(_pieceSuivanteLabel, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 5;
        _contraintes.anchor = GridBagConstraints.CENTER;
        _pieceSuivantePanel.setPreferredSize(new Dimension(81, 81));
        _contenu.setBackground(new Color(221,221,221));
        _contenuInfo.setBackground(new Color(221,221,221));
        _contenuInfo.add(_pieceSuivantePanel, _contraintes);

        _contraintes.gridx = 1;
        _contraintes.gridy = 0;
        _contenu.add(_contenuInfo, _contraintes);

        Connection connexion;
        PreparedStatement ps;
        Blob blob;
        ImageIcon icon;

        try {
            Class.forName("com.mysql.jdbc.Driver");

            connexion = DriverManager.getConnection("jdbc:mysql://" + _URLConnexion + "/" + _nomBDD, _idConnexion, _mdpConnexion);
            ps = connexion.prepareStatement("SELECT wallpaper FROM membre WHERE id=?");

            ps.setString(1, _idJoueur);
            ResultSet rs = ps.executeQuery();
            rs.next();
            blob = rs.getBlob("wallpaper");
            icon = new ImageIcon(blob.getBytes(1, (int) blob.length()));

            _grillePanel = new GrillePanel(icon.getImage());

            connexion.close();
        } catch (Exception e) {

            System.out.println("echec pilote : " + e);
        }

        if (_grillePanel == null) {
            _grillePanel = new GrillePanel();
        }

        _contraintes.gridx = 0;
        _contraintes.gridy = 0;
        _grillePanel.setPreferredSize(new Dimension(201, 441));
        _grillePanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2));
        _contenu.add(_grillePanel, _contraintes);
        this.setSize(new Dimension(500, 500));

        this.setFocusable(true);

        this.setContentPane(_contenu);
    }

    private void finDePartie() {
        stopThread();

        this.removeKeyListener(this);

        String message = "Fin de la partie !" + System.getProperty("line.separator") + "Le score final est de " + _grille.GetScore();

        try {
            Class.forName("com.mysql.jdbc.Driver");

            Connection connexion = DriverManager.getConnection("jdbc:mysql://" + _URLConnexion + "/" + _nomBDD, _idConnexion, _mdpConnexion);

            Statement instruction = connexion.createStatement();

            // create a java calendar instance
            Calendar calendar = Calendar.getInstance();

            // get a java.util.Date from the calendar instance.
            // this date will represent the current instant, or "now".
            java.util.Date now = calendar.getTime();

            // a java current time (now) instance
            java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());

            int res = instruction.executeUpdate("INSERT INTO score VALUES (" + _idJoueur + ",'" + currentTimestamp + "'," + _grille.GetScore() + ")");

            message += System.getProperty("line.separator") + "Votre score à été enregistré";

            connexion.close();
        } catch (Exception e) {
            message += System.getProperty("line.separator") + "Erreur dans l'enregistrement du score";

            System.out.println("echec pilote : " + e);
        }

        _pauseButton.setEnabled(false);
        JOptionPane.showMessageDialog(null, message, "Perdu !", JOptionPane.WARNING_MESSAGE);
        _startButton.setText("Jouer");
        _scoreLabel.setText("Score : 0");
        _niveauLabel.setText("Niveau : 1");
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {

        if (_monProcess != null) {

            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    _mutexGrille.lock();
                    _grille.Gauche();
                    _mutexGrille.unlock();
                    break;
                case KeyEvent.VK_RIGHT:
                    _mutexGrille.lock();
                    _grille.Droite();
                    _mutexGrille.unlock();
                    break;
                case KeyEvent.VK_UP:
                    _mutexGrille.lock();
                    _grille.Retourner();
                    _mutexGrille.unlock();
                    break;
                case KeyEvent.VK_DOWN:
                    try {
                        _mutexGrille.lock();
                        _grille.Descendre();
                    } catch (EndGameException exepetion) {
                        this.finDePartie();
                    } finally {
                        _mutexGrille.unlock();
                    }
                    break;
                default:
                    break;
            }
            _grillePanel.repaint();
            _pieceSuivantePanel.repaint();
        }

        if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            if (_monProcess != null) {
                _pauseButton.setText("Reprendre");
                stopThread();
            } else {
                _pauseButton.setText("Pause");
                startThread();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _pauseButton) {
            if (_monProcess != null) {
                _pauseButton.setText("Reprendre");
                stopThread();
            } else {
                _pauseButton.setText("Pause");
                startThread();
            }
        }
        if (e.getSource() == _startButton) {
            _grille = new Grille();
            _startButton.setText("Recommencer");
            _pauseButton.setEnabled(true);
            startThread();
            this.addKeyListener(this);
        }
    }

    private void startThread() {
        if (_monProcess != null) {
            stopThread();
        }
        _monProcess = new MonThread();
        _monProcess.start();
    }

    private void stopThread() {
        if (_monProcess != null) {
            _monProcess.interrupt();
            _monProcess = null;
        }
    }

    public class MonThread extends Thread {

        public void run() {
            //Boucle infinie (tant que mon_process existe et n'est pas interrompu)
            while ((!this.isInterrupted())) {


                try {
                    _mutexGrille.lock();
                    _grille.Descendre();
                } catch (EndGameException ex) {

                    finDePartie();
                    this.interrupt();
                } finally {
                    _mutexGrille.unlock();
                }


                _niveauLabel.setText("Niveau : " + (_grille.GetScore() / 100 + 1));
                _scoreLabel.setText("Score : " + _grille.GetScore());
                _grillePanel.repaint();
                _pieceSuivantePanel.repaint();
                try {
                    this.sleep(_minSpeed / 1, ((2 * _grille.GetScore()) / 100));
                } catch (InterruptedException e) {
                    return;
                }
            }
        }
    }
}

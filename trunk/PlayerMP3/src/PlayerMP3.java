
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.DefaultListModel;
import javax.swing.JApplet;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.AudioDevice;
import javazoom.jl.player.FactoryRegistry;
import javazoom.jl.player.Player;

/**
 *
 * @author Aurelien
 * 
 * Pour valider l'application (Dans la ligne de commande) :
 * keytool -genkey -alias PlayerMP3 -validity 365
 * jarsigner PlayerMP3.jar PlayerMP3
 * 
 */
public class PlayerMP3 extends JApplet implements ActionListener {

    private MonThread _monProcess;
    private static Player _player;
    private PlayerMP3 _playerMp3;
    private String fFilename = null;
    private boolean remote = false;
    private GridBagConstraints _contraintes;
    private JPanel _contenu = new JPanel();
    private JPanel _contenuButton = new JPanel();
    private JPanel _contenuButton2 = new JPanel();
    private JPanel _contenuLabel = new JPanel();
    private JButton _startButton = new JButton(">");
    private JButton _supprimButton = new JButton("Supprimmer");
    private JButton _suivanteButton = new JButton(">>");
    private JButton _precedanteButton = new JButton("<<");
    private JButton _choisirButton = new JButton("Parcourir");
    private JLabel _musiclabel = new JLabel("En cours : ");
    private JFileChooser _fc = new JFileChooser("c:/");
    private JLabel _musicEnCours = new JLabel();
    private boolean _isLecture = false;
    private boolean _isLectBtn = false;
    private ArrayList<File> _listFile = new ArrayList<File>();
    private int _indiceLecture = 0;
    private DefaultListModel listModel = new DefaultListModel();
    private JList _list = new JList(listModel);
    private JScrollPane _listScroller;

    @Override
    public void init() {
        _contenu.setLayout(new GridBagLayout());
        _contraintes = new GridBagConstraints();
        _contraintes.anchor = GridBagConstraints.WEST;
        _contraintes.insets = new Insets(3, 3, 3, 3);

        _contenu.setBackground(new Color( 221, 221, 221));
        _contenuButton.setBackground(new Color( 221, 221, 221));
        _contenuButton2.setBackground(new Color( 221, 221, 221));
        _contenuLabel.setBackground(new Color( 221, 221, 221));

        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _precedanteButton.addActionListener(this);
        _precedanteButton.setFocusable(false);
        _precedanteButton.setEnabled(false);
        _contenuButton.add(_precedanteButton, _contraintes);
        _contraintes.gridx = 1;
        _contraintes.gridy = 1;
        _startButton.addActionListener(this);
        _startButton.setFocusable(false);
        _startButton.setEnabled(false);
        _contenuButton.add(_startButton, _contraintes);
        _contraintes.gridx = 2;
        _contraintes.gridy = 1;
        _suivanteButton.addActionListener(this);
        _suivanteButton.setFocusable(false);
        _suivanteButton.setEnabled(false);
        _contenuButton.add(_suivanteButton, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _contenu.add(_contenuButton, _contraintes);

        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _contraintes.insets = new Insets(3, 3, 3, 3);
        _contenuLabel.add(_musiclabel, _contraintes);
        _contraintes.gridx = 1;
        _contraintes.gridy = 1;
        _contenuLabel.add(_musicEnCours, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 2;
        _contenu.add(_contenuLabel, _contraintes);

        _list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        _list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
        _list.setVisibleRowCount(-1);
        _listScroller = new JScrollPane(_list);
        _listScroller.setPreferredSize(new Dimension(220, 80));
        _contraintes.gridx = 0;
        _contraintes.gridy = 3;
        _contenu.add(_listScroller, _contraintes);

        _contraintes.gridx = 1;
        _contraintes.gridy = 1;
        _supprimButton.addActionListener(this);
        _supprimButton.setFocusable(false);
        _supprimButton.setEnabled(false);
        _contenuButton2.add(_supprimButton, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 1;
        _choisirButton.addActionListener(this);
        _choisirButton.setFocusable(false);
        _contenuButton2.add(_choisirButton, _contraintes);
        _contraintes.gridx = 0;
        _contraintes.gridy = 4;
        _contenu.add(_contenuButton2, _contraintes);



        this.setSize(new Dimension(230, 250));

        this.setFocusable(true);
        this.setContentPane(_contenu);


    }

    static public PlayerMP3 createInstance(String[] args) {
        PlayerMP3 player = new PlayerMP3();
        if (!player.parseArgs(args)) {
            player = null;
        }
        return player;
    }

    protected void init(String filename) {
        fFilename = filename;
    }

    protected boolean parseArgs(String[] args) {
        boolean parsed = false;
        if (args.length == 1) {
            init(args[0]);
            parsed = true;
            remote = false;
        } else if (args.length == 2) {
            if (!(args[0].equals("-url"))) {
                System.out.println("kou");
            } else {
                init(args[1]);
                parsed = true;
                remote = true;
            }
        } else {
        }
        return parsed;
    }

    public void play()
            throws JavaLayerException {
        try {
            System.out.println("playing " + fFilename + "...");
            InputStream in = null;
            if (remote == true) {
                in = getURLInputStream();
            } else {
                in = getInputStream();
            }
            AudioDevice dev = getAudioDevice();
            _player = new Player(in, dev);
            _player.play();

        } catch (IOException ex) {
            throw new JavaLayerException("Problem playing file " + fFilename, ex);
        } catch (Exception ex) {
            throw new JavaLayerException("Problem playing file " + fFilename, ex);
        }
    }

    /**
     * Playing file from URL (Streaming).
     */
    protected InputStream getURLInputStream()
            throws Exception {

        URL url = new URL(fFilename);
        InputStream fin = url.openStream();
        BufferedInputStream bin = new BufferedInputStream(fin);
        return bin;
    }

    /**
     * Playing file from FileInputStream.
     */
    protected InputStream getInputStream()
            throws IOException {
        FileInputStream fin = new FileInputStream(fFilename);
        BufferedInputStream bin = new BufferedInputStream(fin);
        return bin;
    }

    protected AudioDevice getAudioDevice()
            throws JavaLayerException {
        return FactoryRegistry.systemRegistry().createAudioDevice();
    }

    private void startMp3() {

        int retval = 0;
        try {
            String[] ar = new String[1];
            ar[0] = _listFile.get(_indiceLecture).getPath();
            String[] s = ar[0].split("\\\\");
            String sTmp = s[s.length - 1];
            if (sTmp.length() > 18) {
                sTmp = sTmp.substring(0, 18);
                sTmp += "...";
            }
            _musicEnCours.setText(sTmp);

            _playerMp3 = createInstance(ar);
            if (_playerMp3 != null) {
                _playerMp3.play();
                _isLecture = true;
            }

        } catch (Exception ex) {
            System.err.println(ex);
            ex.printStackTrace(System.err);
            retval = 1;
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
            _player.close();
            _isLecture = false;
            _monProcess.interrupt();
            _monProcess = null;
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == _supprimButton) {
            int i = _list.getSelectedIndex();
            listModel.remove(_list.getSelectedIndex());
            if(_listFile.size() > 1)
                _listFile.remove(_list.getSelectedIndex());
            else
                _listFile = new ArrayList<File>();
            _list.setSelectedIndex(0);
            if (listModel.isEmpty()) {
                _supprimButton.setEnabled(false);
            }
        } else if (e.getSource() == _startButton) {
            if (_isLectBtn) {
                _isLectBtn = false;
                _suivanteButton.setEnabled(false);
                _precedanteButton.setEnabled(false);
                _startButton.setText(">");
                stopThread();
                _musicEnCours.setText("");
            } else {
                _isLectBtn = true;
                _suivanteButton.setEnabled(true);
                _precedanteButton.setEnabled(true);
                _startButton.setText("[]");
                startThread();
            }
            _list.setSelectedIndex(_indiceLecture);
        } else if (e.getSource() == _choisirButton) {
            // Show open dialog; this method does not return until the dialog is closed
            _fc.showOpenDialog(this);
            File f = _fc.getSelectedFile();
            String[] s = f.getPath().split("\\\\");
            _listFile.add(f);
            listModel.addElement(s[s.length - 1]);
            _supprimButton.setEnabled(true);
            if (!_isLectBtn) {
                _startButton.setEnabled(true);
            }
        } else if (e.getSource() == _suivanteButton) {
            if (_indiceLecture != _listFile.size() - 1) {
                _indiceLecture++;
            } else {
                _indiceLecture = 0;
            }
            startThread();
            String[] s = fFilename.split("\\\\");
            _musicEnCours.setText(s[s.length - 1]);
        } else if (e.getSource() == _precedanteButton) {
            if (_indiceLecture != 0) {
                _indiceLecture--;
            } else {
                _indiceLecture = _listFile.size() - 1;
            }
            startThread();
            String[] s = fFilename.split("\\\\");
            _musicEnCours.setText(s[s.length - 1]);
        }

    }

    public class MonThread extends Thread {

        @Override
        public void run() {
            startMp3();

        }
    }
}

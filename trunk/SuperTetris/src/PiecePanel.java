
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hadrien
 */
public class PiecePanel extends JComponent {

    private Piece _piece;

    public PiecePanel() {
        if (TetrisApplet._grille != null) {
            _piece = TetrisApplet._grille.GetPieceSuivante();
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        if (TetrisApplet._grille != null) {
            _piece = TetrisApplet._grille.GetPieceSuivante();
            for (int y = (Piece.GetHeight() - 1); y >= 0; y--) {
                for (int x = 0; x < Piece.GetWidth(); x++) {
                    switch (_piece._grillePiece[x][(Piece.GetHeight() - 1) - y]) {
                        case 1:
                            g.setColor(new Color(0, 192, 0));
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //vert
                        case 2:
                            g.setColor(Color.pink);
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //rouge
                        case 3:
                            g.setColor(new Color(0, 128, 224));
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //bleu
                        case 4:
                            g.setColor(new Color(0, 192, 192));
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //cyan
                        case 5:
                            g.setColor(Color.orange);
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //orange
                        case 6:
                            g.setColor(Color.darkGray);
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //gris
                        case 7:
                            g.setColor(Color.magenta);
                            g.fill3DRect(20 * x, 20 * y, 20, 20, true);
                            break; //magenta
                        default:
                            break;
                    }
                }
            }
        }
    }
}

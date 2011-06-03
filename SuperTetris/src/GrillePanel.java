
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JComponent;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Hadrien
 */
public class GrillePanel extends JComponent {

    private Image img;

    public GrillePanel(Image image) {
        img = image;
    }

    public GrillePanel() {
        img = null;
    }

    @Override
    public void paintComponent(Graphics g) {
        if (img != null) {
            int height = this.getHeight();
            double rapport = img.getHeight(this) / (double) height;
            //System.out.println(rapport);
            int newHeight = this.getHeight();
            int newWidth = (int) Math.round(img.getWidth(this) / rapport);

            int difference = newWidth - this.getWidth();
            difference = (int) Math.round(difference / 2.0);

            g.drawImage(img, -difference, 0, newWidth, newHeight, null);
        }
        if (TetrisApplet._grille != null) {
            for (int y = (Grille.GetHeight() - 1 - Piece.GetHeight()); y >= 0; y--) {
                for (int x = 0; x < Grille.GetWidth(); x++) {
                    switch (TetrisApplet._grille.GetElementGrille(x, (Grille.GetHeight() - 1 - Piece.GetHeight()) - y)) {
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

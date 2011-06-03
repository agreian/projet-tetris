
import java.util.Random;

public abstract class Piece {

    protected int[][] _grillePiece;
    protected int _position;
    private static int width = 4;
    private static int height = 4;

    protected Piece() {
        this._grillePiece = new int[width][height];
        this._position = 1;
    }

    public static int GetWidth() {
        return width;
    }

    public static int GetHeight() {
        return height;
    }

    public int GetElementPiece(int x, int y) {
        return this._grillePiece[x][y];
    }

    public int[][] GetGrillePiece() {
        int[][] retour = new int[Piece.GetWidth()][Piece.GetHeight()];
        for (int x = 0; x < Piece.GetWidth(); ++x) {
            for (int y = 0; y < Piece.GetHeight(); ++y) {
                retour[x][y] = this.GetElementPiece(x, y);
            }
        }

        return retour;
    }

    public static Piece GenererPieceAleatoire() {
        Random rnd = new Random();
        int val = rnd.nextInt(7);
        Piece retour;

        switch (val) {
            case 0:
                retour = new PieceI();
                break;
            case 1:
                retour = new PieceO();
                break;
            case 2:
                retour = new PieceT();
                break;
            case 3:
                retour = new PieceL();
                break;
            case 4:
                retour = new PieceJ();
                break;
            case 5:
                retour = new PieceZ();
                break;
            case 6:
                retour = new PieceS();
                break;
            default:
                retour = null;
                break;
        }

        return retour;

    }

    public abstract void Rotation();

    public abstract int[][] RetournerMatriceRotation();
}

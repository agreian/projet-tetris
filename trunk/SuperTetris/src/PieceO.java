
public class PieceO extends Piece {

    public PieceO() {
        super();
        this._grillePiece[0][0] = 2;
        this._grillePiece[1][0] = 2;
        this._grillePiece[0][1] = 2;
        this._grillePiece[1][1] = 2;
    }

    public void Rotation() {
    }

    public int[][] RetournerMatriceRotation() {
        int[][] retour = new int[Piece.GetWidth()][Piece.GetHeight()];
        for (int x = 0; x < Piece.GetWidth(); ++x) {
            for (int y = 0; y < Piece.GetHeight(); ++y) {
                retour[x][y] = this.GetElementPiece(x, y);
            }
        }

        return retour;
    }
}

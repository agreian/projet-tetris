
public class PieceS extends Piece {

    public PieceS() {
        super();
        this._grillePiece[0][0] = 7;
        this._grillePiece[1][0] = 7;
        this._grillePiece[1][1] = 7;
        this._grillePiece[2][1] = 7;
    }

    public void Rotation() {
        if (this._position == 1) {
            this._position = 2;
            this._grillePiece[0][0] = 0;
            this._grillePiece[1][0] = 0;
            this._grillePiece[1][1] = 0;
            this._grillePiece[2][1] = 0;

            this._grillePiece[0][2] = 7;
            this._grillePiece[0][1] = 7;
            this._grillePiece[1][1] = 7;
            this._grillePiece[1][0] = 7;
        } else {
            this._position = 1;
            this._grillePiece[0][2] = 0;
            this._grillePiece[0][1] = 0;
            this._grillePiece[1][1] = 0;
            this._grillePiece[1][0] = 0;

            this._grillePiece[0][0] = 7;
            this._grillePiece[1][0] = 7;
            this._grillePiece[1][1] = 7;
            this._grillePiece[2][1] = 7;
        }
    }

    public int[][] RetournerMatriceRotation() {
        int[][] retour = new int[Piece.GetWidth()][Piece.GetHeight()];
        for (int x = 0; x < Piece.GetWidth(); ++x) {
            for (int y = 0; y < Piece.GetHeight(); ++y) {
                retour[x][y] = this.GetElementPiece(x, y);
            }
        }

        if (this._position == 1) {
            retour[0][0] = 0;
            retour[1][0] = 0;
            retour[1][1] = 0;
            retour[2][1] = 0;

            retour[0][2] = 7;
            retour[0][1] = 7;
            retour[1][1] = 7;
            retour[1][0] = 7;
        } else {
            retour[0][2] = 0;
            retour[0][1] = 0;
            retour[1][1] = 0;
            retour[1][0] = 0;

            retour[0][0] = 7;
            retour[1][0] = 7;
            retour[1][1] = 7;
            retour[2][1] = 7;
        }

        return retour;
    }
}

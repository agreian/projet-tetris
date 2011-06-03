
public class PieceI extends Piece {

    public PieceI() {
        super();
        this._grillePiece[0][0] = 1;
        this._grillePiece[1][0] = 1;
        this._grillePiece[2][0] = 1;
        this._grillePiece[3][0] = 1;
    }

    public void Rotation() {
        if (this._position == 1) {
            this._position = 2;

            // Tourne la pièce
            this._grillePiece[0][0] = 0;
            this._grillePiece[1][0] = 0;
            this._grillePiece[2][0] = 0;
            this._grillePiece[3][0] = 0;

            this._grillePiece[0][0] = 1;
            this._grillePiece[0][1] = 1;
            this._grillePiece[0][2] = 1;
            this._grillePiece[0][3] = 1;
        } else {
            this._position = 1;

            // Tourne la pièce
            this._grillePiece[0][0] = 0;
            this._grillePiece[0][1] = 0;
            this._grillePiece[0][2] = 0;
            this._grillePiece[0][3] = 0;

            this._grillePiece[0][0] = 1;
            this._grillePiece[1][0] = 1;
            this._grillePiece[2][0] = 1;
            this._grillePiece[3][0] = 1;
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
            retour[2][0] = 0;
            retour[3][0] = 0;

            retour[0][0] = 1;
            retour[0][1] = 1;
            retour[0][2] = 1;
            retour[0][3] = 1;
        } else {
            retour[0][0] = 0;
            retour[0][1] = 0;
            retour[0][2] = 0;
            retour[0][3] = 0;

            retour[0][0] = 1;
            retour[1][0] = 1;
            retour[2][0] = 1;
            retour[3][0] = 1;
        }

        return retour;
    }
}

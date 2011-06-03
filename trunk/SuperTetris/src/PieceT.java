
public class PieceT extends Piece {

    public PieceT() {
        super();
        this._grillePiece[0][1] = 3;
        this._grillePiece[1][1] = 3;
        this._grillePiece[2][1] = 3;
        this._grillePiece[1][0] = 3;
    }

    public void Rotation() {
        switch (this._position) {
            case 1:
                this._position = 2;
                this._grillePiece[0][1] = 0;
                this._grillePiece[1][1] = 0;
                this._grillePiece[2][1] = 0;
                this._grillePiece[1][0] = 0;

                this._grillePiece[0][1] = 3;
                this._grillePiece[1][1] = 3;
                this._grillePiece[1][2] = 3;
                this._grillePiece[1][0] = 3;
                break;
            case 2:
                this._position = 3;
                this._grillePiece[0][1] = 0;
                this._grillePiece[1][1] = 0;
                this._grillePiece[1][2] = 0;
                this._grillePiece[1][0] = 0;

                this._grillePiece[0][0] = 3;
                this._grillePiece[1][0] = 3;
                this._grillePiece[2][0] = 3;
                this._grillePiece[1][1] = 3;
                break;
            case 3:
                this._position = 4;
                this._grillePiece[0][0] = 0;
                this._grillePiece[1][0] = 0;
                this._grillePiece[2][0] = 0;
                this._grillePiece[1][1] = 0;

                this._grillePiece[0][0] = 3;
                this._grillePiece[0][1] = 3;
                this._grillePiece[0][2] = 3;
                this._grillePiece[1][1] = 3;
                break;
            case 4:
                this._position = 1;
                this._grillePiece[0][0] = 0;
                this._grillePiece[0][1] = 0;
                this._grillePiece[0][2] = 0;
                this._grillePiece[1][1] = 0;

                this._grillePiece[0][1] = 3;
                this._grillePiece[1][1] = 3;
                this._grillePiece[2][1] = 3;
                this._grillePiece[1][0] = 3;
                break;
            default:
                break;
        }
    }

    public int[][] RetournerMatriceRotation() {
        int[][] retour = new int[Piece.GetWidth()][Piece.GetHeight()];
        for (int x = 0; x < Piece.GetWidth(); ++x) {
            for (int y = 0; y < Piece.GetHeight(); ++y) {
                retour[x][y] = this.GetElementPiece(x, y);
            }
        }

        switch (this._position) {
            case 1:
                retour[0][1] = 0;
                retour[1][1] = 0;
                retour[2][1] = 0;
                retour[1][0] = 0;

                retour[0][1] = 3;
                retour[1][1] = 3;
                retour[1][2] = 3;
                retour[1][0] = 3;
                break;
            case 2:
                retour[0][1] = 0;
                retour[1][1] = 0;
                retour[1][2] = 0;
                retour[1][0] = 0;

                retour[0][0] = 3;
                retour[1][0] = 3;
                retour[2][0] = 3;
                retour[1][1] = 3;
                break;
            case 3:
                retour[0][0] = 0;
                retour[1][0] = 0;
                retour[2][0] = 0;
                retour[1][1] = 0;

                retour[0][0] = 3;
                retour[0][1] = 3;
                retour[0][2] = 3;
                retour[1][1] = 3;
                break;
            case 4:
                retour[0][0] = 0;
                retour[0][1] = 0;
                retour[0][2] = 0;
                retour[1][1] = 0;

                retour[0][1] = 3;
                retour[1][1] = 3;
                retour[2][1] = 3;
                retour[1][0] = 3;
                break;
            default:
                break;
        }

        return retour;
    }
}


public class Grille {

    private int[][] _grilleJeu;
    private Piece _pieceCourante;
    private Piece _pieceSuivante;
    private int _xPiece;
    private int _yPiece;
    private int _score;
    private boolean _piecePose;
    private static int width = 10;
    private static int height = 22 + Piece.GetHeight();

    Grille() {
        this._grilleJeu = new int[width][height];
        for (int y = 0; y < height; ++y) {
            for (int x = 0; x < width; ++x) {
                _grilleJeu[x][y] = 0;
            }
        }
        _pieceCourante = null;
        _pieceSuivante = Piece.GenererPieceAleatoire();

        this._piecePose = false;
        this._score = 0;
    }

    public int GetElementGrille(int x, int y) {
        return this._grilleJeu[x][y];
    }

    public Piece GetPieceSuivante() {
        return this._pieceSuivante;
    }

    public static int GetWidth() {
        return width;
    }

    public static int GetHeight() {
        return height;
    }

    public int GetScore() {
        return this._score;
    }

    public boolean Descendre() throws EndGameException {
        if (this._pieceCourante == null) {
            this._pieceCourante = this._pieceSuivante;
            this._pieceSuivante = Piece.GenererPieceAleatoire();
            this._xPiece = width / 2 - Piece.GetWidth() / 2;
            this._yPiece = height - Piece.GetWidth();

            EcrirePieceCourante();

            return false;
        }
        if (this._piecePose == true) {
            if (VerifierBordSuperieur() == true) {
                throw new EndGameException();
            }

            this._piecePose = false;
            DecalerLigne();
            this._pieceCourante = null;

            return false;
        }

        EffacerPieceCourante();
        if (Collision(this._pieceCourante.GetGrillePiece(), this._xPiece, this._yPiece - 1) == true) {
            EcrirePieceCourante();
            this._piecePose = true;

            return true;
        }

        --this._yPiece;
        EcrirePieceCourante();

        return false;
    }

    public boolean Gauche() {
        if (this._pieceCourante == null || this._piecePose == true) {
            return false;
        }

        EffacerPieceCourante();
        if (Collision(this._pieceCourante.GetGrillePiece(), this._xPiece - 1, this._yPiece) == true) {
            EcrirePieceCourante();
            return false;
        }
        --this._xPiece;
        EcrirePieceCourante();

        return true;
    }

    public boolean Droite() {
        if (this._pieceCourante == null || this._piecePose == true) {
            return false;
        }

        EffacerPieceCourante();
        if (Collision(this._pieceCourante.GetGrillePiece(), this._xPiece + 1, this._yPiece) == true) {
            EcrirePieceCourante();
            return false;
        }
        ++this._xPiece;
        EcrirePieceCourante();

        return true;
    }

    public boolean Retourner() {
        if (this._pieceCourante == null || this._piecePose == true) {
            return false;
        }

        EffacerPieceCourante();
        if (Collision(this._pieceCourante.RetournerMatriceRotation(), this._xPiece, this._yPiece) == true) {
            EcrirePieceCourante();
            return false;
        }
        this._pieceCourante.Rotation();
        EcrirePieceCourante();

        return true;
    }

    public void DescendreTout() throws EndGameException {
        while (Descendre() == false) {
        }
    }

    /**
     * Renvoie vrai s'il y a une collision, faux sinon
     * 
     * @param matricePiece
     *            Copie de la matrice de la pièce
     * @param newX
     *            Nouvelle position sur l'axe des abscisses de la pièce
     * @param newY
     *            Nouvelle position sur l'axe des ordonnées de la pièce
     * @return Vrai s'il y a une collision, faux sinon
     */
    public boolean Collision(int[][] matricePiece, int newX, int newY) {
        //System.out.println("Debut collision!");
        for (int y = 0; y < Piece.GetHeight(); ++y) {
            for (int x = 0; x < Piece.GetWidth(); ++x) {
                if (matricePiece[x][y] != 0) {
                    int sumX = x + newX;
                    int sumY = y + newY;
                    if (sumX < 0 || sumX > (width - 1) || sumY < 0 || sumY > (height - 1)) {
                        return true;
                    }
                }
                //System.out.println((x + newX) + " : " + (y + newY));
                if (matricePiece[x][y] != 0
                        && this._grilleJeu[x + newX][y + newY] != 0) {
                    return true;
                }
            }
        }
        //System.out.println("Fin collision!");
        return false;
    }

    /**
     * Retourne vrai si la ligne est pleine, faux sinon
     * 
     * @param indice
     *            Indice de la ligne
     * @return Vrai si la ligne est pleine, faux sinon
     */
    private boolean LignePleine(int indice) {
        for (int x = 0; x < width; ++x) {
            if (this._grilleJeu[x][indice] == 0) {
                return false;
            }
        }
        return true;
    }

    private boolean LigneVide(int indice) {
        for (int x = 0; x < width; ++x) {
            if (this._grilleJeu[x][indice] != 0) {
                return false;
            }
        }
        return true;
    }

    private void EffacerLigne(int indice) {
        for (int x = 0; x < width; ++x) {
            this._grilleJeu[x][indice] = 0;
        }
    }

    /**
     * Deplace une ligne de la grille
     * 
     * @param nouvelIndice
     *            Indice de la ligne cible
     * @param ancienIndice
     *            Indice de la ligne à déplacer
     */
    private void DeplacerLigne(int nouvelIndice, int ancienIndice) {
        for (int x = 0; x < width; ++x) {
            this._grilleJeu[x][nouvelIndice] = this._grilleJeu[x][ancienIndice];
        }
    }

    private void DecalerLigne() {
        for (int y = 0; y < height; ++y) {
            if (LignePleine(y) == true) {
                this._score += 10;
                for (int j = y; j < height - 1; ++j) {
                    DeplacerLigne(j, j + 1);
                }
                EffacerLigne(height - 1);
                --y;
            }
        }
    }

    private void EcrirePieceCourante() {
        for (int y = 0; y < Piece.GetHeight(); ++y) {
            for (int x = 0; x < Piece.GetWidth(); ++x) {
                int val;
                if ((val = this._pieceCourante.GetElementPiece(x, y)) != 0) {
                    int sumX = x + this._xPiece;
                    int sumY = y + this._yPiece;
                    if (!(sumX < 0 || sumX > (width - 1) || sumY < 0 || sumY > (height - 1))) {
                        this._grilleJeu[x + this._xPiece][y + this._yPiece] = val;
                    }
                }
            }
        }
    }

    private void EffacerPieceCourante() {
        for (int y = 0; y < Piece.GetHeight(); ++y) {
            for (int x = 0; x < Piece.GetWidth(); ++x) {
                if (this._pieceCourante.GetElementPiece(x, y) != 0) {
                    int sumX = x + this._xPiece;
                    int sumY = y + this._yPiece;
                    if (!(sumX < 0 || sumX > (width - 1) || sumY < 0 || sumY > (height - 1))) {
                        this._grilleJeu[x + this._xPiece][y + this._yPiece] = 0;
                    }
                }
            }
        }
    }

    /**
     * Verifie qu'une pièce se situe sur le bord supérieur de la grille
     * @return Vrai si une pièce est situé sur le bord supérieur de la grille, faux sinon
     */
    private boolean VerifierBordSuperieur() {
        for (int y = 0; y < Piece.GetHeight(); ++y) {
            for (int x = 0; x < Piece.GetWidth(); ++x) {
                if (this._pieceCourante.GetElementPiece(x, y) != 0 && (y + this._yPiece) > (height - Piece.GetHeight() - 1)) {
                    return true;
                }
            }
        }
        return false;
    }
}

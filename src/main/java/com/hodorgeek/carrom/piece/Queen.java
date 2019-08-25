package com.hodorgeek.carrom.piece;

public class Queen extends Piece{

    private int faceValue;

    public Queen(PieceColor pieceColor, int faceValue) {
        super(pieceColor);
        this.faceValue = faceValue;
    }

    public int getFaceValue() {
        return faceValue;
    }

    public void setFaceValue(int value) {
        this.faceValue = value;
    }
}

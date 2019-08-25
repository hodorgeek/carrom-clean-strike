package com.hodorgeek.carrom.piece;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Coin extends Piece {

    private int faceValue;

    public Coin(PieceColor pieceColor, int faceValue) {
        super(pieceColor);
        this.faceValue = faceValue;
    }
}

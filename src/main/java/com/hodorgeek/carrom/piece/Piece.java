package com.hodorgeek.carrom.piece;

import lombok.Getter;

@Getter
public abstract class Piece {

    PieceColor pieceColor;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }
}

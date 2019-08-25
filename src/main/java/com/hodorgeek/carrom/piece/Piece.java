package com.hodorgeek.carrom.piece;

import jdk.nashorn.internal.objects.annotations.Setter;
import lombok.Getter;

@Getter
public abstract class Piece {

    PieceColor pieceColor;

    public Piece(PieceColor pieceColor) {
        this.pieceColor = pieceColor;
    }
}

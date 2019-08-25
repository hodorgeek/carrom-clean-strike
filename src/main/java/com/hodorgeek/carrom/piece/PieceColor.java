package com.hodorgeek.carrom.piece;

import lombok.Getter;

@Getter
public enum PieceColor {
    BLACK("black"),
    RED("red"),

    GREEN("green"),

    WHITE("white");

    private String color;

    PieceColor(String color) {
        this.color = color;
    }
}

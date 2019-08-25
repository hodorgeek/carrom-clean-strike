package com.hodorgeek.carrom.piece;


import com.hodorgeek.carrom.exception.InvalidInputException;

import java.util.HashMap;
import java.util.Map;

public class Striker extends Piece {

    public Striker(PieceColor pieceColor) {
        super(pieceColor);
        this.strike = StrikeMe.NONE;
    }

    // strikers current strike
    private Strike strike;

    public Strike getStrike() {
        return strike;
    }

    public void setStrike(Strike strike) {
        this.strike = strike;
    }

    public StrikeResult hitStrike() {
        final StrikeResult result = strike.getResult();
        result.setStrike(strike);
        return result;
    }

    public enum StrikeMe implements Strike {
        SINGLE_STRIKE(1, "Strike") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.FALSE)
                        .pointScored(1)
                        .pieceColor(PieceColor.BLACK)
                        .noOfCoinsRemoved(1).build();
            }
        },

        MULTI_STRIKE(2, "Multi_Strike") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.FALSE)
                        .pointScored(2)
                        .pieceColor(PieceColor.BLACK)
                        .noOfCoinsRemoved(2).build();
            }
        },

        RED_STRIKE(3, "Red_Strike") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.FALSE)
                        .pointScored(3)
                        .pieceColor(PieceColor.RED)
                        .noOfCoinsRemoved(1).build();
            }
        },

        STRIKER_STRIKE(4, "Striker_Strike") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.TRUE)
                        .pointScored(-1)
                        .pieceColor(PieceColor.BLACK)
                        .noOfCoinsRemoved(1).build();
            }
        },

        DEFUNCT_COIN(5, "Defunct_Coin") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.TRUE)
                        .pointScored(-1)
                        .pieceColor(PieceColor.BLACK)
                        .noOfCoinsRemoved(1).build();
            }
        },

        NONE(6, "None") {
            @Override
            public StrikeResult getResult() {
                return StrikeResult.builder()
                        .foulsStrike(Boolean.FALSE)
                        .noOfCoinsRemoved(0)
                        .noOfCoinsRemoved(0).build();
            }
        };

        private int option;

        private String name;

        StrikeMe(int option, String name) {
            this.option = option;
            this.name = name;
        }

        public int getOption() {
            return option;
        }

        public String getName() {
            return name;
        }
    }

    private static Map<String, StrikeMe> nameHitsMap = new HashMap<>();
    private static Map<Integer, StrikeMe> optionHitsMap = new HashMap<>();

    static {
        for (StrikeMe strike : StrikeMe.values()) {
            nameHitsMap.put(strike.getName(), strike);
            optionHitsMap.put(strike.getOption(), strike);
        }
    }

    public static StrikeMe getStrikeByOption(final int option) {
        final StrikeMe strikeMe = optionHitsMap.get(option);
        if(strikeMe == null) {
            throw new InvalidInputException(String.format("strike with option:%d is not valid", option));
        }
        return strikeMe;
    }

    public static StrikeMe getStrikeByName(final String name) {
        final StrikeMe strikeMe = nameHitsMap.get(name);
        if(strikeMe == null) {
            throw new InvalidInputException(String.format("strike with name:%s is not valid", name));
        }
        return strikeMe;
    }
}

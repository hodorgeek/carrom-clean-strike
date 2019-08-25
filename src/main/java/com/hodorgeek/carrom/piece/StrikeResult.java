package com.hodorgeek.carrom.piece;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class StrikeResult {

    private PieceColor pieceColor;

    private int pointScored;

    private int noOfCoinsRemoved;

    private boolean foulsStrike;

    private Strike strike;
}

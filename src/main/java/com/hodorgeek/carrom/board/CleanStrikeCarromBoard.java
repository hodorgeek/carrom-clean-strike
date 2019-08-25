package com.hodorgeek.carrom.board;

import com.hodorgeek.carrom.piece.PieceColor;
import com.hodorgeek.carrom.piece.Queen;
import com.hodorgeek.carrom.piece.StrikeResult;
import com.hodorgeek.carrom.piece.Striker;

public class CleanStrikeCarromBoard extends Board {

    public CleanStrikeCarromBoard(int coinCount) {
        super(coinCount);
        super.striker = new Striker(PieceColor.WHITE);
        super.queen = new Queen(PieceColor.RED, 3);
        super.addCoin(coinCount, PieceColor.BLACK, 1);
    }

    @Override
    public void updateBoard(StrikeResult strikeResult) {
        if(strikeResult.getPieceColor() == PieceColor.RED && super.containsQueen()) {
            super.setQueen(null);
        } else {
            super.removeCoin(strikeResult.getPieceColor(), strikeResult.getNoOfCoinsRemoved());
        }
    }
}

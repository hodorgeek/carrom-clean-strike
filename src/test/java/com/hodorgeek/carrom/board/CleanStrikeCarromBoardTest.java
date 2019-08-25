package com.hodorgeek.carrom.board;

import com.hodorgeek.carrom.piece.PieceColor;
import com.hodorgeek.carrom.piece.StrikeResult;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CleanStrikeCarromBoardTest {

    private Board boardUnderTest;

    @BeforeEach
    void setUp() {
        boardUnderTest = new CleanStrikeCarromBoard(9);
    }

    @Test
    void testUpdateBoard_WhenBlackCoinIsRemoved() {
        // given:
        final StrikeResult strikeResult = StrikeResult.builder()
                .pieceColor(PieceColor.BLACK)
                .noOfCoinsRemoved(5)
                .build();
        // when:
        boardUnderTest.updateBoard(strikeResult);

        // then:
        assertEquals(5, boardUnderTest.size());
        assertTrue(boardUnderTest.containsQueen());
        for (int i = 0; i < boardUnderTest.getCoins().size(); i++) {
            assertEquals(PieceColor.BLACK, boardUnderTest.getCoins().get(i).getPieceColor());
        }
    }


    @Test
    void testUpdateBoard_WhenQueenIsRemoved() {
        // given:
        final StrikeResult strikeResult = StrikeResult.builder()
                .pieceColor(PieceColor.RED)
                .noOfCoinsRemoved(1)
                .build();

        // when:
        boardUnderTest.updateBoard(strikeResult);

        // then:
        assertFalse(boardUnderTest.containsQueen());
        assertEquals(9, boardUnderTest.size());
        for (int i = 0; i < boardUnderTest.size(); i++) {
            assertEquals(PieceColor.BLACK, boardUnderTest.getCoins().get(i).getPieceColor());
        }
    }


    @Test
    public void testSize() {
        assertEquals(10, boardUnderTest.size());
    }

    @Test
    public void testIsEmpty_WhenBoardContainsAllPieces() {
        assertFalse(boardUnderTest.isEmpty());
    }

    @Test
    public void testIsEmpty_WhenBoardContainsNothing() {
        // given:
        final StrikeResult strikeResultForQueen = StrikeResult.builder()
                .pieceColor(PieceColor.RED)
                .noOfCoinsRemoved(1)
                .build();
        boardUnderTest.updateBoard(strikeResultForQueen);

        final StrikeResult strikeResultForCoin = StrikeResult.builder()
                .pieceColor(PieceColor.BLACK)
                .noOfCoinsRemoved(9)
                .build();
        boardUnderTest.updateBoard(strikeResultForCoin);

        // when & then
        assertTrue(boardUnderTest.isEmpty());
    }
}
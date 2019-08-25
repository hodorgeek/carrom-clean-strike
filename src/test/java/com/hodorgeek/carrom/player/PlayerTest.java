package com.hodorgeek.carrom.player;

import com.hodorgeek.carrom.piece.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

    private Player playerUnderTest;

    @BeforeEach
    void setUp() {
        playerUnderTest = new Player("myPlayer");
        playerUnderTest.setPoint(2);
        Striker striker = new Striker(PieceColor.WHITE);
        playerUnderTest.allocateStriker(striker);
        assertNotNull(playerUnderTest.getStriker());
    }


    @Test
    void testUpdatePlayer_WhenRedStrikeWasHit() {
        // given
        final StrikeResult strikeResult = StrikeResult.builder()
                .strike(Striker.StrikeMe.RED_STRIKE)
                .noOfCoinsRemoved(1)
                .pointScored(3)
                .foulsStrike(Boolean.FALSE)
                .pieceColor(PieceColor.RED)
                .build();
        final List<Strike> expectedStrikeList = Arrays.asList(Striker.StrikeMe.RED_STRIKE);

        // when
        playerUnderTest.updatePlayer(strikeResult);

        // then
        assertTrue(playerUnderTest.hasStriker());
        assertEquals(expectedStrikeList, playerUnderTest.getStrikes());
        assertEquals(5, playerUnderTest.getPoint());
        assertEquals(0, playerUnderTest.getFoulCount());
    }

    @Test
    void testUpdatePlayer_WhenFoulStrikeWasHit() {
        // given
        final StrikeResult strikeResult = StrikeResult.builder()
                .strike(Striker.StrikeMe.DEFUNCT_COIN)
                .noOfCoinsRemoved(1)
                .pointScored(-1)
                .foulsStrike(Boolean.TRUE)
                .pieceColor(PieceColor.RED)
                .build();
        final List<Strike> expectedStrikeList = Arrays.asList(Striker.StrikeMe.DEFUNCT_COIN);

        // when
        playerUnderTest.updatePlayer(strikeResult);

        // then
        assertEquals(expectedStrikeList, playerUnderTest.getStrikes());
        assertEquals(1, playerUnderTest.getPoint());
        assertEquals(1, playerUnderTest.getFoulCount());
    }

    @Test
    void testUpdatePlayer_WhenExtraChargeApplyDueToNoScoredInLast3SuccesiveMove() {

        // given
        playerUnderTest.addStrike(Striker.StrikeMe.RED_STRIKE);
        playerUnderTest.addStrike(Striker.StrikeMe.NONE);
        playerUnderTest.addStrike(Striker.StrikeMe.NONE);
        playerUnderTest.setFoulCount(0);
        playerUnderTest.setPoint(3);

        final StrikeResult strikeResult = StrikeResult.builder()
                .strike(Striker.StrikeMe.NONE)
                .noOfCoinsRemoved(1)
                .pointScored(0)
                .foulsStrike(Boolean.FALSE)
                .pieceColor(PieceColor.BLACK)
                .build();

        final List<Strike> expectedStrikeList = Arrays.asList(Striker.StrikeMe.RED_STRIKE, Striker.StrikeMe.NONE, Striker.StrikeMe.NONE, Striker.StrikeMe.NONE);

        // when
        playerUnderTest.updatePlayer(strikeResult);

        // then
        assertTrue(playerUnderTest.hasStriker());
        assertEquals(expectedStrikeList, playerUnderTest.getStrikes());
        assertEquals(2, playerUnderTest.getPoint());
        assertEquals(0, playerUnderTest.getFoulCount());
    }



    private static Stream<Object[]> dataForLastThreeSuccessiveMove() {
        return Stream.of(
                new Object[][]{
                        {Arrays.asList(Striker.StrikeMe.RED_STRIKE, Striker.StrikeMe.NONE), Boolean.FALSE, "when number of strikes are less than 3"},
                        {Arrays.asList(Striker.StrikeMe.MULTI_STRIKE, Striker.StrikeMe.NONE, Striker.StrikeMe.NONE, Striker.StrikeMe.SINGLE_STRIKE), Boolean.FALSE, "player strikes at last 3 times but last three successive strike are not NONE"},
                        {Arrays.asList(Striker.StrikeMe.SINGLE_STRIKE, Striker.StrikeMe.MULTI_STRIKE, Striker.StrikeMe.NONE, Striker.StrikeMe.NONE, Striker.StrikeMe.NONE), Boolean.TRUE, "player strikes at least 3 times and there is no scores in las three succesive strikes"}
                });
    }

    @DisplayName("test player if he strikes none in last 3 successive strikes and scored nothing")
    @ParameterizedTest(name = "{2}")
    @MethodSource(value = "dataForLastThreeSuccessiveMove")
    void testIsThereNoScoringInLastThreeSuccessiveStrikes(List<Strike> strikes, boolean expected, String description) {
        // given
        playerUnderTest.setStrikes(strikes);

        // when & then:
        assertEquals(expected, playerUnderTest.isThereNoScoringInLastThreeSuccessiveStrikes());
    }

    @Test
    void testPlay() {
        // given
        final Strike strike = Striker.StrikeMe.STRIKER_STRIKE;
        final StrikeResult expectedStrikeResult = StrikeResult.builder()
                .foulsStrike(Boolean.TRUE)
                .noOfCoinsRemoved(1)
                .pieceColor(PieceColor.BLACK)
                .pointScored(-1)
                .strike(strike)
                .build();
        // when
        final StrikeResult actualStrikeResult = playerUnderTest.play(strike);

        // then
        assertEquals(expectedStrikeResult, actualStrikeResult);
    }

    @Test
    void testResetStriker() {
        // when
        playerUnderTest.resetStriker();
        // then
        assertFalse(playerUnderTest.hasStriker());
    }
}
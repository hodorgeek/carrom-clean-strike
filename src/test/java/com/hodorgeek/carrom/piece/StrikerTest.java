package com.hodorgeek.carrom.piece;

import com.hodorgeek.carrom.exception.InvalidInputException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrikerTest {

    private Striker strikerUnderTest;

    @BeforeEach
    void setUp() {
        strikerUnderTest = new Striker(PieceColor.WHITE);
    }

    @Test
    void testHitStrike() {
        // given
        strikerUnderTest.setStrike(Striker.StrikeMe.RED_STRIKE);
        StrikeResult expectedStrikeResult = StrikeResult.builder()
                .foulsStrike(Boolean.FALSE)
                .pointScored(3)
                .pieceColor(PieceColor.RED)
                .strike(Striker.StrikeMe.RED_STRIKE)
                .noOfCoinsRemoved(1).build();
        // when
        final StrikeResult strikeResult = strikerUnderTest.hitStrike();

        // then:
        assertEquals(Striker.StrikeMe.RED_STRIKE, strikerUnderTest.getStrike());
        assertEquals(expectedStrikeResult, strikeResult);
    }

    @Test
    void testGetStrikeByOption() {
        // given
        final int option = 3;
        final Striker.StrikeMe expectedStrike = Striker.StrikeMe.RED_STRIKE;

        // when
        Striker.StrikeMe strikeByOption = Striker.getStrikeByOption(option);

        // then
        assertEquals(expectedStrike, strikeByOption);
    }

    @Test
    void testThrowsExceptionWhenInvalidStrikeOptionIsPassed() {
        // given
        final int option = 7;
        final String expectedMessage = String.format("strike with option:%d is not valid", option);

        // when
        final InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, () -> {
            Striker.getStrikeByOption(option);
        });

        // then
        assertNotNull(invalidInputException);
        assertEquals(expectedMessage, invalidInputException.getMessage());
    }


    @Test
    void testGetStrikeByName() {
        // given
        final String name = "Strike";
        final Striker.StrikeMe expectedStrike = Striker.StrikeMe.SINGLE_STRIKE;

        // when
        Striker.StrikeMe strikeByName = Striker.getStrikeByName(name);

        // then
        assertEquals(expectedStrike, strikeByName);
    }

    @Test
    void testThrowsExceptionWhenInvalidStrikeNameIsPassed() {
        // given
        final String name = "myStrike";
        final String expectedMessage = String.format("strike with name:%s is not valid", name);

        // when
        final InvalidInputException invalidInputException = assertThrows(InvalidInputException.class, () -> {
            Striker.getStrikeByName(name);
        });

        // then
        assertNotNull(invalidInputException);
        assertEquals(expectedMessage, invalidInputException.getMessage());
    }

}
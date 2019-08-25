package com.hodorgeek.carrom.game;

import com.hodorgeek.carrom.piece.Striker;
import com.hodorgeek.carrom.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class CleanStrikeCarromGameTest {

    private CleanStrikeCarromGame cleanStrikeCarromGame;
    private Player playerOne;
    private Player playerTwo;

    @BeforeEach
    void init() {
        cleanStrikeCarromGame = new CleanStrikeCarromGame();
        playerOne = cleanStrikeCarromGame.getPlayerOne();
        playerTwo = cleanStrikeCarromGame.getPlayerTwo();
    }

    private static Stream<Object[]> dataForWinnerSituation() {
        return Stream.of(
                new Object[][] {
                        {5, 0, "More than 5 point difference and one player is >= 5 and another player point is zero, player one should winner"},
                        {5, 2,  "More than 3 points difference and one player >= 5 and another player point is greater that zero, player one should winner"},
                        {6, 9,  "3 point difference and both players >=5, winning situation, player two should be winner"},
                });
    }

    @DisplayName("Test Winning situation - match result should be come either one plyer would win")
    @ParameterizedTest(name = "{2}")
    @MethodSource("dataForWinnerSituation")
    void testWinnerIsDeclaredCorrect(final int playerOnePoint, final int playerTwoPoint, final String description) {
        // given
        playerOne.setPoint(playerOnePoint);
        playerTwo.setPoint(playerTwoPoint);

        // when and then
        assertTrue(cleanStrikeCarromGame.isThereWinner(playerOne.getPoint(), playerTwo.getPoint()));
    }

    private static Stream<Object[]> dataForDrawSituation() {
        return Stream.of(
                new Object[][] {
                        {0, 3, "Three point difference alone isn't enough to declare winner, the player also needs >= 5 points"},
                        {3, 2,  "At least 3 point difference, the player score's difference should also needs >= 3"},
                        {7, 5,  "Both >=5, but no 3 point difference"},
                });
    }

    @DisplayName("Test Draw situation - match result should be draw and no one will declared winner ")
    @ParameterizedTest(name = "{2}")
    @MethodSource("dataForDrawSituation")
    void testMatchIsDeclaredCorrect_WhenMatchIsDrawn(final int playerOnePoint, final int playerTwoPoint, final String description) {
        // given
        playerOne.setPoint(playerOnePoint);
        playerTwo.setPoint(playerTwoPoint);

        // when & then
        assertFalse(cleanStrikeCarromGame.isThereWinner(playerOne.getPoint(), playerTwo.getPoint()));
    }

    @Test
    void testIsGameOverWhenAllCoinsOnBoardAreFinished() {
        // given
        for (int i = 1; i <= 9; i++) {
            cleanStrikeCarromGame.playGame(Striker.StrikeMe.SINGLE_STRIKE);
        }
        cleanStrikeCarromGame.exchangeAndGetAnotherPlayerOnStrike();
        cleanStrikeCarromGame.playGame(Striker.StrikeMe.RED_STRIKE);

        // when & then
        assertTrue(cleanStrikeCarromGame.getBoard().isEmpty(), "all coins and queens are pocketed");
    }

}
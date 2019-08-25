package com.hodorgeek.carrom;

import com.hodorgeek.carrom.exception.StrikeNotPossibleException;
import com.hodorgeek.carrom.game.CarromGame;
import com.hodorgeek.carrom.game.CleanStrikeCarromGame;
import com.hodorgeek.carrom.helper.InputValidator;
import com.hodorgeek.carrom.piece.Striker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class CleanStrikeGameSimulationTest {

    // define pair of input's
    private static List<String> PLAYER_ONE_TURNS = new ArrayList<>();
    private static List<String> PLAYER_TWO_TURNS = new ArrayList<>();

    private CarromGame carromGame;

    @BeforeEach
    void init() {
        carromGame = new CleanStrikeCarromGame();
    }

    @Test
    @DisplayName("The game has no result since the player's score difference is not at least 3")
    void simulateGameHasDrawnAndNoPlayerIsTheWinner_SinceDiffOfScoreIsNotAtLeastThreeTest() {
        PLAYER_ONE_TURNS =  Arrays.asList("Strike", "Red_Strike",
                "None", "None", "None", "Strike", "Strike", "None", "None");
        PLAYER_TWO_TURNS = Arrays.asList("Multi_Strike", "Multi_Strike",
                "None", "None", "None", "Strike", "Striker_Strike", "Defunct_Coin", "Striker_Strike");
        for (int i = 0; i < PLAYER_ONE_TURNS.size() && !carromGame.isGameOver(); i++) {
            Striker.StrikeMe inputOne = Striker.getStrikeByName(PLAYER_ONE_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputOne, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this time by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputOne.getName()));
            }
            carromGame.playGame(inputOne);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();

            Striker.StrikeMe inputTwo = Striker.getStrikeByName(PLAYER_TWO_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputTwo, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this stage of game by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputTwo.getName()));
            }
            carromGame.playGame(inputTwo);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();
        }
        carromGame.declareResult();
        assertTrue(carromGame.isGameOver());
        assertTrue(carromGame.getBoard().isEmpty());
        assertFalse(carromGame.hasResult());
    }

    @Test
    @DisplayName("The game has no result since the no one players score is at least five")
    void simulateGameHasDrawnAndNoPlayerIsTheWinner_SinceScoreIsNotInTotalFiveTest() {
        PLAYER_ONE_TURNS =  Arrays.asList("Strike",
                "None", "None", "None", "Red_Strike", "Strike", "None", "Defunct_Coin");
        PLAYER_TWO_TURNS = Arrays.asList("Multi_Strike",
                "None", "None", "None", "Strike", "Striker_Strike", "Defunct_Coin", "Striker_Strike");
        for (int i = 0; i < PLAYER_ONE_TURNS.size() && !carromGame.isGameOver(); i++) {
            Striker.StrikeMe inputOne = Striker.getStrikeByName(PLAYER_ONE_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputOne, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this stage of game by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputOne.getName()));
            }
            carromGame.playGame(inputOne);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();

            Striker.StrikeMe inputTwo = Striker.getStrikeByName(PLAYER_TWO_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputTwo, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this time by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputTwo.getName()));
            }
            carromGame.playGame(inputTwo);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();
        }
        carromGame.declareResult();
        assertTrue(carromGame.isGameOver());
        assertTrue(carromGame.getBoard().isEmpty());
        assertFalse(carromGame.hasResult());
    }

    @Test
    @DisplayName("The game has result and player one is the winner")
    void simulateGameHasResultAndPlayerOneIsTheWinner_SinceScoreIsNotInTotalFiveTest() {
        PLAYER_ONE_TURNS =  Arrays.asList("Multi_Strike",
                "None", "None", "None", "Red_Strike", "Strike", "None", "Defunct_Coin");
        PLAYER_TWO_TURNS = Arrays.asList("Multi_Strike",
                "None", "None", "None", "Strike", "Striker_Strike", "Defunct_Coin", "Striker_Strike");
        for (int i = 0; i < PLAYER_ONE_TURNS.size() && !carromGame.isGameOver(); i++) {
            Striker.StrikeMe inputOne = Striker.getStrikeByName(PLAYER_ONE_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputOne, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this time by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputOne.getName()));
            }
            carromGame.playGame(inputOne);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();

            Striker.StrikeMe inputTwo = Striker.getStrikeByName(PLAYER_TWO_TURNS.get(i));
            if(!InputValidator.isStrikePossible(inputTwo, carromGame.getBoard())) {
                throw new StrikeNotPossibleException("Invalid strike at this time by - ".concat(carromGame.getPlayerOnStrike().getName()).concat(" with strike ").concat(inputTwo.getName()));
            }
            carromGame.playGame(inputTwo);
            carromGame.exchangeAndGetAnotherPlayerOnStrike();
        }
        carromGame.declareResult();
        assertTrue(carromGame.isGameOver());
        assertTrue(carromGame.hasResult());
    }
}
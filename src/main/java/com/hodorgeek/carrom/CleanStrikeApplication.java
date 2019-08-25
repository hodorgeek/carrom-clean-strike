package com.hodorgeek.carrom;

import com.hodorgeek.carrom.board.Board;
import com.hodorgeek.carrom.exception.InvalidInputException;
import com.hodorgeek.carrom.game.CarromGame;
import com.hodorgeek.carrom.game.CleanStrikeCarromGame;
import com.hodorgeek.carrom.helper.InputValidator;
import com.hodorgeek.carrom.piece.Striker;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

import static com.hodorgeek.carrom.piece.Striker.StrikeMe.*;

@Slf4j
public class CleanStrikeApplication {
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        CarromGame game = new CleanStrikeCarromGame();
        System.out.println("Beginning Game - Player 1 on Strike \n\n");
        while (!game.isGameOver()) {
            Striker.StrikeMe strikeMe = readAndValidateInput(game.getBoard());
            System.out.println(strikeMe);
            game.playGame(strikeMe);
            game.exchangeAndGetAnotherPlayerOnStrike();
        }
        game.declareResult();
    }


    public static Striker.StrikeMe readAndValidateInput(Board board) {
        Striker.StrikeMe inputStrike = null;
        for (Striker.StrikeMe strike: Striker.StrikeMe.values()) {
            System.out.println(strike.getOption() + ". "+strike.getName());
        }
        int option;
        boolean isInValidSelection, isStrikeNotPossible;
        do {
            isInValidSelection  = Boolean.FALSE;
            isStrikeNotPossible = Boolean.FALSE;
            System.out.println("Select an option from the Menu:");
            while (!scanner.hasNextInt()) {
                System.out.println("Enter a valid option that is in the MENU");
                scanner.next();
            }
            option = scanner.nextInt();
            try {
                inputStrike = Striker.getStrikeByOption(option);
            } catch (InvalidInputException e) {
                System.out.println("Not a valid option : "+e.getMessage());
                isInValidSelection = Boolean.TRUE;
            }
            isStrikeNotPossible = InputValidator.isStrikePossible(inputStrike, board);
        } while (isInValidSelection && isStrikeNotPossible);
        log.debug("Hitting {} for scoring {} points", inputStrike.getName(), inputStrike.getResult().getPointScored()) ;
        return inputStrike;
    }
}

package com.hodorgeek.carrom;

import com.hodorgeek.carrom.board.Board;
import com.hodorgeek.carrom.exception.InvalidInputException;
import com.hodorgeek.carrom.game.CarromGame;
import com.hodorgeek.carrom.game.CleanStrikeCarromGame;
import com.hodorgeek.carrom.helper.InputValidator;
import com.hodorgeek.carrom.piece.Striker;
import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

@Slf4j
public class CleanStrikeApplication {

    private static final BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args) throws IOException {
        CarromGame game = new CleanStrikeCarromGame();
        System.out.println("Beginning Game - Player One is on Strike \n");
        while (!game.isGameOver()) {
            Striker.StrikeMe strikeMe = readAndValidateInput(game.getBoard());
            game.playGame(strikeMe);
            game.exchangeAndGetAnotherPlayerOnStrike();
        }
        game.declareResult();
        br.close();
    }


    public static Striker.StrikeMe readAndValidateInput(Board board) throws IOException {
        Striker.StrikeMe inputStrike = null;
        int option = -1;
        boolean isValidChoice, isStrikePossible;
        do {
            isValidChoice  = Boolean.TRUE;
            isStrikePossible = Boolean.TRUE;
            printMenu();
            System.out.print("Select an option from the Menu:");
            option = Integer.parseInt(br.readLine());
            try {
                inputStrike = Striker.getStrikeByOption(option);
            } catch (InvalidInputException e) {
                System.out.println("Invalid option : "+e.getMessage());
                isValidChoice = Boolean.FALSE;
            }
            if(isValidChoice) {
                isStrikePossible = InputValidator.isStrikePossible(inputStrike, board);
                if(!isStrikePossible) {
                    System.out.print(inputStrike.getName() +" is not possible at this stage, ");
                    System.out.println("Enter other option from Menu");
                }
            } else {
                System.out.println("Enter a valid option that is in the MENU");
            }
        } while (!isValidChoice || !isStrikePossible);
        log.debug("Hitting {} for scoring {} points", inputStrike.getName(), inputStrike.getResult().getPointScored()) ;
        return inputStrike;
    }

    public static void printMenu() {
        for (Striker.StrikeMe strike: Striker.StrikeMe.values()) {
            System.out.println(strike.getOption() + ". "+strike.getName());
        }
    }
}

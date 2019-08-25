package com.hodorgeek.carrom.game;

import com.hodorgeek.carrom.board.Board;
import com.hodorgeek.carrom.board.CleanStrikeCarromBoard;
import com.hodorgeek.carrom.piece.Strike;
import com.hodorgeek.carrom.piece.StrikeResult;
import com.hodorgeek.carrom.player.Player;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Setter
@Slf4j
public class CleanStrikeCarromGame implements CarromGame {

    private Player playerOne;

    private Player playerTwo;

    private Player playerOnStrike;

    private Board board;

    public CleanStrikeCarromGame() {
        board = new CleanStrikeCarromBoard(9);
        playerOne = new Player("PlayerOne");
        playerTwo = new Player("PlayerTwo");
        playerOne.allocateStriker(board.getStriker());
        playerOnStrike = playerOne;
    }

    @Override
    public void exchangeAndGetAnotherPlayerOnStrike() {
        if (playerOnStrike.hasStriker() == playerOne.hasStriker()) {
            playerTwo.allocateStriker(board.getStriker());
            playerOnStrike = playerTwo;
            playerOne.resetStriker();
        } else {
            playerOne.allocateStriker(board.getStriker());
            playerOnStrike = playerOne;
            playerTwo.resetStriker();
        }
        System.out.println("------------------------------------------------------------");
        System.out.println("Strike Swapped to - " + playerOnStrike.getName());
        System.out.println();
    }


    @Override
    public void playGame(Strike strike) {
        StrikeResult strikeResult = playerOnStrike.play(strike);
        System.out.printf("%s has played %s%n", playerOnStrike.getName(), strike);
        board.updateBoard(strikeResult);
        playerOnStrike.updatePlayer(strikeResult);
        System.out.printf("There are %d pieces on board [coins=%d , queen:%d]%n", board.size(), board.getCoins().size(), board.containsQueen() ? 1 :0);
    }

    @Override
    public void declareResult() {
        if (isThereWinner(playerOne.getPoint(), playerTwo.getPoint())) {
            if (playerOne.getPoint() > playerTwo.getPoint()) {
                System.out.println("Player 1 wins, ["+playerOne.getPoint() + " <--> " + playerTwo.getPoint() + "]");
            } else {
                System.out.println("Player 2 wins, ["+playerTwo.getPoint() + " <--> " + playerOne.getPoint() + "]");
            }
        } else {
            System.out.println("Game is Drawn, [Player 1 Points: " + playerOne.getPoint() + ", Player 2 Points: " + playerTwo.getPoint() + "]");
        }
        log.info("Player one strikes played:{}", playerOne.getStrikes());
        log.info("Player two strikes played:{}", playerTwo.getStrikes());
    }

    @Override
    public boolean isGameOver() {
        return isThereWinner(playerOne.getPoint(), playerTwo.getPoint()) || board.isEmpty();
    }

    public boolean isThereWinner(int playerOnePoint, int playerTwoPoint) {
        return isWinnerScoredAtLestFivePoints(playerOnePoint, playerTwoPoint)
                && isWinnerLeadingByThreePoints(playerOnePoint, playerTwoPoint);
    }

    @Override
    public boolean hasResult() {
        return isWinnerScoredAtLestFivePoints(playerOne.getPoint(),playerTwo.getPoint())
                && isWinnerLeadingByThreePoints(playerOne.getPoint(), playerTwo.getPoint());
    }

    private boolean isWinnerScoredAtLestFivePoints(int firstPoint, int secondPoint) {
        return firstPoint >= 5 || secondPoint >= 5;
    }

    private boolean isWinnerLeadingByThreePoints(int firstPoint, int secondPoint) {
        final int pointDiff = Math.abs(firstPoint - secondPoint);
        return pointDiff >= 3;
    }


    public Player getPlayerOnStrike() {
        return playerOnStrike;
    }

    @Override
    public void setPlayerOnStrike(Player playerOnStrike) {
        this.playerOnStrike = playerOnStrike;
    }

    @Override
    public Board getBoard() {
        return board;
    }
}

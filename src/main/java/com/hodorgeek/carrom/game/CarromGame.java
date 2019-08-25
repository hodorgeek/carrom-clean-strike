package com.hodorgeek.carrom.game;

import com.hodorgeek.carrom.board.Board;
import com.hodorgeek.carrom.piece.Strike;
import com.hodorgeek.carrom.player.Player;

public interface CarromGame {

    void playGame(Strike strike);

    void exchangeAndGetAnotherPlayerOnStrike();

    boolean isGameOver();

    boolean hasResult();

    void declareResult();

    Board getBoard();

    Player getPlayerOnStrike();
}

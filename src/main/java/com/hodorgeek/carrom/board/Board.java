package com.hodorgeek.carrom.board;

import com.hodorgeek.carrom.piece.*;

import java.util.ArrayList;
import java.util.List;

public abstract class Board {

    protected List<Coin> coins;

    protected Queen queen;

    protected Striker striker;

    public Board(int coinCount) {
        coins = new ArrayList<>(coinCount);
    }

    public void addCoin(int coin, PieceColor pieceColor, int faceValue) {
        for (int i = 0; i < coin; i++) {
            Coin piece = new Coin(pieceColor, faceValue);
            coins.add(piece);
        }
    }

    public void removeCoin(PieceColor pieceColor, int noOfCoinsToRemoved) {
        for (int i = 0; i < noOfCoinsToRemoved && !isEmpty(); i++) {
            int coinToBeRemoved = (int) (this.coins.size() * Math.random());
            Coin coin = coins.get(coinToBeRemoved);
            if(pieceColor == coin.getPieceColor()) {
                coins.remove(coinToBeRemoved);
            }
        }
    }

    public boolean containsQueen() {
        return queen != null;
    }

    public int size() {
        int queenCount = this.containsQueen() ? 1 : 0;
        return this.coins.size() + queenCount;
    }

    public boolean isEmpty() {
        return this.size() == 0 && !this.containsQueen();
    }

    public Striker getStriker() {
        return striker;
    }

    public void setStriker(Striker striker) {
        this.striker = striker;
    }

    public Queen getQueen() {
        return queen;
    }

    public void setQueen(Queen queen) {
        this.queen = queen;
    }

    public List<Coin> getCoins() {
        return coins;
    }

    public abstract void updateBoard(StrikeResult strikeResult);

}

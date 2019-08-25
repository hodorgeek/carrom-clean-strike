package com.hodorgeek.carrom.player;

import com.hodorgeek.carrom.piece.Strike;
import com.hodorgeek.carrom.piece.StrikeResult;
import com.hodorgeek.carrom.piece.Striker;

import java.util.ArrayList;
import java.util.List;

public class Player {

    private String name;

    private int point;

    private int foulCount;

    private List<Strike> strikes;

    private Striker striker;

    public Striker getStriker() {
        return striker;
    }

    public void allocateStriker(Striker striker) {
        this.striker = striker;
    }

    public void resetStriker() {
        this.striker = null;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Strike> getStrikes() {
        return strikes;
    }

    public void setStrikes(List<Strike> strikes) {
        this.strikes = strikes;
    }

    public boolean hasStriker() {
        return this.striker != null;
    }

    public Player(String name) {
        this.name = name;
        this.strikes = new ArrayList<>();
    }

    public void updatePlayer(StrikeResult strikeResult) {
        this.addStrike(strikeResult.getStrike());
        this.updatePoints(strikeResult.getPointScored());
        this.updateFoulCountIfAny(strikeResult.isFoulsStrike());
    }

    private void updatePoints(int pointForLastStrike) {
        this.point = this.point + pointForLastStrike + this.reducedPoint();
        System.out.printf("Points for %s is %d %n", this.name, this.getPoint());
    }

    private int reducedPoint() {
        int minusPoint = 0;
        if (isThereNoScoringInLastThreeSuccessiveStrikes())
            minusPoint--;
        if (areLastThreeStrikesFouls())
            minusPoint--;
        return minusPoint;
    }

    private void updateFoulCountIfAny(boolean isFoulStrike) {
        if (isFoulStrike) {
            this.foulCount++;
        }
    }

    private boolean areLastThreeStrikesFouls() {
        if (this.getFoulCount()!=0 && this.getFoulCount() % 3 == 0) {
            System.out.println("All three last strikes are foul, additional penalty of -1 (Total of -2)");
            return true;
        }
        return false;
    }

    public boolean isThereNoScoringInLastThreeSuccessiveStrikes() {
        if (this.strikes.size() < 3)
            return false;

        final int lastThreeCount = this.strikes.size() - 3;
        int count = 0;
        for (int i = lastThreeCount; i <= this.strikes.size() - 1; i++) {
            if (this.strikes.get(i).equals(Striker.StrikeMe.NONE)) {
                count++;
            }
        }
        if (count == 3) {
            System.out.println("No point in the last three strikes, so (-1) extra point as charge");
            return true;
        }
        return false;
    }

    public int getPoint() {
        return point;
    }

    public void setPoint(int point) {
        this.point = point;
    }

    public void setFoulCount(int foulCount) {
        this.foulCount = foulCount;
    }

    public int getFoulCount() {
        return foulCount;
    }

    @Override
    public String toString() {
        return new StringBuilder("Player [name=")
                .append(point)
                .append(", point=")
                .append(point).toString();
    }

    public void addStrike(Strike strike) {
        this.strikes.add(strike);
    }

    public StrikeResult play(Strike strike) {
        striker.setStrike(strike);
        return striker.hitStrike();
    }
}

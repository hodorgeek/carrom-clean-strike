package com.hodorgeek.carrom.helper;

import com.hodorgeek.carrom.board.Board;
import com.hodorgeek.carrom.piece.Coin;
import com.hodorgeek.carrom.piece.PieceColor;
import com.hodorgeek.carrom.piece.Striker.StrikeMe;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class InputValidator {
	public static boolean isStrikePossible(StrikeMe strike, Board board) {
		boolean isValid = Boolean.FALSE;
		switch (strike) {
			case RED_STRIKE:
				isValid = board.containsQueen();
				break;
			case SINGLE_STRIKE:
				isValid = boardContainsAtLeastOneBlackCoin(board.getCoins());
				break;
			case MULTI_STRIKE:
				isValid = boardContainsMultipleBlackCoins(board.getCoins());
				break;
			case STRIKER_STRIKE:
			case DEFUNCT_COIN:
			case NONE:
				isValid = Boolean.TRUE;
				break;
		}
		return isValid;
	}

	private static boolean boardContainsMultipleBlackCoins(List<Coin> coinsOnBoard) {
		return coinsOnBoard
				.stream()
				.filter(coin -> coin.getPieceColor() == PieceColor.BLACK)
				.count() > 1;
	}

	private static boolean boardContainsAtLeastOneBlackCoin(List<Coin> coinsOnBoard) {
		return coinsOnBoard
				.stream()
				.filter(coin -> coin.getPieceColor() == PieceColor.BLACK)
				.count() > 0;
	}
}

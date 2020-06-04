package engine.parser.commands;

import engine.parser.Command;
import engine.Game;
import engine.chess.Point;
import engine.chess.Result;
import engine.chess.Table;

public class Move implements Command {

	private final String moveInput;

	public Move(String moveInput) {
		this.moveInput = moveInput;
	}

	@Override
	public void accept() {
		Game game = Game.getInstance();
		Table table = game.getState();

		/*
		 * There are 3 move cases:
		 * 1. Engine wants to send a move command;
		 * 2. Xboard sent a 'move' command;
		 * 3. Xboard sent a 'move now' command.
		 */
		if (moveInput.contains("move") && !game.isInForceMode()) {
			System.out.println(moveInput);
		} else if (isValid(moveInput)) {

			table.movePiece(getPrevious(), getNext());
			Result move = game.minimax(table, game.getPlaySide(), game.DEPTH,
					-game.INF, game.INF);
			Point from = move.getFrom();
			Point to = move.getTo();
			table.movePiece(from, to);

			Game.getInstance().send(new Move("move " + from + "" + to));
		} else if (moveInput.equals("?")) {
			table.randomMove();
		}
	}

	/**
	 * Check if the given string represents a valid move command.
	 *
	 * @param input a given move command.
	 * @return true if the move is valid, else false.
	 */
	private boolean isValid(String input) {

		if (input.length() > 1) {
			char c = input.charAt(1);
			return c >= '1' && c <= '8';
		}

		return false;
	}

	/**
	 * Get the previous position based on the received move.
	 *
	 * @return the previous position.
	 */
	private Point getPrevious() {
		return new Point(moveInput.charAt(1), moveInput.charAt(0));
	}

	/**
	 * Get the next position based on the received move.
	 *
	 * @return the next position.
	 */
	private Point getNext() {
		return new Point(moveInput.charAt(3), moveInput.charAt(2));
	}
}

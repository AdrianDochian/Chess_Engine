package engine.parser.commands;

import engine.Game;
import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Result;
import engine.chess.Table;
import engine.logger.Logger;
import engine.parser.Command;

public class Go implements Command {

	@Override
	public void accept() {
		Logger.log("Leaving force mode.");
		Game.getInstance().setForceMode(false);

		// Make first move
		if (Game.getInstance().getPlaySide() == Color.WHITE) {
			Game game = Game.getInstance();
			Table table = game.getState();

			Result move = game.minimax(table, game.getPlaySide(), game.DEPTH,
					-game.INF, game.INF);
			Point from = move.getFrom();
			Point to = move.getTo();
			table.movePiece(from, to);

			Game.getInstance().send(new Move("move " + from + "" + to));
		}
	}

}

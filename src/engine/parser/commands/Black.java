package engine.parser.commands;

import engine.Game;
import engine.chess.Color;
import engine.logger.Logger;
import engine.parser.Command;

public class Black implements Command {

	@Override
	public void accept() {
		Logger.log("Playing with black now.");
		Game.getInstance().setPlaySide(Color.BLACK);
	}

}

package engine.parser.commands;

import engine.Game;
import engine.chess.Color;
import engine.logger.Logger;
import engine.parser.Command;

public class White implements Command {

	@Override
	public void accept() {
		Logger.log("Playing with white now.");
		Game.getInstance().setPlaySide(Color.WHITE);
	}

}

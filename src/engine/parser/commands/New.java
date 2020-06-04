package engine.parser.commands;

import engine.Game;
import engine.logger.Logger;
import engine.parser.Command;

public class New implements Command {

	@Override
	public void accept() {
		Logger.log("Starting a new game.");
		Game.getInstance().reset();
	}

}

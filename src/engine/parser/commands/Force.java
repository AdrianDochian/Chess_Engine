package engine.parser.commands;

import engine.Game;
import engine.logger.Logger;
import engine.parser.Command;

public class Force implements Command {

	@Override
	public void accept() {
		Logger.log("Enabling force mode.");
		Game.getInstance().setForceMode(true);
	}

}

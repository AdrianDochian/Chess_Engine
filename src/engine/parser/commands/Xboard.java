package engine.parser.commands;

import engine.logger.Logger;
import engine.parser.Command;

public class Xboard implements Command {

	@Override
	public void accept() {
		Logger.log("Engine has successfully been started.");
	}

}

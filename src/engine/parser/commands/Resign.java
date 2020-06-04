package engine.parser.commands;

import engine.logger.Logger;
import engine.parser.Command;

public class Resign implements Command {

	@Override
	public void accept() {
		Logger.log("Sent resign request.");
		System.out.println("resign");
	}

}

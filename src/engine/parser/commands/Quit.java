package engine.parser.commands;

import engine.parser.Command;

public class Quit implements Command {

	@Override
	public void accept() {
		System.exit(0);
	}
}

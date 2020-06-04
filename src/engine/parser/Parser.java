package engine.parser;

import engine.parser.commands.*;

/**
 * This singleton class is used to parse strings sent by xboard and translate
 * them into valid commands that this engine can understand.
 *
 */
public final class Parser {

	private static Parser instance;

	private Parser() {
	}

	public static Parser getInstance() {
		if (instance == null) {
			instance = new Parser();
		}

		return instance;
	}

	/**
	 * Parse a string into a valid engine command.
	 *
	 * @param input a string sent by Xboard.
	 * @return a valid command.
	 */
	public Command parse(String input) {
		Command cmd;

		switch (input) {
			case "xboard":
				cmd = new Xboard();
				break;

			case "quit":
				cmd = new Quit();
				break;

			case "new":
				cmd = new New();
				break;

			case "force":
				cmd = new Force();
				break;

			case "go":
				cmd = new Go();
				break;

			case "resign":
				cmd = new Resign();
				break;

			case "protover 2":
				cmd = new Protover();
				break;

			case "white":
				cmd = new White();
				break;

			case "black":
				cmd = new Black();
				break;

			default:
				cmd = new Move(input);
				break;
		}

		return cmd;
	}
}

package engine;

import engine.parser.Command;

/**
 * This is the starting point of all execution.
 * <p>
 * Communication protocol:
 * Xboard sends commands to System.in
 * the Engine sends commands to System.out
 * </p>
 *
 */
public final class Main {

	/**
	 * Main game loop.
	 *
	 * @param args
	 */
	public static void main(String[] args) {
		Game game = Game.getInstance();

		// Receive and execute commands
		while (game.update() >= 0) {
			Command cmd = game.recv();

			if (cmd != null) {
				cmd.accept();
			}
		}
	}
}

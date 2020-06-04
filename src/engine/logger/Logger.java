package engine.logger;

import engine.Game;
import engine.parser.Command;

/**
 * This class is used to output messages while the -debug parameter is set.
 * <p>
 * XBoard will create a 'xboard.debug' file in the current directory. This file
 * contains all actions/messages sent or performed by this engine and Xboard.
 * </p>
 *
 */
public final class Logger {

	/**
	 * Log the given message.
	 *
	 * @param message to be logged.
	 */
	public static void log(String message) {
		Game.getInstance().send(new Command() {
			@Override
			public void accept() {
				System.out.println("#[LOGGER] " + message);
			}
		});
	}
}

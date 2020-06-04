package engine;

import engine.chess.Color;
import static engine.chess.Color.BLACK;
import static engine.chess.Color.WHITE;
import engine.chess.Point;
import engine.chess.Result;
import engine.chess.Table;
import engine.chess.pieces.Piece;
import engine.parser.Command;
import engine.parser.Parser;
import engine.parser.commands.Go;
import engine.parser.commands.Quit;
import java.util.LinkedList;
import java.util.Scanner;

public final class Game {

	private static Game instance;
	public final int DEPTH = 4;
	public final int INF = 123456789;

	private final Scanner xboard;
	private final LinkedList<Command> cmds;
	private boolean forceMode = false;

	private Table state;
	private Color playSide;

	private Game() {
		xboard = new Scanner(System.in);
		cmds = new LinkedList<>();
	}

	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}

		return instance;
	}

	public boolean isInForceMode() {
		return forceMode;
	}

	public void setForceMode(boolean forceMode) {
		this.forceMode = forceMode;
	}

	public Table getState() {
		return state;
	}

	public Color getPlaySide() {
		return playSide;
	}

	public void setPlaySide(Color side) {
		playSide = side;
	}

	/**
	 * Record xboard commands.
	 * <p>
	 * If xboard has sent a new command, read it and add it to the commands
	 * queue.
	 * </p>
	 *
	 * @return the current commands queue size.
	 */
	public int update() {

		if (xboard.hasNextLine()) {
			String input = xboard.nextLine();
			Command cmd = Parser.getInstance().parse(input);

			if (cmd != null) {
				cmds.add(cmd);
			}
		}

		return cmds.size();
	}

	/**
	 * Reset game state.
	 * <p>
	 * Clear commands queue and reset the chess state.
	 * </p>
	 */
	public void reset() {
		cmds.clear();

		// Reset chess state and side
		state = new Table();
		setPlaySide(Color.BLACK);

		// We are ready to play again
		send(new Go());
	}

	/**
	 * Receive the last command sent from xboard.
	 *
	 * @return the received command.
	 */
	public Command recv() {
		return cmds.poll();
	}

	/**
	 * Send a command to xboard.
	 *
	 * @param cmd the command to send.
	 */
	public void send(Command cmd) {
		cmd.accept();
	}

	/**
	 * Exit game.
	 */
	public void quit() {
		xboard.close();
		send(new Quit());
	}

	public Result minimax(Table state, Color side, int depth, int alfa, int beta) {

		Color opSide = side == WHITE ? BLACK : WHITE;

		if (state.ended(side) || state.ended(opSide) || depth == 0) {
			return new Result(null, null, state.eval(side));
		}

		Result res = new Result(null, null, -INF);

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point from = new Point(line, col);
				Piece piece = state.getPiece(from);

				if (piece == null || piece.getSide() != side) {
					continue;
				}

				LinkedList<Point> moves = piece.getNextMoves(state);

				for (Point to : moves) {
					Table nextState = (Table) state.clone();

					if (nextState.applyMove(side, from, to)) {
						Result tmp = minimax(nextState, opSide, depth - 1,
								-beta, -alfa);

						if (-tmp.getScore() >= alfa) {
							alfa = -tmp.getScore();
							res = new Result(from, to, alfa);
						}

						if (alfa >= beta) {
							break;
						}
					}
				}
			}
		}

		return res;
	}
}

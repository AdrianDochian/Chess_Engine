package engine.chess.pieces;

import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public abstract class Piece {

	protected int val;
	protected Color side;
	protected Point pos;

	public Piece(int val, Color side, Point pos) {
		this.val = val;
		this.side = side;
		this.pos = pos;
	}

	public int getVal() {
		return val;
	}

	public Color getSide() {
		return side;
	}

	public Point getPos() {
		return pos;
	}

	public void setPos(Point pos) {
		this.pos = pos;
	}

	/**
	 * Compute next possible moves for this piece.
	 *
	 * @param state of game.
	 * @return a list of possible moves.
	 */
	public abstract LinkedList<Point> getNextMoves(Table state);

	public boolean isMoveValid(Table state, Point to) {
		if (to.isValid()) {
			Piece enemy = state.getPiece(to);

			if (enemy == null || enemy.side != side) {
				return true;
			}
		}

		return false;
	}

	public LinkedList<Point> goRight(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		for (int col = pos.getColumn() + 1; col <= 8; col++) {
			Point to = new Point(pos.getLine(), col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}
		}

		return moves;
	}

	public LinkedList<Point> goLeft(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		for (int col = pos.getColumn() - 1; col >= 1; col--) {
			Point to = new Point(pos.getLine(), col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}
		}

		return moves;
	}

	public LinkedList<Point> goUp(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		for (int line = pos.getLine() + 1; line <= 8; line++) {
			Point to = new Point(line, pos.getColumn());
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}
		}

		return moves;
	}

	public LinkedList<Point> goDown(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		for (int line = pos.getLine() - 1; line >= 1; line--) {
			Point to = new Point(line, pos.getColumn());
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}
		}

		return moves;
	}

	public LinkedList<Point> goUpLeft(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		int line = pos.getLine() + 1;
		int col = pos.getColumn() - 1;

		while (line <= 8 && col >= 1) {
			Point to = new Point(line, col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}

			line++;
			col--;
		}

		return moves;
	}

	public LinkedList<Point> goUpRight(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		int line = pos.getLine() + 1;
		int col = pos.getColumn() + 1;

		while (line <= 8 && col <= 8) {
			Point to = new Point(line, col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}

			line++;
			col++;
		}

		return moves;
	}

	public LinkedList<Point> goDownRight(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		int line = pos.getLine() - 1;
		int col = pos.getColumn() + 1;

		while (line >= 1 && col <= 8) {
			Point to = new Point(line, col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}

			line--;
			col++;
		}

		return moves;
	}

	public LinkedList<Point> goDownLeft(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		int line = pos.getLine() - 1;
		int col = pos.getColumn() - 1;

		while (line >= 1 && col >= 1) {
			Point to = new Point(line, col);
			Piece encounter = state.getPiece(to);

			// Check if the piece has a clear path:
			if (encounter == null) {
				moves.add(to);
			} // If the piece encounters another piece:
			else {
				// Capture it.
				if (encounter.side != side) {
					moves.add(to);
				}

				// Done.
				break;
			}

			line--;
			col--;
		}

		return moves;
	}

	@Override
	public String toString() {
		return getClass().getName();
	}

	@Override
	public Object clone() {
		if (this instanceof Bishop) {
			return new Bishop(side, pos);
		}
		if (this instanceof King) {
			return new King(side, pos);
		}
		if (this instanceof Knight) {
			return new Knight(side, pos);
		}
		if (this instanceof Pawn) {
			return new Pawn(side, pos);
		}
		if (this instanceof Queen) {
			return new Queen(side, pos);
		}
		if (this instanceof Rook) {
			return new Rook(side, pos);
		}

		return null;
	}

}

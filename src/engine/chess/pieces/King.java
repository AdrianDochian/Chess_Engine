package engine.chess.pieces;

import engine.chess.Color;
import static engine.chess.Color.WHITE;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class King extends Piece {

	private final Point start;
	private boolean moved;

	public King(Color side, Point pos) {
		super(10000, side, pos);

		if (side == WHITE) {
			start = new Point(1, 5);
		} else {
			start = new Point(8, 5);
		}
	}

	public Point getStart() {
		return start;
	}

	@Override
	public LinkedList<Point> getNextMoves(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		// Castle
		Point leftRookStart;
		Point rightRookStart;

		if (side == WHITE) {
			leftRookStart = new Point(1, 1);
			rightRookStart = new Point(1, 8);
		} else {
			leftRookStart = new Point(8, 1);
			rightRookStart = new Point(8, 8);
		}

		if (pos.equals(start) && moved == false) {
			// Small castle
			Piece rightRook = state.getPiece(rightRookStart);

			if (rightRook != null && rightRook instanceof Rook
					&& ((Rook) rightRook).hasMoved() == false) {
				if (state.getPiece(rightRookStart.minus(0, 1)) == null
						&& state.getPiece(rightRookStart.minus(0, 2)) == null) {

					// Check for mate
					Table nextState = (Table) state.clone();
					if (nextState.applyMove(side, pos, start.plus(0, 2))) {
						moves.add(start.plus(0, 2));
					}
				}
			}

			// Big castle
			Piece leftRook = state.getPiece(leftRookStart);

			if (leftRook != null && leftRook instanceof Rook
					&& ((Rook) leftRook).hasMoved() == false) {
				if (state.getPiece(leftRookStart.plus(0, 1)) == null
						&& state.getPiece(leftRookStart.plus(0, 2)) == null
						&& state.getPiece(leftRookStart.plus(0, 3)) == null) {

					// Check for mate
					Table nextState = (Table) state.clone();
					if (nextState.applyMove(side, pos, start.minus(0, 2))) {
						moves.add(start.minus(0, 2));
					}
				}
			}
		}

		// check up-left enemies
		Point upLeft = pos.plus(1, -1);
		if (isMoveValid(state, upLeft)) {
			moves.add(upLeft);
		}

		// check up enemies
		Point up = pos.plus(1, 0);
		if (isMoveValid(state, up)) {
			moves.add(up);
		}

		// check up-right enemies
		Point upRight = pos.plus(1, 1);
		if (isMoveValid(state, upRight)) {
			moves.add(upRight);
		}

		// check right enemies
		Point right = pos.plus(0, 1);
		if (isMoveValid(state, right)) {
			moves.add(right);
		}

		// check down-right enemies
		Point downRight = pos.minus(1, -1);
		if (isMoveValid(state, downRight)) {
			moves.add(downRight);
		}

		// check down enemies
		Point down = pos.minus(1, 0);
		if (isMoveValid(state, down)) {
			moves.add(down);
		}

		// check down-left enemies
		Point downLeft = pos.minus(1, 1);
		if (isMoveValid(state, downLeft)) {
			moves.add(downLeft);
		}

		// check left enemies
		Point left = pos.minus(0, 1);
		if (isMoveValid(state, left)) {
			moves.add(left);
		}

		return moves;
	}

	@Override
	public void setPos(Point pos) {
		super.setPos(pos);
		moved = true;
	}

}

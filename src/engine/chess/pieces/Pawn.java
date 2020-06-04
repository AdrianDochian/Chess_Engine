package engine.chess.pieces;

import engine.chess.Color;
import static engine.chess.Color.BLACK;
import static engine.chess.Color.WHITE;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class Pawn extends Piece {

	private boolean fifthRank;
	private boolean twoStep;

	public Pawn(Color side, Point pos) {
		super(100, side, pos);
		fifthRank = twoStep = false;
	}

	public boolean hasFifthRank() {
		return fifthRank;
	}

	public boolean hasTwoStepped() {
		return twoStep;
	}

	@Override
	public LinkedList<Point> getNextMoves(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		Color enemySide;
		Point left;
		Point right;
		Point forward;

		if (side == WHITE) {
			enemySide = BLACK;
			left = pos.plus(1, -1);
			right = pos.plus(1, 1);
			forward = pos.plus(1, 0);

			// Check if the pawn hasn't been moved yet
			if (pos.getLine() == 2
					&& state.getPiece(pos.plus(1, 0)) == null
					&& state.getPiece(pos.plus(2, 0)) == null) {
				moves.add(pos.plus(2, 0));
			}
		} else {
			enemySide = WHITE;
			left = pos.minus(1, 1);
			right = pos.minus(1, -1);
			forward = pos.minus(1, 0);

			// Check if the pawn hasn't been moved yet
			if (pos.getLine() == 7
					&& state.getPiece(pos.minus(1, 0)) == null
					&& state.getPiece(pos.minus(2, 0)) == null) {
				moves.add(pos.minus(2, 0));
			}
		}

		// Check enemy on left side
		if (left.isValid()) {
			Piece enemy = state.getPiece(left);

			if (enemy != null && enemy.side == enemySide) {
				moves.add(left);
			}
		}

		// Check enemy on right side
		if (right.isValid()) {
			Piece enemy = state.getPiece(right);

			if (enemy != null && enemy.side == enemySide) {
				moves.add(right);
			}
		}

		// Check if it can go 1 square forward
		if (forward.isValid() && state.getPiece(forward) == null) {
			moves.add(forward);
		}

		// Check en passant
		Piece lastMoved = state.getLastMovedPiece();

		if (lastMoved instanceof Pawn) {
			Pawn enemyPawn = (Pawn) lastMoved;

			// Check flags
			if (fifthRank && enemyPawn.twoStep) {

				// Check adjacency-left
				if (pos.minus(0, 1).equals(enemyPawn.pos)) {
					moves.add(left);
				}

				// Check adjacency-right
				if (pos.plus(0, 1).equals(enemyPawn.pos)) {
					moves.add(right);
				}
			}
		}

		return moves;
	}

	@Override
	public void setPos(Point pos) {
		Point from = new Point(this.pos);
		super.setPos(pos);

		// Check fifth rank
		int rank = side == WHITE ? 5 : 4;
		fifthRank = pos.getLine() == rank;

		// Check twostep
		int diff = Math.abs(pos.getLine() - from.getLine());
		twoStep = diff == 2;
	}

}

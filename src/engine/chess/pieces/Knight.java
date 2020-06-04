package engine.chess.pieces;

import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class Knight extends Piece {

	public Knight(Color side, Point pos) {
		super(350, side, pos);
	}

	@Override
	public LinkedList<Point> getNextMoves(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		// check up-left enemies
		Point upLeft = pos.plus(2, -1);
		if (isMoveValid(state, upLeft)) {
			moves.add(upLeft);
		}

		// check up-right enemies
		Point upRight = pos.plus(2, 1);
		if (isMoveValid(state, upRight)) {
			moves.add(upRight);
		}

		// check right-up enemies
		Point rightUp = pos.plus(1, 2);
		if (isMoveValid(state, rightUp)) {
			moves.add(rightUp);
		}

		// check right-down enemies
		Point rightDown = pos.minus(1, -2);
		if (isMoveValid(state, rightDown)) {
			moves.add(rightDown);
		}

		// check down-right enemies
		Point downRight = pos.minus(2, -1);
		if (isMoveValid(state, downRight)) {
			moves.add(downRight);
		}

		// check down-left enemies
		Point downLeft = pos.minus(2, 1);
		if (isMoveValid(state, downLeft)) {
			moves.add(downLeft);
		}

		// check left-down enemies
		Point leftDown = pos.minus(1, 2);
		if (isMoveValid(state, leftDown)) {
			moves.add(leftDown);
		}

		// check left-up enemies
		Point leftUp = pos.plus(1, -2);
		if (isMoveValid(state, leftUp)) {
			moves.add(leftUp);
		}

		return moves;
	}

}

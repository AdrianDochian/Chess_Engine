package engine.chess.pieces;

import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class Bishop extends Piece {

	public Bishop(Color side, Point pos) {
		super(350, side, pos);
	}

	@Override
	public LinkedList<Point> getNextMoves(Table state) {
		LinkedList<Point> moves = new LinkedList<>();

		// Go diagonal up-left
		moves.addAll(goUpLeft(state));

		// Go diagonal up-right
		moves.addAll(goUpRight(state));

		// Go diagonal down-right
		moves.addAll(goDownRight(state));

		// Go diagonal down-left  
		moves.addAll(goDownLeft(state));

		return moves;
	}

}

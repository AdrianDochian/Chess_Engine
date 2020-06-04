package engine.chess.pieces;

import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class Queen extends Piece {

	public Queen(Color side, Point pos) {
		super(1000, side, pos);
	}

	@Override
	public LinkedList<Point> getNextMoves(Table table) {
		LinkedList<Point> moves = new LinkedList<>();

		// Go right
		moves.addAll(goRight(table));

		// Go left
		moves.addAll(goLeft(table));

		// Go up
		moves.addAll(goUp(table));

		// Go down  
		moves.addAll(goDown(table));

		// Go diagonal up-left
		moves.addAll(goUpLeft(table));

		// Go diagonal up-right
		moves.addAll(goUpRight(table));

		// Go diagonal down-right
		moves.addAll(goDownRight(table));

		// Go diagonal down-left  
		moves.addAll(goDownLeft(table));

		return moves;
	}

}

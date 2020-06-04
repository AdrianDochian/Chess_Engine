package engine.chess.pieces;

import engine.chess.Color;
import engine.chess.Point;
import engine.chess.Table;
import java.util.LinkedList;

public class Rook extends Piece {

	private boolean moved;

	public Rook(Color side, Point pos) {
		super(525, side, pos);
		moved = false;
	}

	public boolean hasMoved() {
		return moved;
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

		return moves;
	}

	@Override
	public void setPos(Point pos) {
		super.setPos(pos);
		moved = true;
	}

}

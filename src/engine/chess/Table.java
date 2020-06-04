package engine.chess;

import engine.chess.pieces.Piece;
import engine.Game;
import engine.chess.pieces.*;
import static engine.chess.Color.BLACK;
import static engine.chess.Color.WHITE;
import engine.logger.Logger;
import java.util.LinkedList;
import engine.parser.commands.Move;
import engine.parser.commands.Resign;

public class Table {

	private Piece[][] board;
	private Piece lastMovedPiece;

	public Table() {
		initTable();
	}

	public Piece getLastMovedPiece() {
		return lastMovedPiece;
	}

	private void initTable() {
		board = new Piece[9][9];

		// Pawns
		for (int i = 1; i <= 8; i++) {
			board[2][i] = new Pawn(WHITE, new Point(2, i));
			board[7][i] = new Pawn(BLACK, new Point(7, i));
		}

		// Rooks
		board[1][1] = new Rook(WHITE, new Point(1, 1));
		board[1][8] = new Rook(WHITE, new Point(1, 8));
		board[8][1] = new Rook(BLACK, new Point(8, 1));
		board[8][8] = new Rook(BLACK, new Point(8, 8));

		// Knights
		board[1][2] = new Knight(WHITE, new Point(1, 2));
		board[1][7] = new Knight(WHITE, new Point(1, 7));
		board[8][2] = new Knight(BLACK, new Point(8, 2));
		board[8][7] = new Knight(BLACK, new Point(8, 7));

		// Bishops
		board[1][3] = new Bishop(WHITE, new Point(1, 3));
		board[1][6] = new Bishop(WHITE, new Point(1, 6));
		board[8][3] = new Bishop(BLACK, new Point(8, 3));
		board[8][6] = new Bishop(BLACK, new Point(8, 6));

		// Queens
		board[1][4] = new Queen(WHITE, new Point(1, 4));
		board[8][4] = new Queen(BLACK, new Point(8, 4));

		// Kings
		board[1][5] = new King(WHITE, new Point(1, 5));
		board[8][5] = new King(BLACK, new Point(8, 5));
	}

	public boolean isKingInDanger(Color side) {
		Color opSide = side == WHITE ? BLACK : WHITE;

		// Find king
		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point from = new Point(line, col);
				Piece king = getPiece(from);

				if (king == null) {
					continue;
				}

				// Check if the king can be captured
				if (king instanceof King && king.getSide() == side) {
					for (Point to : getSideMoves(opSide)) {
						if (to.equals(king.getPos())) {
							return true;
						}
					}
				}
			}
		}

		return false;
	}

	public LinkedList<Point> getSideMoves(Color side) {
		LinkedList<Point> moves = new LinkedList<>();

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point from = new Point(line, col);
				Piece piece = getPiece(from);

				if (piece == null || piece.getSide() != side) {
					continue;
				}

				moves.addAll(piece.getNextMoves(this));
			}
		}

		return moves;
	}

	/**
	 * TEMP: In the end, this will make the best possible move, for now it's
	 * random.
	 */
	public void randomMove() {

		Color playSide = Game.getInstance().getPlaySide();

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point from = new Point(line, col);
				Piece ally = getPiece(from);

				if (ally != null && ally.getSide() == playSide) {
					// Try to move current piece
					for (Point to : ally.getNextMoves(this)) {
						Piece enemy = getPiece(to);
						movePiece(from, to);

						if (isKingInDanger(playSide)) {
							// restore table... try something else
							ally.setPos(from);
							board[line][col] = ally;

							if (enemy != null) {
								board[to.getLine()][to.getColumn()] = enemy;
							} else {
								board[to.getLine()][to.getColumn()] = null;
							}

							continue;
						}

						// we've got ourselves a valid move lads
						Logger.log(from + "" + to);

						Game.getInstance().send(new Move(
								"move " + from + "" + to));
						return;
					}
				}
			}
		}

		// no valid moves left... resign
		Game.getInstance().send(new Resign());
	}

	/**
	 * Get a reference to a chess piece based on the given point.
	 *
	 * @param from position.
	 * @return the piece at the given point, or null.
	 */
	public Piece getPiece(Point from) {
		return board[from.getLine()][from.getColumn()];
	}

	/**
	 * Move a given piece on the chess table.
	 *
	 * @param from position.
	 * @param to position.
	 */
	public void movePiece(Point from, Point to) {

		Piece piece = getPiece(from);

		// Update previous position.
		board[from.getLine()][from.getColumn()] = null;

		if (piece instanceof Pawn) {

			// Check queen spawn
			if ((to.getLine() == 8 || to.getLine() == 1)) {
				piece = new Queen(piece.getSide(), new Point(to));
			} // Check en passant
			else if (lastMovedPiece instanceof Pawn) {
				Pawn ally = (Pawn) piece;
				Pawn enemy = (Pawn) lastMovedPiece;

				// Check flags
				if (ally.hasFifthRank() && enemy.hasTwoStepped()) {

					// Check adjacency
					if (from.minus(0, 1).equals(enemy.getPos())
							|| from.plus(0, 1).equals(enemy.getPos())) {

						if (getPiece(to) == null
								&& to.getColumn() != from.getColumn()) {
							Logger.log(ally.getSide() + " , " + from
									+ " en passant la " + enemy.getPos());

							int line = lastMovedPiece.getPos().getLine();
							int col = lastMovedPiece.getPos().getColumn();
							board[line][col] = null;
						}
					}
				}
			}
		}

		// Check castle
		if (piece instanceof King) {
			Point kingStart = ((King) piece).getStart();
			Point smallCastle;
			Point bigCastle;

			if (piece.getSide() == WHITE) {
				smallCastle = new Point(1, 7);
				bigCastle = new Point(1, 3);
			} else {
				smallCastle = new Point(8, 7);
				bigCastle = new Point(8, 3);
			}

			if (from.equals(kingStart)) {

				// Small castle
				if (to.equals(smallCastle)) {
					// Move right-rook
					Piece leftRook = getPiece(from.plus(0, 3));
					movePiece(leftRook.getPos(), from.plus(0, 1));
				}

				// Big castle
				if (to.equals(bigCastle)) {
					// Move left-rook
					Piece rightRook = getPiece(from.minus(0, 4));
					movePiece(rightRook.getPos(), from.minus(0, 1));
				}
			}
		}

		// Set current position.
		piece.setPos(to);
		board[to.getLine()][to.getColumn()] = piece;
		lastMovedPiece = piece;
	}

	public boolean applyMove(Color side, Point from, Point to) {

		Piece ally = getPiece(from);
		Piece enemy = getPiece(to);

		movePiece(from, to);

		if (isKingInDanger(side)) {
			// Restore
			ally.setPos(from);
			board[from.getLine()][from.getColumn()] = ally;

			if (enemy != null) {
				board[to.getLine()][to.getColumn()] = enemy;
			} else {
				board[to.getLine()][to.getColumn()] = null;
			}

			return false;
		}

		return true;
	}

	// @@@ NEEDS IMPROVEMENT @@@
	public int eval(Color side) {

		Color opSide = side == WHITE ? BLACK : WHITE;

		if (ended(side)) {
			return -Game.getInstance().INF;
		}

		if (ended(opSide)) {
			return Game.getInstance().INF;
		}

		// Nr. of remaining pieces
		int count = 0;

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point pos = new Point(line, col);
				Piece piece = getPiece(pos);

				if (piece != null) {
					int val = piece.getVal();

					// Material evaluation
					count = piece.getSide() == side ? count + val : count - val;

					// @@@ TO DO: Square control evaluation @@@
					if (piece instanceof Pawn) {
						Point left;
						Point right;
						Point forward;
						Color enemySide;

						if (piece.getSide() == WHITE) {
							enemySide = BLACK;
							left = pos.plus(1, -1);
							right = pos.plus(1, 1);
							forward = pos.plus(1, 0);
						} else {
							enemySide = WHITE;
							left = pos.minus(1, 1);
							right = pos.minus(1, -1);
							forward = pos.minus(1, 0);
						}

						// Check enemy on left side
						if (left.isValid()) {
							Piece enemy = getPiece(left);

							if (enemy != null && enemy.getSide() == enemySide) {
								count = piece.getSide() == side ? count + 1 : count - 1;
							}
						}

						// Check enemy on right side
						if (right.isValid()) {
							Piece enemy = getPiece(right);

							if (enemy != null && enemy.getSide() == enemySide) {
								count = piece.getSide() == side ? count + 1 : count - 1;
							}
						}

						// Check if it can go 1 square forward
						if (forward.isValid() && getPiece(forward) == null) {
							count = piece.getSide() == side ? count + 1 : count - 1;
						}
					}
				}
			}
		}

		return count;
	}

	// @@@ TEMP @@@
	public boolean ended(Color side) {

		for (int i = 1; i <= 8; i++) {
			for (int j = 1; j <= 8; j++) {
				Point from = new Point(i, j);
				Piece ally = getPiece(from);

				if (ally != null && ally.getSide() == side) {
					for (Point to : ally.getNextMoves(this)) {
						Piece enemy = getPiece(to);
						movePiece(from, to);

						if (isKingInDanger(side) == false) {

							// Restore
							ally.setPos(from);
							board[i][j] = ally;

							if (enemy != null) {
								board[to.getLine()][to.getColumn()] = enemy;
							} else {
								board[to.getLine()][to.getColumn()] = null;
							}

							return false;
						}

						// Restore
						ally.setPos(from);
						board[i][j] = ally;

						if (enemy != null) {
							board[to.getLine()][to.getColumn()] = enemy;
						} else {
							board[to.getLine()][to.getColumn()] = null;
						}
					}
				}
			}
		}

		// no valid moves left
		return true;
	}

	@Override
	public Object clone() {
		Table ret = new Table();
		Piece[][] retBoard = new Piece[9][9];

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Piece piece = getPiece(new Point(line, col));

				if (piece != null) {
					retBoard[line][col] = (Piece) piece.clone();
				}
			}
		}

		ret.board = retBoard;
		return ret;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		for (int line = 1; line <= 8; line++) {
			for (int col = 1; col <= 8; col++) {
				Point from = new Point(line, col);
				Piece piece = getPiece(from);

				if (piece == null) {
					continue;
				}

				sb.append(piece.getClass() + ", " + piece.getSide() + ", "
						+ from + "\n");
			}
		}

		return sb.toString();
	}

}

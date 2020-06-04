package engine.chess;

public class Point {

	public static final String COLUMNS = "abcdefgh";

	private final int line;
	private final int column;

	public Point(char line, char column) {
		this(line - '0', COLUMNS.indexOf(column) + 1);
	}

	public Point(Point p) {
		this(p.line, p.column);
	}

	public Point(int line, int column) {
		this.line = line;
		this.column = column;
	}

	public int getLine() {
		return line;
	}

	public int getColumn() {
		return column;
	}

	public Point minus(int x, int y) {
		return new Point(line - x, column - y);
	}

	public Point plus(int x, int y) {
		return new Point(line + x, column + y);
	}

	public boolean isValid() {
		return (line >= 1 && line <= 8) && (column >= 1 && column <= 8);
	}

	@Override
	public String toString() {
		return COLUMNS.charAt(column - 1) + "" + line;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Point) {
			Point other = (Point) o;
			return line == other.line && column == other.column;
		}

		return false;
	}
}

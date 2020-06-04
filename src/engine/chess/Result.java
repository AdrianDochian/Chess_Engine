package engine.chess;

public class Result {

	private final Point from;
	private final Point to;
	private final int score;

	public Result(Point from, Point to, int score) {
		this.from = from;
		this.to = to;
		this.score = score;
	}

	public Point getFrom() {
		return from;
	}

	public Point getTo() {
		return to;
	}

	public int getScore() {
		return score;
	}

}

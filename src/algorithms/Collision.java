package algorithms;

import static java.lang.Math.abs;

public class Collision implements IProblema {
	Board x;

	public Collision(Board x){
		this.x = x;
	}
	
	public int getCollisions() {
		return findCollision(x);
	}
	
	private boolean verificDiagonal(int y, int x, int y2, int x2) {
		int dy = abs(y - y2);
		int dx = abs(x - x2);

		if (dy == dx)
			return true;
		return false;
	}

	private int findCollision(Board board) {
		int n = 0;
		for (int i = 0; i < board.nqueens; i++)
			for (int j = i + 1; j < board.nqueens; j++)
				if (verificDiagonal(i, board.board[i], j, board.board[j]))
					n++;
		return n;
	}
}

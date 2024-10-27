package algorithms;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class Board {
	final int nqueens;
	final int[] board;
	private final int ncollisions;
	Random generator = new Random();

	public Board(int nqueens) {
		this.nqueens = nqueens;
		this.board = randomPermutation(nqueens);
		IProblema ip = new Collision(this);
		ncollisions = getCollisions(ip);
	}

	private int[] randomPermutation(int val) {
		assert (val < 100);
		int[] x = new int[val];
		for (int i = 0; i < val; i++)
			x[i] = i;
		for (int i = 0; i < val - 1; i++) {
			double ndouble = generator.nextDouble();
			int index = (int) (i + Math.round(ndouble * ((val - 1) - i)));
			int a = x[index];
			x[index] = x[i];
			x[i] = a;
		}
		return x;
	}

	public int getCollisions(IProblema ip) {
		return ip.getCollisions();
	}

	public double getCollisions() {
		return ncollisions;
	}

	private int[] create(int nqueens) {
		int[] board = new int[nqueens];
		int met = nqueens / 2;
		int j = 0;
		for (int i = 0; i < nqueens; i++) {
			if (i % 2 != 0) {
				board[i] = j++;
			} else {
				board[i] = met++;
			}
		}
		return board;
	}

	public Board(int nqueens, int[] board) {
		this.nqueens = nqueens;
		this.board = board;
		IProblema ip = new Collision(this);
		ncollisions = getCollisions(ip);
	}

	public String toString() {
		StringBuilder str = new StringBuilder();
		for (int i = 0; i < nqueens; i++) {
			for (int j = 0; j < nqueens; j++) {
				if (board[i] == j)
					str.append(" * ");
				else
					str.append(" - ");
			}
			str.append("\n");
		}
		str.append(ncollisions);
		return str.toString();
	}

	void print(int[] a) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(a[i] + " ");
		}
		System.out.println();
	}

	public Board[] crossoverPMX(Board parent2, int randomNum1, int randomNum2) {
		if (randomNum1 == -1) {
			randomNum1 = (int) (1 + Math.round(generator.nextDouble() * ((nqueens - 2) - 0)));
			randomNum2 = (int) (1 + Math.round(generator.nextDouble() * ((nqueens - 2) - 0)));
		}
//		System.out.println(randomNum1 + " " + randomNum2);
//		print(this.board);
//		print(parent2.board);

		Board[] x = new Board[2];
		if (randomNum1 != randomNum2 && randomNum1 < randomNum2) {
			x[0] = new Board(parent2.nqueens, updatePermutation(randomNum1, randomNum2, parent2.board, this.board));
			x[1] = new Board(parent2.nqueens, updatePermutation(randomNum1, randomNum2, this.board, parent2.board));
		} else {
			x[0] = this;
			x[1] = parent2;
		}

//		print(x[0].board);
//		print(x[1].board);

		return x;
	}

	private static boolean contains(final int[] arr, final int key) {
		return Arrays.stream(arr).anyMatch(i -> i == key);
	}

	private int[] updatePermutation(int randomNum1, int randomNum2, int[] parent1, int[] parent2) {
		int[] children = new int[parent1.length];
		Arrays.fill(children, -1); // array all elements "-1"
		int count = 0;

		for (int i = randomNum1; i < randomNum2; i++) { // middle
			children[i] = parent1[i];
			count++;
		}

		for (int i = 0; i < randomNum1; i++) { // start
			if (!contains(children, parent2[i])) {
				children[i] = parent2[i];
				count++;
			}
		}

		for (int i = randomNum2; i < parent1.length; i++) { // end
			if (!contains(children, parent2[i])) {
				children[i] = parent2[i];
				count++;
			}
		}

		if (count == parent1.length) {
//			System.out.println("     " + count);
			return children;
		} else {
//			print(children);
			return complet(children, parent1.length);
		}
	}

	private int[] complet(int[] children, int n) {
		for (int i = 0; i < n; i++) {
			if (children[i] == -1) {
				int val = 0;
				for (int j = 0; j < n; j++) {
					if (!contains(children, j)) {
						val = j;
						break;
					}
				}
				children[i] = val;
			}
		}
		return children;
	}

	public int compareToCres(Board c) {
		double value = this.getCollisions();
		double oValue = c.getCollisions();
		return (int) value < oValue ? -1 : value > oValue ? 1 : 0;
	}

	private int[] swap(int[] children, int i, int j) {
		int tempo = children[i];
		children[i] = children[j];
		children[j] = tempo;
		return children;
	}

	public Board mutacaoBitFlip(int randomNum1, int randomNum2) {
		if (randomNum1 == -1) {
			randomNum1 = (int) (1 + Math.round(generator.nextDouble() * ((nqueens - 2) - 0)));
			randomNum2 = (int) (1 + Math.round(generator.nextDouble() * ((nqueens - 2) - 0)));
		}
//		System.out.println(randomNum1 + " " + randomNum2);
//		print(this.board);
		int[] children = swap(this.board, randomNum1 - 1, randomNum2 - 1);
//		print(children);
		Board x = new Board(children.length, children);
		return x;
	}

	private boolean muta(double p, double rnd) {
		return rnd < p;
	}
}

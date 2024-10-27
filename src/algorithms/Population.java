package algorithms;

import java.util.LinkedList;
import java.util.Random;

public class Population {
	private LinkedList<Board> populacao;
	private int n;
	Random generator = new Random();

	public LinkedList<Board> getpopulacao() {
		return populacao;
	}

	public Population() {
		this.populacao = new LinkedList<>();
		this.n = 0;
	}

	public Population(int nqueens) {
		this.populacao = create(nqueens);
		this.n = nqueens;
	}

	private LinkedList<Board> create(int nqueens) {
		LinkedList<Board> x = new LinkedList<>();
		for (int i = 0; i < nqueens; i++) {
			x.add(new Board(nqueens));
		}
		return x;
	}

	public void addBoard(Board Board) {
		populacao.add(Board);
		n++;
	}

	private int[] randomInt(int n, int l) {
		int[] randomNum = new int[n];
		for (int i = 0; i < n; i++) {
			double index = generator.nextDouble();
			randomNum[i] = (int) (0 + Math.round(index * ((l - 1) - 0)));
//			System.out.println(randomNum[i]);
		}
		return randomNum;
	}

	private int[] randomInt(int n, int l, Random generator) {
		int[] randomNum = new int[n];
		for (int i = 0; i < n; i++) {
			double index = generator.nextDouble();
			randomNum[i] = (int) (0 + Math.round(index * ((l - 1) - 0)));
		}
		return randomNum;
	}

	private double[] randomDouble(int n, int l, Random generator) {
		double[] randomNum = new double[n];
		for (int i = 0; i < n; i++) {
			randomNum[i] = generator.nextDouble();
		}
		return randomNum;
	}

	private LinkedList<Board> binaryTournament(Random generator) {
		int[] randomNum = randomInt(n * 2, n, generator);
		LinkedList<Board> x = new LinkedList<>();

		for (int i = 0; i < (n * 2) - 1; i += 2) {
			if (populacao.get(randomNum[i]).getCollisions() >= populacao.get(randomNum[i + 1]).getCollisions()) {
				x.add(populacao.get(randomNum[i]));
			} else {
				x.add(populacao.get(randomNum[i + 1]));
			}
		}
		return x;
	}

	private LinkedList<Board> rouletteWheelSelection(Random generator, int allFit) {
		assert (n <= 100);
		double[] randomNum = randomDouble(n, n, generator);

		LinkedList<Board> x = new LinkedList<>();
		double val = 0.0;
		for (int j = 0; j < n; j++) {
			for (int i = 0; i < n; i++) {
				val += populacao.get(i).getCollisions() / allFit;
				if (randomNum[j] < val) {
					x.add(populacao.get(i));
					break;
				}
			}
			val = 0.0;
		}

		return x;
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

	private LinkedList<Board> tournamentReplacement(int s, int nqueens, int val) {
		LinkedList<Board> x = new LinkedList<>();
		int N = nqueens;
		LinkedList<Board> y = new LinkedList<>();
		LinkedList<Board> y1 = new LinkedList<>();

		for (int j = 0; j < s; j++) {
			int[] a = new int[N];
			a = randomPermutation(N);

			for (int i = 0; i < N; i++) {
				x.add(populacao.get(a[i]));
			}

			for (int i = 0; i < N; i++) {
				y.add(x.get(i));
				if ((i + 1) % s == 0) {
					y.sort((cromo1, cromo2) -> cromo1.compareToCres(cromo2));
					for (int h = 0; h < 1; h++)
						y1.add(y.get(h));
					y = new LinkedList<>();
				}
			}

			x = new LinkedList<>();
		}
		return y1;
	}

	private LinkedList<Board> copyP(Population p) {
		LinkedList<Board> x = new LinkedList<>();
		for (int i = 0; i < p.getpopulacao().size(); i++) {
			x.add(p.getpopulacao().get(i));
		}

		return x;
	}

	private LinkedList<Board> oneGeneration(int s, int n, double pm, int num) {
		LinkedList<Board> x2 = new LinkedList<>();
		LinkedList<Board> x1 = new LinkedList<>();
		if (n % 2 != 0)
			n--;
		int u = 0;
		while (u < num) {
			LinkedList<Board> x = this.tournamentReplacement(s, this.getpopulacao().size(), n);
			x2 = new LinkedList<>();
			x1 = new LinkedList<>();

			Board[] y = new Board[2];
			for (int i = 0; i < n - 1; i += 2) {
				y = x.get(i).crossoverPMX(x.get(i + 1), -1, -1);
				for (int o = 0; o < 2; o++)
					x1.add(y[o]);
			}

			for (int i = 0; i < x1.size(); i++) {
				x2.add(x1.get(i).mutacaoBitFlip(-1, -1));
			}

			atualizarPop(x1);
			u++;
		}

		return x2;
	}

	public void atualizarPop(LinkedList<Board> x2) {
		this.populacao = new LinkedList<>();
		for (int i = 0; i < x2.size(); i++)
			this.addBoard(x2.get(i));
	}

	@SuppressWarnings("unused")
	public LinkedList<Board> genericAlgorithm(int s, int n, double pm, int num) {
		return this.oneGeneration(s, n, pm, num);
	}

	public Population Procreate() {
		Population p = new Population();
		LinkedList<Board> x = copyP(this);
		Board[] y = new Board[2];
		for (int i = 0; i < n - 1; i += 2) {
			y = x.get(i).crossoverPMX(x.get(i + 1), -1, -1);
			for (int o = 0; o < 2; o++)
				p.addBoard(y[o]);
		}
		return p;
	}

	private Population newPopulation(Population x, Population y) {
		Population newP = new Population();
		for (int i = 0; i < x.getpopulacao().size(); i++)
			newP.addBoard(x.getpopulacao().get(i));
		for (int i = 0; i < y.getpopulacao().size(); i++)
			newP.addBoard(x.getpopulacao().get(i));
		return newP;
	}

	private Population copyLinked(LinkedList<Board> y) {
		Population x = new Population();
		for (int i = 0; i < y.size(); i++) {
			x.addBoard(y.get(i));
		}
		return x;
	}

	public Population Select_Survivors(Population p) {
		Population newP = newPopulation(this, p);
		Population fim = copyLinked(newP.tournamentReplacement(2, newP.getpopulacao().size(), p.getpopulacao().size()));
		return fim;
	}
}
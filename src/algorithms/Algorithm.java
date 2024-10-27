package algorithms;

import java.util.LinkedList;

public class Algorithm {

	public static Board algorithm(int N) {
		Population p = new Population(N);
		Board boardfim = new Board(N);
		boolean find = true;
		while (find == true) {
			LinkedList<Board> x = p.genericAlgorithm(2, N, 8, 1);

			for (int i = 0; i < x.size(); i++) {
				if (x.get(i).getCollisions() == 0) {
					boardfim = x.get(i);
					find = false;
					break;
				}
			}

			p.atualizarPop(x);
		}
		return boardfim;
	}

	public static Board algorithmES(int N) {
		Population parents = new Population(N);
		Board boardfim = null;
		boolean find = true;
		for (int j = 0; j < parents.getpopulacao().size(); j++) {
			if (parents.getpopulacao().get(j).getCollisions() == 0) {
				boardfim = parents.getpopulacao().get(j);
				find = false;
				break;
			}
		}
		while (find == true) {
			Population childrens = parents.Procreate();
			for (int i = 0; i < childrens.getpopulacao().size(); i++) {
				if (childrens.getpopulacao().get(i).getCollisions() == 0) {
					boardfim = childrens.getpopulacao().get(i);
					find = false;
					break;
				}
			}
			parents = parents.Select_Survivors(childrens);
		}
		return boardfim;
	}
}

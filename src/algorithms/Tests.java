package algorithms;

import static org.junit.jupiter.api.Assertions.*;

import java.util.LinkedList;
import java.util.Random;

import org.junit.jupiter.api.Test;

class Tests {

	@Test
	void testgetCollisions() {
		int[] x = { 2, 0, 3, 1 };
		Board y = new Board(4, x);
		IProblema ip = new Collision(y);
		assertEquals(y.getCollisions(ip), 0);
	}

	@Test
	void testcrossoverPMX() {
		int[] x1 = { 2, 0, 3, 1, 4, 5 };
		Board y1 = new Board(6, x1);
		int[] x2 = { 1, 0, 2, 3, 5, 4 };
		Board y2 = new Board(6, x2);
		Board[] o = y1.crossoverPMX(y2, 1, 4);
		int[] x3 = { 1, 0, 2, 3, 4, 5 };
		int[] x4 = { 2, 0, 3, 1, 5, 4 };
		Board y3 = new Board(6, x3);
		Board y4 = new Board(6, x4);
		assertEquals(o[0].toString(), y3.toString());
		assertEquals(o[1].toString(), y4.toString());
	}

	@Test
	void testmutacaoBitFlip() {
		int[] x1 = { 2, 0, 3, 1, 4, 5 };
		Board y1 = new Board(6, x1);
		double r = 0.5;
		Board o = y1.mutacaoBitFlip(1, 4);
		int[] x3 = { 1, 0, 3, 2, 4, 5 };
		Board y3 = new Board(6, x3);
		assertEquals(o.toString(), y3.toString());
	}

	@Test
	void testcomparateToCres() {
		int[] x1 = { 2, 0, 3, 1, 4, 5 };
		Board y1 = new Board(6, x1);
		int[] x2 = { 2, 3, 1, 0, 4, 5 };
		Board y2 = new Board(6, x2);
		int[] x3 = { 2, 0, 1, 3, 5, 4 };
		Board y3 = new Board(6, x3);
		LinkedList<Board> total = new LinkedList<>();
		total.add(y3);
		total.add(y2);
		total.add(y1);
		LinkedList<Board> verif = new LinkedList<>();
		verif.add(y1);
		verif.add(y2);
		verif.add(y3);
		total.sort((cromo1, cromo2) -> cromo1.compareToCres(cromo2));
		assertEquals(total, verif);
	}

	@Test
	void testatualizarPop() {
		int[] x1 = { 2, 0, 3, 1, 4, 5 };
		Board y1 = new Board(6, x1);
		int[] x2 = { 2, 3, 1, 0, 4, 5 };
		Board y2 = new Board(6, x2);
		int[] x3 = { 2, 0, 1, 3, 5, 4 };
		Board y3 = new Board(6, x3);
		LinkedList<Board> total = new LinkedList<>();
		total.add(y1);
		total.add(y2);
		total.add(y3);
		Population p = new Population(6);
		p.atualizarPop(total);
		assertEquals(p.getpopulacao(), total);
	}

	@Test
	void testalgorithmES() {
		Board x = Algorithm.algorithmES(5);
		assertEquals(x.getCollisions(), 0);
	}
	
	@Test
	void testalgorithm() {
		Board x = Algorithm.algorithm(5);
		assertEquals(x.getCollisions(), 0);
	}

}

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.Math;
import java.text.DecimalFormatSymbols;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

import algorithms.Algorithm;
import algorithms.Board;
import algorithms.IProblema;
import algorithms.Population;

public class Main {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);

//		int n = sc.nextInt();
		for (int i = 4; i < 100; i++) {
			Board x = Algorithm.algorithm(i);
			System.out.println(i);
		}

//		Population p = new Population(N);
//		LinkedList<Board> x = p.genericAlgorithm(2, N, 8, 1);
	}
}
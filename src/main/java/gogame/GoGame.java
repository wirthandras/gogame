package gogame;

import java.util.Scanner;
import java.util.Random;

// robot játék logika pont szerzésre
// andris kommentje
public class GoGame {

	private static int size;
	private static int[][] goTable;
	private final static int endOfGame = 20;
	private static int pointsOfPlayer1;
	private static int pointsOfPlayer2;
	private static int i = 0;
	private static int j = 0;

	public static void main(String[] args) {

		size = Integer.parseInt(args[0]);
		goTable = new int[size][size];
		print();
		scanGameMethod();
	}

	private static void print() {

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				System.out.print(goTable[i][j] + " ");
			}
			System.out.println();
		}
	}

	private static void printResults() {

		System.out.println("End of game");

		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {

				if (goTable[i][j] == 1) {
					pointsOfPlayer1 = pointsOfPlayer1 + 1;
				}
				if (goTable[i][j] == 2) {
					pointsOfPlayer2 = pointsOfPlayer2 + 1;
				}
			}
		}
		System.out.println("Player1: " + pointsOfPlayer1);
		System.out.println("Player2: " + pointsOfPlayer2);

		if (pointsOfPlayer1 > pointsOfPlayer2) {
			System.out.println("Winner is Player1");
		}
		if (pointsOfPlayer1 < pointsOfPlayer2) {
			System.out.println("Winner is Player2");
		}
		if (pointsOfPlayer1 == pointsOfPlayer2) {
			System.out.println("Draw");
		}
	}

	private static void scanGameMethod() {
		Scanner sc = new Scanner(System.in);
		int gameMethod = 0;
		System.out.println("One Player (1); or Two Player: (2)");
		gameMethod = sc.nextInt();

		if (gameMethod != 1 && gameMethod != 2) {
			scanGameMethod();
			return;
		}
		for (int game = 0; game < endOfGame; game++) {
			int actualPlayer = game % 2 == 0 ? 1 : 2;

			boolean result = scan(actualPlayer, gameMethod);

			if (result) {
				System.out.println("Game brake");
				break;

			}
		}
		printResults();
	}

	private static boolean hasProblem() {
		
		
		
		return true;
	}
	
	
	
	private static boolean scan(int actualPlayer, int gameMethod) {

		if (gameMethod == 1 && actualPlayer == 2) {

			System.out.println("Robot");
			attack();
			/*Random ran = new Random();
			i = ran.nextInt(size);
			j = ran.nextInt(size);
*/
		} else {
			System.out.println("Player:" + actualPlayer);
			Scanner sc = new Scanner(System.in);
			i = sc.nextInt();
			if (i < 0) {
				return true;
			}
			j = sc.nextInt();

			if (j < 0) {
				return true;
			}
		}

		boolean result = false;
		if (i <= size && j <= size) {
			if (goTable[i][j] == 0) {
				goTable[i][j] = actualPlayer;

				check(i, j);
				print();

			} else {
				System.out.println("Reserved place");

				result = scan(actualPlayer, gameMethod);
			}
		} else {
			System.out.println("Bad input");

			result = scan(actualPlayer, gameMethod);
		}
		return result;
	}

	private static void attack() {

		int attackPlus1 = 1;
		int attackMinus1 = -1;
		int ki = 0;
		int kj = 0;
		int ti;
		int tj;

		for (ti = 0; ti < size; ti++) {
			for (tj = 0; tj < size; tj++) {

				if (goTable[ti][tj] == 1) {
					if (ti + attackPlus1 < size && ti + attackMinus1 >= 0 && goTable[ti + attackPlus1][tj] == 0
							&& goTable[ti + attackMinus1][tj] == 2) {
						ki = ti + attackPlus1;
						kj = tj;
					} else if (ti + attackPlus1 < size && ti + attackMinus1 >= 0 &&goTable[ti + attackPlus1][tj] == 2
							&& goTable[ti + attackMinus1][tj] == 0) {
						ki = ti + attackMinus1;
						kj = tj;
					} else if (tj + attackPlus1 < size && tj + attackMinus1 >= 0 && goTable[ti][tj + attackPlus1] == 0
							&& goTable[ti][tj + attackMinus1] == 2) {
						ki = ti;
						kj = tj + attackPlus1;
					} else if (tj + attackPlus1 < size && tj + attackMinus1 >= 0 && goTable[ti][tj + attackPlus1] == 2
							&& goTable[ti][tj + attackMinus1] == 0) {
						ki = ti;
						kj = tj + attackMinus1;
					} else if (ti + attackPlus1 < size && ti + attackMinus1 >= 0 && goTable[ti + attackPlus1][tj] == 0
							&& goTable[ti + attackMinus1][tj] == 0) {
						ki = ti + attackPlus1;
						kj = tj;
					} else {
						Random ran = new Random();
						ki = ran.nextInt(size);
						kj = ran.nextInt(size);						
					}
				}
			}
		}
		i = ki;
		j = kj;
	}

	private static void check(int i, int j) {

		int checkPlus1 = 1;
		int checkMinus1 = -1;

		int checkPlus2 = 2;
		int checkMinus2 = -2;

		if (goTable[i][j] == 1) {
			if (i + checkPlus2 < size && goTable[i + checkPlus1][j] == 2 && goTable[i + checkPlus2][j] == 1) {
				goTable[i + checkPlus1][j] = 0;
			}
			if (i + checkMinus2 >= 0 && goTable[i + checkMinus1][j] == 2 && goTable[i + checkMinus2][j] == 1) {
				goTable[i + checkMinus1][j] = 0;
			}
			if (j + checkPlus2 < size && goTable[i][j + checkPlus1] == 2 && goTable[i][j + checkPlus2] == 1) {
				goTable[i][j + checkPlus1] = 0;
			}
			if (j + checkMinus2 >= 0 && goTable[i][j + checkMinus1] == 2 && goTable[i][j + checkMinus2] == 1) {
				goTable[i][j + checkMinus1] = 0;
			}
		} else {
			if (i + checkPlus2 < size && goTable[i + checkPlus1][j] == 1 && goTable[i + checkPlus2][j] == 2) {
				goTable[i + checkPlus1][j] = 0;
			}
			if (i + checkMinus2 >= 0 && goTable[i + checkMinus1][j] == 1 && goTable[i + checkMinus2][j] == 2) {
				goTable[i + checkMinus1][j] = 0;
			}
			if (j + checkPlus2 < size && goTable[i][j + checkPlus1] == 1 && goTable[i][j + checkPlus2] == 2) {
				goTable[i][j + checkPlus1] = 0;
			}
			if (j + checkMinus2 >= 0 && goTable[i][j + checkMinus1] == 1 && goTable[i][j + checkMinus2] == 2) {
				goTable[i][j + checkMinus1] = 0;
			}
		}

	}
}
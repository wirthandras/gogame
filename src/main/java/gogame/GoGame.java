package gogame;

import java.util.Scanner;

import gogame.demo.Coordinate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// robot játék logika pont szerzésre
// andris kommentje
public class GoGame {

	private static int size;
	private static int[][] goTable;
	private static List<Coordinate> maxCoordinates = new ArrayList<>();
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

		System.out.print(" |");
		for (int i = 0; i < size; i++) {
			System.out.print(i + " ");
		}
		System.out.println();
		System.out.print("-+");
		for (int i = 0; i < size; i++) {
			System.out.print("--");
		}
		System.out.println();

		for (int i = 0; i < size; i++) {
			System.out.print(i + "|");
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

			Idea1();

//			Random ran = new Random();
//			int r = 0;
//			r = ran.nextInt(15);

			/*
			 * if (r % 2 == 0 || r % 3 == 0) { attack(); } else { defend(); }
			 * 
			 * resultsOfCell();
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
				System.out.println("Reserved place" + i + "__" + j);

				result = scan(actualPlayer, gameMethod);
			}
		} else {
			System.out.println("Bad input");

			result = scan(actualPlayer, gameMethod);
		}
		return result;
	}

	private static void Idea1() {

		int resultsOfCellPlus1 = 1;
		int resultsOfCellMinus1 = -1;
		int resultsOfCellPlus2 = 2;
		int resultsOfCellMinus2 = -2;
		int ci = 0;
		int cj = 0;
		int li = 0;
		int lj = 0;
		int ri;
		int rj;
		int va = 0;
		int vl = 0;
		int next1value = 0;
		int next2value = 0;
		int vdown = 0;
		int vup = 0;
		int vrigth = 0;
		int vleft = 0;

		for (ri = 0; ri < size; ri++) {
			for (rj = 0; rj < size; rj++) {

				// ha a vizsgált cella 1 vagy 2
				if (goTable[ri][rj] == 1 || goTable[ri][rj] == 2) {
					va = 0;
					ci = ri;
					cj = rj;
				} else {

					for (int direction = 0; direction < 4; direction++) {

						switch (direction) {
						case 0: // lefelé vizsgál
							if (ri >= size - 2) {
								vdown = 0;
							} else {
								next1value = goTable[ri + resultsOfCellPlus1][rj];
								next2value = goTable[ri + resultsOfCellPlus2][rj];

								vdown = cellValue(next1value, next2value);
							}
							break;

						case 1: // felfele vizsgál
							if (ri <= 1) {
								vup = 0;
							} else {
								next1value = goTable[ri + resultsOfCellMinus1][rj];
								next2value = goTable[ri + resultsOfCellMinus2][rj];

								vup = cellValue(next1value, next2value);
							}
							break;

						case 2: // jobbra vizsgál
							if (rj >= size - 2) {
								vrigth = 0;
							} else {
								next1value = goTable[ri][rj + resultsOfCellPlus1];
								next2value = goTable[ri][rj + resultsOfCellPlus2];

								vrigth = cellValue(next1value, next2value);
							}
							break;

						case 3: // balra vizsgál
							if (rj <= 1) {
								vleft = 0;
							} else {
								next1value = goTable[ri][rj + resultsOfCellMinus1];
								next2value = goTable[ri][rj + resultsOfCellMinus2];

								vleft = cellValue(next1value, next2value);
							}
							break;

						}
					}
					va = vdown + vup + vrigth + vleft;
					ci = ri;
					cj = rj;

					Coordinate A1 = new Coordinate(ci, cj);
					if (vl == va) {
						maxCoordinates.add(A1);

					}
					if (vl < va) {
						maxCoordinates.clear();
						maxCoordinates.add(A1);
						vl = va;
					}
					
				}

				int listSize = maxCoordinates.size();
				Random NrOfList = new Random();
				int NrOfLi = 0;
				NrOfLi = NrOfList.nextInt(listSize);
				Coordinate randomizedCoordinate = maxCoordinates.get(NrOfLi);
				
				li = randomizedCoordinate.i;
				lj = randomizedCoordinate.j;
				
				
				
//				if (vl <= va) {
//
//					li = ci;
//					lj = cj;
//
//					vl = va;
//				}
//				if (vl == va) {
//
//					Random rcell = new Random();
//					int rc = 0;
//					rc = rcell.nextInt(2);
//
//					if (rc == 0) {
//
//						li = ci;
//						lj = cj;
//
//						vl = va;
//					}
//				}

			}
		}

		i = li;
		j = lj;
	}

	/*
	 * Coordinate A1 = new Coordinate (ci,cj);
	 *  maxCoordinates.add(A1);
	 * maxCoordinates.get(2);
	 *  maxCoordinates.size();
	 *  maxCoordinates.clear();
	 * maxCoordinates.remove(3);
	 *  for (Coordinate actual : maxCoordinates) {
	 * 
	 * int value = actual.i;
	 * 
	 * }
	 */
	
	
	private static int cellValue(int next1value, int next2value) {

		int v1 = 0;
		int v2 = 0;
		int v3 = 0;
		int v4 = 0;

		// gyenge védekezés
		if (next1value == 2 && next2value == 0) {
			v1 = 1;
		}
		// muszáj védekezés
		if (next1value == 2 && next2value == 1) {
			v2 = 2;
		}
		// gyenge támadás
		if (next1value == 1 && next2value == 0) {
			v3 = 3;
		}
		// támadás
		if (next1value == 1 && next2value == 2) {
			v4 = 4;

		}
		return v1 + v2 + v3 + v4;
	}

	/*
	 * 
	 * 
	 * 
	 * private static void resultsOfCell() {
	 * 
	 * int resultsOfCellPlus1 = 1; int resultsOfCellMinus1 = -1; int
	 * resultsOfCellPlus2 = 2; int resultsOfCellMinus2 = -2; int ci = 0; int cj = 0;
	 * int li = 0; int lj = 0; int ri; int rj; int va = 0; int vl = 0;
	 * 
	 * for (ri = 0; ri < size; ri++) { for (rj = 0; rj < size; rj++) {
	 * 
	 * // 0 - ha a vizsgált cella 1 vagy 2 if (goTable[ri][rj] == 1 &&
	 * goTable[ri][rj] == 2 || goTable[ri + resultsOfCellPlus1][rj] == 0 &&
	 * goTable[ri + resultsOfCellMinus1][rj] == 0 && goTable[ri][rj +
	 * resultsOfCellPlus1] == 0 && goTable[ri][rj + resultsOfCellMinus1] == 0) { va
	 * = 0; ci = ri; cj = rj; } if (goTable[ri][rj] == 0) {
	 * 
	 * // 1 - ha a vizsgált cella 0 és egy védekezés van if (goTable[ri +
	 * resultsOfCellPlus1][rj] == 2 && goTable[ri + resultsOfCellMinus1][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus1] == 0 && goTable[ri][rj +
	 * resultsOfCellMinus1] == 0 && goTable[ri + resultsOfCellPlus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri][rj +
	 * resultsOfCellMinus2] == 0) {
	 * 
	 * va = 1; ci = ri; cj = rj;
	 * 
	 * } // 2 - ha a vizsgált cella 0 és egy támadás van if
	 * (investCellZeroOneAttack(resultsOfCellPlus1, resultsOfCellMinus1,
	 * resultsOfCellPlus2, resultsOfCellMinus2, ri, rj)) {
	 * 
	 * va = 2; ci = ri; cj = rj; } // 3 - ha a vizsgált cella 0 és két védekezés van
	 * if (goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0
	 * ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 3; ci = ri; cj = rj; }
	 * 
	 * // 4 - ha a vizsgált cella 0 és egy támadás és egy védekezés van if
	 * (goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0
	 * ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0
	 * ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 4; ci = ri; cj = rj; } // 5 - ha a vizsgált cella 0 és két támadás van
	 * if (goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0
	 * ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 5; ci = ri; cj = rj; } // 6 - ha a vizsgált cella 0 és három védekezés
	 * van if (goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 6; ci = ri; cj = rj; }
	 * 
	 * // 7 - ha a vizsgált cella 0 és egy támadás és két védekezés van if
	 * (goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 7; ci = ri; cj = rj; } // 8 - ha a vizsgált cella 0 és két támadás és
	 * egy védekezés van if (goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri
	 * + resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1
	 * && goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2
	 * 
	 * && goTable[ri + resultsOfCellMinus2][rj] == 0 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 8; ci = ri; cj = rj; } // 9 - ha a vizsgált cella 0 és három támadás van
	 * if (goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 9; ci = ri; cj = rj; }
	 * 
	 * // 10 - ha a vizsgált cella 0 és négy védekezés van if
	 * (tableWithAllParameter(resultsOfCellPlus1, resultsOfCellMinus1,
	 * resultsOfCellPlus2, resultsOfCellMinus2, ri, rj, 2)) {
	 * 
	 * va = 10; ci = ri; cj = rj; }
	 * 
	 * // 11 - ha a vizsgált cella 0 és egy támadás és három védekezés van
	 * 
	 * if (goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 11; ci = ri; cj = rj; } // 12 - ha a vizsgált cella 0 és két támadás és
	 * két védekezés van if (goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri
	 * + resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2
	 * && goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0
	 * ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 && goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 12; ci = ri; cj = rj; } // 13 - ha a vizsgált cella 0 és három támadás
	 * és egy védekezés van if (goTable[ri + resultsOfCellPlus1][rj] == 1 &&
	 * goTable[ri + resultsOfCellMinus1][rj] == 1 && goTable[ri][rj +
	 * resultsOfCellPlus1] == 1 && goTable[ri][rj + resultsOfCellMinus1] == 2 &&
	 * goTable[ri + resultsOfCellPlus2][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 2 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 2 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 2 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 && goTable[ri + resultsOfCellMinus2][rj] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0) {
	 * 
	 * va = 13; ci = ri; cj = rj; }
	 * 
	 * // 14 - ha a vizsgált cella 0 és négy támadás van if
	 * (tableWithAllParameter(resultsOfCellPlus1, resultsOfCellMinus1,
	 * resultsOfCellPlus2, resultsOfCellMinus2, ri, rj, 1)) {
	 * 
	 * va = 14; ci = ri; cj = rj; }
	 * 
	 * }
	 * 
	 * if (vl <= va) {
	 * 
	 * li = ci; lj = cj;
	 * 
	 * vl = va; } if (vl == va) {
	 * 
	 * Random rcell = new Random(); int rc = 0; rc = rcell.nextInt(2);
	 * 
	 * if (rc == 0) {
	 * 
	 * li = ci; lj = cj;
	 * 
	 * vl = va; } else { li = li; lj = lj;
	 * 
	 * vl = vl;
	 * 
	 * }
	 * 
	 * if (vl >= va) {
	 * 
	 * li = li; lj = lj;
	 * 
	 * vl = vl; } } }
	 * 
	 * } i = li; j = lj; }
	 * 
	 * adott eset int riplus1 = ; int riminus1 = ; int rjplus1 = ; int rjminus1 = ;
	 * int riplus1 = ; int riplus1 = ;
	 * 
	 * 
	 * függvény goTable[ri + resultsOfCellPlus1][rj] == riplus1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == riminus1 && goTable[ri][rj + resultsOfCellPlus1]
	 * == rjplus1 && goTable[ri][rj + resultsOfCellMinus1] == rjminus1
	 * 
	 * && goTable[ri + resultsOfCellPlus2][rj] == riplus2 && goTable[ri +
	 * resultsOfCellMinus2][rj] == riminus2 && goTable[ri][rj + resultsOfCellPlus2]
	 * == rjplus2 && goTable[ri][rj + resultsOfCellMinus2] == rjminus
	 * 
	 * private static boolean tableWithAllParameter(int resultsOfCellPlus1, int
	 * resultsOfCellMinus1, int resultsOfCellPlus2, int resultsOfCellMinus2, int ri,
	 * int rj, int playerID) { return goTable[ri + resultsOfCellPlus1][rj] ==
	 * playerID && goTable[ri + resultsOfCellMinus1][rj] == playerID &&
	 * goTable[ri][rj + resultsOfCellPlus1] == playerID && goTable[ri][rj +
	 * resultsOfCellMinus1] == playerID
	 * 
	 * && goTable[ri + resultsOfCellPlus2][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 && goTable[ri][rj + resultsOfCellPlus2] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus2] == 0; }
	 * 
	 * private static boolean investCellZeroOneAttack(int resultsOfCellPlus1, int
	 * resultsOfCellMinus1, int resultsOfCellPlus2, int resultsOfCellMinus2, int ri,
	 * int rj) { return goTable[ri + resultsOfCellPlus1][rj] == 1 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellPlus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 1 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri +
	 * resultsOfCellMinus2][rj] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 1 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 0 && goTable[ri][rj +
	 * resultsOfCellPlus2] == 0 ||
	 * 
	 * goTable[ri + resultsOfCellPlus1][rj] == 0 && goTable[ri +
	 * resultsOfCellMinus1][rj] == 0 && goTable[ri][rj + resultsOfCellPlus1] == 0 &&
	 * goTable[ri][rj + resultsOfCellMinus1] == 1 && goTable[ri][rj +
	 * resultsOfCellMinus2] == 0; }
	 */
	private static void attack() {

		int attackPlus1 = 1;
		int attackMinus1 = -1;
		int ki = 0;
		int kj = 0;
		int ti;
		int tj;

		Random attackTactic = new Random();
		int aT = attackTactic.nextInt(4);

		for (ti = 0; ti < size; ti++) {
			for (tj = 0; tj < size; tj++) {

				if (goTable[ti][tj] == 1) {
					if (aT == 0 && ti + attackPlus1 < size && ti + attackMinus1 >= 0
							&& goTable[ti + attackPlus1][tj] == 0 && goTable[ti + attackMinus1][tj] == 2) {
						ki = ti + attackPlus1;
						kj = tj;
					} else if (aT == 1 && ti + attackPlus1 < size && ti + attackMinus1 >= 0
							&& goTable[ti + attackPlus1][tj] == 2 && goTable[ti + attackMinus1][tj] == 0) {
						ki = ti + attackMinus1;
						kj = tj;
					} else if (aT == 2 && tj + attackPlus1 < size && tj + attackMinus1 >= 0
							&& goTable[ti][tj + attackPlus1] == 0 && goTable[ti][tj + attackMinus1] == 2) {
						ki = ti;
						kj = tj + attackPlus1;
					} else if (aT == 3 && tj + attackPlus1 < size && tj + attackMinus1 >= 0
							&& goTable[ti][tj + attackPlus1] == 2 && goTable[ti][tj + attackMinus1] == 0) {
						ki = ti;
						kj = tj + attackMinus1;
					} else if (aT == 0 && aT == 1 && aT == 2 && aT == 3 && ti + attackPlus1 < size
							&& ti + attackMinus1 >= 0 && goTable[ti + attackPlus1][tj] == 0
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

	private static void defend() {

		int defendPlus1 = 1;
		int defendMinus1 = -1;
		int ki = 0;
		int kj = 0;
		int ti;
		int tj;

		Random defendTactic = new Random();
		int dT = defendTactic.nextInt(4);

		for (ti = 0; ti < size; ti++) {
			for (tj = 0; tj < size; tj++) {

				if (goTable[ti][tj] == 2) {
					if (dT == 0 && ti + defendPlus1 < size && ti + defendMinus1 >= 0
							&& goTable[ti + defendPlus1][tj] == 0 && goTable[ti + defendMinus1][tj] == 1) {
						ki = ti + defendPlus1;
						kj = tj;
					} else if (dT == 1 && ti + defendPlus1 < size && ti + defendMinus1 >= 0
							&& goTable[ti + defendPlus1][tj] == 1 && goTable[ti + defendMinus1][tj] == 0) {
						ki = ti + defendMinus1;
						kj = tj;
					} else if (dT == 2 && tj + defendPlus1 < size && tj + defendMinus1 >= 0
							&& goTable[ti][tj + defendPlus1] == 0 && goTable[ti][tj + defendMinus1] == 1) {
						ki = ti;
						kj = tj + defendPlus1;
					} else if (dT == 3 && tj + defendPlus1 < size && tj + defendMinus1 >= 0
							&& goTable[ti][tj + defendPlus1] == 1 && goTable[ti][tj + defendMinus1] == 0) {
						ki = ti;
						kj = tj + defendMinus1;
					} else if (dT == 0 && dT == 1 && dT == 2 && dT == 3 && ti + defendPlus1 < size
							&& ti + defendMinus1 >= 0 && goTable[ti + defendPlus1][tj] == 0
							&& goTable[ti + defendMinus1][tj] == 0) {
						ki = ti + defendPlus1;
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
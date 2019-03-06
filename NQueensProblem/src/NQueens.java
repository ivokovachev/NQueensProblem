import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class NQueens {

	public static Random rnd = new Random(); 
	
	public static void generateRandomBoard(int[] queens, int[] rows, int[] d1, int[] d2) {
		for(int i = 0; i < queens.length; i++) {
			int randomRow = rnd.nextInt(queens.length);
			queens[i] = randomRow;
			rows[randomRow]++;
			d1[i + queens.length-1 - randomRow]++;
			d2[i + randomRow]++;
		}
	}
	
	public static int getColWithMaxConflicts(int[] queens, int[] rows, int[] d1, int[] d2) {
		int colWithMaxConflicts = 0;
		int maxConflicts = 0;
		List<Integer> l = new ArrayList<Integer>();
		
		for(int i = 0; i < queens.length; i++) {
			int currentColConflicts = 0;
			if(rows[queens[i]] > 1) {
				currentColConflicts += rows[queens[i]] - 1;
			}
			if(d1[i + queens.length-1 - queens[i]] > 1) {
				currentColConflicts += d1[i + queens.length-1 - queens[i]] - 1;
			}
			if(d2[i + queens[i]] > 1) {
				currentColConflicts += d2[i + queens[i]] - 1;
			}
			
			if(currentColConflicts > maxConflicts) {
				maxConflicts = currentColConflicts;
				colWithMaxConflicts = i;
				l.clear();
				l.add(i);
			}
			if(currentColConflicts == maxConflicts) {
				l.add(i);
			}
		}
		
		if(!l.isEmpty()) {
			int randCol = NQueens.rnd.nextInt(l.size());
			return l.get(randCol);
		}
		
		return colWithMaxConflicts;
	}
	
	
	public static int getRowWithMinConflicts(int[] queens, int[] rows, int[] d1, int[] d2, int col) {
		int rowWithMinConflicts = 0;
		int minConflicts = queens.length;
		List<Integer> l = new ArrayList<Integer>();

		for(int row = 0; row < queens.length; row++) {
			int currentRow = 0;
			if(row != queens[col]) {
				currentRow += rows[row];
				currentRow += d1[col + queens.length-1 -row];
				currentRow += d2[row + col];
				
				if(currentRow < minConflicts) {
					minConflicts = currentRow;
					rowWithMinConflicts = row;
					l.clear();
					l.add(row);
				}
				if(currentRow == minConflicts) {
					l.add(row);
				}
			}
		}
		
		if(!l.isEmpty()) {
			int randomRow = NQueens.rnd.nextInt(l.size());
			return l.get(randomRow);
		}
		
		return rowWithMinConflicts;
	}
	
	
	public static boolean isSolution(int[] queens, int[] rows, int[] d1, int[] d2)
	{
		for (int i = 0; i < queens.length; i++)
		{
			if (rows[i] != 1)
				return false;

			if (d1[i + queens.length - 1 - queens[i]] != d2[i + queens[i]] ||
				d1[i + queens.length - 1 - queens[i]] != 1 ||
				d2[i + queens[i]] != 1)
				return false;
		}

		return true;
	}
	
	public static void printQueensArr(int[] queens)
	{
		for(int i = 0; i < queens.length; i++) {
			for(int j = 0; j < queens.length; j++) {
				if(j == queens[i]) {
					System.out.print("*");
				} else {
					System.out.print("-");
				}
			}
			System.out.println();
		}
		
	}
	
	public static void SolveNQueen(int n, int max_iter)
	{
		int[] queens = new int[n];
		int[] rows = new int[n];
		int[] d1 = new int[2*n-1];
		int[] d2 = new int[2*n-1];
		
		generateRandomBoard(queens, rows, d1, d2);

		int i = 0;
		while (i++ < max_iter)
		{
			if (isSolution(queens, rows, d1, d2))
			{
				printQueensArr(queens);
				return;
			}

			int colWithMaxConflicts = getColWithMaxConflicts(queens, rows, d1, d2);
			int rowWithMinConflicts = getRowWithMinConflicts(queens, rows, d1, d2, colWithMaxConflicts);
		
			rows[queens[colWithMaxConflicts]]--;
			d1[colWithMaxConflicts + queens.length-1 - queens[colWithMaxConflicts]]--;
			d2[queens[colWithMaxConflicts] + colWithMaxConflicts]--;
			queens[colWithMaxConflicts] = rowWithMinConflicts;
			rows[rowWithMinConflicts]++;
			d1[colWithMaxConflicts + queens.length-1 - rowWithMinConflicts]++;
			d2[rowWithMinConflicts + colWithMaxConflicts]++;
		}

		SolveNQueen(n, max_iter);
	}
	
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Insert n: ");
		int n = input.nextInt();
		int maxIter = 2 * n;
		
		double start = System.currentTimeMillis();
		SolveNQueen(n, maxIter);

		double end = System.currentTimeMillis();
		System.out.println((end - start)/1000);
		
		input.close();
	}

}

//import java.util.Scanner;
import java.util.ArrayList;
import java.until.*;
import java.math.*;
import java.util.Random;
import java.util.Random.ints;
import java.io.*; 
import java.util.Hashtable;
import java.util.Enumeration;
public class SudokuSolver {						   
public static void main (String[] args) {
	Random rand = new Random();
	int [][] board = new int[9][9];
	int count = 0;
	ArrayList<Integer> num = new ArrayList<>(); 
	num.add(1);
	num.add(2);
	num.add(3);
	num.add(4);
	num.add(5);
	num.add(6);
	num.add(7);
	num.add(8);
	num.add(9);

	int temp = 0;
	int size = num.size();
	for (int row = 0; row < board.length; row++) {
		for (int col = 0; col < board[row].length; col++) {
			temp = rand.nextInt(size -0) + 0;
			board[row][col] = num.get(temp);
			num.remove(temp);
			size--;
		}
		num.clear();
		num.add(1);
		num.add(2);
		num.add(3);
		num.add(4);
		num.add(5);
		num.add(6);
		num.add(7);
		num.add(8);
		num.add(9);
		temp = 0;
	    size = num.size();
	}
	Hashtable<Integer, Integer> co = new Hashtable<>(); 
	Hashtable<Integer, Integer> duplicate = new Hashtable<>(); 
	//first store column into co with indices
	//find duplicates and store it into duplicate with indices
	//iterate through the columns of the duplicates row index and store row into ro with indices
	//replace the duplicate in array board with a 0
	int col_index = 0;
	for (int col = 0; col < 9; col++) {
		col_index = col;
		for (int row = 0; row < board.length; row++) {
			if (co.containsKey(board[row][col]) == true & board[row][col] != 10) {
				board[row][col] = 0;
				duplicate.put(board[row][col], row);
			}
			else {
				co.put(board[row][col], row);
			}
		}
		co.clear();
		duplicate.clear();
	}	

	co.clear();
	duplicate.clear();
	int col_end = 3;
	int row_end = 3;
	int row = 0;
	int col = 0;
	Hashtable<Integer, Integer> mini = new Hashtable<>(); 
	while(row < row_end) {
		while(col < col_end) {
			//System.out.print("ROW: " + row + " COLUMN: " + col);
			//System.out.println();
			if (mini.containsKey(board[row][col]) == true) {
				duplicate.put(board[row][col], row);
				board[row][col] = 0;
			}
			else {
				
				mini.put(board[row][col], row);
			}
			col++;
		}
		row++;
		//iterate through mini box
		if (row != 3 && row != 6 && row != 9) {
				if (col == 3) {
					//System.out.print("FIRST BOXES");
					//System.out.println();
					col = 0;
				}
				if (col == 6 ) {
					//System.out.print("SECOND BOXES");
					//System.out.println();
					col = 3;
				}
				if (col == 9 ) {
					//System.out.print("THIRD BOXES");
					//System.out.println();
					col = 6;
				}
		}

		if (row == 3 || row == 6 || row == 9) {

			if (col == 9 && row == 9) {
				break;
			}

			if (col != 9) {
				if (row == 3) {
					row = 0;
				}
				if (row == 6) {
					row = 3;
				}
				if (row == 9) {
					row = 6;
				}
				col = col_end;
				col_end = col_end + 3;
				duplicate.clear();
				mini.clear();
			}

			if (col == 9) {
				//System.out.print("NEXT ROW");
				//System.out.println();
				row = row_end;
				row_end = row_end + 3;
				if (row > 8) {
					break;
				}
				col = 0;
				col_end = 3;
				duplicate.clear();
				mini.clear();
			}
		}

	}

	for (int i = 0; i<board.length; i++ ) {
		board[0][i] = 0;
		board[2][i] = 0;
		board[4][i] = 0;
	}


	for (int i = 0; i<board.length; i++ ) {
		for (int j = 0; j<board[i].length; j++){
			if(j == 2 || j == 5 ) {
				System.out.print(board[i][j] + " " + "|" + " ");
			}
			else {
				System.out.print(board[i][j] + " ");
			}
		}
		if(i == 2 || i == 5 ) {
			System.out.println();
			System.out.print("------ ------  ------");
			System.out.println();
	 	}
		else {
			System.out.println();
		}


   	}

	System.out.println ("Solved Board:");
	
	int N = board.length;
	if (solver(board, N)) {
		
		for (int i = 0; i<board.length; i++ ) {
			for (int j = 0; j<board[i].length; j++){
				if(j == 2 || j == 5 ) {
					System.out.print(board[i][j] + " " + "|" + " ");
				}
				else {
					System.out.print(board[i][j] + " ");
				}
			}
			if(i == 2 || i == 5 ) {
				System.out.println();
				System.out.print("------ ------  ------");
				System.out.println();
			}
			else {
				System.out.println();
			}

   		}
	}

	else {
		System.out.println("No solution");
	}

}
   
public static boolean isSafe(int[][] board, int row, int col, int num) {
        // Row has the unique (row-clash)
        for (int d = 0; d < board.length; d++)
        {
             
            // Check if the number we are trying to
            // place is already present in
            // that row, return false;
            if (board[row][d] == num) {
                return false;
            }
        }
 
        // Column has the unique numbers (column-clash)
        for (int r = 0; r < board.length; r++)
        {
             
            // Check if the number
            // we are trying to
            // place is already present in
            // that column, return false;
            if (board[r][col] == num)
            {
                return false;
            }
        }
 
        // Corresponding square has
        // unique number (box-clash)
        int sqrt = (int)Math.sqrt(board.length);
        int boxRowStart = row - row % sqrt;
        int boxColStart = col - col % sqrt;
 
        for (int r = boxRowStart;
             r < boxRowStart + sqrt; r++)
        {
            for (int d = boxColStart;
                 d < boxColStart + sqrt; d++)
            {
                if (board[r][d] == num)
                {
                    return false;
                }
            }
        }
 
        // if there is no clash, it's safe
        return true;
    }

	
public static boolean solver( int[][] board, int n) {
        int row = -1;
        int col = -1;
        boolean isEmpty = true;
        for (int i = 0; i < n; i++)
        {
            for (int j = 0; j < n; j++)
            {
                if (board[i][j] == 0)
                {
                    row = i;
                    col = j;
 
                    // We still have some remaining
                    // missing values in Sudoku
                    isEmpty = false;
                    break;
                }
            }
            if (!isEmpty) {
                break;
            }
        }
 
        // No empty space left
        if (isEmpty)
        {
            return true;
        }
 
        // Else for each-row backtrack
        for (int num = 1; num <= n; num++)
        {
            if (isSafe(board, row, col, num))
            {
                board[row][col] = num;
                if (solver(board, n))
                {
                    // print(board, n);
                    return true;
                }
                else
                {
                    // replace it
                    board[row][col] = 0;
                }
            }
        }
        return false;
    }



}



 

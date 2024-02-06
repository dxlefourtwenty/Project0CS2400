import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.*;
import java.io.IOException;

// This is to simulate the game of Life
// Please use wisely.
public class GameOfLife {

	public static void main (String[] args) throws FileNotFoundException, IOException {
		Scanner scnr = new Scanner(System.in);
		File newFile;
		boolean fileValid;
        while (true) {
        System.out.print("Please enter a valid file name: ");
        String fileName = scnr.nextLine();
            newFile = new File(fileName);
            validateFile(newFile);
            if (newFile.exists()) {
            	System.out.print("How many generations to compute: ");
            	int gensToCompute = scnr.nextInt();
            	Board newBoard = new Board();
            	int gameBoard[][] = newBoard.initializeBoard(newFile);
            	System.out.println();
            	System.out.println("Generation 1");
            	System.out.println();
            	newBoard.printCurrentBoard();
            	for (int i = 1; i < gensToCompute; ++i) {
            		newBoard.computeNextGeneration(gensToCompute);
            		System.out.println();
            		System.out.println("Generation " + (i+1));
            		System.out.println();
            		newBoard.print();
            	}
            	break;
            }
        }
	
	}

	public static File validateFile(File inputFile) {
        Scanner scnr = new Scanner(System.in);
        File newFile;
        while (!inputFile.exists()) {
            if (inputFile.exists() && !inputFile.isDirectory()) {
                break;
            }
            else {
                System.out.println("File does not exist!");
                break;
            }
        }

        return inputFile;
    }

}

class Board {
	int col = 0;
	int row = 0;
	int gameBoard[][];
	int future[][];

    public int[][] initializeBoard(File inputFile) throws IOException {
    	Scanner fileScnr = new Scanner(inputFile);
    	col = fileScnr.nextInt();
    	row = fileScnr.nextInt();
    	gameBoard = new int[col][row];
        for (int i = 0; i < col; ++i) {
            for (int j = 0; j < row; ++j) {
                gameBoard[i][j] = fileScnr.nextInt();
                }
            }
        return gameBoard;
    }

    public int getColumns() {
    	return col;
    }

    public int getRows() {
    	return row;
    }

    public int getCell(int curColumn, int curRow) {
    	int cellValue = 0;
    	cellValue = gameBoard[curColumn][curRow];
    	return cellValue;
    }

    public void setCell(int curColumn, int curRow, int curValue) {
    	gameBoard[curColumn][curRow] = curValue;
    }

    public void computeNextGeneration(int gen) {
    	future = new int[col][row];
    	for (int c = 0; c < col; ++c) {
    		for (int d = 0; d < row; ++d) {

    			int aliveNeighbors = 0;
    			for (int i = -1; i <= 1; ++i) {
    				for (int j = -1; j <= 1; ++j) {
    					if ((c+i >= 0 && c+i < col) && (d+j >= 0 && d+j < row)) {
    						aliveNeighbors += gameBoard[c + i][d + j];
    					}
    				}
    			}

    			aliveNeighbors -= gameBoard[c][d];

    			if ((gameBoard[c][d] == 1) && (aliveNeighbors < 2)) {
    				future[c][d] = 0;
    			}

    			else if ((gameBoard[c][d] == 1) && (aliveNeighbors > 3)) {
    				future[c][d] = 0;
    			} 

    			else if ((gameBoard[c][d] == 0) && (aliveNeighbors == 3)) {
    				future[c][d] = 1;
    			}

    			else {
    				future[c][d] = gameBoard[c][d];
    			}   		
    		}

    	}

    	if (gen != 0) {
    		for (int i=0; i < gameBoard.length; i++)
  				for (int j=0; j < gameBoard[i].length; j++)
    				gameBoard[i][j] = future[i][j];
    	}
    }

    public void printCurrentBoard() {
    	for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (gameBoard[i][j] == 0)
                    System.out.print("0");
                else
                    System.out.print("1");
            }
            System.out.println();
        }
    }

    public void print() {
    	for (int i = 0; i < col; i++) {
            for (int j = 0; j < row; j++) {
                if (future[i][j] == 0)
                    System.out.print("0");
                else
                    System.out.print("1");
            }
            System.out.println();
        }
    }
}

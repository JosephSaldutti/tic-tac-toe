
import java.awt.Point;
import java.util.Arrays;
import java.util.Scanner;

public class TicTacToe {
	final int X = 1;
	final int O = 0;
	final int EMPTY = -1;
	int[][] myBoard = new int[3][3];
	
	public TicTacToe(){
		for(int i=0; i<3; i++){
			Arrays.fill(myBoard[i], EMPTY);
		}
	}
	
	public boolean isX(int i, int j){
		return myBoard[i][j] == X;
	}
	
	public boolean isO(int i, int j){
		return myBoard[i][j] == O;
	}
	
	//get next move from user
	public int user(){
		Scanner in = new Scanner(System.in);
		in.useDelimiter("");
		int i = in.nextInt();
		int j = in.nextInt();
		myBoard[i][j] = X;
		return gameOver();
	}
	
	//get next move from computer (analyzer)
	public int comp(){
		Analyzer a = new Analyzer(this);
		Point next = a.analyze();
		if(next.x == -1){
			return 2;
		}
		myBoard[next.x][next.y] = O;
		return gameOver();
	}
	
	/*
	 * -1 means keep playing
	 * 	0 means O wins
	 * 	1 means X wins
	 */
	public int gameOver(){
		
		//check rows and columns for three in a row
		for(int i=0; i<3; i++){
			if(myBoard[i][0] == myBoard[i][1] && myBoard[i][1] == myBoard[i][2]){
				return myBoard[i][0];
			}
			if(myBoard[0][i] == myBoard[1][i] && myBoard[1][i] == myBoard[2][i]){
				return myBoard[0][i];
			}
		}
		
		//check diagonals
		if(myBoard[0][0] == myBoard[1][1] && myBoard[1][1] == myBoard[2][2]){
			return myBoard[1][1];
		}
		if(myBoard[0][2] == myBoard[1][1] && myBoard[1][1] == myBoard[2][0]){
			return myBoard[1][1];
		}
		
		return -1;
	}
	
}

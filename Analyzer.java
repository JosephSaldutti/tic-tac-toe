
import java.awt.Point;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class Analyzer {
	TicTacToe T;
	Point next;
	final int X = 1;
	final int O = 0;
	final int EMPTY = -1;
	static Random r = new Random(1224);
	List<Point> empty;
	
	public Analyzer(TicTacToe t){
		T = t;
		next = new Point(-1, -1);
		empty = new ArrayList<Point>();
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(T.myBoard[i][j] == EMPTY){
					empty.add(new Point(i, j));
				}
			}
		}
		analyze();
	}
	
	public Point analyze(){
		next = random(empty);
		if(next.x == -1){
			return next;
		}
		middle();
		couldWin();
		corner();
		canWin(X);
		canWin(O);
		return next;
	}
	
	//checks whether player x can win on the next turn
	public void canWin(int x){
		//rows and columns
		for(int i=0; i<3; i++){
			if(T.myBoard[i][0] == EMPTY && T.myBoard[i][1] == x && T.myBoard[i][2] == x){
				next.x = i;
				next.y = 0;
			}
			if(T.myBoard[i][0] == x && T.myBoard[i][1] == EMPTY && T.myBoard[i][2] == x){
				next.x = i;
				next.y = 1;
			}
			if(T.myBoard[i][0] == x && T.myBoard[i][1] == x && T.myBoard[i][2] == EMPTY){
				next.x = i;
				next.y = 2;
			}
			if(T.myBoard[0][i] == EMPTY && T.myBoard[1][i] == x && T.myBoard[2][i] == x){
				next.x = 0;
				next.y = i;
			}
			if(T.myBoard[0][i] == x && T.myBoard[1][i] == EMPTY && T.myBoard[2][i] == x){
				next.x = 1;
				next.y = i;
			}
			if(T.myBoard[0][i] == x && T.myBoard[1][i] == x && T.myBoard[2][i] == EMPTY){
				next.x = 2;
				next.y = i;
			}
		}
		
		//diagonals
		if(T.myBoard[0][0] == EMPTY && T.myBoard[1][1] == x && T.myBoard[2][2] == x){
			next.x = 0;
			next.y = 0;
		}
		if(T.myBoard[0][0] == x && T.myBoard[1][1] == EMPTY && T.myBoard[2][2] == x){
			next.x = 1;
			next.y = 1;
		}
		if(T.myBoard[0][0] == x && T.myBoard[1][1] == x && T.myBoard[2][2] == EMPTY){
			next.x = 2;
			next.y = 2;
		}
		if(T.myBoard[0][2] == EMPTY && T.myBoard[1][1] == x && T.myBoard[2][0] == x){
			next.x = 0;
			next.y = 2;
		}
		if(T.myBoard[0][2] == x && T.myBoard[1][1] == EMPTY && T.myBoard[2][0] == x){
			next.x = 1;
			next.y = 1;
		}
		if(T.myBoard[0][2] == x && T.myBoard[1][1] == x && T.myBoard[2][0] == EMPTY){
			next.x = 2;
			next.y = 0;
		}
	}
	
	//case if there are X's in corner(s)
	public void corner(){
		
		if((T.myBoard[0][0] == X || T.myBoard[0][2] == X || T.myBoard[2][0] == X || T.myBoard[2][2] == X) && T.myBoard[1][1] == EMPTY){
			next.x = 1;
			next.y = 1;
		}
		
		if((T.myBoard[0][0] == X && T.myBoard[2][2] == X) || (T.myBoard[2][0] == X && T.myBoard[0][2] == X)){
			ArrayList<Point> e = new ArrayList<Point>();
			for(int i=0; i<3; i+=2){
				if(T.myBoard[i][1] == EMPTY){
					e.add(new Point(i, 1));
				}
				if(T.myBoard[1][i] == EMPTY){
					e.add(new Point(1, i));
				}
			}
			next = random(e);
		}
		
	}
	
	//case if middle is empty or X
	public void middle(){
		if(T.myBoard[1][1] == EMPTY){
			next.x = 1;
			next.y = 1;
		}
		//if X is the middle go in a corner
		if(T.myBoard[1][1] == X){
			ArrayList<Point> e = new ArrayList<Point>();
			for(int i=0; i<3; i+=2){
				for(int j=0; j<3; j+=2){
					e.add(new Point(i, j));
				}
			}
			next = random(e);
		}
	}

	//case if player x can set itself up for a win
	public void couldWin(){
		
		ArrayList<ArrayList<Point>> couldWin = couldWinList(X);
		couldWin.addAll(couldWinList(O));
		
		HashSet<Point> all = new HashSet<Point>();
		for(ArrayList<Point> l: couldWin) all.addAll(l);
		
		//counts which spot has the most overlap
		int c1 = 0;
		for(Point p: all){
			int c2 = 0;
			for(ArrayList<Point> l: couldWin){
				if(l.contains(p)){
					c2++;
				}
			}
			if(c2 > c1){
				c1 = c2;
				next = p;
			}
		}
	}
	
	//helper for couldWin
	public ArrayList<ArrayList<Point>> couldWinList(int x){
		
		ArrayList<ArrayList<Point>> move = new ArrayList<ArrayList<Point>>();
		
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(T.myBoard[i][j] == x){
					
					//rows and columns
					ArrayList<Point> e1 = new ArrayList<Point>();
					int k = 1;
					while(!outOfBounds(i+k, j)){
						if(T.myBoard[i+k][j] == EMPTY){
							e1.add(new Point(i+k, j));
						}
						k++;
					}
					k = 1;
					while(!outOfBounds(i-k, j)){
						if(T.myBoard[i-k][j] == EMPTY){
							e1.add(new Point(i-k, j));
						}
						k++;
					}
					if(e1.size() == 2){
						move.add(e1);
					}
					
					ArrayList<Point> e2 = new ArrayList<Point>();
					k = 1;
					while(!outOfBounds(i, j+k)){
						if(T.myBoard[i][j+k] == EMPTY){
							e2.add(new Point(i, j+k));
						}
						k++;
					}
					k = 1;
					while(!outOfBounds(i, j-k)){
						if(T.myBoard[i][j-k] == EMPTY){
							e2.add(new Point(i, j-k));
						}
						k++;
					}
					if(e2.size() == 2){
						move.add(e2);
					}
					
					
					//diagonals
					ArrayList<Point> e3 = new ArrayList<Point>();
					k = 1;
					while(!outOfBounds(i+k, j+k)){
						if(T.myBoard[i+k][j+k] == EMPTY){
							e3.add(new Point(i+k, j+k));
						}
						k++;
					}
					k = 1;
					while(!outOfBounds(i-k, j-k)){
						if(T.myBoard[i-k][j-k] == EMPTY){
							e3.add(new Point(i-k, j-k));
						}
						k++;
					}
					if(e3.size() == 2){
						move.add(e3);
					}
					ArrayList<Point> e4 = new ArrayList<Point>();
					k = 1;
					while(!outOfBounds(i-k, j+k)){
						if(T.myBoard[i-k][j+k] == EMPTY){
							e4.add(new Point(i-k, j+k));
						}
						k++;
					}
					k = 1;
					while(!outOfBounds(i+k, j-k)){
						if(T.myBoard[i+k][j-k] == EMPTY){
							e4.add(new Point(i+k, j-k));
						}
						k++;
					}
					if(e4.size() == 2){
						move.add(e4);
					}
				}
			}
		}
		return move;
	}
	
	//return a random empty point from the set
	//also checks whether board is full
	public Point random(List<Point> e){
		if(e.size() == 0){
			return new Point(-1, -1);
		}
		return e.get(r.nextInt(e.size()));
	}
	
	//is the index i, j out of bounds
	public boolean outOfBounds(int i, int j){
		if(i < 0 || i > 2 || j < 0 || j > 2){
			return true;
		}
		return false;
	}
}

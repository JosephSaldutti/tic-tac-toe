

public class Vizualizer {
	final static int DELAY = 100;
	
	public Vizualizer(){
		StdDraw.setXscale(0, 110);
		StdDraw.setYscale(0, 110);
		drawBoard();
	}
	
	//draws lines of board
	private void drawBoard(){
		for(int i=0; i<3; i++){
			StdDraw.line(10, 10+30*i, 100, 10+30*i);
			StdDraw.line(10+30*i, 10, 10+30*i, 100);
		}
		StdDraw.line(100, 10, 100, 100);
		StdDraw.line(100, 100, 10, 100);
	}
	
	//i and j are the row and column of the box
	public void drawX(int i, int j){
		if(i < 0 || i > 2 || j < 0 || j > 2){
			throw new IndexOutOfBoundsException("i or j out of bounds!");
		}
		int x = 25+30*i;
		int y = 25+30*j;
		StdDraw.line(x-10, y+10, x+10, y-10);
		StdDraw.line(x-10, y-10, x+10, y+10);
	}
	public void drawO(int i, int j){
		if(i < 0 || i > 2 || j < 0 || j > 2){
			throw new IndexOutOfBoundsException("i or j out of bounds!");
		}
		int x = 25+30*i;
		int y = 25+30*j;
		StdDraw.circle(x, y, 10);
	}
	
	//re-draw Board
	public void drawBoard(TicTacToe t){
		for(int i=0; i<3; i++){
			for(int j=0; j<3; j++){
				if(t.isX(i, j)){
					drawX(i, j);
				} else if(t.isO(i, j)){
					drawO(i, j);
				}
			}
		}
	}
	
	public void run(){
		TicTacToe t = new TicTacToe();
		int over;
		
		while(true){
			over = t.user();
			drawBoard(t);
			if(over == t.X){
				System.out.println("X wins!");
				break;
			} else if(over == t.O){
				System.out.println("O wins!");
				break;
			}
			over = t.comp();
			if(over == 2){
				System.out.println("Tie! You couldn't beat it!");
				break;
			}
			drawBoard(t);
			if(over == t.X){
				System.out.println("X wins!");
				break;
			} else if(over == t.O){
				System.out.println("O wins!");
				break;
			}
		}
	}

	public static void main(String[] args) {
		Vizualizer v = new Vizualizer();
		v.run();
	}
	
}

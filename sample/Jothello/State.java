public class State {
	
	public byte board[][]; // 2 for nothing, 1 is white, 0 is dark, same for turn
	public byte turn;
	public final static byte DARK = 0;
	public final static byte LIGHT = 1;
	public final static byte NONE = 2;
	public final static byte TIE = 3;
	
	public State() {
		board = new byte[8][8];
		
		for(int i=0;i<8;i++)
			for(int j=0;j<8;j++)
				board[i][j] = NONE;
		
		board[3][3] = LIGHT;
		board[3][4] = DARK;
		board[4][3] = DARK;
		board[4][4] = LIGHT;
		
		turn = DARK;		
	}
	
	
}

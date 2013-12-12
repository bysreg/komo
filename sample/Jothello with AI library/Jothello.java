import java.awt.Point;
import java.util.ArrayList;

public class Jothello {

	protected State state;
	private static final int dcol[] = { 0, 1, 0, -1, 1, 1, -1, -1 };
	private static final int drow[] = { -1, 0, 1, 0, -1, 1, -1, 1 };

	public Jothello() {
		state = new State();
	}

	public Jothello(String game_state) {
		state = new State();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				state.board[i][j] = (byte) Character.getNumericValue(game_state
						.charAt(i * 8 + j));
			}
		}
		state.turn = (byte) Character.getNumericValue(game_state.charAt(64));
	}

	public Jothello(State state) {
		this.state = state;
	}

	public static StringBuilder getGameStateString(State state) {
		StringBuilder sb = new StringBuilder(64);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				sb.append(state.board[i][j]);
			}
		}

		sb.append(state.turn);

		return sb;
	}

	public String getGameStateString() {
		StringBuilder sb = new StringBuilder(64);

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				sb.append(state.board[i][j]);
			}
		}

		sb.append(state.turn);

		return sb.toString();
	}

	public byte getTurn() {
		return state.turn;
	}

	public byte whoWin() {
		// The player with the most pieces on the board at the end of the game
		// wins
		// State.DARK dark wins, State.LIGHT light wins, State.TIE tie,
		// state.NONE nobody wins yet(game hasnt finished yet)

		if (getNumberOfMoves(state.board, State.DARK) != 0
				|| getNumberOfMoves(state.board, State.LIGHT) != 0) {
			return State.NONE; // nobody wins yet
		}

		int dark_pieces = 0;
		int light_pieces = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (state.board[i][j] == State.DARK)
					dark_pieces++;
				else if (state.board[i][j] == State.LIGHT)
					light_pieces++;
			}
		}

		if (dark_pieces > light_pieces)
			return State.DARK;
		else if (dark_pieces < light_pieces)
			return State.LIGHT;
		else
			return State.TIE;
	}

	public boolean isLegalMove(int row, int col) {
		return Jothello.isLegalMove(state, row, col);
	}

	public boolean isLegalMove(byte turn, int row, int col) {
		return Jothello.isLegalMove(state.board, turn, row, col);
	}

	public static boolean isLegalMove(byte[][] board, byte turn, int row,
			int col) {
		if (row < 0 || col < 0 || row >= 8 || col >= 8)
			return false;
		if (board[row][col] != State.NONE)
			return false;

		for (int i = 0; i < 8; i++) {
			int check_row = row + drow[i];
			int check_col = col + dcol[i];
			int num_flipped = 0;

			while (true) {
				if (check_row < 0 || check_col < 0 || check_row >= 8
						|| check_col >= 8)
					break;
				if (board[check_row][check_col] == State.NONE)
					break;
				if (board[check_row][check_col] == turn && num_flipped == 0)
					break;
				if (board[check_row][check_col] == turn)
					return true;
				num_flipped++;
				check_row += drow[i];
				check_col += dcol[i];
			}
		}

		return false;
	}

	public static boolean isLegalMove(State state, int row, int col) {
		return Jothello.isLegalMove(state.board, state.turn, row, col);
	}
	
	//mengambil jumlah langkah legal(langkah pass tidak dihitung) yang munkgin
	public static int getNumberOfMoves(byte[][] board, byte turn) {
		int count = 0;

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isLegalMove(board, turn, i, j))
					count++;
			}
		}

		return count;
	}
	
	public int getNumberOfMovesWithPass() {
		return Math.max(getNumberOfMoves(state), 1);
	}

	public static int getNumberOfMoves(State state) {
		return getNumberOfMoves(state.board, state.turn);
	}

	public int getNumberOfMoves() {
		return Jothello.getNumberOfMoves(state);
	}

	//mengembalikan daftar langkah yang bisa dilakukan (termasuk langkah pass, jika tidak ada langkah legal lain)
	public ArrayList<Point> getAllMoves() {
		ArrayList<Point> points = new ArrayList<Point>();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (isLegalMove(i, j))
					points.add(new Point(j, i));
			}
		}
		
		if(points.size() == 0)
			points.add(new Point(-1, -1)); //langkah pass
		
		return points;
	}

	private void nextTurn() {
		state.turn = (byte) ((state.turn + 1) % 2);
	}

	public boolean putPiece(int row, int column) {
		if(row == -1 && column == -1 && getNumberOfMoves() == 0) {
			//langkah -1, -1 berarti maksudnya si player ingin pass(hanya bisa jika player tidak punya legal move)	
			nextTurn();
			return true;
		}			
		
		if (!isLegalMove(row, column))
			return false;

		state.board[row][column] = state.turn;
		flipPiece(row, column);
		nextTurn();
		return true;
	}

	private void flipPiece(int row, int col) {
		byte new_piece = state.board[row][col];
		for (int i = 0; i < 8; i++) {
			int check_row = row + drow[i];
			int check_col = col + dcol[i];
			int num_flipped = 0;

			while (true) {
				if (check_row < 0 || check_col < 0 || check_row >= 8
						|| check_col >= 8)
					break;
				if (state.board[check_row][check_col] == State.NONE)
					break;
				if (state.board[check_row][check_col] == new_piece
						&& num_flipped == 0)
					break;
				if (state.board[check_row][check_col] == new_piece) {
					for (int j = 0; j < num_flipped; j++) {
						check_row -= drow[i];
						check_col -= dcol[i];
						state.board[check_row][check_col] = new_piece;
					}
					break;
				}
				num_flipped++;
				check_row += drow[i];
				check_col += dcol[i];
			}
		}
	}

	public static State parseGameStateString(String game_state) {
		State state = new State();
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				state.board[i][j] = (byte) Character.getNumericValue(game_state
						.charAt(i * 8 + j));
			}
		}
		state.turn = (byte) Character.getNumericValue(game_state.charAt(64));

		return state;
	}

	public void printBoard() {
		System.out.print(' ');
		for (int i = 0; i < 8; i++)
			System.out.print(i);
		System.out.println(' ');
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if (j == 0)
					System.out.print(i);
				if (isLegalMove(i, j))
					System.out.print('?');
				else if (state.board[i][j] == State.NONE)
					System.out.print('_');
				else
					System.out.print(state.board[i][j]);
			}
			System.out.println(' ');
		}
	}
}

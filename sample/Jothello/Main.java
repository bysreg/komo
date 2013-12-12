import java.awt.Point;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {				
		Jothello jothello = new Jothello();
		Scanner scanner = new Scanner(System.in);
		do {		
			jothello.printBoard();
			System.out.println("current turn : " + (jothello.getTurn() == State.DARK ? "DARK" : "LIGHT"));
			byte turn = jothello.getTurn();			
			int row = scanner.nextInt();
			int col = scanner.nextInt();
			Point p = new Point(col, row);							
			jothello.putPiece(p.y, p.x);			
			System.out.printf("P%d langkah ke [%d, %d] sukses%n", turn, p.y, p.x);			
		}while(jothello.whoWin() == State.NONE);
		System.out.println("winner : " + jothello.whoWin());
		scanner.close();
	}
}

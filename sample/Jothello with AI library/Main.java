import java.awt.Point;

public class Main {

	public static void main(String[] args) {
		Ai ai_1 = new Ai("my_ai_1"); // dark		
		Ai ai_2 = new Ai("my_ai_2"); // light
		Jothello jothello = new Jothello();
		int count = 0;		
		do {	
			jothello.printBoard();
			byte turn = jothello.getTurn();		
			Point p = null;
			if(turn == State.DARK) {
				p = ai_1.selectMove(jothello);
			}else{
				p = ai_2.selectMove(jothello);
			}
				
			jothello.putPiece(p.y, p.x);
			System.out.printf("P%d langkah ke [%d, %d] sukses%n", turn, p.y, p.x);
			count++;			
		}while(jothello.whoWin() == State.NONE);
		System.out.printf("turns : %d , ", count);
		System.out.println("winner : " + (jothello.whoWin() == State.DARK ? "Dark" : "Light"));
	}
	
}

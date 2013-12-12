#include "CChecker.h"
#include "Ai.h"

#include <cstdio>

using namespace std;

int main() {
	// AI vs AI
	Ai ai_1("my_ai_1");	
	Ai ai_2("my_ai_2");
				
	CChecker ca(10);
	int count = 0;
	do {		
		int turn = ca.getTurn();			
		GameMove m;
		if(turn == 1)
			m = ai_1.selectMove(ca);
		else
			m = ai_2.selectMove(ca);
		ca.movePiece(m);
		printf("P%d langkah dari [%d, %d] ke [%d, %d] sukses\n", turn, m.from.row, m.from.col, m.to.row, m.to.col);
		count++;			
	}while(ca.whoWin() == 0);
	printf("end game reached!\n");
	printf("turns : %d , result : %d\n", count, ca.whoWin());								
	return 0;
}
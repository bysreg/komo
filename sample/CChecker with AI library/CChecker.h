#pragma once

#include <string>
#include <vector>
#include <cmath>
#include <iostream>

using namespace std;

struct Point {
	int row;
	int col;

	Point() {row = 0; col = 0;}
	Point(int row, int col) {
		this->row = row;
		this->col = col;
	}
};

struct GameMove {
	Point from;
	Point to;	

	GameMove() {}
	GameMove(Point from, Point to) {
		this->from = from;
		this->to = to;
	}
};

struct GameTile {
public:
	bool is_there_piece;
	int player_number; // 1 or 2 
	bool is_king;

	GameTile() {
		is_there_piece = false;
		is_king = false;
		player_number = 1;
	}
	GameTile(bool is_there_piece, int player_number, bool is_king) {
		this->is_there_piece = is_there_piece;
		this->player_number = player_number;
		this->is_king = is_king;
	}
};

class CChecker
{
private:
	int size_;
	vector<vector<GameTile> > board_;
	int turn_;
	bool any_eatable_; // apakah ada piece musuh yang bisa dimakan
	int win_;

	void removePiece(Point p);
	void setPiece(Point p, int player_number, bool is_king);

public:	
	CChecker(int size);
	CChecker(const string& game_state);	

	//yang diperlukan AI 
	string getGameStateString() const;
	static CChecker parseGameStateString(const string& game_state);
	CChecker simulate(GameMove move) const;	
	vector<GameMove> getAllLegalMoves() const;
	int getTurn() const;
	int whoWin() const;

	int getSize() const;
	bool movePiece(GameMove move);
	vector<Point> getWalkableFromCoinInTile(int row,int col) const; 	
	static GameTile convertToTile(char c);
	void printBoard() const;	
	bool isThereEatable() const;
	bool nextTurn();		
	bool isMoveValid(GameMove move) const;
};


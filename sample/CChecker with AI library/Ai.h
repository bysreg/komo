#pragma once
#include "CChecker.h"
#include "lua.hpp"
#include "luaconf.h"

using namespace std;

class Ai
{
public:	
	Ai(string);
	GameMove selectMove(const CChecker& cchecker);
	~Ai();	
	static void staticInit();

private:
	static lua_State *L;
    int profile_ref;
};

typedef int (*lua_CFunction) (lua_State *L);	
#include <cstdlib>
#include "Ai.h"

void bail(lua_State *L, const char *msg){
	fprintf(stderr, "\nFATAL ERROR:\n  %s: %s\n\n", msg, lua_tostring(L, -1));	
	getchar(); //pause before exit
	exit(1);
}

// return number of legal moves according to State game_state
static int l_getNumberOfMoves(lua_State *L) {
	string game_state = luaL_checkstring(L, 1);
	CChecker checker(game_state);
	vector<GameMove> legalMoves = checker.getAllLegalMoves();	
	lua_pushnumber(L, legalMoves.size());
	return 1;
}

//return new State from game_state with move number move_num
static int l_simulate(lua_State *L) {
	string game_state = luaL_checkstring(L, 1);
	int move_num = luaL_checkinteger(L, 2);
	CChecker checker(game_state);
	vector<GameMove> legalMoves = checker.getAllLegalMoves();
	checker.movePiece(legalMoves[move_num]);
	lua_pushstring(L, checker.getGameStateString().c_str());
	return 1;
}

//return winner player number. 1 for the first move player, -1 for the second move player
//0 for tie, 2 for no winner yet 
static int l_whoWin(lua_State *L) {
	string game_state = luaL_checkstring(L, 1);
	CChecker checker(game_state);
	int winner = checker.whoWin(); // 0 berarti seri, 1 berarti P1, -1 berarti P2, 2 berarti blom ada yang menang
	if(winner == 2) 
		winner = -1;
	else if(winner == 1)
		winner = 1;
	else if(winner == 0)
		winner = 2;
	lua_pushinteger(L, winner);
	return 1;
}

//return 1 for first move player, 2 for second move player for State game_state
static int l_getTurn(lua_State *L) {
	string game_state = luaL_checkstring(L, 1);
	CChecker checker(game_state);	
	lua_pushinteger(L, checker.getTurn());
	return 1;
}

//maps 4 mandatory function names to their respective function name in C so that they can be used by the ai library
static const struct luaL_Reg aiclib_funcs [] = {
    {"getNumberOfMoves", l_getNumberOfMoves},
	{"simulate", l_simulate},
	{"whoWin", l_whoWin},
	{"getTurn", l_getTurn},
    {NULL, NULL}  /* sentinel */
};

//function to all the extra functions so that it can be called from the AI library and wrap it inside a library(later called aif)
int luaopen_aiclib (lua_State *L) {
	luaL_newlib(L, aiclib_funcs);	
    return 1;
}

//holds lua environment housekeeping 
lua_State* Ai::L = NULL;

//create new AI with ai_profile configuration
Ai::Ai(string ai_profile) {	
	string ai_profiles_path = "ai_profiles.lua";

	staticInit();
	lua_getglobal(L, "createProfile");
	lua_pushstring(L, ai_profiles_path.c_str());
    lua_pushstring(L, ai_profile.c_str());
    if(lua_pcall(L, 2, 1, 0))
        bail(L, "lua_pcall() failed");          /* Error out if Lua file has an error */

    profile_ref = luaL_ref(L, LUA_REGISTRYINDEX); // pop the resulting profile object and store its reference
}

Ai::~Ai() {
	luaL_unref(L, LUA_REGISTRYINDEX, profile_ref); // release the memory used by this AI profile
}

GameMove Ai::selectMove(const CChecker& checker) {
	vector<GameMove> legalMoves = checker.getAllLegalMoves();	
		
	lua_getglobal(L, "exec");
    lua_rawgeti(L, LUA_REGISTRYINDEX, profile_ref);
    lua_pushstring(L, checker.getGameStateString().c_str());

    if(lua_pcall(L, 2, 1, 0))
        bail(L, "lua_pcall() failed");

    int ret = lua_tonumber(L, -1);
    lua_pop(L, 1);  /* pop returned value */    
	return legalMoves[ret];
}

//initialize lua environment
void Ai::staticInit()
{
    if(L == NULL) {        
        string ai_lib_path = "ai.lua";
        L = luaL_newstate();
        luaL_openlibs(L);  // open all standard Lua libraries 
        luaL_requiref(L, "aif", luaopen_aiclib, 1);

		if (luaL_loadfile(L, ai_lib_path.c_str()))  // Load but don't run the Lua script
            bail(L, "luaL_loadfile() failed");      // Error out if file can't be read

        if (lua_pcall(L, 0, 0, 0))                  // Run the loaded Lua script
            bail(L, "lua_pcall() failed");          // Error out if Lua file has an error
    }
}

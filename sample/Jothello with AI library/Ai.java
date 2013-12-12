import java.awt.Point;
import java.util.ArrayList;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Ai {
		
	public final static int CORNER_WEIGHT = 10;
	public final static double DISC_WEIGHT = 0.01;	
	static LuaValue brain = null;
	static Globals globals;
	LuaValue profile;
	
	public Ai(String ai_profile) {
		String ai_lib_path = "ai.lua";
		String ai_profiles_path = "ai_profiles.lua";
		
		// create an environment to run in
		if(globals == null) {
			globals = JsePlatform.standardGlobals();
			globals.load(new aif());
			
			LuaValue lib_chunk = globals.loadFile(ai_lib_path);
			lib_chunk.call( LuaValue.valueOf(ai_lib_path));
			
			brain = globals.get("exec");		
		}
		
		// initialize and store profile object
		profile = globals.get("createProfile").call(LuaValue.valueOf(ai_profiles_path), LuaValue.valueOf(ai_profile));
	}
	
	public Point selectMove(Jothello jothello) {
		ArrayList<Point> legalMoves = jothello.getAllMoves();
		LuaValue retvals = null; 
		// get the best move index
		retvals = brain.call(profile,LuaValue.valueOf(jothello.getGameStateString()));
		return legalMoves.get(retvals.toint());
	}	
}


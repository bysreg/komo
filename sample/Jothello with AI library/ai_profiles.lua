--[[
	variable that starts with _ is reserved for internal use (_mc for monte carlo)
--]]

local default_monte_carlo = {type = "monte_carlo",time = 5,}
local monte_carlo_with_max_size = {type = "monte_carlo", time = 5, max_tree_size = 100000}
local default_minimax = {type = "minimax", time = 5, use_tt = true,}
local minimax_without_tt = {type = "minimax", time = 5,}
local minimax_max_depth_with_tt = {type = "minimax", max_depth = 6, use_tt = true}
local minimax_max_depth_with_tt_no_move_ordering = {type = "minimax", max_depth = 6, use_tt = true, no_tt_move_ordering = true}

local profiles = {	
	default_monte_carlo = default_monte_carlo, 
	default_minimax = default_minimax, 
	minimax_without_tt = minimax_without_tt, 
	minimax_max_depth_with_tt = minimax_max_depth_with_tt, 
	my_ai_1 = monte_carlo_with_max_size,--{type = "minimax", max_depth = 6, use_tt = true}, 
	my_ai_2 = minimax_max_depth_with_tt_no_move_ordering,--{type = "minimax", max_depth = 3, use_tt = true}, 
}

return profiles
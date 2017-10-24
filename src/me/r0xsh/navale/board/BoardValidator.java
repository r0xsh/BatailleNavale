package me.r0xsh.navale.board;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import org.javatuples.Pair;

import me.r0xsh.navale.boat.Boat;
import me.r0xsh.navale.boat.State;

public class BoardValidator {

	public static boolean validBoard(Board board) {

		List<Pair<Integer, Integer>> list = new ArrayList<Pair<Integer, Integer>>();

		for (Boat i : board.getBoats()) {
			for (Entry<Pair<Integer, Integer>, State> o : i.state.entrySet()) {
				if (!list.contains(o.getKey()))
					list.add(o.getKey());
				else
					return false;
			}
		}
		return true;
	}
	
	
	public static boolean validCoord(int[] coord) {
		return coord[0] >= 0 && coord[0] < Board.BOARD_SIZE &&
				coord[1] >= 0 && coord[1] < Board.BOARD_SIZE;
	}

}

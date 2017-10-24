package me.r0xsh.navale.boat;

import me.r0xsh.navale.board.Board;

public class BoatValidator {
	
	public static boolean validCoords(Boat boat) {
		int diff = 0;
		if (
				boat.coord_a[0] >= 0 && boat.coord_a[0] < Board.BOARD_SIZE &&
				boat.coord_a[1] >= 0 && boat.coord_a[1] < Board.BOARD_SIZE &&
				boat.coord_b[0] >= 0 && boat.coord_b[0] < Board.BOARD_SIZE &&
				boat.coord_b[1] >= 0 && boat.coord_b[1] < Board.BOARD_SIZE
		)
		if (boat.coord_a[0] != boat.coord_b[0])
			diff = (
					Math.max(boat.coord_a[0], boat.coord_b[0]) - 
					Math.min(boat.coord_a[0], boat.coord_b[0])
				) + 1;
		else if (boat.coord_a[1] != boat.coord_b[1])
			diff = (
					Math.max(boat.coord_a[1], boat.coord_b[1]) - 
					Math.min(boat.coord_a[1], boat.coord_b[1])
				) + 1;
		else
			diff = 0;
		
		return Properties.getSize(boat.type) == diff;
	}
}

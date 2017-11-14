package me.r0xsh.navale.boat;

import me.r0xsh.navale.board.BoardValidator;

public class BoatValidator {
	
	/*
	 * Valide le placement d'un navire, regarde si la taille est respectée
	 * @return boolean
	 */
	public static boolean validCoords(Boat boat) {
		int diff = 0;
		if (
				BoardValidator.validCoord(boat.getCoord_a()) &&
				BoardValidator.validCoord(boat.getCoord_b())
		)
		if (boat.getCoord_a()[0] != boat.getCoord_b()[0])
			diff = (
					Math.max(boat.getCoord_a()[0], boat.getCoord_b()[0]) - 
					Math.min(boat.getCoord_a()[0], boat.getCoord_b()[0])
				) + 1;
		else if (boat.getCoord_a()[1] != boat.getCoord_b()[1])
			diff = (
					Math.max(boat.getCoord_a()[1], boat.getCoord_b()[1]) - 
					Math.min(boat.getCoord_a()[1], boat.getCoord_b()[1])
				) + 1;
		else
			diff = 0;
		
		return Properties.getSize(boat.getType()) == diff;
	}
}

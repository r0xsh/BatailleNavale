package me.r0xsh.navale.AiPlayer;

import java.security.SecureRandom;

import me.r0xsh.navale.board.*;
import me.r0xsh.navale.boat.*;

public class AiPlayer {
	
	private SecureRandom r = new SecureRandom();

	private Board board;
	
	private int[] nextHit;
	
	public AiPlayer(Board board) {
		this.board = board;
		initBoard();
	}
	
	/*
	 * Calcul les positions du prochain missile
	 * @return int[]
	 */
	public int[] nextHit() {
		
		do {
			this.nextHit = nextRandom();
		} while (
				this.board.getCell(this.nextHit) != Cell.Hit ||
				this.board.getCell(this.nextHit) != Cell.Miss);
		
		return this.nextHit;
	}
	
	/*
	 * Créée une position aléatoire
	 * @return int[]
	 */
	private int[] nextRandom() {
		return new int[]{r.nextInt(Board.BOARD_SIZE), r.nextInt(Board.BOARD_SIZE)};
	}
	
	/*
	 * Positionne un navire sur le plateau
	 * @param type Type
	 * @return Boat
	 */
	private Boat boatBuilder(Type type) {
		int[] pos = {0, 0};
		int diff = Properties.getSize(type) - 1;
		Boat boat = null;
		
		do {
			
			// Trouve une position sans navire
			do {
				pos = nextRandom();
			} while (this.board.getCell(pos) != Cell.Waves);
			
			// Place le navire aléatoirement entre verticale et horizontale
			if (r.nextBoolean()) {
				if (pos[0] + diff < Board.BOARD_SIZE) {
					boat = new Boat(type, pos, new int[] {pos[0] + diff, pos[1]});
				} else if (pos[0] - diff < Board.BOARD_SIZE) {
					boat = new Boat(type, pos, new int[] {pos[0] - diff, pos[1]});
				} else {
					System.out.println("Erreur lors de l'initiation de l'IA");
					System.exit(1);
				}
			} else {
				if (pos[1] + diff < Board.BOARD_SIZE) {
					boat = new Boat(type, pos, new int[] {pos[0], pos[1] + diff});
				} else if (pos[1] - diff < Board.BOARD_SIZE) {
					boat = new Boat(type, pos, new int[] {pos[0], pos[1] - diff});
				} else {
					System.out.println("Erreur lors de l'initiation de l'IA");
					System.exit(1);
				}
			}
		// Replace le navire si un autre navire se trouve déjà sur le passage
		} while(!BoatValidator.validCoords(boat));
		
		return boat;
		
	}
	
	/*
	 * Place les navires sur le plateau
	 */
	private void initBoard() {
		boolean retry = false;
		
		for (Type t : Type.values()) {
			retry = false;
			do {
				if (retry)
					this.board.undoLastBoat();
				this.board.addBoat(this.boatBuilder(t));
	
				retry = true;
			} while (!BoardValidator.validBoard(board));
		}
	}
}

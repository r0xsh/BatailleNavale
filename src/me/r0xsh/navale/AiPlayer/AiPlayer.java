package me.r0xsh.navale.AiPlayer;

import java.security.SecureRandom;
import me.r0xsh.navale.board.*;
import me.r0xsh.navale.boat.*;

public class AiPlayer {
	
	private SecureRandom r = new SecureRandom();
	
	private int shots = 0;

	private Board board;
	
	private Board otherBoard;
	
	private int[] nextHit;
	
	private int[][] AiMatrix = new int[Board.BOARD_SIZE][Board.BOARD_SIZE];
	
	public AiPlayer(Board board, Board otherBoard) {
		this.board = board;
		this.otherBoard = otherBoard;
		initBoard();
	}
	
	/*
	 * Retourne la position du prochain tir
	 * @return int[]
	 */
	public int[] nextHit() {
		this.shots++;
		if (this.shots > 3)
			return this.nextHitAI();
		else
			return this.nextHitRandom();
	}
	
	/*
	 * Calcul par probabilité où se trouve le meilleur endroit pour lancer le missile
	 * @return int[]
	 */
	private int[] nextHitAI() {
		int[] ret = {0, 0};
		int prob = 0;
		
		this.populateMatrix();
		
		for (int i = 0; i < this.AiMatrix.length; i++) {
			for (int j = 0; j < this.AiMatrix[i].length; j++)
				if (this.AiMatrix[i][j] > prob) {
					ret = new int[] {i, j};
					prob = this.AiMatrix[i][j];
				}
		}
		
		return ret;
	}
	
	/*
	 * Rempli le tableau de proba
	 */
	private void populateMatrix() {
		boolean fits;
		int hits;
		int[] coord;
		Cell coordCell;
		
		this.resetMatrix();
		
		// H : hauteur
		// L : largeur
		// l : longeur (navire)
		
		for (int H = 0; H < this.AiMatrix.length; H++) {
			for (int L = 0; L < this.AiMatrix[H].length; L++) {
				for (Boat s : this.otherBoard.getBoats()) {
					fits = true;
					hits = 0;
					for (int l = 0; l < s.getSize(); l++) {
						coord = new int[] {H, L + l};
						coordCell = this.otherBoard.getCell(coord);
						if (!BoardValidator.validCoord(coord) || coordCell == Cell.Miss) {
							fits = false;
							break;
						}
						if (coordCell == Cell.Hit)
							hits++;
					}
					if (fits)
						for (int l = 0; l < s.getSize(); l++) {
							coordCell = this.otherBoard.getCell(new int[] {H, L + l});
							if (coordCell == Cell.Waves || coordCell == Cell.Boat) {
								this.AiMatrix[H][L + l] += 5 + (hits * 30);
							}
						}
				}
			}
		}
		
		for (int H = 0; H < this.AiMatrix.length; H++) {
			for (int L = 0; L < this.AiMatrix[H].length; L++) {
				for (Boat s : this.otherBoard.getBoats()) {
					fits = true;
					hits = 0;
					for (int l = 0; l < s.getSize(); l++) {
						coord = new int[] {H + l, L};
						coordCell = this.otherBoard.getCell(coord);
						if (!BoardValidator.validCoord(coord) || coordCell == Cell.Miss) {
							fits = false;
							break;
						}
						if (coordCell == Cell.Hit)
							hits++;
					}
					if (fits)
						for (int l = 0; l < s.getSize(); l++) {
							coordCell = this.otherBoard.getCell(new int[] {H + l, L});
							if (coordCell == Cell.Waves || coordCell == Cell.Boat) {
								this.AiMatrix[H + l][L] += 5 + (hits * 5);
						}
							
				}
			}
			}
		}
	}
	
	/*
	 * Remette à zéro la matrice de l'IA
	 */
	public void resetMatrix() {
		for (int i = 0; i < this.AiMatrix.length; i++) {
			for (int j = 0; j < this.AiMatrix[i].length; j++)
				this.AiMatrix[i][j] = 0;
		}
	}
	
	/*
	 * Retourne une position aléatoire, où l'état est inconnu (ni touché, ni raté)
	 * @return int[]
	 */
	private int[] nextHitRandom() {
		
		do {
			this.nextHit = nextRandom();
		} while (
				this.otherBoard.getCell(this.nextHit) == Cell.Hit ||
				this.otherBoard.getCell(this.nextHit) == Cell.Miss);
		
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
		boolean retry;
		
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

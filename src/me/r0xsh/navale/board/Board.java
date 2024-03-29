package me.r0xsh.navale.board;

import java.util.ArrayList;
import java.util.List;

import org.javatuples.Pair;

import me.r0xsh.navale.Ui;
import me.r0xsh.navale.boat.*;

public class Board {

	public final static int BOARD_SIZE = 10;

	private int boatsCount = 0;

	private List<Boat> boats = new ArrayList<Boat>();

	private Cell[][] map = new Cell[BOARD_SIZE][BOARD_SIZE];

	public Board() {

		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[i].length; j++) {
				this.map[i][j] = Cell.Waves;
			}
		}

	}

	/*
	 * Ajouter un navire au plateau
	 * @param Boat
	 */
	public void addBoat(Boat boat) {
		boats.add(boat);
		this.boatsCount++;
	}

	/*
	 * Delete le dernier navire ajouté au plateau
	 */
	public void undoLastBoat() {
		this.boatsCount--;
		boats.remove(this.boatsCount);
	}

	/*
	 * Permet de modifier la case d'un plateau
	 * @param coord int[]
	 * @param cell Cell
	 */
	public void setCell(int[] coord, Cell cell) {
		Pair<Integer, Integer> key;

		key = new Pair<Integer, Integer>(coord[0], coord[1]);

		for (Boat i : this.boats) {
			if (i.getState().containsKey(key)) {
				switch (cell) {
				case Hit:
					i.getState().replace(key, State.Ko);
					return;
				case Boat:
					i.getState().replace(key, State.Ok);
					return;
				default:
					break;
				}
			}
		}

		try {
			this.map[coord[0]][coord[1]] = cell;
		} catch (ArrayIndexOutOfBoundsException e) {}
	}

	/*
	 * Retourne le contenu d'une case du plateau
	 * @param coord int[]
	 * @return Cell
	 */
	public Cell getCell(int[] coord) {
		Pair<Integer, Integer> key;

		key = new Pair<Integer, Integer>(coord[0], coord[1]);

		for (Boat i : this.boats) {
			if (i.getState().containsKey(key)) {
				switch (i.getState().get(key)) {
				case Ko:
					return Cell.Hit;
				case Ok:
					return Cell.Boat;
				}
			}
		}

		try {
			return this.map[coord[0]][coord[1]];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		}
	}
	
	/*
	 * Lance un missile
	 * @param coord int[]
	 */
	public void hit(int[] coord) {
		switch (this.getCell(coord)) {
		case Boat: this.setCell(coord, Cell.Hit);
			break;
		case Waves: this.setCell(coord, Cell.Miss);
			break;
		default:
			break;
		}
		for (Boat b : this.boats) {
			if (b.isSunkEvent()) {
				// Fait un truc quand ça a touché !	
			}
		}
	}
	
	/**
	 * Demande si le joueur à perdu la partie
	 * @return boolean
	 */
	public boolean itsLost() {
		for (Boat b : this.boats) {
			if (!b.isSunk())
				return false;
		}
		return true;
	}
	
	public String toString() {
		return this.toString(true);
	}
	
	public String toString(boolean hide) {
		StringBuilder r = new StringBuilder();
		int width = (Board.BOARD_SIZE * 2) + 2;
		
		// Affiche la première ligne avec les chiffres
		r.append("\n       ");
		for (int c = 1; c <= Board.BOARD_SIZE; c++)
			r.append(c + " ");
		
		r.append("\n     ");
		
		// Affiche la bordure du haut
		for (int c = 0; c < width + 1; c++)
			if (c == 0)
				r.append("╔");
			else if (c == width)
				r.append("╗");
			else
				r.append("═");
		
		for (int i = 0; i < Board.BOARD_SIZE; i++) {
			r.append("\n ");
			r.append((char)(i + 65));
			r.append("   ║");
			
			for (int j = 0; j < Board.BOARD_SIZE; j++) {
				r.append(' ');
				switch(this.getCell(new int[] {i, j})) {
				case Boat: if (hide) r.append(Ui.ANSI_CYAN + "~" + Ui.ANSI_RESET); else r.append(Ui.ANSI_YELLOW + "B" + Ui.ANSI_RESET);
					break;
				case Hit: r.append(Ui.ANSI_RED + "#" + Ui.ANSI_RESET);
					break;
				case Miss: r.append(Ui.ANSI_GREY + "@" + Ui.ANSI_RESET);
					break;
				case Waves: r.append(Ui.ANSI_CYAN + "~" + Ui.ANSI_RESET);
					break;
				default: r.append(Ui.ANSI_CYAN + "~" + Ui.ANSI_RESET);
					break;
				
				}
			}
			
			r.append(" ║");
			
		}
		
		r.append("\n     ");
		// Affiche la bordure du bas
				for (int c = 0; c < width + 1; c++)
					if (c == 0)
						r.append("╚");
					else if (c == width)
						r.append("╝");
					else
						r.append("═");
		
		return r.toString();
	}
	
	public int getBoatsCount() {
		return boatsCount;
	}

	public List<Boat> getBoats() {
		return boats;
	}

	public Cell[][] getMap() {
		return map;
	}

}

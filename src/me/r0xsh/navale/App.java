package me.r0xsh.navale;

import java.util.Scanner;


import me.r0xsh.navale.AiPlayer.AiPlayer;
import me.r0xsh.navale.board.*;
import me.r0xsh.navale.boat.*;

public class App {

	private Scanner s = new Scanner(System.in);
	private Board board_p1;
	private Board board_p2;

	App() {
		
		
		this.board_p1 = new Board();
		this.board_p2 = new Board();

		// Charge le plateau de l'IA
		AiPlayer bot = new AiPlayer(this.board_p2, this.board_p1);

		// Demande au joueur de placer ses navires
		for (Type t : Type.values()) {
			// Nettoie la console
			Utils.cleanConsole();
			// Affiche le plateau
			System.out.println(this.board_p1.toString(false));
			// Demande au joueur de placer le navire
			this.addBoat(t, this.board_p1);
		}
		
		// Tant que aucun des deux joueurs à perdu
		while (!this.board_p1.itsLost() && !this.board_p2.itsLost()) {
			
			// Nettoie la console
			Utils.cleanConsole();
			
			// Affiche le plateau
			System.out.println(this);
			
			// Demande au joueur de tirer
			this.board_p2.hit(this.parseCoord());
			
			// Demande à l'IA de tirer
			this.board_p1.hit(bot.nextHit());
			
		}
		
		if (this.board_p1.itsLost())
			System.out.println("Vous avez perdu");
		else
			System.out.println("Vous avez gagné !!");
	}
	
	
	/*
	 * Demande au joueur d'ajouter un bateau sur son plateau
	 * @param type Type
	 * @param board Board
	 */
	private void addBoat(Type type, Board board) {
		boolean retry = false;

		do {
			if (retry) {
				System.err.println("⚠  Un navire se trouve déjà sur ces coordonnées, réessayez");
				board.undoLastBoat();
			}
			board.addBoat(this.newBoat(type));

			retry = true;
		} while (!BoardValidator.validBoard(board));
	}

	/*
	 * Génère un nouveau navire
	 * @param type Type
	 * @return Boat
	 */
	private Boat newBoat(Type type) {
		Boat boat = null;
		int[] coord_a = { 0, 0 }, coord_b = { 0, 0 };
		int size;
		boolean retry = false;

		size = Properties.getSize(type);

		do {
			if (retry)
				System.err.println("⚠  Les coordonnées entrées ne correspondent à la taille du navire ou se trouve en dehors du plateau, réessayez");

			System.out.println("Entrez les coordonnées du navire '" + type + "' (" + size + " cases)");

			coord_a = parseCoord();
			coord_b = parseCoord();

			boat = new Boat(type, coord_a, coord_b);
			retry = true;
		} while (!BoatValidator.validCoords(boat));

		return boat;
	}

	/*
	 * Parse et valide les coordonnées entrées par le joueur
	 * @return int[]
	 */
	private int[] parseCoord() {
		int[] ret = { -1, -1 };
		boolean retry = false;
		
		do {
			if (retry)
				System.out.println("⚠  Les coordonnées entrées n'existent pas, réessayez");
			
			System.out.print("> ");
			ret = Utils.coordDecode(this.s.nextLine());
			retry = true;
			
		} while (!BoardValidator.validCoord(ret));
		return ret;
	}

	
	public String toString() {
		StringBuilder ret = new StringBuilder();
		String[] p1 = this.board_p1.toString(false).split("[\n]");
		String[] p2 = this.board_p2.toString().split("[\n]");
		
		for (int i = 0; i < p1.length; i++) {
			ret.append(p1[i]);
			ret.append('\t');
			ret.append(p2[i]);
			ret.append('\n');
		}
		
		
		return ret.toString();
	}
	
	public static void main(String[] args) {
		new App();
	}
}

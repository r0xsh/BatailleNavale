package me.r0xsh.navale;

import java.io.IOException;
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
		
		// Demande au joueur de placer ses navires
		for (Type t : Type.values()) {
			this.addBoat(t, this.board_p1);
			System.out.println(this.board_p1.toString(false));
		}
		
		// Charge le plateau de l'IA
		new AiPlayer(board_p2);
		
		while (true) {
				try {
					new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
				} catch (InterruptedException | IOException e) {}
			System.out.println(this.board_p2);
			this.board_p2.hit(this.parseCoord());
		}
	}
	
//	private void loadPVP() {
//		System.out.println("Joueur 1 // Positionnez vos navires");
//		this.board_p1 = new Board();
//		for (Type t : Type.values()) {
//			this.addBoat(t, this.board_p1);
//			System.out.println(this.board_p1);
//		}
//		
//		System.out.println("Joueur 2 // Positionnez vos navires");
//		this.board_p2 = new Board();
//		for (Type t : Type.values()) {
//			this.addBoat(t, this.board_p2);
//			System.out.println(this.board_p2);
//		}
//	}

	/*
	 * Demande au joueur d'ajouter un bateau sur son plateau
	 * @param type Type
	 * @param board Board
	 */
	private void addBoat(Type type, Board board) {
		boolean retry = false;

		do {
			if (retry) {
				System.out.println("⚠  Un navire se trouve déjà sur ces coordonnées, réessayez");
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
				System.out.println("⚠  Les coordonnées entrées ne correspondent à la taille du navire ou se trouve en dehors du plateau, réessayez");

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

	public static void main(String[] args) {
		new App();
	}
}

package me.r0xsh.navale;

import java.io.IOException;
import java.util.regex.*;

public class Utils {

	/*
	 * Convertie les coordonnées entrées au format joueur en format ordinateur
	 * Exemples :
	 * 	- b3 => [1, 2]
	 *  - d10 => [3, 9]
	 *  @param input String
	 *  @return int[]
	 *  @error [-1,-1]
	 */
	public static int[] coordDecode(String input) {
		int[] ret = { -1, -1 };

		Pattern pattern = Pattern.compile("^([a-zA-Z])([1-9][0-9]?)$");
		Matcher matcher = pattern.matcher(input);

		if (matcher.matches()) {
			ret[0] = ((int) matcher.group(1).trim().toUpperCase().charAt(0) - 65);
			ret[1] = Integer.parseInt(matcher.group(2)) - 1;
		}

		return ret;
	}
	
	/*
	 * Efface la console
	 */
	public static void cleanConsole() {
		
		String OS = System.getProperty("os.name").toLowerCase();
		
		if (OS.indexOf("win") >= 0)
			try {
				new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
			} catch (InterruptedException | IOException e) {}
		
		if ((OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0 )) {
			System.out.print("\033[H\033[2J");
			System.out.flush();
		}
	}

}

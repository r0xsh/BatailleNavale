package me.r0xsh.navale.boat;


public class Properties {

	
	public static int getSize(Type type) {
		int size = 0;
		
		switch (type) {
		case PorteAvion: size = 4; break;
		case SousMarin: size = 3; break;
		case Chasseur: size = 2; break;
		default : size = 3; break;
		}
		return size;
	}
}

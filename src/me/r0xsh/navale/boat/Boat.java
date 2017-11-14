package me.r0xsh.navale.boat;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;

public class Boat {

	private boolean sunk = false;
	
	private Type type;

	private int[] coord_a;
	private int[] coord_b;

	private int size;

	private Map<Pair<Integer, Integer>, State> state = new HashMap<Pair<Integer, Integer>, State>();
	
	public Boat(Type type, int[] coord_a, int[] coord_b) {
		this.type = type;
		this.coord_a = coord_a;
		this.coord_b = coord_b;
		this.size = Properties.getSize(type);
		this.initState();
	}

	/*
	 * Charge le navire en mémoire avec toutes ses cases au state -> Ok
	 */
	private void initState() {
		if (this.isVertical())
			for (int i = 0; i < this.size; i++)
				if (this.coord_a[0] < this.coord_b[0])
					this.state.put(new Pair<Integer, Integer>(this.coord_a[0] + i, this.coord_a[1]), State.Ok);
				else
					this.state.put(new Pair<Integer, Integer>(this.coord_b[0] + i, this.coord_a[1]), State.Ok);

		if (this.isHorizontal())
			for (int i = 0; i < this.size; i++)
				if (this.coord_a[1] < this.coord_b[1])
					this.state.put(new Pair<Integer, Integer>(this.coord_a[0], this.coord_a[1] + i), State.Ok);
				else
					this.state.put(new Pair<Integer, Integer>(this.coord_a[0], this.coord_b[1] + i), State.Ok);
	}

 
	/*
	 * Check si le navire est à la verticale
	 * @return boolean
	 */
	public boolean isVertical() {
		return this.coord_a[1] == this.coord_b[1];
	}

	/*
	 * Check si le navire est à l'horizontale
	 * @return boolean
	 */
	public boolean isHorizontal() {
		return this.coord_a[0] == this.coord_b[0];
	}
	

	/*
	 * Check si le bateau est coulé (event)
	 * @return boolean
	 */
	public boolean isSunkEvent() {
		if (this.sunk)
			return false;
		return this.isSunk();
	}
	
	/*
	 * Check si le bateau est coulé
	 * @return boolean
	 */
	public boolean isSunk() {
		if (this.sunk)
			return true;
		for (State s : this.state.values()) {
			if (s == State.Ok)
				return false;
		}
		this.sunk = true;
		return true;
	}
	
	/*
	 * Retourne le nombre de case du navire
	 */
	public int getSize() {
		return this.size;
	}

	public Type getType() {
		return type;
	}
	
	public int[] getCoord_a() {
		return coord_a;
	}

	public int[] getCoord_b() {
		return coord_b;
	}

	public Map<Pair<Integer, Integer>, State> getState() {
		return state;
	}

	public String toString() {
		return String.format("====\nPositionA: %s\nPositionB: %s\nType: %s\nSize: %d\n====", Arrays.toString(this.coord_a), Arrays.toString(this.coord_b), this.type, this.size);
	}

}

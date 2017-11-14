package me.r0xsh.navale.tests;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.javatuples.Pair;
import org.junit.Before;
import org.junit.Test;

import me.r0xsh.navale.boat.Boat;
import me.r0xsh.navale.boat.Properties;
import me.r0xsh.navale.boat.State;
import me.r0xsh.navale.boat.Type;

public class BoatTests {

	Boat boatA;

	Boat boatB;

	Boat boatC;

	@Before
	public void setUp() throws Exception {

		// Porte avion de 4 cases à l'horizontal
		this.boatA = new Boat(Type.PorteAvion, new int[] { 2, 1 }, new int[] { 2, 4 });

		// SousMarin de 3 cases à la vertival
		this.boatB = new Boat(Type.SousMarin, new int[] { 2, 2 }, new int[] { 4, 2 });

		// Chasseur de 2 cases en erreur
		// this.boatC = new Boat(Type.Chasseur, new int[]{2, 2}, new int[]{2,
		// 2});
	}

	@Test
	public void directionTestBoatA() {
		assertFalse(this.boatA.isVertical());
	}

	@Test
	public void directionTestBoatB() {
		assertFalse(this.boatB.isHorizontal());
	}

	@Test
	public void size() {
		int[] a = { 0, 0, 0 };
		a[0] = Properties.getSize(Type.PorteAvion);
		a[1] = Properties.getSize(Type.SousMarin);
		a[2] = Properties.getSize(Type.Chasseur);

		assertArrayEquals(new int[] { 4, 3, 2 }, a);
	}

	@Test
	public void stateValidBoatA() {
		Map<Pair<Integer, Integer>, State> state = new HashMap<Pair<Integer, Integer>, State>();
		int test = 1;

		for (int i = 0; i < this.boatA.getState().size(); ++i) {
			state.put(new Pair<Integer, Integer>(2, test), State.Ok);
			++test;
		}

		assertEquals(state, this.boatA.getState());
	}

	@Test
	public void stateValidBoatB() {
		Map<Pair<Integer, Integer>, State> state = new HashMap<Pair<Integer, Integer>, State>();
		int test = 2;

		for (int i = 0; i < this.boatB.getState().size(); ++i) {
			state.put(new Pair<Integer, Integer>(test, 2), State.Ok);
			++test;
		}

		assertEquals(state, this.boatB.getState());
	}

}

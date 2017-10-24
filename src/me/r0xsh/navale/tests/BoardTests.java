package me.r0xsh.navale.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import me.r0xsh.navale.board.*;
import me.r0xsh.navale.boat.*;

public class BoardTests {

	Board board;

	Boat boatA;

	Boat boatB;

	private Board boardErr;

	@Before
	public void setUp() throws Exception {

		this.board = new Board();

		// Porte avion de 4 cases à l'horizontal
		this.boatA = new Boat(Type.PorteAvion, new int[] { 1, 9 }, new int[] { 1, 6 });

		// SousMarin de 3 cases à la vertival
		this.boatB = new Boat(Type.SousMarin, new int[] { 5, 2 }, new int[] { 7, 2 });

		this.board.addBoat(this.boatA);

		this.board.addBoat(this.boatB);

		this.boardErr = new Board();

		this.boardErr.addBoat(this.boatA);

		this.boardErr.addBoat(this.boatA);
	}

	@Test
	public void getCellWaves() {
		assertEquals(this.board.getCell(new int[] { 5, 5 }), Cell.Waves);
	}

	@Test
	public void getCellBoatA() {
		assertEquals(this.board.getCell(new int[] { 1, 7 }), Cell.Boat);
	}

	@Test
	public void getCellBoatB() {
		assertEquals(this.board.getCell(new int[] { 6, 2 }), Cell.Boat);
	}

	@Test
	public void OutOfMap() {
		assertEquals(this.board.getCell(new int[] { 50, 50 }), null);
	}

	@Test
	public void boardValideBoard() {
		assertTrue(BoardValidator.validBoard(this.board));
	}

	@Test
	public void boardInvalideBoard() {
		assertFalse(BoardValidator.validBoard(this.boardErr));
	}

}

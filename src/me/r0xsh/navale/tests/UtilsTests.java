package me.r0xsh.navale.tests;

import static org.junit.Assert.*;

import org.junit.Test;

import me.r0xsh.navale.Utils;

public class UtilsTests {

	@Test
	public void coordDecode11() {
		assertArrayEquals(new int[] { 1, 1 }, Utils.coordDecode("b2"));
	}

	@Test
	public void coordDecode00() {
		assertArrayEquals(new int[] { 0, 0 }, Utils.coordDecode("a1"));
	}

	@Test
	public void coordDecode99() {
		assertArrayEquals(new int[] { 9, 9 }, Utils.coordDecode("j10"));
	}

}

package game;

import java.util.*;

public class IntBoard {
	
	private static final int NUM_COLS = 4;

	public IntBoard() {
		super();
	}
	
	public void calcAdjacencies() {
		
	}
	
	public void startTargets(int cell, int numSteps) {
		
	}
	
	public Set<Integer> getTargets() {
		return new HashSet<Integer>();
	}
	
	public LinkedList<Integer> getAdjList(int current) {
		return new LinkedList<Integer>();
	}
	
	public int calcIndex(int row, int col) {
		return row*NUM_COLS + col;
	}

}

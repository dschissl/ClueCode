package game;

import java.util.*;

public class IntBoard {
	
	private static final int NUM_COLS = 4;
	private LinkedList<LinkedList<Integer>> adjacencies;
	private Set<Integer> targets;
	private boolean[] visited;

	public IntBoard() {
		super();
		
		adjacencies = new LinkedList<LinkedList<Integer>>();
		targets = new HashSet<Integer>();
		visited = new boolean[16];	
	}
	
	//makes a new LinkedList containing all numbers in [nums]
	private LinkedList<Integer> makeList(int[] nums) {
		LinkedList<Integer> list = new LinkedList<Integer>();
		for (int num : nums) {
			list.add(num);
		}
		
		return list;
	}
	
	//creates list of cells adjacent to each cell
	public void calcAdjacencies() {
		adjacencies.clear();	
		
		//cell 0
		adjacencies.add(makeList(new int[] {4,1}));		
		//cell 1
		adjacencies.add(makeList(new int[] {0,5,2}));		
		//cell 2
		adjacencies.add(makeList(new int[] {1,6,3}));		
		//cell 3
		adjacencies.add(makeList(new int[] {2,7}));				
		//cell 4
		adjacencies.add(makeList(new int[] {0,8,5}));
		//cell 5
		adjacencies.add(makeList(new int[] {1,4,6,9}));		
		//cell 6
		adjacencies.add(makeList(new int[] {2,5,7,10}));	
		//cell 7
		adjacencies.add(makeList(new int[] {3,6,11}));
		//cell 8
		adjacencies.add(makeList(new int[] {4,9,12}));
		//cell 9
		adjacencies.add(makeList(new int[] {5,8,10,13}));
		//cell 10
		adjacencies.add(makeList(new int[] {6,9,11,14}));
		//cell 11
		adjacencies.add(makeList(new int[] {7,10,15}));
		//cell 12
		adjacencies.add(makeList(new int[] {8,13}));
		//cell 13
		adjacencies.add(makeList(new int[] {9,12,14}));
		//cell 14
		adjacencies.add(makeList(new int[] {10,13,15}));
		//cell 15
		adjacencies.add(makeList(new int[] {11,14}));	
	}
	
	//creates target list for cell at ([row], [col]) with [numSteps] steps
	public void startTargets(int row, int col, int numSteps) {
		startTargets(calcIndex(row, col), numSteps);
	}
	
	//creates target list for [cell] with [numSteps] steps
	public void startTargets(int cell, int numSteps) {
		//set all visited to false
		for (int i = 0; i < 16; i++) {
			visited[i] = false;
		}
		calcAdjacencies(); //create adjacency list
		targets.clear(); //clear targets
		visited[cell] = true; //set starting cell as visited
		calcTargets(cell, numSteps); //create target list
		
		//System.out.println("TARGETS FOR CELL " + cell + " WITH " + numSteps + " STEPS.");
		//printTargets();
	}
	
	//used to recursively create target list
	private void calcTargets(int cell, int numSteps) {
		LinkedList<Integer> adjacentCells = getAdjList(cell); //get all adjacent cells
		
		for (int adjCell : adjacentCells) {
			//make sure adjCell has not been visited
			if (visited[adjCell] == false) {
				visited[adjCell] = true; //set adjCell as visited
				if (numSteps == 1) {
					targets.add(adjCell); //add adjCell to target list
				}
				else {
					calcTargets(adjCell, (numSteps - 1)); //recursive call
				}
				visited[adjCell] = false; //set adjCell as not visited
			}
		}
	}
	
	//prints all values in target list
	private void printTargets() {
		for (int num : targets) {
			System.out.print("" + num + ", ");
		}
		System.out.println("");
	}
	
	//return target list
	public Set<Integer> getTargets() {
		return targets;
	}
	
	//returns list of cells adjacent to [cell]
	public LinkedList<Integer> getAdjList(int cell) {
		return adjacencies.get(cell);
	}
	
	//returns cell at ([row], [col])
	public int calcIndex(int row, int col) {
		return row*NUM_COLS + col;
	}
}

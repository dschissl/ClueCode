package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class Board {
	
	private ArrayList<BoardCell> cells;
	private Map<Character, String> rooms;
	private LinkedList<LinkedList<Integer>> adjacencies;
	private Set<BoardCell> targets;
	private boolean[] visited;
	private int numRows, numColumns;
	private String boardLayoutLocation = "boardLayout.csv", boardLegendLocation = "roomKey.txt"; //default values

	public Board() {
		super();
		
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		adjacencies = new LinkedList<LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		
		loadConfigFiles();
		calcAdjacencies();        //Calculate adjacencices when the board is initialized
	}
	
	public Board(String boardLayoutPath, String boardLegendPath) {
		super();
		
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		adjacencies = new LinkedList<LinkedList<Integer>>();
		targets = new HashSet<BoardCell>();
		boardLayoutLocation = boardLayoutPath;
		boardLegendLocation = boardLegendPath;
		
		loadConfigFiles();
		calcAdjacencies();
	}
	
	public void loadConfigFiles() {
		try {
			loadBoardLegend();
			loadBoardLayout();		
		} 
		catch (BadConfigFormatException ex) {
			System.err.println(ex.toString());
		}	
	}
	
	public void loadBoardLayout() throws BadConfigFormatException {
		Scanner in = null;
		try {
	    	FileReader reader = new FileReader(boardLayoutLocation);
	    	in = new Scanner(reader);
		  
	    	String line;
	    	int cRow = 0;
	    	int cCol = 0;
	        while (in.hasNext()) {
	        	cCol = 0;
	        	line = in.nextLine();
		        String[] strCells = line.split(",");
		        for (String strCell : strCells) {	        	
		        	if (strCell.length() > 1) {
		        		//cell is a door      		
		        		char roomChar = strCell.charAt(0);
		        		
		        		//make sure room key is valid (defined in legend)
		        		if (!rooms.containsKey(roomChar))
		        			throw new BadConfigFormatException(boardLayoutLocation);
		        		
		        		//get door direction
		        		switch (strCell.charAt(1)) {
		        			case 'L': 
		        				//add door cell to board
		        				cells.add(new RoomCell(cRow, cCol, roomChar, RoomCell.DoorDirection.LEFT));
		        				break;
		        			case 'U': 
		        				//add door cell to board
		        				cells.add(new RoomCell(cRow, cCol, roomChar, RoomCell.DoorDirection.UP));
		        				break;
		        			case 'R':
		        				//add door cell to board
		        				cells.add(new RoomCell(cRow, cCol, roomChar, RoomCell.DoorDirection.RIGHT));
		        				break;
		        			case 'D': 
		        				//add door cell to board
		        				cells.add(new RoomCell(cRow, cCol, roomChar, RoomCell.DoorDirection.DOWN));
		        				break;
		        			default: 
		        				//invalid door direction, lets make cell a regular room cell instead
		        				cells.add(new RoomCell(cRow, cCol, roomChar));
		        				//throw new BadConfigFormatException(boardLayoutLocation);
		        				break;
		        		}
		        	}
		        	else if (strCell.equalsIgnoreCase("w")) {
		        		//cell is a walkway
		        		
		        		//add walkway cell to board
		        		cells.add(new WalkwayCell(cRow, cCol));
		        	}
		        	else {
		        		//cell is a room
		        		char roomChar = strCell.charAt(0);
		        		
		        		//make sure room key is valid (defined in legend)
		        		if (!rooms.containsKey(roomChar))
		        			throw new BadConfigFormatException(boardLayoutLocation);
		        		
		        		//add room cell to board
		        		cells.add(new RoomCell(cRow, cCol, roomChar));
		        	}
		        	//increment column counter
		        	cCol++;
		        }
		        //increment row counter
		        cRow++;
		        
		        if (numColumns <= 0) {
		        	//set number of columns
		        	numColumns = cCol;
		        }
		        else {
		        	//number of columns does not match
		        	if (cCol != numColumns)
		        		throw new BadConfigFormatException(boardLayoutLocation);
		        }
	        }
	        //set number of rows
	        numRows = cRow;      
	    } 
		catch (FileNotFoundException fnf_ex) {
			System.err.println("Can't open file: " + boardLayoutLocation);
	    }	
		finally {
			in.close();	
		}
	}
	
	public void loadBoardLegend() throws BadConfigFormatException {
		Scanner in = null;
		try {
	    	FileReader reader = new FileReader(boardLegendLocation);
	    	in = new Scanner(reader);
		  
	    	String line;
	        while (in.hasNext()) {
	        	line = in.nextLine();
	        	String[] strs = line.split(",");
	        	if (strs.length != 2) {
	        		//bad line
	        		throw new BadConfigFormatException(boardLegendLocation);
	        	}
	        	if (strs[0].length() > 1) {
	        		//first part is not a single character
	        		throw new BadConfigFormatException(boardLegendLocation);
	        	}
	        	//add new room to legend
	        	rooms.put(strs[0].charAt(0), strs[1].trim());
	        }
        }
    	catch (FileNotFoundException fnf_ex) {
			System.err.println("Can't open file: " + boardLegendLocation);
	    }	
		finally {
			in.close();	
		}		
	}
	
	public int calcIndex(int row, int col) {
		return row*numColumns + col;
	}
	
	//Row, col version of getRoomCellAt
	public RoomCell getRoomCellAt(int row, int col) {
		BoardCell cellAt = cells.get(calcIndex(row, col));
		if (cellAt instanceof RoomCell)
			return (RoomCell) cellAt;
		
		return null;
	}
	//Cell version
	public RoomCell getRoomCellAt(int cell) {
		BoardCell cellAt = cells.get(cell);
		if (cellAt instanceof RoomCell)
			return (RoomCell) cellAt;
		
		return null;
	}
	
	public BoardCell getCellAt(int cell) {
		return cells.get(cell);
	}
	
	public Map<Character, String> getRooms() {
		return rooms;
	}

	public int getNumRows() {
		return numRows;
	}
	
	public int getNumColumns() {
		return numColumns;
	}
	
	//creates list of cells adjacent to each cell
	public void calcAdjacencies() {
		adjacencies.clear();
		//Step through each cell in the table
		for(int i = 0; i < numRows; i++) {
			for (int j = 0; j < numColumns; j++) {
				LinkedList<Integer> adj = new LinkedList<Integer>();
				adjacencies.add(adj);
				int curCell = calcIndex(i,j);
				boolean isDoorway = cells.get(curCell).isDoorway();
				boolean isRoom = cells.get(curCell).isRoom();
				//Room cells can't have adjacencies
				if (isRoom && !isDoorway) {
					continue;
				}
				//Test cells surrounding current cell for validity.  Add to adjacency list if
				//they are valid
				if(j - 1 >= 0) {
					int leftCell = calcIndex(i,j-1);
					BoardCell left = cells.get(leftCell);
					if (isDoorway) {                              //Can only go out the direction you came in
						RoomCell curDoor = getRoomCellAt(curCell);
						if (curDoor.getDoorDirection() == RoomCell.DoorDirection.LEFT) {
							adj.add(leftCell);
						}
					}
					else if (left.isWalkway()) {    //Add all walkways to list
						adj.add(leftCell);
					}				
					else if ((left.isDoorway())) {   //Make sure door is facing the right direction
						RoomCell leftDoor = getRoomCellAt(leftCell);
						if (leftDoor.getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
							adj.add(leftCell);
						}
					}
				}
				if(j + 1 < numColumns) {
					int rightCell = calcIndex(i,j+1);
					BoardCell right = cells.get(rightCell);
					if (isDoorway) {
						RoomCell curDoor = getRoomCellAt(curCell);
						if (curDoor.getDoorDirection() == RoomCell.DoorDirection.RIGHT) {
							adj.add(rightCell);
						}
					}
					else if (right.isWalkway()) {
						adj.add(rightCell);
					}				
					else if ((right.isDoorway())) {
						RoomCell rightDoor = getRoomCellAt(rightCell);
						if (rightDoor.getDoorDirection() == RoomCell.DoorDirection.LEFT) {
							adj.add(rightCell);
						}
					}
				}
				if(i-1 >= 0) {
					int upCell = calcIndex(i-1,j);
					BoardCell up = cells.get(upCell);
					if (isDoorway) {
						RoomCell curDoor = getRoomCellAt(curCell);
						if (curDoor.getDoorDirection() == RoomCell.DoorDirection.UP) {
							adj.add(upCell);
						}
					}
					else if (up.isWalkway()) {
						adj.add(upCell);
					}				
					else if ((up.isDoorway())) {
						RoomCell upDoor = getRoomCellAt(upCell);
						if (upDoor.getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							adj.add(upCell);
						}
					}
				}
				if(i+1 < numRows) {
					int downCell = calcIndex(i+1,j);
					BoardCell down = cells.get(downCell);
					if (isDoorway) {
						RoomCell curDoor = getRoomCellAt(curCell);
						if (curDoor.getDoorDirection() == RoomCell.DoorDirection.DOWN) {
							adj.add(downCell);
						}
					}
					else if (down.isWalkway()) {
						adj.add(downCell);
					}				
					else if ((down.isDoorway())) {
						RoomCell downDoor = getRoomCellAt(downCell);
						if (downDoor.getDoorDirection() == RoomCell.DoorDirection.UP) {
							adj.add(downCell);
						}
					}
				}				
			}
		}	
	}
	
	//creates target list for cell at ([row], [col]) with [numSteps] steps
	public void startTargets(int row, int col, int numSteps) {
		startTargets(calcIndex(row, col), numSteps);
	}
	
	//creates target list for [cell] with [numSteps] steps
	public void startTargets(int cell, int numSteps) {
		//reset visited array
		visited = new boolean[numColumns*numRows];
		calcAdjacencies(); //create adjacency list
		targets.clear(); //clear targets
		visited[cell] = true; //set starting cell as visited
		calcTargets(cell, numSteps); //create target list
	}
	
	//used to recursively create target list
	private void calcTargets(int row, int col, int numSteps) {
		calcTargets(calcIndex(row, col), numSteps);
	}
	
	//used to recursively create target list
	private void calcTargets(int cell, int numSteps) {
		LinkedList<Integer> adjacentCells = getAdjList(cell); //get all adjacent cells
		
		for (int adjCell : adjacentCells) {
			//make sure adjCell has not been visited
			if (visited[adjCell] == false) {
				visited[adjCell] = true; //set adjCell as visited
				if (numSteps == 1 || cells.get(adjCell).isDoorway()) {      //make sure doors get added to targets
					targets.add(cells.get(adjCell)); //add cell at index adjCell to target list
				}
				else {
					calcTargets(adjCell, (numSteps - 1)); //recursive call
				}
				visited[adjCell] = false; //set adjCell as not visited
			}
		}
	}
	
	//return target list
	public Set<BoardCell> getTargets() {
		return targets;
	}
	
	//returns list of cells adjacent to [cell]
	public LinkedList<Integer> getAdjList(int cell) {
		return adjacencies.get(cell);
	}

}

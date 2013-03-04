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
	private int numRows, numColumns;
	private String boardLayoutLocation = "boardLayout.csv", boardLegendLocation = "roomKey.txt"; //default values

	public Board() {
		super();
		
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		
		loadConfigFiles();
	}
	
	public Board(String boardLayoutPath, String boardLegendPath) {
		super();
		
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		boardLayoutLocation = boardLayoutPath;
		boardLegendLocation = boardLegendPath;
		
		loadConfigFiles();
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
	
	public RoomCell getRoomCellAt(int row, int col) {
		BoardCell cellAt = cells.get(calcIndex(row, col));
		if (cellAt instanceof RoomCell)
			return (RoomCell) cellAt;
		
		return null;
	}
	
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
	
	//Stub added to generate failing PathTests
	public LinkedList<Integer> getAdjList(int calcIndex) {
		// TODO Auto-generated method stub
		return new LinkedList<Integer>();
	}
	
	//Stub added to generate failing PathTests
	public void calcTargets(int row, int col, int numSteps) {
		// TODO Auto-generated method stub
		
	}

	//Stub added to generate failing PathTests
	public Set<BoardCell> getTargets() {
		// TODO Auto-generated method stub
		return new HashSet<BoardCell>();
	}

}

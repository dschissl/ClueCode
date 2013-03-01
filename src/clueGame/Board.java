package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;

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
			loadBoardLayout();
			loadBoardLegend();
		} 
		catch (BadConfigFormatException ex) {
			System.err.println(ex.toString());
		}	
	}
	
	public void loadBoardLayout() throws BadConfigFormatException {
		try {
	    	FileReader reader = new FileReader(boardLayoutLocation);
	    	Scanner in = new Scanner(reader);
		  
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
		        		RoomCell.DoorDirection dir = RoomCell.DoorDirection.DOWN;
		        		switch (strCell.charAt(1)) {
		        			case 'L': 
		        				dir = RoomCell.DoorDirection.LEFT;
		        				break;
		        			case 'U': 
		        				dir = RoomCell.DoorDirection.UP;
		        				break;
		        			case 'R': 
		        				dir = RoomCell.DoorDirection.RIGHT;
		        				break;
		        			case 'D': 
		        				dir = RoomCell.DoorDirection.DOWN;
		        				break;
		        			default: 
		        				throw new BadConfigFormatException(boardLayoutLocation);
		        		}
		        		
		        		cells.add(new RoomCell(cRow, cCol, strCell.charAt(0), dir));
		        	}
		        	else if (strCell.equalsIgnoreCase("w")) {
		        		//cell is a walkway
		        		cells.add(new WalkwayCell(cRow, cCol));
		        	}
		        	else {
		        		cells.add(new RoomCell(cRow, cCol, strCell.charAt(0)));
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
	        
	        in.close();	        
	    } 
		catch (FileNotFoundException fnf_ex) {
			System.err.println("Can't open file: " + boardLayoutLocation);
	    }	
	}
	
	public void loadBoardLegend() throws BadConfigFormatException {
		if (false)
			throw new BadConfigFormatException(boardLegendLocation);
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

}

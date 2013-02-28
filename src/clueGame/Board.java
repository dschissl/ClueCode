package clueGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
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
	}
	
	public Board(String boardLayoutPath, String boardLegendPath) {
		super();
		
		cells = new ArrayList<BoardCell>();
		rooms = new HashMap<Character, String>();
		boardLayoutLocation = boardLayoutPath;
		boardLegendLocation = boardLegendPath;
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
		if (false)
			throw new BadConfigFormatException(boardLayoutLocation);
	}
	
	public void loadBoardLegend() throws BadConfigFormatException {
		if (false)
			throw new BadConfigFormatException(boardLegendLocation);
	}
	
	public int calcIndex(int row, int col) {
		return -1;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		return new RoomCell();
	}
	
	public RoomCell getRoomCellAt(int cell) {
		return new RoomCell();
	}
	
	public BoardCell getCellAt(int cell) {
		return new RoomCell();
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

}

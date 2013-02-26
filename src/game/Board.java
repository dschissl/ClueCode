package game;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
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
	}
	
	public Board(String boardLayoutPath, String boardLegendPath) {
		super();
		
		cells = new ArrayList<BoardCell>();
		boardLayoutLocation = boardLayoutPath;
		boardLegendLocation = boardLegendPath;
	}
	
	public void loadConfigFiles() {
		loadBoardLayout();
		loadBoardLegend();
	}
	
	private void loadBoardLayout() throws BadConfigFormatException {
		if (false)
			throw new BadConfigFormatException(boardLayoutLocation);
	}
	
	private void loadBoardLegend() {
		if (false)
			throw new BadConfigFormatException(boardLegendLocation);
	}
	
	public int calcIndex(int row, int col) {
		return -1;
	}
	
	public RoomCell getRoomCellAt(int row, int col) {
		return new RoomCell();
	}

}

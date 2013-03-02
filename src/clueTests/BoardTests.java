package clueTests;

import static org.junit.Assert.*;

import java.io.FileNotFoundException;
import java.util.Map;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import clueGame.BadConfigFormatException;
import clueGame.Board;
import clueGame.BoardCell;
import clueGame.RoomCell;

public class BoardTests {
	
	public static final int NUM_ROOMS = 11;
	public static final int NUM_DOORS = 16;
	public static final int NUM_ROWS = 19;
	public static final int NUM_COLUMNS = 18;
	private Board b;

	@Before
	public void setup() {
		b = new Board();
	}

	@Test
	public void testRooms() {
		Map<Character, String> rooms = b.getRooms();

		assertEquals(NUM_ROOMS, rooms.size());

		assertEquals("Great Hall", rooms.get('G'));
		assertEquals("Bedroom", rooms.get('B'));
		assertEquals("Library", rooms.get('R'));
		assertEquals("Foyer", rooms.get('F'));
		assertEquals("Bowling Alley", rooms.get('A'));	
		assertEquals("Walkway", rooms.get('W'));
	}
	
	@Test
	public void testBoardSize() {
		assertEquals(NUM_ROWS, b.getNumRows());		
		assertEquals(NUM_COLUMNS, b.getNumColumns());	
	}
	
	@Test
	public void testDoorDirections() {
		RoomCell room = b.getRoomCellAt(2, 1);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.DOWN, room.getDoorDirection());
		
		room = b.getRoomCellAt(10, 3);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.RIGHT, room.getDoorDirection());
		
		room = b.getRoomCellAt(9, 16);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.LEFT, room.getDoorDirection());
		
		room = b.getRoomCellAt(14, 15);
		assertTrue(room.isDoorway());
		assertEquals(RoomCell.DoorDirection.UP, room.getDoorDirection());
		
		room = b.getRoomCellAt(1, 2);
		assertFalse(room.isDoorway());
		
		BoardCell cell = b.getRoomCellAt(17, 8);
		assertFalse(cell.isDoorway());		
	}
	
	@Test
	public void testNumberDoorways() 
	{
		int totalCells = b.getNumColumns() * b.getNumRows();
		Assert.assertEquals(NUM_ROWS * NUM_COLUMNS, totalCells);
		
		int numDoors = 0;
		for (int i = 0; i < totalCells; i++)
		{
			BoardCell cell = b.getCellAt(i);
			if (cell.isDoorway())
				numDoors++;
		}
		Assert.assertEquals(NUM_DOORS, numDoors);
	}
	
	@Test
	public void testCalcIndex() {
		assertEquals(0, b.calcIndex(0, 0));
		assertEquals(NUM_COLUMNS-1, b.calcIndex(0, NUM_COLUMNS-1));
		assertEquals(324, b.calcIndex(NUM_ROWS-1, 0));
		assertEquals(341, b.calcIndex(NUM_ROWS-1, NUM_COLUMNS-1));
		assertEquals(19, b.calcIndex(1, 1));
		assertEquals(41, b.calcIndex(2, 5));		
	}
	
	@Test
	public void testRoomInitials() {
		assertEquals('B', b.getRoomCellAt(0, 0).getInitial());
		assertEquals('F', b.getRoomCellAt(17, 7).getInitial());
		assertEquals('P', b.getRoomCellAt(2, 16).getInitial());
		assertEquals('G', b.getRoomCellAt(10, 2).getInitial());
		assertEquals('S', b.getRoomCellAt(NUM_ROWS-1, NUM_COLUMNS-1).getInitial());
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadColumns() throws BadConfigFormatException, FileNotFoundException {
		Board nb = new Board("ClueLayoutBadColumns.csv", "ClueLegend.txt");
		nb.loadBoardLayout();
		nb.loadBoardLegend();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoom() throws BadConfigFormatException, FileNotFoundException {
		Board nb = new Board("ClueLayoutBadRoom.csv", "ClueLegend.txt");
		nb.loadBoardLayout();
		nb.loadBoardLegend();
	}
	
	@Test (expected = BadConfigFormatException.class)
	public void testBadRoomFormat() throws BadConfigFormatException, FileNotFoundException {
		Board nb = new Board("ClueLayout.csv", "ClueLegendBadFormat.txt");
		nb.loadBoardLayout();
		nb.loadBoardLegend();
	}



}

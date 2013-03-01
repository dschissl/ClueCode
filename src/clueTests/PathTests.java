package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;
import java.util.Set;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;
import clueGame.BoardCell;

public class PathTests {

	private static Board board;
	@BeforeClass
	public static void setUp() {
		board = new Board();
		board.loadConfigFiles();
	//	board.calcAdjacencies();
	}

	//Tests for walkways near rooms and edges - not near doorways
	@Test
	public void testWalkwayAdj() {
		//Test for walkway surrounded by walkways
		LinkedList<Integer> test = board.getAdjList(board.calcIndex(13, 6));
		assertEquals(4, test.size());
		assertTrue(test.contains(board.calcIndex(14, 6)));
		assertTrue(test.contains(board.calcIndex(12, 6)));
		assertTrue(test.contains(board.calcIndex(13, 7)));
		assertTrue(test.contains(board.calcIndex(13, 5)));
		
		//Test for walkways next to each wall
		//Left Wall
		test = board.getAdjList(board.calcIndex(15,0));
		assertEquals(2,test.size());
		assertTrue(test.contains(board.calcIndex(15, 1)));
		assertTrue(test.contains(board.calcIndex(14, 0)));
		//Right Wall
		test = board.getAdjList(board.calcIndex(12, 17));
		assertEquals(2,test.size());
		assertTrue(test.contains(board.calcIndex(12, 16)));
		assertTrue(test.contains(board.calcIndex(13, 17)));
		//Bottom Wall
		test = board.getAdjList(board.calcIndex(18, 12));
		assertEquals(2,test.size());
		assertTrue(test.contains(board.calcIndex(17, 12)));
		assertTrue(test.contains(board.calcIndex(18, 13)));
		//Top Wall
		test = board.getAdjList(board.calcIndex(0, 4));
		assertEquals(2,test.size());
		assertTrue(test.contains(board.calcIndex(0, 3)));
		assertTrue(test.contains(board.calcIndex(1, 4)));
		
		//Test for walkway with only one possible direction of travel 
		//Next to two room cells.
		test = board.getAdjList(board.calcIndex(0,8));
		assertEquals(1,test.size());
		assertTrue(test.contains(board.calcIndex(0, 3)));
		
		//Test for walkway with back against closet
		test = board.getAdjList(board.calcIndex(7,11));
		assertEquals(3,test.size());
		assertTrue(test.contains(board.calcIndex(6, 11)));
		assertTrue(test.contains(board.calcIndex(7, 10)));
		assertTrue(test.contains(board.calcIndex(7, 12)));
		
		//Test for walkway with 1 room cell adjacent
		test = board.getAdjList(board.calcIndex(1, 12));
		assertEquals(3,test.size());
		assertTrue(test.contains(board.calcIndex(0,12)));
		assertTrue(test.contains(board.calcIndex(2,12)));
		assertTrue(test.contains(board.calcIndex(0,13)));
		
		//Test for walkway next to door, not facing the needed direction
		test = board.getAdjList(board.calcIndex(3,4));
		assertEquals(3,test.size());
		assertTrue(test.contains(board.calcIndex(2, 4)));
		assertTrue(test.contains(board.calcIndex(4, 4)));
		assertTrue(test.contains(board.calcIndex(3, 3)));
	}
	
	//Tests for walkways adjacent to doors
	@Test
	public void testWalkwayToDoorAdj() {
		//UP
		LinkedList<Integer> test = board.getAdjList(board.calcIndex(4, 0));
		assertEquals(3,test.size());
		assertTrue(test.contains(board.calcIndex(3, 0)));
		assertTrue(test.contains(board.calcIndex(5, 0)));
		assertTrue(test.contains(board.calcIndex(4, 1)));
		//DOWN
		test = board.getAdjList(board.calcIndex(4, 5));
		assertEquals(4,test.size());
		assertTrue(test.contains(board.calcIndex(5, 5)));
		assertTrue(test.contains(board.calcIndex(4, 4)));
		assertTrue(test.contains(board.calcIndex(4, 6)));
		assertTrue(test.contains(board.calcIndex(3, 5)));
		//LEFT
		test = board.getAdjList(board.calcIndex(3, 15));
		assertEquals(3,test.size());
		assertTrue(test.contains(board.calcIndex(3, 16)));
		assertTrue(test.contains(board.calcIndex(3, 14)));
		assertTrue(test.contains(board.calcIndex(4, 15)));
		//RIGHT
		test = board.getAdjList(board.calcIndex(15, 4));
		assertEquals(4,test.size());
		assertTrue(test.contains(board.calcIndex(14, 4)));
		assertTrue(test.contains(board.calcIndex(16, 4)));
		assertTrue(test.contains(board.calcIndex(15, 5)));
		assertTrue(test.contains(board.calcIndex(15, 3)));		
	}
	
	//Test for doorways - only adjacency is the one in the direction of the door
	@Test
	public void testDoorwayAdj() {
		//UP
		LinkedList<Integer> test = board.getAdjList(board.calcIndex(16, 8));
		assertEquals(1, test.size());
		assertTrue(test.contains(board.calcIndex(15,8)));
		//DOWN
		test = board.getAdjList(board.calcIndex(3,11));
		assertEquals(1, test.size());
		assertTrue(test.contains(board.calcIndex(4, 11)));
		//LEFT
		test = board.getAdjList(board.calcIndex(8,16));
		assertEquals(1, test.size());
		assertTrue(test.contains(board.calcIndex(8,15)));
		//RIGHT
		test = board.getAdjList(board.calcIndex(8,3));
		assertEquals(1, test.size());
		assertTrue(test.contains(board.calcIndex(8, 4)));
	}
	
	// Tests of just walkways, 1 step, includes on edge of board
	// and beside room
	// These are brown on the planning spreadsheet
	@Test
	public void testTargetsOneStep() {
		board.calcTargets(18, 5, 1);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(17, 5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(18, 4))));
		
		board.calcTargets(3, 0, 1);
		targets= board.getTargets();
		assertEquals(2, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(3,1))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(4,0))));				
	}
	
	// Tests of just walkways, 2 steps
	// These are brown on the planning spreadsheet
	@Test
	public void testTargetsTwoSteps() {
		board.calcTargets(18, 5, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(20, 6))));
		
		board.calcTargets(3, 0, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(5,0))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(4,1))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(3,2))));			
	}
	// Tests of just walkways, 4 steps
	// These are brown on the planning spreadsheet
	@Test
	public void testTargetsFourSteps() {
		board.calcTargets(18, 5, 4);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16,5))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,6))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(17,4))));
		
		board.calcTargets(0, 3, 4);
		targets= board.getTargets();
		assertEquals(4, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(4,3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(3,2))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(3,4))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(2,3))));
	}	
	
	// Tests of just walkways plus one door, 6 steps
	// These are brown on the planning spreadsheet
	@Test
	public void testTargetsSixSteps() {
		board.calcTargets(10, 10, 6);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(21, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,7))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,8))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,9))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,9))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,10))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,11))));	
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12,10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11,11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12,12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10,12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(12,14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10,14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11,15))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(7,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(8,14))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9,15))));
	}	
	
	// Test getting into a room
	// These are brown on the planning spreadsheet
	@Test 
	public void testTargetsIntoRoom()
	{
		// One room is exactly 2 away
		board.calcTargets(15, 12, 2);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(8, targets.size());
		// directly left and right
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,10))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,14)))); //door
		// directly up and down
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,12))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(17,12))));
		// one up/down, one left/right
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16,13))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,11))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(16,11))));
	}
	
	// Test getting into room, doesn't require all steps
	// These are brown on the planning spreadsheet
	@Test
	public void testTargetsIntoRoomShortcut() 
	{
		board.calcTargets(14, 0, 3);
		Set<BoardCell> targets= board.getTargets();
		assertEquals(5, targets.size());
		// directly right, can't move left
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,3))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(14,1))));
		// right two, down 1
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(15,2))));
		// right two, up 1
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13, 2))));
		// enter the room
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(13,0))));		
	}

	// Test getting out of a room
	// These are brown on the planning spreadsheet
	@Test
	public void testRoomExit()
	{
		// Take one step, essentially just the adj list
		board.calcTargets(10, 3, 1);
		Set<BoardCell> targets= board.getTargets();
		// Ensure doesn't exit through the wall
		assertEquals(1, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 4))));
		// Take two steps
		board.calcTargets(10, 3, 2);
		targets= board.getTargets();
		assertEquals(3, targets.size());
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(9, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(11, 4))));
		assertTrue(targets.contains(board.getCellAt(board.calcIndex(10, 5))));
	}


}

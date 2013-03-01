package clueTests;

import static org.junit.Assert.*;

import java.util.LinkedList;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import clueGame.Board;

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
		Assert.assertEquals(1, test.size());
		Assert.assertTrue(test.contains(board.calcIndex(15,8)));
		//DOWN
		test = board.getAdjList(board.calcIndex(3,11));
		Assert.assertEquals(1, test.size());
		Assert.assertTrue(test.contains(board.calcIndex(4, 11)));
		//LEFT
		test = board.getAdjList(board.calcIndex(8,16));
		Assert.assertEquals(1, test.size());
		Assert.assertTrue(test.contains(board.calcIndex(8,15)));
		//RIGHT
		test = board.getAdjList(board.calcIndex(8,3));
		Assert.assertEquals(1, test.size());
		Assert.assertTrue(test.contains(board.calcIndex(8, 4)));
	}
	
	

}

package game;

public class RoomCell extends BoardCell {

	public enum DoorDirection {UP, DOWN, LEFT, RIGHT};
	private DoorDirection doorDirection;
	private char roomInitial;
	
	public boolean isRoom() {
		return true;
	}
	
	public DoorDirection getDoorDirection() {
		return doorDirection;
	}
	
	public char getInitial() {
		return roomInitial;
	}
	
	/*
	 * Space to implement draw function
	 */
	
}

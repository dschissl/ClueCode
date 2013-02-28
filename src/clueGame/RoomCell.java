package clueGame;

public class RoomCell extends BoardCell {

	public enum DoorDirection {UP, DOWN, LEFT, RIGHT};
	private DoorDirection doorDirection;
	private char roomInitial;
	
	//creates a room cell
	public RoomCell(int row, int col, char roomInitial) {
		super();
		this.row = row;
		this.col = col;
		this.doorDirection = null;
		this.roomInitial = roomInitial;
	}
	
	//creates a doorway cell
	public RoomCell(int row, int col, char roomInitial, DoorDirection doorDirection) {
		super();
		this.row = row;
		this.col = col;
		this.doorDirection = doorDirection;
		this.roomInitial = roomInitial;
	}

	@Override
	public boolean isRoom() {
		return true;
	}
	
	@Override
	public boolean isDoorway() {
		return !(doorDirection == null);
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

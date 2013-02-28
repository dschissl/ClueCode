package clueGame;

public class WalkwayCell extends BoardCell {

	public WalkwayCell(int row, int col) {
		super();
		this.row = row;
		this.col = col;
	}

	@Override
	public boolean isWalkway() {
		return true;
	}
	
	/*
	 * Space to implement draw function
	 */
	
}

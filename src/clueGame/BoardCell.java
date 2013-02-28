package clueGame;

public abstract class BoardCell {

	protected int row;
	protected int col;
	
	public boolean isWalkway() {
		return false;
	}
	
	public boolean isRoom() {
		return false;
	}
	
	public boolean isDoorway() {
		return false;
	}
	
	/*
	 * Space to enter draw function
	 */
	 
}

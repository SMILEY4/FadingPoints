package com.ruegnerlukas.ld39.game;

public class Snapshot {

	
	private Grid grid;
	
	
	public Snapshot(Grid grid) {
		this.grid = new Grid(grid.getWidth(), grid.getHeight());
		for(int y=0; y<grid.getWidth(); y++) {
			for(int x=0; x<grid.getHeight(); x++) {
				this.grid.getCells()[x][y] = new Cell(x, y);
				this.grid.getCells()[x][y].setObject(grid.getCells()[x][y].getObject().copy());
			}
		}
	}

	
	
	public Grid getGrid() {
		return this.grid;
	}
	
	
	
	
	
	
	
}

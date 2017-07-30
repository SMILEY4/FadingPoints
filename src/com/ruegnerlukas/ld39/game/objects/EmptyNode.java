package com.ruegnerlukas.ld39.game.objects;

import java.awt.Graphics2D;

import com.ruegnerlukas.ld39.game.Data;

public class EmptyNode extends CellObject {

	
	
	
	public EmptyNode(int x, int y) {
		super(x, y);
	}
	
	
	
	
	@Override
	public void draw(Graphics2D g, int offX, int offY, int cellSize) {
		int x = (getX() * cellSize + offX) + (cellSize/2) - (Data.CELL_POINT_SIZE/2);
		int y = (getY() * cellSize + offY) + (cellSize/2) - (Data.CELL_POINT_SIZE/2);
		int w = Data.CELL_POINT_SIZE;
		int h = Data.CELL_POINT_SIZE;
		g.setColor(Data.COLOR_GRID_POINTS);
		g.fillOval(x, y, w, h);
	}

	
	@Override
	public CellObject copy() {
		return new EmptyNode(getX(), getY());
	}
	

}

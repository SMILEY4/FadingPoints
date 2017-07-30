package com.ruegnerlukas.ld39.game.objects;

import java.awt.Graphics2D;

public abstract class CellObject {

	
	private int x, y;
	
	
	public CellObject(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	
	
	
	public abstract void draw(Graphics2D g, int offX, int offY, int cellSize);
	
	
	public abstract CellObject copy();
	
	
	public int getX() {
		return x;
	}
	
	
	public int getY() {
		return y;
	}
	
}

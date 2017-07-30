package com.ruegnerlukas.ld39.game;

import com.ruegnerlukas.ld39.game.objects.CellObject;
import com.ruegnerlukas.ld39.game.objects.EmptyNode;

public class Cell {

	private int x, y;
	private CellObject obj;
	
	
	public Cell(int x, int y) {
		this.x = x;
		this.y = y;
		this.obj = new EmptyNode(x, y);
	}
	
	
	
	
	
	
	public int getX() {
		return x;
	}
	
	
	
	
	public int getY() {
		return y;
	}
	
	
	
	
	public void setObject(CellObject obj) {
		this.obj = obj;
	}
	
	
	
	
	public CellObject getObject() {
		return obj;
	}
	

}

package com.ruegnerlukas.ld39.game.objects;

import java.awt.Graphics2D;

import com.ruegnerlukas.ld39.game.Data;

public class Wall extends CellObject {

	
	
	private long ts;
	private long t = (long)(0.125f * 1000);
	private float scale = 0.00001f;
	
	
	
	
	public Wall(int x, int y) {
		super(x, y);
		ts = System.currentTimeMillis();
	}

	
	
	
	@Override
	public void draw(Graphics2D g, int offX, int offY, int cellSize) {
		long tl = System.currentTimeMillis() - ts;
		float perc = (float)tl / (float)t;
		scale = Math.max(0, Math.min(perc, 1f));
		
		int x = (int) ((getX() * cellSize + offX) + (cellSize/2) - (Data.WALL_POINT_SIZE*scale/2));
		int y = (int) ((getY() * cellSize + offY) + (cellSize/2) - (Data.WALL_POINT_SIZE*scale/2));
		int w = (int) (Data.WALL_POINT_SIZE*scale);
		int h = (int) (Data.WALL_POINT_SIZE*scale);
		g.setColor(Data.COLOR_WALL);
		g.fillOval(x, y, w, h);
	}




	@Override
	public CellObject copy() {
		return new Wall(getX(), getY());
	}


}






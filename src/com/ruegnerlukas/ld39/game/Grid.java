package com.ruegnerlukas.ld39.game;

import java.awt.Color;
import java.awt.Graphics2D;

import com.ruegnerlukas.ld39.FadingPoints;
import com.ruegnerlukas.ld39.game.objects.EmptyNode;

public class Grid {


	private int width;
	private int height;
	private int cellSize;
	
	private Cell[][] cells;
	
	
	
	public Grid(int width, int height) {
		this.width = width;
		this.height = height;
		this.cellSize = Data.CELL_SIZE;
		this.cells = new Cell[width][height];
		for(int y=0; y<height; y++) {
			for(int x=0; x<width; x++) {
				cells[x][y] = new Cell(x, y);
				cells[x][y].setObject(new EmptyNode(x, y));
			}
		}
	}

	
	
	public int getWidth() {
		return width;
	}





	public int getHeight() {
		return height;
	}





	public int getCellSize() {
		return cellSize;
	}

	
	
	public Cell getCell(int x, int y) {
		if( (0 <= x && x < width) && (0 <= y && y < height)) {
			return cells[x][y];
		}
		return null;
	}


	public Cell[][] getCells() {
		return this.cells;
	}


	
	
}








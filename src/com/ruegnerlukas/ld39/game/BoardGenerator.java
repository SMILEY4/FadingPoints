package com.ruegnerlukas.ld39.game;

import java.util.Random;

import com.ruegnerlukas.ld39.game.objects.EmptyNode;
import com.ruegnerlukas.ld39.game.objects.EnemyBase;
import com.ruegnerlukas.ld39.game.objects.EnemyMinion;
import com.ruegnerlukas.ld39.game.objects.PlayerBase;
import com.ruegnerlukas.ld39.game.objects.Wall;

public class BoardGenerator {

	
	
	
	
	
	public static GameBoard generate(long seed) {
		Random random = new Random(seed);
		
		int size = 5 + random.nextInt(7);
		float percWall = random.nextFloat()*0.12f + 0.08f;
		
		int area = size*size;
		int nWalls = (int) (area * percWall);
		int nPlayerWalls = 1 + (area > 6*6 ? 1 : 0);
		int nPlayerMinions = 3;
		if(area > 4*4) { nPlayerMinions++; }
		if(area > 6*6) { nPlayerMinions++; }
		if(area > 7*7) { nPlayerMinions++; }
		if(area > 8*8) { nPlayerMinions++; }

		
		Grid grid;
		int[][] distanceField;
		
		do {
			
			grid = new Grid(size, size);
			distanceField = new int[size][size];
			
			// place Wall
			for(int i=0; i<nWalls; i++) {

				for(int j=0; j<grid.getWidth()*grid.getHeight(); j++) {
					int x = random.nextInt(grid.getWidth());
					int y = random.nextInt(grid.getHeight());
					if( (grid.getCell(x, y).getObject() instanceof EmptyNode) ) {
						grid.getCell(x, y).setObject(new Wall(x,y));
						break;
					}
				}
			
			}
			
			// place enemy base
			int xBase = -1, yBase = -1;
			for(int j=0; j<grid.getWidth()*grid.getHeight(); j++) {
				int x = random.nextInt(grid.getWidth());
				int y = random.nextInt(grid.getHeight());
				if( (grid.getCell(x, y).getObject() instanceof EmptyNode) ) {
					grid.getCell(x, y).setObject(new EnemyBase(x,y));
					xBase = x;
					yBase = y;
					break;
				}
			}
			
			if(grid.getCell(xBase+1, yBase)!=null && grid.getCell(xBase+1, yBase).getObject() instanceof EmptyNode) { grid.getCell(xBase+1, yBase).setObject(new EnemyMinion(xBase+1, yBase)); }
			if(grid.getCell(xBase-1, yBase)!=null && grid.getCell(xBase-1, yBase).getObject() instanceof EmptyNode) { grid.getCell(xBase-1, yBase).setObject(new EnemyMinion(xBase-1, yBase)); }
			if(grid.getCell(xBase, yBase+1)!=null && grid.getCell(xBase, yBase+1).getObject() instanceof EmptyNode) { grid.getCell(xBase, yBase+1).setObject(new EnemyMinion(xBase, yBase+1)); }
			if(grid.getCell(xBase, yBase-1)!=null && grid.getCell(xBase, yBase-1).getObject() instanceof EmptyNode) { grid.getCell(xBase, yBase-1).setObject(new EnemyMinion(xBase, yBase-1)); }
			if(grid.getCell(xBase+1, yBase-1)!=null && grid.getCell(xBase+1, yBase-1).getObject() instanceof EmptyNode) { grid.getCell(xBase+1, yBase-1).setObject(new EnemyMinion(xBase+1, yBase-1)); }
			if(grid.getCell(xBase+1, yBase+1)!=null && grid.getCell(xBase+1, yBase+1).getObject() instanceof EmptyNode) { grid.getCell(xBase+1, yBase+1).setObject(new EnemyMinion(xBase+1, yBase+1)); }
			if(grid.getCell(xBase-1, yBase-1)!=null && grid.getCell(xBase-1, yBase-1).getObject() instanceof EmptyNode) { grid.getCell(xBase-1, yBase-1).setObject(new EnemyMinion(xBase-1, yBase-1)); }
			if(grid.getCell(xBase-1, yBase+1)!=null && grid.getCell(xBase-1, yBase+1).getObject() instanceof EmptyNode) { grid.getCell(xBase-1, yBase+1).setObject(new EnemyMinion(xBase-1, yBase+1)); }

			
			
			// place player base
			for(int j=0; j<grid.getWidth()*grid.getHeight(); j++) {
				int x = random.nextInt(grid.getWidth());
				int y = random.nextInt(grid.getHeight());
				if( (grid.getCell(x, y).getObject() instanceof EmptyNode) ) {
					grid.getCell(x, y).setObject(new PlayerBase(x,y));
					break;
				}
			}
			
			
		} while(!validate(grid, distanceField));
		
		
		GameBoard board = new GameBoard(grid, nPlayerMinions, nPlayerWalls);
		return board;
	}
	
	
	
	
	
	public static boolean validate(Grid grid, int[][] distanceField) {
		
		Utils.updateDistanceField(grid, distanceField);
		
		// check dist to base
		int px = -1;
		int py = -1;
		int ex = -1;
		int ey = -1;
		
		for(int y=0; y<grid.getHeight(); y++) {
			for(int x=0; x<grid.getWidth(); x++) {
				Cell mid = grid.getCell(x, y);
				if(mid.getObject() instanceof EnemyBase) {
					ex = x;
					ey = y;
				}
				if(mid.getObject() instanceof PlayerBase) {
					px = x;
					py = y;
				}
			}
		}
		
		int dx = Math.abs(ex-px);
		int dy = Math.abs(ey-py);
		float d = (float) Math.sqrt(dx*dx + dy*dy);
		float minDist = (grid.getWidth()+grid.getHeight())/2.5f;
		if(d < minDist ) {
			return false;
		}
		
		
		// check path
		for(int y=0; y<grid.getHeight(); y++) {
			for(int x=0; x<grid.getWidth(); x++) {
				Cell mid = grid.getCell(x, y);
				if(mid.getObject() instanceof EnemyBase) {
					int dist = distanceField[x][y];
					if(dist > grid.getWidth()*grid.getHeight()) {
						return false;
					}
				}
				
			}
		}
		
		
		return true;
	}

	
	
	
}






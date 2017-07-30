package com.ruegnerlukas.ld39.game;

import com.ruegnerlukas.ld39.game.objects.EnemyBase;
import com.ruegnerlukas.ld39.game.objects.PlayerBase;
import com.ruegnerlukas.ld39.game.objects.Wall;

public class Utils {

	

	
	public static void updateDistanceField(Grid grid, int[][] distanceField) {
		
		for(int y=0; y<grid.getHeight(); y++) {
			for(int x=0; x<grid.getWidth(); x++) {
				distanceField[x][y] = Integer.MAX_VALUE/2;
			}
		}
		
		for(int y=0; y<grid.getHeight(); y++) {
			for(int x=0; x<grid.getWidth(); x++) {
				
				Cell mid = grid.getCell(x, y);
				Cell top = grid.getCell(x, y+1);
				Cell bot = grid.getCell(x, y-1);
				Cell right = grid.getCell(x+1, y);
				Cell left = grid.getCell(x-1, y);

				if(mid.getObject() instanceof PlayerBase) {
					distanceField[x][y] = 0;
					continue;
				}
				if(mid.getObject() instanceof Wall) {
					distanceField[x][y] = Integer.MAX_VALUE;
					continue;
				}
				
			}
		}
		
		
		for(int i=0; i<grid.getWidth()*grid.getHeight()+10; i++) {
			
			boolean changed = false;
			for(int y=0; y<grid.getHeight(); y++) {
				for(int x=0; x<grid.getWidth(); x++) {
					
					Cell mid = grid.getCell(x, y);
					Cell top = grid.getCell(x, y+1);
					Cell bot = grid.getCell(x, y-1);
					Cell right = grid.getCell(x+1, y);
					Cell left = grid.getCell(x-1, y);

					if(distanceField[x][y] == Integer.MAX_VALUE) {
						continue;
					}
					
					if(top!=null && (distanceField[x][y+1] < distanceField[x][y]+1) ) {
						distanceField[x][y] = distanceField[x][y+1]+1;
						changed = true;
					}
					if(bot!=null && (distanceField[x][y-1] < distanceField[x][y]+1) ) {
						distanceField[x][y] = distanceField[x][y-1]+1;
						changed = true;
					}
					if(right!=null && (distanceField[x+1][y] < distanceField[x][y]+1) ) {
						distanceField[x][y] = distanceField[x+1][y]+1;
						changed = true;
					}
					if(left!=null && (distanceField[x-1][y] < distanceField[x][y]+1) ) {
						distanceField[x][y] = distanceField[x-1][y]+1;
						changed = true;
					}
					
				}
			}
			
			if(!changed) {
				break;
			}
			
		}
		
	}

	
	
	
	public static boolean validatePath(Grid grid, int[][] distanceField) {
		
		Utils.updateDistanceField(grid, distanceField);
		
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

package com.ruegnerlukas.ld39.game;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Stroke;
import java.util.ArrayList;
import java.util.List;

import com.ruegnerlukas.ld39.FadingPoints;
import com.ruegnerlukas.ld39.game.objects.EmptyNode;
import com.ruegnerlukas.ld39.game.objects.EnemyBase;
import com.ruegnerlukas.ld39.game.objects.EnemyMinion;
import com.ruegnerlukas.ld39.game.objects.PlayerBase;
import com.ruegnerlukas.ld39.game.objects.PlayerMinion;
import com.ruegnerlukas.ld39.game.objects.Wall;
import com.ruegnerlukas.ld39.utils.Display;
import com.ruegnerlukas.ld39.utils.Input;
import com.ruegnerlukas.ld39.utils.Key;

public class GameBoard {

	private static final Font font = new Font("Arial", Font.BOLD, 25);
	
	private Grid grid;
	private int[][] distanceField;
	private List<Snapshot> snapshots;
	
	private boolean hasLost = false;
	
	private int nMinions = 5;
	private int nWalls = 1;
	List<Point> placedWallsInPath = new ArrayList<Point>();
	
	public String text;
	
	
	
	
	
	public GameBoard(Grid grid, int nMinions, int nWalls) {
		this(grid, nMinions, nWalls, "R = Restart  -  Space = Next Step  -  Enter = New Level");
	}
	
	public GameBoard(Grid grid, int nMinions, int nWalls, String text) {
		this.grid = grid;
		this.nMinions = nMinions;
		this.nWalls = nWalls;
		snapshots = new ArrayList<Snapshot>();
		distanceField = new int[grid.getWidth()][grid.getHeight()];
		Utils.updateDistanceField(grid, distanceField);
		this.text = text;
	}

	
	
	
	
	public void update(Graphics2D g) {
		Input input = FadingPoints.input;
		
//		editor(input, g);
		
		
		int gridWidth = grid.getCellSize() * grid.getWidth();
		int gridHeight = grid.getCellSize() * grid.getHeight();
		int gridX = (Display.getDisplay().getWidth() - gridWidth) / 2;
		int gridY = (Display.getDisplay().getHeight() - gridHeight) / 2;
		gridY -= 60;
		
		
		int mx = input.mouseX();
		int my = input.mouseY();
		int cellX = (mx - gridX) / grid.getCellSize();
		int cellY = (my - gridY) / grid.getCellSize();
		
		
		
		// place minion
		if(input.mousePressed(Input.MOUSE_LEFT)) {
			Cell cell = grid.getCell(cellX, cellY);
			if(cell != null) {
				if(cell.getObject() instanceof PlayerMinion) {
					removeMinion(cellX, cellY);
				} else if(cell.getObject() instanceof EmptyNode && nMinions > 0) {
					placeMinion(cellX, cellY);
				}
			}
		}
		
		if(input.mousePressed(Input.MOUSE_RIGHT)) {
			Cell cell = grid.getCell(cellX, cellY);
			if(cell != null) {
				if(cell.getObject() instanceof EmptyNode && nWalls > 0) {
					cell.setObject(new Wall(cellX, cellY));
					if(!Utils.validatePath(grid, distanceField)) {
						placedWallsInPath.add(new Point(cellX, cellY));
					}
					nWalls--;
					Utils.updateDistanceField(grid, distanceField);
				}
			}
		}
		
		
		
		
		// draw grid
		for(int cy=0; cy<grid.getHeight(); cy++) {
			for(int cx=0; cx<grid.getWidth(); cx++) {
				Cell c = grid.getCells()[cx][cy];
				if(c.getObject() != null) {
					c.getObject().draw(g, gridX, gridY, grid.getCellSize());
				}
				
				// distancefield
				int tx = (cx * grid.getCellSize() + gridX) + (grid.getCellSize()/2);
				int ty = (cy * grid.getCellSize() + gridY) + (grid.getCellSize()/2);
				g.setColor(Color.BLACK);
				
				int connections = getConnections(cx, cy);
					
				Stroke strokeLast = g.getStroke();
				g.setStroke(new BasicStroke(5));
				g.setColor(Data.COLOR_PLAYER);	
				
				if(connections == 1 || connections == 3) { // top-bottom
					int lx0 = ( cx    * grid.getCellSize() + gridX) + (grid.getCellSize()/2);
					int ly0 = ((cy-1) * grid.getCellSize() + gridY) + (grid.getCellSize()/2);
					int lx1 = ( cx    * grid.getCellSize() + gridX) + (grid.getCellSize()/2);
					int ly1 = ((cy+1) * grid.getCellSize() + gridY) + (grid.getCellSize()/2);
					g.drawLine(lx0, ly0, lx1, ly1);
				}
				if(connections == 2 || connections == 3) { // left-right
					int lx0 = ((cx-1) * grid.getCellSize() + gridX) + (grid.getCellSize()/2);
					int ly0 = ( cy    * grid.getCellSize() + gridY) + (grid.getCellSize()/2);
					int lx1 = ((cx+1) * grid.getCellSize() + gridX) + (grid.getCellSize()/2);
					int ly1 = ( cy    * grid.getCellSize() + gridY) + (grid.getCellSize()/2);
					g.drawLine(lx0, ly0, lx1, ly1);
				}
					
				g.setStroke(strokeLast);
					
				
			}
		}
		
		
		// draw gui
		int uiObj = nMinions + nWalls;
		int gridCX = gridX + (gridWidth/2);
		int gridTop = gridY - Data.POWER_ORB_SIZE;
		int orbsWidth = (Data.POWER_ORB_SIZE * uiObj) + (10 * uiObj-1);
		int orbsStart = gridCX - (orbsWidth/2);
		
		g.setColor(Data.COLOR_POWER);
		for(int i=0; i<nMinions; i++) {
			int x = orbsStart + (i * (Data.POWER_ORB_SIZE+10));
			int y = gridTop - 20;
			g.fillOval(x, y, Data.POWER_ORB_SIZE, Data.POWER_ORB_SIZE);
		}
		g.setColor(Data.COLOR_WALL);
		for(int i=nMinions; i<nMinions+nWalls; i++) {
			int x = orbsStart + (i * (Data.POWER_ORB_SIZE+10));
			int y = gridTop - 20;
			g.fillOval(x, y, Data.POWER_ORB_SIZE, Data.POWER_ORB_SIZE);
		}
		
		g.setColor(Data.COLOR_GRID_POINTS);

		
		g.setFont(font);
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, gridCX - (textWidth/2), gridY+gridHeight+65);
		
		
	}
	
	
	
	public void loadLastSnapshot() {
		if(snapshots.isEmpty()) {
			return;
		}
		if(nMinions <= 0) {
			return;
		}
		Snapshot s = snapshots.remove(snapshots.size()-1);
		this.grid = s.getGrid();
		nMinions--;
	}
	
	
	
	public void placeMinion(int x, int y) {
		Cell cell = grid.getCell(x, y);
		if(cell != null) {
			cell.setObject(new PlayerMinion(x, y));
			nMinions--;
		}
	}
	
	
	public void removeMinion(int x, int y) {
		Cell cell = grid.getCell(x, y);
		if(cell != null) {
			cell.setObject(new EmptyNode(x, y));
			nMinions++;
		}
	}
	
	
	
	
	public int getConnections(int x, int y) {
		
		Cell mid = grid.getCell(x, y);
		Cell top = grid.getCell(x, y+1);
		Cell bot = grid.getCell(x, y-1);
		Cell right = grid.getCell(x+1, y);
		Cell left = grid.getCell(x-1, y);

		
		if(mid.getObject() instanceof EmptyNode) {
			
			boolean connectionTB = false;
			boolean connectionLR = false;
			
			if(top != null && (top.getObject() instanceof PlayerMinion) ) {
				if(bot != null && (bot.getObject() instanceof PlayerMinion) ) {
					connectionTB = true;
				}
			}
			
			if(left != null && (left.getObject() instanceof PlayerMinion) ) {
				if(right != null && (right.getObject() instanceof PlayerMinion) ) {
					connectionLR = true;
				}
			}
			
			
			if(!connectionTB && !connectionLR) { return 0; }
			if(connectionTB && !connectionLR) { return 1; }
			if(!connectionTB && connectionLR) { return 2; }
			if(connectionTB && connectionLR) { return 3; }
			
		}
		
		return 0;
	}
	
	
	
	
	
	public void calcStep() {
		
		// remove wall
		if(!placedWallsInPath.isEmpty()) {
			Point p = placedWallsInPath.remove(placedWallsInPath.size()-1);
			grid.getCell(p.x, p.y).setObject(new EmptyNode(p.x, p.y));		
			
			if(Utils.validatePath(grid, distanceField)) {
				placedWallsInPath.clear();
			}
			Utils.updateDistanceField(grid, distanceField);
			
		} else if((int)(Math.random()*100) > 15) {
			
			List<Point> walls = new ArrayList<Point>();
			for(int cy=0; cy<grid.getHeight(); cy++) {
				for(int cx=0; cx<grid.getWidth(); cx++) {
					Cell c = grid.getCells()[cx][cy];
					if(c != null && c.getObject() instanceof Wall) {
						walls.add(new Point(cx, cy));
					}
				}
			}
			
			if(!walls.isEmpty()) {
				int ri = Math.max(0, Math.min((int)(Math.random() * walls.size()), walls.size()-1));
				Point p = walls.get(ri);
				grid.getCell(p.x, p.y).setObject(new EmptyNode(p.x, p.y));
				Utils.updateDistanceField(grid, distanceField);
			}
			
		}
		
		
		Utils.updateDistanceField(grid, distanceField);
		
		List<Point> connections = new ArrayList<Point>();
		List<Point> minionsToMove = new ArrayList<Point>();

		for(int cy=0; cy<grid.getHeight(); cy++) {
			for(int cx=0; cx<grid.getWidth(); cx++) {
				
				Cell mid = grid.getCell(cx, cy);
				Cell top = grid.getCell(cx, cy+1);
				Cell bot = grid.getCell(cx, cy-1);
				Cell right = grid.getCell(cx+1, cy);
				Cell left = grid.getCell(cx-1, cy);

				if(mid.getObject() instanceof EnemyMinion) {
					minionsToMove.add(new Point(cx, cy));
				}

				if(mid.getObject() instanceof EmptyNode) {
					
					boolean connectionTB = false;
					boolean connectionLR = false;
					
					if(top != null && (top.getObject() instanceof PlayerMinion) ) {
						if(bot != null && (bot.getObject() instanceof PlayerMinion) ) {
							connectionTB = true;
						}
					}
					
					if(left != null && (left.getObject() instanceof PlayerMinion) ) {
						if(right != null && (right.getObject() instanceof PlayerMinion) ) {
							connectionLR = true;
						}
					}
					
					
					if(connectionTB || connectionLR) {
						connections.add(new Point(cx, cy));
					}
					
				}
				
			}
		}		
		
		
		
		
		// move
		int cnt = 1000;
		while(!minionsToMove.isEmpty()) {
			
			Point p = minionsToMove.remove(0);
			boolean moved = false;
			
			int x = p.x;
			int y = p.y;
			
			Cell cMid = grid.getCell(x, y);
			Cell cTop = grid.getCell(x, y+1);
			Cell cBot = grid.getCell(x, y-1);
			Cell cRight = grid.getCell(x+1, y);
			Cell cLeft = grid.getCell(x-1, y);

			int dMid = cMid==null ? Integer.MAX_VALUE : distanceField[x][y];
			int dTop = cTop==null ? Integer.MAX_VALUE : distanceField[x][y+1];
			int dBot = cBot==null ? Integer.MAX_VALUE : distanceField[x][y-1];
			int dRight = cRight==null ? Integer.MAX_VALUE : distanceField[x+1][y];
			int dLeft = cLeft==null ? Integer.MAX_VALUE : distanceField[x-1][y];

			if(dTop < dMid && (cTop.getObject() instanceof EmptyNode || cTop.getObject() instanceof PlayerMinion || cTop.getObject() instanceof PlayerBase) ) {
				if(cTop.getObject() instanceof PlayerBase) {
					hasLost = true;
					return;
				}
				cMid.setObject(new EmptyNode(x, y));
				if(getConnections(x, y+1) == 0) cTop.setObject(new EnemyMinion(x, y+1));
				moved = true;
				
			} else if(dBot < dMid && (cBot.getObject() instanceof EmptyNode || cBot.getObject() instanceof PlayerMinion || cBot.getObject() instanceof PlayerBase) ) {
				if(cBot.getObject() instanceof PlayerBase) {
					hasLost = true;
					return;
				}
				cMid.setObject(new EmptyNode(x, y));
				if(getConnections(x, y-1) == 0) cBot.setObject(new EnemyMinion(x, y-1));
				moved = true;
				
				
			} else if(dRight < dMid && (cRight.getObject() instanceof EmptyNode || cRight.getObject() instanceof PlayerMinion || cRight.getObject() instanceof PlayerBase) ) {
				if(cRight.getObject() instanceof PlayerBase) {
					hasLost = true;
					return;
				}
				cMid.setObject(new EmptyNode(x, y));
				if(getConnections(x+1, y) == 0) cRight.setObject(new EnemyMinion(x+1, y));
				moved = true;
				
			} else if(dLeft < dMid && (cLeft.getObject() instanceof EmptyNode || cLeft.getObject() instanceof PlayerMinion || cLeft.getObject() instanceof PlayerBase) ) {
				if(cLeft.getObject() instanceof PlayerBase) {
					hasLost = true;
					return;
				}
				cMid.setObject(new EmptyNode(x, y));
				if(getConnections(x-1, y) == 0) cLeft.setObject(new EnemyMinion(x-1, y));
				moved = true;
			}
				
			
			if(!moved) {
				minionsToMove.add(p);
			} else {
				
				
			}
		
			
			cnt--;
			if(cnt < 0) {
				return;
			}
		}
		
		
		
		int nPlayerMinion = 0;
		for(int cy=0; cy<grid.getHeight(); cy++) {
			for(int cx=0; cx<grid.getWidth(); cx++) {
				
				Cell mid = grid.getCell(cx, cy);

				if(mid.getObject() instanceof PlayerMinion) {
					nPlayerMinion++;
				}
			}	
		}
		
		if(nPlayerMinion==0 && nMinions == 0) {
			hasLost = true;
			return;
		}
		
		
		
		if(!hasLost) {
			
			int nEnemies = 0;
			for(int cy=0; cy<grid.getHeight(); cy++) {
				for(int cx=0; cx<grid.getWidth(); cx++) {
					
					Cell mid = grid.getCell(cx, cy);
	
					if(mid.getObject() instanceof EnemyMinion) {
						nEnemies++;
					}
				}	
			}
			
			if(nEnemies == 0) {
				text = "You Won!  Press \"Enter\" for next Level.";
			}
			
		}
		
		
	}
	
	
	
	
	
	
	
	
	
	
	private void editor(Input input, Graphics2D g) {
		
		int gridWidth = grid.getCellSize() * grid.getWidth();
		int gridHeight = grid.getCellSize() * grid.getHeight();
		int gridX = (Display.getDisplay().getWidth() - gridWidth) / 2;
		int gridY = (Display.getDisplay().getHeight() - gridHeight) / 2;
		
		int mx = input.mouseX();
		int my = input.mouseY();
		int cellX = (mx - gridX) / grid.getCellSize();
		int cellY = (my - gridY) / grid.getCellSize();
		
		if(input.keyPressed(Key.VK_1)) {
			grid.getCells()[cellX][cellY].setObject(new EmptyNode(cellX, cellY));
		}
		if(input.keyPressed(Key.VK_2)) {
			grid.getCells()[cellX][cellY].setObject(new Wall(cellX, cellY));
		}
		if(input.keyPressed(Key.VK_3)) {
			grid.getCells()[cellX][cellY].setObject(new EnemyBase(cellX, cellY));
		}
		if(input.keyPressed(Key.VK_4)) {
			grid.getCells()[cellX][cellY].setObject(new PlayerBase(cellX, cellY));
		}		
		if(input.keyPressed(Key.VK_5)) {
			grid.getCells()[cellX][cellY].setObject(new EnemyMinion(cellX, cellY));
		}
		if(input.keyPressed(Key.VK_6)) {
			grid.getCells()[cellX][cellY].setObject(new PlayerMinion(cellX, cellY));
		}
		
	}
	
	
	
	public boolean hasLost() {
		return hasLost;
	}
	
	
}

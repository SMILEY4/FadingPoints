package com.ruegnerlukas.ld39.game;

import com.ruegnerlukas.ld39.game.objects.EnemyBase;
import com.ruegnerlukas.ld39.game.objects.EnemyMinion;
import com.ruegnerlukas.ld39.game.objects.PlayerBase;
import com.ruegnerlukas.ld39.game.objects.PlayerMinion;
import com.ruegnerlukas.ld39.game.objects.Wall;

public class Levels {

	
	
//	GameBoard(Grid grid, int nMinions, int nWalls) {

	
	public static GameBoard getLevel(int i) {
		if(i == 0) { return getLevel0(); }
		if(i == 1) { return getLevel1(); }
		if(i == 2) { return getLevel2(); }
		return null;
	}
	
	
	public static GameBoard getLevel0() {
		Grid grid = new Grid(5, 6);
		grid.getCell(2, 0).setObject(new PlayerBase(2, 0));
		grid.getCell(2, 5).setObject(new EnemyBase(2, 5));
		grid.getCell(2, 4).setObject(new EnemyMinion(2, 4));
		grid.getCell(1, 2).setObject(new PlayerMinion(1, 2));
		grid.getCell(3, 2).setObject(new PlayerMinion(3, 2));
		return new GameBoard(grid, 0, 0, "Press space.");
	}
	
	
	
	public static GameBoard getLevel1() {
		Grid grid = new Grid(5, 6);
		
		grid.getCell(0, 0).setObject(new PlayerMinion(0, 0));
		grid.getCell(1, 0).setObject(new Wall(1, 0));
		grid.getCell(3, 0).setObject(new Wall(3, 0));
		grid.getCell(4, 0).setObject(new PlayerMinion(4, 0));
		grid.getCell(0, 1).setObject(new Wall(0, 1));
		grid.getCell(1, 1).setObject(new Wall(1, 1));
		grid.getCell(3, 1).setObject(new Wall(3, 1));
		grid.getCell(4, 1).setObject(new Wall(4, 1));
		grid.getCell(0, 3).setObject(new Wall(0, 3));
		grid.getCell(1, 3).setObject(new Wall(1, 3));
		grid.getCell(3, 3).setObject(new Wall(3, 3));
		grid.getCell(4, 3).setObject(new Wall(4, 3));
		grid.getCell(0, 4).setObject(new Wall(0, 4));
		grid.getCell(1, 4).setObject(new Wall(1, 4));
		grid.getCell(3, 4).setObject(new Wall(3, 4));
		grid.getCell(4, 4).setObject(new Wall(4, 4));
		grid.getCell(0, 5).setObject(new Wall(0, 5));
		grid.getCell(1, 5).setObject(new Wall(1, 5));
		grid.getCell(3, 5).setObject(new Wall(3, 5));
		grid.getCell(4, 5).setObject(new Wall(4, 5));
		grid.getCell(0, 2).setObject(new Wall(0, 2));
		grid.getCell(4, 2).setObject(new Wall(4, 2));
		grid.getCell(2, 0).setObject(new PlayerBase(2, 0));
		grid.getCell(2, 5).setObject(new EnemyBase(2, 5));
		grid.getCell(2, 4).setObject(new EnemyMinion(2, 4));
		
		return new GameBoard(grid, 0, 0, "Place and remove blue points with \"Left-Mouse\". ");
	}
	
	
	public static GameBoard getLevel2() {
		Grid grid = new Grid(5, 6);
		
		grid.getCell(0, 0).setObject(new Wall(0, 0));
		grid.getCell(1, 0).setObject(new Wall(1, 0));
		grid.getCell(3, 0).setObject(new Wall(3, 0));
		grid.getCell(4, 0).setObject(new Wall(4, 0));
		grid.getCell(0, 1).setObject(new Wall(0, 1));
		grid.getCell(3, 1).setObject(new Wall(3, 1));
		grid.getCell(4, 1).setObject(new Wall(4, 1));
		grid.getCell(0, 3).setObject(new Wall(0, 3));
		grid.getCell(3, 3).setObject(new Wall(3, 3));
		grid.getCell(4, 3).setObject(new Wall(4, 3));
		grid.getCell(0, 4).setObject(new Wall(0, 4));
		grid.getCell(1, 4).setObject(new EnemyMinion(1, 4));
		grid.getCell(3, 4).setObject(new Wall(3, 4));
		grid.getCell(4, 4).setObject(new Wall(4, 4));
		grid.getCell(0, 5).setObject(new Wall(0, 5));
		grid.getCell(1, 5).setObject(new Wall(1, 5));
		grid.getCell(3, 5).setObject(new Wall(3, 5));
		grid.getCell(4, 5).setObject(new Wall(4, 5));
		grid.getCell(0, 2).setObject(new Wall(0, 2));
		grid.getCell(4, 2).setObject(new Wall(4, 2));
		grid.getCell(2, 0).setObject(new PlayerBase(2, 0));
		grid.getCell(2, 5).setObject(new EnemyBase(2, 5));
		grid.getCell(2, 4).setObject(new EnemyMinion(2, 4));
		
		return new GameBoard(grid, 2, 1, "Place Walls with \"Right-Mouse\". Press \"R\" to restart.");
	}
	
}

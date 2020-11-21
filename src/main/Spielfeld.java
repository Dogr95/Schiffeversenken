package main;

public class Spielfeld {
	int width;
	int height;
	Spielmodus mode;
	Player player;
	Spielfeld enemyField;
	Player enemy;
	Space[][] grid;
	Player turn;
	Vector recentHit;
	
	void init(Spielfeld eF, Player p, Player e) {
		enemyField = eF;
		enemy = e;
		player = p;
	}
	
	Spielfeld(int w, int h, Spielmodus m) {
		width = w;
		height = h;
		mode = m;
		grid = new Space[w][h];
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				grid[i][j] = new Space();
			}
		}
	}
	/*Anzahl und Größe der Schiffe
    • ein Schlachtschiff (5 Kästchen)
    • zwei Kreuzer (je 4 Kästchen)
    • drei Zerstörer (je 3 Kästchen)
    • vier U-Boote (je 2 Kästchen)*/
	
	void placeShip(ShipType type, Vector vec, Direction dir) {
		switch(type) {
			case Schlachtschiff:
				switch(dir) {
					case RIGHT:
						for(int i = vec.posY; i < vec.posY+5; i++) {
							grid[vec.posX][i].containsShip = true;
						}
						break;
					case DOWN:
						for(int i = vec.posX; i < vec.posX+5; i++) {
							grid[i][vec.posY].containsShip = true;
						}
						break;
				}
				break;
			case Kreuzer:
				switch(dir) {
					case RIGHT:
						for(int i = vec.posY; i < vec.posY+4; i++) {
							grid[vec.posX][i].containsShip = true;
						}
						break;
					case DOWN:
						for(int i = vec.posX; i < vec.posX+4; i++) {
							grid[i][vec.posY].containsShip = true;
						}
						break;
				}
				break;
			case Zerstoerer:
				switch(dir) {
					case RIGHT:
						for(int i = vec.posY; i < vec.posY+3; i++) {
							grid[vec.posX][i].containsShip = true;
						}
						break;
					case DOWN:
						for(int i = vec.posX; i < vec.posX+3; i++) {
							grid[i][vec.posY].containsShip = true;
						}
						break;
				}
				break;
			case UBoot:
				switch(dir) {
					case RIGHT:
						for(int i = vec.posY; i < vec.posY+2; i++) {
							grid[vec.posX][i].containsShip = true;
						}
						break;
					case DOWN:
						for(int i = vec.posX; i < vec.posX+2; i++) {
							grid[i][vec.posY].containsShip = true;
						}
						break;
				}
				break;
		}
	}
	
	void placeShips(int anzSchlachtschiffe, int anzKreuzer, int anzZerstoerer, int anzUboote) {
		int placed = 0;
		for(int i = 0; i < anzSchlachtschiffe; i++) {
			for(int j = 0; j < grid.length; j++) {
				for(int k = 0; k < grid[j].length; k++) {
					if(k+4 < grid.length &&
						!grid[j][k].containsShip &&
						!grid[j][k+1].containsShip &&
						!grid[j][k+2].containsShip &&
						!grid[j][k+3].containsShip &&
						!grid[j][k+4].containsShip) {
						Direction d;
						if(Math.random() > 0.5) {
							d = Direction.RIGHT;
						} else {
							d = Direction.DOWN;
						}
						placeShip(ShipType.Schlachtschiff, new Vector(this, j, k), d);
						placed++;
						break;
					}
				}
				if(placed >= anzSchlachtschiffe) break;
			}
		}
		placed = 0;
		for(int i = 0; i < anzKreuzer; i++) {
			for(int j = 0; j < grid.length; j++) {
				for(int k = 0; k < grid[j].length; k++) {
					if(k+4 < grid.length &&
						!grid[j][k].containsShip &&
						!grid[j][k+1].containsShip &&
						!grid[j][k+2].containsShip &&
						!grid[j][k+3].containsShip) {
						Direction d;
						if(Math.random() > 0.5) {
							d = Direction.RIGHT;
						} else {
							d = Direction.DOWN;
						}
						placeShip(ShipType.Kreuzer, new Vector(this, j, k), d);
						placed++;
						break;
					}
				}
				if(placed >= anzKreuzer) break;
			}
		}
		placed = 0;
		for(int i = 0; i < anzZerstoerer; i++) {
			for(int j = 0; j < grid.length; j++) {
				for(int k = 0; k < grid[j].length; k++) {
					if(k+4 < grid.length &&
						!grid[j][k].containsShip &&
						!grid[j][k+1].containsShip &&
						!grid[j][k+2].containsShip) {
						Direction d;
						if(Math.random() > 0.5) {
							d = Direction.RIGHT;
						} else {
							d = Direction.DOWN;
						}
						placeShip(ShipType.Zerstoerer, new Vector(this, j, k), d);
						placed++;
						break;
					}
				}
				if(placed >= anzZerstoerer) break;
			}
		}
		placed = 0;
		for(int i = 0; i < anzUboote; i++) {
			for(int j = 0; j < grid.length; j++) {
				for(int k = 0; k < grid[j].length; k++) {
					if(k+4 < grid.length &&
						!grid[j][k].containsShip &&
						!grid[j][k+1].containsShip &&
						!grid[j][k+2].containsShip) {
						Direction d;
						if(Math.random() > 0.5) {
							d = Direction.RIGHT;
						} else {
							d = Direction.DOWN;
						}
						placeShip(ShipType.UBoot, new Vector(this, j, k), d);
						placed++;
						break;
					}
				}
				if(placed >= anzUboote) break;
			}
		}
	}
	
	void show() {
		System.out.print("   "+player);
		for(int i = 0; i < grid.length-((player+"").length()); i++) {
			System.out.print(" ");
		}
		System.out.print("   "+enemy);
		System.out.println();
		System.out.print("   ");
		for(int i = 0; i < grid.length; i++) {
			System.out.print(Vector.characters[i]);
		}
		System.out.print("   ");
		for(int i = 0; i < enemyField.grid.length; i++) {
			System.out.print(Vector.characters[i]);
		}
		System.out.println("");
		for(int i = 0; i < grid.length; i++) {
			if(i+1 < 10) {
				System.out.print(i+1 + "  ");
			} else {
				System.out.print(i+1 + " ");
			}
			for(int j = 0; j < grid[i].length; j++) {
				switch(grid[i][j].status) {
					case HIT:
						System.out.print("X");
						break;
					case MISS:
						System.out.print("V");
						break;
					case FREE:
						if(grid[i][j].containsShip) {
							System.out.print("S");
						} else {
							System.out.print("~");
						}
						break;
				}
			}
			System.out.print("   ");
			for(int j = 0; j < enemyField.grid[i].length; j++) {
				switch(enemyField.grid[i][j].status) {
					case HIT:
						System.out.print("X");
						break;
					case MISS:
						System.out.print("V");
						break;
					case FREE:
						System.out.print("~");
						break;
				}
			}
			System.out.println();
		}
	}
	
	boolean aiTurn() {
		Vector r = recentHit;
		if (r == null || grid[r.posX][r.posY].status == Status.MISS) {
			shoot(
				new Vector(this,
							(int) (Math.random()*(grid.length-1)),
							(int) (Math.random()*(grid.length-1))
						)
				);
		} else {
			if(grid[r.posX][r.posY].status == Status.HIT) {
				if(r.posX+1 < grid.length &&
						grid[r.posX+1][r.posY].status == Status.FREE) {
					shoot(new Vector(this, r.posX+1, r.posY));
				} else if(r.posX-1 > 0 &&
						grid[r.posX-1][r.posY].status == Status.FREE) {
					shoot(new Vector(this, r.posX-1, r.posY));
				} else if(r.posY+1 < grid.length &&
						grid[r.posX][r.posY+1].status == Status.FREE) {
					shoot(new Vector(this, r.posX, r.posY+1));
				} else if(r.posY-1 < grid.length &&
						grid[r.posX][r.posY-1].status == Status.FREE) {
					shoot(new Vector(this, r.posX, r.posY-1));
				}
			}
		}
		return true;
	}
	
	void changeTurn() {
		switch(turn) {
			case SELF:
				turn = Player.ENEMY;
				break;
			case ENEMY:
				turn = Player.SELF;
				break;
		}
	}
	
	boolean shoot(String input) {
		Vector coords = Vector.validate(this, input);
		if(coords != null) {
			if(grid[coords.posX][coords.posY].hit()) {
				recentHit = coords;
			}
			return true;
		} else {
			System.out.println("Invalid move!");
			return false;
		}
	}
	
	boolean isOver() {
		for(int i = 0; i < grid.length; i++) {
			for(int j = 0; j < grid[i].length; j++) {
				if(grid[i][j].containsShip && grid[i][j].status == Status.FREE) return false;
			}
		}
		return true;
	}
	
	boolean shoot(Vector coords) {
		if(coords != null) {
			if(grid[coords.posX][coords.posY].hit()) {
				recentHit = coords;
			}
			System.out.println(Vector.characters[coords.posY] +""+(coords.posX+1));
			return true;
		} else {
			System.out.println("Invalid move!");
			return false;
		}
		
	}
}

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
		if(m != null) switch(m) {
			case vsAI:
				enemyField = new Spielfeld(w, h, null);
				player = Player.SELF;
				enemy = Player.ENEMY;
				break;
			case local:
				player = Player.SELF;
				enemy = Player.ENEMY;
				break;
			case online:
				player = Player.SELF;
				enemy = Player.ENEMY;
				break;
		} else {
			// wenn Gegnerisches Feld
			enemy = Player.SELF;
			player = Player.ENEMY;
		}
		placeShips(1, 2, 3, 4);
		turn = player;
	}
	/*	Anzahl und Größe der Schiffe
    	ein Schlachtschiff (5 Kästchen)
    	zwei Kreuzer (je 4 Kästchen)
    	drei Zerstörer (je 3 Kästchen)
    	vier U-Boote (je 2 Kästchen)
    */
	
	void placeShip(int amount, Direction dir) {
		boolean found = false;
		while(!found) {
			// Zufällige Ausgangsposition festlegen
			int y = (int) (Math.random() * grid.length);
			int x = (int) (Math.random() * grid[y].length);
			boolean error = false;
			// Überprüfen ob platzieren möglich ist
			switch(dir) {
				case DOWN:
					if(y+amount > grid.length) y -= amount;
					for(int i = 0; i < amount; i++) {
						if(!checkSpace(y+i, x)) {
							error = true;
							break;
						}
					}
					if(!error) {
						for(int i = 0; i < amount; i++) {
							grid[y+i][x].containsShip = true;
						}
						found = true;
						break;
					}
					break;
				case RIGHT:
					if(x+amount > grid[y].length) x -= amount;
					for(int i = 0; i < amount; i++) {
						if(!checkSpace(y, x+i)) {
							error = true;
							break;
						}
					}
					if(!error) {
						for(int i = 0; i < amount; i++) {
							grid[y][x+i].containsShip = true;
						}
						found = true;
						break;
					}
					break;
			}
		}
	}
	
	Direction randomDir() {
		return Math.random() > 0.5 ? Direction.RIGHT : Direction.DOWN;
	}
	
	void placeShips(int anzSchlachtschiffe, int anzKreuzer, int anzZerstoerer, int anzUboote) {
		for(int i = 0; i < anzSchlachtschiffe; i++) {
			placeShip(5, randomDir());
		}
		for(int i = 0; i < anzKreuzer; i++) {
			placeShip(4, randomDir());
		}
		for(int i = 0; i < anzZerstoerer; i++) {
			placeShip(3, randomDir());
		}
		for(int i = 0; i < anzUboote; i++) {
			placeShip(2, randomDir());
		}
	}
	
	boolean checkSpace(int y, int x) {
		boolean center = !grid[y][x].containsShip;
		boolean topLeft = y-1 > 0 && x > 0 ? !grid[y-1][x-1].containsShip : true;
		boolean topCenter = y-1 > 0 ? !grid[y-1][x].containsShip : true;
		boolean topRight = y-1 > 0 && x+1 < grid[y].length ? !grid[y-1][x+1].containsShip : true;
		boolean left = x-1 > 0 ? !grid[y][x-1].containsShip : true;
		boolean right = x+1 < grid[y].length ? !grid[y][x+1].containsShip : true;
		boolean botLeft = y+1 < grid.length && x-1 > 0 ? !grid[y+1][x-1].containsShip : true;
		boolean botCenter = y+1 < grid.length ? !grid[y+1][x].containsShip : true;
		boolean botRight = y+1 < grid.length && x+1 < grid[y].length ? !grid[y+1][x+1].containsShip : true;
		return topLeft && topCenter && topRight &&
			   left    && center    && right    &&
			   botLeft && botCenter && botRight;
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
						if(mode == Spielmodus.local) {
							if(enemyField.grid[i][j].containsShip) {
								System.out.print("S");
							} else {
								System.out.print("~");
							}
						} else System.out.print("~");
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

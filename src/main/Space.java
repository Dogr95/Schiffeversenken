package main;

public class Space {
	Status status = Status.FREE;
	boolean containsShip = false;
	
	Space() {
		
	}
	
	Space(boolean cS) {
		containsShip = cS;
	}
	
	boolean hit() {
		if(containsShip) {
			status = Status.HIT;
			return true;
		} else {
			status = Status.MISS;
			return false;
		}
	}
}

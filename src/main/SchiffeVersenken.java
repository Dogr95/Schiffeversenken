package main;

import java.util.Scanner;

public class SchiffeVersenken {
	
	private static boolean gameover = false;
	private static Scanner scan = new Scanner(System.in);
	private static Spielfeld feld;
	private static Spielfeld feldGegner;
	private static boolean validMove = true;
	
	public static void init() {
		feld = new Spielfeld(10, 10, Spielmodus.vsAI);
		feld.placeShips(1, 2, 3, 4);
		feldGegner = new Spielfeld(10, 10, Spielmodus.vsAI);
		feldGegner.placeShips(1, 2, 3, 4);
		feld.init(feldGegner, Player.SELF, Player.ENEMY);
		feldGegner.init(feld, Player.ENEMY, Player.SELF);
		feld.turn = Player.SELF;
	}

	public static void main(String[] args) {
		init();
		gameloop();
	}
	
	private static void gameloop() {
		while(!gameover) {
			feld.show();
			System.out.println("Spieler " + feld.turn + " ist an der Reihe!");
			if(feld.turn == Player.SELF) {
				String input = scan.next();
				validMove = feldGegner.shoot(input);
			} else {
				validMove = feld.aiTurn();
			}
			if(feld.isOver()) {
				System.out.println(Player.ENEMY +" won the game!");
				gameover = true;
			};
			if(feldGegner.isOver()) {
				System.out.println(Player.SELF +" won the game!");
				gameover = true;
			};
			if(validMove) feld.changeTurn();
		}
	}

}

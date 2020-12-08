package main;

import java.util.Scanner;

public class SchiffeVersenken {
	
	private static boolean gameover = false;
	private static Scanner scan = new Scanner(System.in);
	private static Spielfeld feld;
	private static boolean validMove = true;
	
	public static void init() {
		// Spielfeld erstellen unter angabe der Breite/Höhe und des Spielmodus
		feld = new Spielfeld(10, 10, Spielmodus.vsAI);
	}

	public static void main(String[] args) {
		init();
		gameloop();
	}
	
	private static void gameloop() {
		while(!gameover) {
			feld.show();
			System.out.println("Spieler " + feld.turn + " ist an der Reihe!");
			switch(feld.mode) {
				case vsAI:
					if(feld.turn == Player.SELF) {
						String input = scan.next();
						validMove = feld.enemyField.shoot(input);
					} else {
						validMove = feld.aiTurn();
					}
					break;
				case local:
					if(feld.turn == Player.SELF) {
						String input = scan.next();
						validMove = feld.enemyField.shoot(input);
					} else {
						String input = scan.next();
						validMove = feld.shoot(input);
					}
					break;
				case online:
					break;
			}
			
			if(feld.isOver()) {
				System.out.println(Player.ENEMY +" hat gewonnen!");
				gameover = true;
			};
			if(feld.enemyField.isOver()) {
				System.out.println(Player.SELF +" hat gewonnen!");
				gameover = true;
			};
			if(validMove) feld.changeTurn();
		}
	}

}

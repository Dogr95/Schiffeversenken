package main;

import java.util.Scanner;

public class SchiffeVersenken {
	
	private static boolean gameover = false;
	private static Scanner scan = new Scanner(System.in);
	public static Spielfeld feld;
	private static boolean validMove = true;
	
	public static void clear(int nl) {
		for(int i = 0; i < nl; i++) {
			System.out.println();
		}
	}

	public static void main(String[] args) {
		clear(20);
		MainMenu mm = new MainMenu();
		mm.show();
		gameloop();
	}
	
	private static void gameloop() {
		while(!gameover) {
			clear(20);
			System.out.println("Spieler " + feld.turn + " ist an der Reihe!");
			switch(feld.mode) {
				case vsAI:
					System.out.println(feld.turn);
					if(feld.turn == Player.SELF) {
						feld.show();
						String input = scan.next();
						validMove = feld.enemyField.shoot(input);
					} else {
						validMove = feld.aiTurn();
						feld.enemyField.show();
					}
					break;
				case local:
					if(feld.turn == Player.SELF) {
						feld.show();
						String input = scan.next();
						validMove = feld.enemyField.shoot(input);
					} else {
						feld.enemyField.show();
						String input = scan.next();
						validMove = feld.shoot(input);
					}
					break;
				case online:
					break;
			}
			
			if(feld.isOver()) {
				feld.show();
				System.out.println(Player.ENEMY +" hat gewonnen!");
				gameover = true;
			};
			if(feld.enemyField.isOver()) {
				feld.show();
				System.out.println(Player.SELF +" hat gewonnen!");
				gameover = true;
			};
			if(validMove) feld.changeTurn();
		}
	}

}

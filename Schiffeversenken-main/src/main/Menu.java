package main;

import java.util.Scanner;

public abstract class Menu {
	void line(int width) {
		for(int i = 0; i < width; i++) {
			System.out.print("#");
		}
		System.out.print("\n");
	}
	
	void center(String txt, int width) {
		int c = 0;
		if(txt.length() % 2 != 1) {
			txt += " ";
		}
		for(int i = 0; i < width; i++) {
			// Erstes und letztes Zeichen soll "#" sein
			if(i == 0 || i == width-1) System.out.print("#");
			// Text zentrieren
			else if(!(i < width/2 - txt.length()/2 || i > width/2 + txt.length()/2)) {
				System.out.print(txt.charAt(c++));
			// Leere Felder füllen
			} else {
				System.out.print(" ");
			}
		}
		System.out.print("\n");
	}
	
	int input() {
		Scanner scan = new Scanner(System.in);
		String tmp = scan.next();
		int ret = -1;
		try {
			return Integer.parseInt(tmp.charAt(0) + "");
		} catch(Exception e) {
			return ret;
		}
	}
}

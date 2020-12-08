package main;

public class MainMenu extends Menu {
	int width = 30;
	
	String[] options = { "PvE (lokal)",
						 "PvP (lokal)",
						 "PvP (online)",
						 "Quit "
						};
	
	public MainMenu() {
		super();
	}
	
	void show() {
		header();
		selection();
	}
	
	void header() {
		// Erste Zeile
		line(width);
		
		// Hauptzeile
		center("Hauptmenü", width);
		
		// Abschließende Zeile
		line(width);
	}
	
	void selection() {
		for(int i = 0; i < options.length; i++) {
			center((i+1)+"-"+options[i], width);
		}
		line(width);
		SchiffeVersenken.clear(5);
		boolean validInput = false;
		int selection;
		Spielmodus m = null;
		while(!validInput) {
			System.out.print("Auswahl: ");
			selection = input();
			switch(selection) {
				case 1:
					// start local vs ai
					validInput = true;
					m = Spielmodus.vsAI;
					break;
				case 2:
					// start local vs player
					validInput = true;
					m = Spielmodus.local;
					break;
				case 3:
					// start online vs player
					validInput = true;
					m = Spielmodus.online;
					break;
				case 4:
					// quit
					validInput = true;
					break;
				default:
					System.out.println("Ungültige Eingabe");
					break;
			}
		}
		if(m != null) {
			SchiffeVersenken.feld = new Spielfeld(10, 10, m);
		}
	}
}

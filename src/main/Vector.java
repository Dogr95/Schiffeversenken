package main;

public class Vector {
	int posX;
	int posY;
	public static char[] characters = {
			'A', 'B', 'C',
			'D', 'E', 'F',
			'G', 'H', 'I',
			'J'
			};
	
	Vector(Spielfeld f, int pX, int pY) {
		posX = pX;
		posY = pY;
	}
	
	public static boolean inBounds(Spielfeld f, int x, int y) {
		return x < f.grid.length && x >= 0 && y < f.grid[x].length && y >= 0;
	}
	
	public static Vector validate(Spielfeld f, String input) {
		try {
			input = input.toUpperCase();
			char y = input.charAt(0);
			int x = Integer.parseInt(String.valueOf(input.charAt(1)))-1;
			if(input.length() > 2) x += (10 * (Integer.parseInt(String.valueOf(input.charAt(2)))+1))-1;
			int result = -1;
			for(int i = 0; i < characters.length; i++) {
				if(characters[i] == y) {
					result = i;
					break;
				}
			}
			if(result != -1 && inBounds(f, x, result)) {
				return new Vector(f, x, result);
			} else {
				return null;
			}
		} catch (StringIndexOutOfBoundsException e) {
			return null;
		} catch (NumberFormatException e) {
			try {
				input = input.toUpperCase();
				char y = input.charAt(1);
				int x = Integer.parseInt(String.valueOf(input.charAt(0)))-1;;
				if(input.length() > 2) {
					x += (10 * (Integer.parseInt(String.valueOf(input.charAt(1)))+1))-1;
					y = input.charAt(2);
				}
				int result = -1;
				for(int i = 0; i < characters.length; i++) {
					if(characters[i] == y) {
						result = i;
						break;
					}
				}
				if(result != -1 && inBounds(f, x, result)) {
					return new Vector(f, x, result);
				} else {
					return null;
				}
			} catch(StringIndexOutOfBoundsException sE) {
				return null;
			} catch (NumberFormatException eE) {
				return null;
			}
		}
	}
} 

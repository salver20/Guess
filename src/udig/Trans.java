package udig;

public class Trans {
	private int x;
	private int y;

	public void transform(String s) {
		int nif = -1;
		for (int i = 0; i < s.length(); i++) {
			if (s.charAt(i) == ',') {
				nif = i;
			}
		}
		String hd = s.substring(0, nif);
		String hl = s.substring(nif + 1);
		
		x = Integer.parseInt(hd);
		y = Integer.parseInt(hl);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}

package udig;

import java.awt.*;

public class Mark{
	public int R, G, B;
	public float pstroke;
	public float estroke;
	public int x1, x2, y1, y2;// 定义坐标属性

	void draw(Graphics2D g2d) {
	}
}

class Pencil extends Mark {

	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(R, G, B));
		g2d.setStroke(new BasicStroke(pstroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
}

class Eraser extends Mark {
	
	void draw(Graphics2D g2d) {
		g2d.setPaint(new Color(255, 255, 255));// 白色
		g2d.setStroke(new BasicStroke(estroke, BasicStroke.CAP_ROUND,
				BasicStroke.JOIN_BEVEL));
		g2d.drawLine(x1, y1, x2, y2);
	}
}

package udig;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import javax.swing.*;

public class Drawarea extends JPanel {

	private static final long serialVersionUID = 1L;
	private Drawpad drawpad = null;
	public Mark[] mark = new Mark[5000];
	private int currentChoice = 1;
	public int index = 0;
	private Color color = Color.black;
	private int R, G, B;
	private float pstroke = 1.0f;
	private float estroke = 4.0f;
	private DatagramSocket dgs;

	Drawarea(Drawpad dp) {
		drawpad = dp;
		// 把鼠标设置成十字形
		setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
		setBackground(Color.white);// 设置绘制区的背景是白色
		addMouseListener(new MouseA());// 添加鼠标事件
		addMouseMotionListener(new MouseB());
		createNewitem();
		try {
			dgs = new DatagramSocket();
		} catch (SocketException e) {
			e.printStackTrace();
		}
	}

	public void setR(int r) {
		R = r;
	}

	public void setG(int g) {
		G = g;
	}

	public void setB(int b) {
		B = b;
	}

	public void setPS(float ps) {
		pstroke = ps;
	}

	public void setES(float es) {
		estroke = es;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		int j = 0;
		while (j <= index) {
			draw(g2d, mark[j]);
			j++;
		}
	}

	void draw(Graphics2D g2d, Mark m) {
		m.draw(g2d);
	}

	void createNewitem() {
		switch (currentChoice) {

		case 1:
			mark[index] = new Pencil();
			break;
		case 2:
			mark[index] = new Eraser();
			break;
		}
		mark[index].R = R;
		mark[index].G = G;
		mark[index].B = B;
		mark[index].pstroke = this.pstroke;
		mark[index].estroke = this.estroke;
	}

	public void setCurrentChoice(int i) {
		currentChoice = i;
		try {
			String s = "CU" + currentChoice + "-";
			DatagramPacket dgp = new DatagramPacket(s.getBytes(),
					s.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void chooseColor()// 选择当前颜色
	{
		color = JColorChooser.showDialog(drawpad, "请选择颜色", color);
		try {
			R = color.getRed();
			G = color.getGreen();
			B = color.getBlue();
		} catch (Exception e) {
			R = 0;
			G = 0;
			B = 0;
		}
		mark[index].R = R;
		mark[index].G = G;
		mark[index].B = B;
		try {
			String s = "RE" + R + "-";
			DatagramPacket dgp = new DatagramPacket(s.getBytes(),
					s.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp);

			String s2 = "GR" + G + "-";
			DatagramPacket dgp2 = new DatagramPacket(s2.getBytes(),
					s2.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp2);

			String s3 = "BL" + B + "-";
			DatagramPacket dgp3 = new DatagramPacket(s3.getBytes(),
					s3.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp3);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setPstroke()// 画笔粗细的调整
	{
		String input;
		input = JOptionPane.showInputDialog("请输入画笔的粗细( >0 )");
		try {
			pstroke = Float.parseFloat(input);
		} catch (Exception e) {
			pstroke = 1.0f;
		}
		mark[index].pstroke = pstroke;
		try {
			String s = "PS" + pstroke + "-";
			DatagramPacket dgp = new DatagramPacket(s.getBytes(),
					s.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void setEstroke()// 画笔粗细的调整
	{
		String input;
		input = JOptionPane.showInputDialog("请输入橡皮的粗细( >0 )");
		try {
			estroke = Float.parseFloat(input);
		} catch (Exception e) {
			estroke = 4.0f;
		}
		mark[index].estroke = estroke;
		try {
			String s = "ES" + estroke + "-";
			DatagramPacket dgp = new DatagramPacket(s.getBytes(),
					s.getBytes().length, InetAddress.getByName("127.0.0.1"),
					8888);
			dgs.send(dgp);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	class MouseA extends MouseAdapter {

		public void mouseEntered(MouseEvent me) {
			drawpad.setstatebar("鼠标进入在：[" + me.getX() + " ," + me.getY() + "]");
		}

		public void mouseExited(MouseEvent me) {
			drawpad.setstatebar("鼠标退出在：[" + me.getX() + " ," + me.getY() + "]");
		}

		public void mousePressed(MouseEvent me) {
			drawpad.setstatebar("鼠标按下在：[" + me.getX() + " ," + me.getY() + "]");
			mark[index].x1 = mark[index].x2 = me.getX();
			mark[index].y1 = mark[index].y2 = me.getY();
			index++;
			createNewitem();
			try {
				String s = "XY" + me.getX() + "," + me.getY() + "-";
				DatagramPacket dgp = new DatagramPacket(s.getBytes(),
						s.getBytes().length,
						InetAddress.getByName("127.0.0.1"), 8888);
				dgs.send(dgp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		public void mouseReleased(MouseEvent me) {
			drawpad.setstatebar("鼠标松开在：[" + me.getX() + " ," + me.getY() + "]");
			mark[index].x1 = mark[index].x2 = me.getX();
			mark[index].y1 = mark[index].y2 = me.getY();
			repaint();
			index++;
			createNewitem();
			try {
				String s = "xY" + me.getX() + "," + me.getY() + "-";
				DatagramPacket dgp = new DatagramPacket(s.getBytes(),
						s.getBytes().length,
						InetAddress.getByName("127.0.0.1"), 8888);
				dgs.send(dgp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	class MouseB extends MouseMotionAdapter {
		public void mouseDragged(MouseEvent me)// 鼠标的拖动
		{
			drawpad.setstatebar("鼠标拖动在：[" + me.getX() + " ," + me.getY() + "]");
			mark[index - 1].x1 = mark[index].x2 = mark[index].x1 = me.getX();
			mark[index - 1].y1 = mark[index].y2 = mark[index].y1 = me.getY();
			index++;
			createNewitem();
			repaint();
			try {
				String s = "Xy" + me.getX() + "," + me.getY() + "-";
				DatagramPacket dgp = new DatagramPacket(s.getBytes(),
						s.getBytes().length,
						InetAddress.getByName("127.0.0.1"), 8888);
				dgs.send(dgp);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}

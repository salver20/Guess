package udig;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

//主界面类
public class Drawpad extends JFrame implements ActionListener {
	private JMenuBar bar;// 定义菜单栏
	private JMenu help, start;// 定义菜单
	private JMenuItem helpcontent, aboutit;// 定义help菜单项
	private JMenuItem addgamer;// 定义start菜单项
	private JToolBar Tool;// 定义工具栏
	private JButton stroke, strokesize, color, eraser, erasersize;// 定义工具栏按钮
	private JLabel statebar, word, timecounter;// 定义状态栏，词语显示区，计时区，聊天区
	private Chatarea chatingarea;
	private JPanel workingarea;
	private Drawarea drawingarea;// 定义绘画区、工作区、交流区
	public int UDPport;
	public Socket socket;

	// 主界面的构造函数

	public Drawpad(String name, int port, Socket s) throws IOException {
		super(name);
		UDPport = port;
		socket = s;

		bar = new JMenuBar();// 菜单栏的初始化
		help = new JMenu("帮助");
		start = new JMenu("开始");

		// help菜单项的初始化
		helpcontent = new JMenuItem("帮助");
		aboutit = new JMenuItem("关于软件");
		helpcontent.addActionListener(this);
		aboutit.addActionListener(this);

		// 界面中添加菜单条
		setJMenuBar(bar);

		bar.add(help);
		bar.add(start);

		addgamer = new JMenuItem("添加玩家");
		addgamer.addActionListener(this);
		start.add(addgamer);

		help.add(helpcontent);
		help.add(aboutit);

		// 初始化工具栏
		Tool = new JToolBar(JToolBar.HORIZONTAL);

		// 初始化工具栏按钮
		stroke = new JButton("画笔");
		strokesize = new JButton("画笔大小");
		color = new JButton("颜色");
		eraser = new JButton("橡皮");
		erasersize = new JButton("橡皮大小");

		stroke.addActionListener(this);
		strokesize.addActionListener(this);
		erasersize.addActionListener(this);
		eraser.addActionListener(this);
		color.addActionListener(this);

		// 工具栏中添加按钮
		Tool.add(stroke);
		Tool.add(strokesize);
		Tool.add(color);
		Tool.add(eraser);
		Tool.add(erasersize);

		// 初始化词语显示区、计时器、聊天区、状态栏
		word = new JLabel("词语");
		timecounter = new JLabel("3:00");

		statebar = new JLabel("你画我猜");

		// 初始化绘画区、工作区、交流区

		workingarea = new JPanel();
		drawingarea = new Drawarea(this);
		chatingarea = new Chatarea(this);

		// 初始化布局方式
		BorderLayout L1 = new BorderLayout();
		BorderLayout L3 = new BorderLayout();

		// 为中间容器设置布局方式
		workingarea.setLayout(L1);

		// 工作区内添加组件
		workingarea.add(word, BorderLayout.NORTH);
		workingarea.add(timecounter, BorderLayout.SOUTH);

		setLayout(L3);
		add(Tool, BorderLayout.NORTH);
		add(workingarea, BorderLayout.WEST);
		add(chatingarea, BorderLayout.EAST);
		add(statebar, BorderLayout.SOUTH);
		add(drawingarea, BorderLayout.CENTER);

		Toolkit tool = getToolkit();// 得到一个Tolkit类的对象（主要用于得到屏幕的大小）
		Dimension dim = tool.getScreenSize();// 得到屏幕的大小 （返回Dimension对象）
		setBounds(40, 40, dim.width - 70, dim.height - 100);
		setVisible(true);
		validate();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void setstatebar(String s) {
		statebar.setText(s);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == eraser) {
			drawingarea.setCurrentChoice(2);
		}
		if (e.getSource() == strokesize) {
			drawingarea.setPstroke();
		}
		if (e.getSource() == stroke) {
			drawingarea.setCurrentChoice(1);
		}
		if (e.getSource() == erasersize) {
			drawingarea.setEstroke();
		}
		if (e.getSource() == color) {
			drawingarea.chooseColor();
		}
		if (e.getSource() == helpcontent) {
			JOptionPane.showMessageDialog(this, "你画我猜", "你画我猜",
					JOptionPane.WARNING_MESSAGE);
		}
		if (e.getSource() == aboutit) {
			JOptionPane.showMessageDialog(this, "你画我猜" + "\n" + "版本: 1.1.1"
					+ "\n" + "时间:2015", "你画我猜", JOptionPane.WARNING_MESSAGE);
		}

	}

	public Chatarea getChatingarea() {
		return chatingarea;
	}

	public void setChatingarea(Chatarea chatingarea) {
		this.chatingarea = chatingarea;
	}

	public Drawarea getDrawingarea() {
		return drawingarea;
	}

	public void setDrawingarea(Drawarea drawingarea) {
		this.drawingarea = drawingarea;
	}

}

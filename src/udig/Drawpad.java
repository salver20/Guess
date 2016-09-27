package udig;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.Socket;

import javax.swing.*;

//��������
public class Drawpad extends JFrame implements ActionListener {
	private JMenuBar bar;// ����˵���
	private JMenu help, start;// ����˵�
	private JMenuItem helpcontent, aboutit;// ����help�˵���
	private JMenuItem addgamer;// ����start�˵���
	private JToolBar Tool;// ���幤����
	private JButton stroke, strokesize, color, eraser, erasersize;// ���幤������ť
	private JLabel statebar, word, timecounter;// ����״̬����������ʾ������ʱ����������
	private Chatarea chatingarea;
	private JPanel workingarea;
	private Drawarea drawingarea;// ����滭������������������
	public int UDPport;
	public Socket socket;

	// ������Ĺ��캯��

	public Drawpad(String name, int port, Socket s) throws IOException {
		super(name);
		UDPport = port;
		socket = s;

		bar = new JMenuBar();// �˵����ĳ�ʼ��
		help = new JMenu("����");
		start = new JMenu("��ʼ");

		// help�˵���ĳ�ʼ��
		helpcontent = new JMenuItem("����");
		aboutit = new JMenuItem("�������");
		helpcontent.addActionListener(this);
		aboutit.addActionListener(this);

		// ��������Ӳ˵���
		setJMenuBar(bar);

		bar.add(help);
		bar.add(start);

		addgamer = new JMenuItem("������");
		addgamer.addActionListener(this);
		start.add(addgamer);

		help.add(helpcontent);
		help.add(aboutit);

		// ��ʼ��������
		Tool = new JToolBar(JToolBar.HORIZONTAL);

		// ��ʼ����������ť
		stroke = new JButton("����");
		strokesize = new JButton("���ʴ�С");
		color = new JButton("��ɫ");
		eraser = new JButton("��Ƥ");
		erasersize = new JButton("��Ƥ��С");

		stroke.addActionListener(this);
		strokesize.addActionListener(this);
		erasersize.addActionListener(this);
		eraser.addActionListener(this);
		color.addActionListener(this);

		// ����������Ӱ�ť
		Tool.add(stroke);
		Tool.add(strokesize);
		Tool.add(color);
		Tool.add(eraser);
		Tool.add(erasersize);

		// ��ʼ��������ʾ������ʱ������������״̬��
		word = new JLabel("����");
		timecounter = new JLabel("3:00");

		statebar = new JLabel("�㻭�Ҳ�");

		// ��ʼ���滭������������������

		workingarea = new JPanel();
		drawingarea = new Drawarea(this);
		chatingarea = new Chatarea(this);

		// ��ʼ�����ַ�ʽ
		BorderLayout L1 = new BorderLayout();
		BorderLayout L3 = new BorderLayout();

		// Ϊ�м��������ò��ַ�ʽ
		workingarea.setLayout(L1);

		// ��������������
		workingarea.add(word, BorderLayout.NORTH);
		workingarea.add(timecounter, BorderLayout.SOUTH);

		setLayout(L3);
		add(Tool, BorderLayout.NORTH);
		add(workingarea, BorderLayout.WEST);
		add(chatingarea, BorderLayout.EAST);
		add(statebar, BorderLayout.SOUTH);
		add(drawingarea, BorderLayout.CENTER);

		Toolkit tool = getToolkit();// �õ�һ��Tolkit��Ķ�����Ҫ���ڵõ���Ļ�Ĵ�С��
		Dimension dim = tool.getScreenSize();// �õ���Ļ�Ĵ�С ������Dimension����
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
			JOptionPane.showMessageDialog(this, "�㻭�Ҳ�", "�㻭�Ҳ�",
					JOptionPane.WARNING_MESSAGE);
		}
		if (e.getSource() == aboutit) {
			JOptionPane.showMessageDialog(this, "�㻭�Ҳ�" + "\n" + "�汾: 1.1.1"
					+ "\n" + "ʱ��:2015", "�㻭�Ҳ�", JOptionPane.WARNING_MESSAGE);
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

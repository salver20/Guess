package udig;

import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

import javax.swing.*;

public class Chatarea extends JPanel implements ActionListener {

	private Drawpad drawpad = null;
	private JTextField tf = new JTextField(40);
	private JButton send = new JButton("send");
	private JTextArea ta = new JTextArea();
	private JPanel p = new JPanel();
	private String s = null;
	private BufferedWriter bw;
	private PrintWriter pw;
	private Socket socket;

	public Chatarea(Drawpad dp) throws IOException {
		drawpad = dp;
		setLayout(new BorderLayout());
		add(new JScrollPane(ta), BorderLayout.CENTER);
		p.add(tf);
		p.add(send);
		add(p, BorderLayout.SOUTH);
		setVisible(true);
		ta.setBackground(Color.white);
		ta.setForeground(Color.gray);

		tf.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == KeyEvent.VK_ENTER) {
					send.doClick();
				}
			}
		});
		send.addActionListener(this);
		socket = drawpad.socket;
		try {
			bw = new BufferedWriter(new OutputStreamWriter(
					socket.getOutputStream()));
			pw = new PrintWriter(bw, true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == send) {
			s = tf.getText();
			if (s != null && s != "") {
				pw.println(s);
				pw.flush();
				s = null;
			}
			tf.setText("");
		}
	}

	public void Send(String str) {
		String line = System.getProperty("line.separator");
		ta.append(str);
		ta.append(line);
	}
}

package udig;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;

public class Postman extends Thread {
	private Socket socket;
	private BufferedReader br;
	//private BufferedWriter bw;
	//private PrintWriter pw;
	private int count;
	private int clUDPport;

	public Postman(Socket s, int count, int UDPport) throws IOException {
		this.socket = s;
		this.count = count;
		this.clUDPport = UDPport;
		start();
	}

	private void SendUDPport() throws IOException {
		String port = String.valueOf(clUDPport);
		BufferedWriter bw = new BufferedWriter(
				new OutputStreamWriter(socket.getOutputStream()));
		PrintWriter pwp = new PrintWriter(bw, true);
		pwp.println(port);
		pwp.flush();
	}

	public void run() {
		try {
			SendUDPport();
			br = new BufferedReader(new InputStreamReader(
					socket.getInputStream()));
			String str = "";
			while (true) {
				str = br.readLine();
				str = "player" + count + "said: " + str;
				for (int i = 0; i < Server.count; i++) {
					BufferedWriter bwc = new BufferedWriter(
							new OutputStreamWriter(
									Server.clients[i].getOutputStream()));
					PrintWriter pwc = new PrintWriter(bwc, true);
					pwc.println(str);
					pwc.flush();
				}
			}
		} catch (Exception e) {
			e.getStackTrace();
		}
	}
}

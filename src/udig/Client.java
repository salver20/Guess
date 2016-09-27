package udig;

import java.io.BufferedReader;

import java.io.IOException;
import java.io.InputStreamReader;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.Socket;

public class Client {
	public Socket socket;
	public Drawpad dp;
	public Chatarea ca;
	public Drawarea da;
	private BufferedReader br;
	public int UDPport;
	private InetAddress addr;

	public Client() throws IOException {
		addr = InetAddress.getByName(null);
		socket = new Socket(addr, Server.PORT);
		getUDPport();
		dp = new Drawpad("Äã»­ÎÒ²Â", UDPport, socket);
		ca = dp.getChatingarea();
		da = dp.getDrawingarea();
		ca.Send("player" + "ÒÑµÇÂ¼");

		new Thread(new Runnable() {
			public void run() {
				try {
					br = new BufferedReader(new InputStreamReader(
							socket.getInputStream()));
					String str = "";
					while (true) {
						str = br.readLine();
						ca.Send(str);
					}
				} catch (Exception e) {
					System.out.println("client meets mistakes");
				}
			}
		}).start();

		new Thread(new Runnable() {
			public void run() {
				try {
					DatagramSocket DataReceive = new DatagramSocket(UDPport);
					while (true) {
						byte buf[] = new byte[1024];
						DatagramPacket dp = new DatagramPacket(buf, buf.length);
						DataReceive.receive(dp);
						String temp = new String(dp.getData(), 0, dp
								.getLength());

						int end = 0;
						for (int i = 2; i < temp.length(); i++) {
							if (temp.charAt(i) == '-') {
								end = i;
								break;
							}
						}

						String info = temp.substring(0, end);
						String id = info.substring(0, 2);
						Trans trans = new Trans();
						switch (id) {
						case "CU":
							String me = info.substring(2);
							int data = Integer.parseInt(me);
							da.setCurrentChoice(data);
							break;
						case "RE":
							String me2 = info.substring(2);
							int data2 = Integer.parseInt(me2);
							da.setR(data2);
							break;
						case "GR":
							String me3 = info.substring(2);
							int data3 = Integer.parseInt(me3);
							da.setG(data3);
							break;
						case "BL":
							String me4 = info.substring(2);
							int data4 = Integer.parseInt(me4);
							da.setB(data4);
							break;
						case "PS":
							String me5 = info.substring(2);
							float data5 = Float.parseFloat(me5);
							da.setPS(data5);
							break;
						case "ES":
							String me6 = info.substring(2);
							float data6 = Float.parseFloat(me6);
							da.setES(data6);
							break;
						case "XY":
							String me7 = info.substring(2);
							trans.transform(me7);
							da.mark[da.index].x1 = da.mark[da.index].x2 = trans
									.getX();
							da.mark[da.index].y1 = da.mark[da.index].y2 = trans
									.getY();
							da.repaint();
							da.index++;
							da.createNewitem();
							break;
						case "xY":
							String me8 = info.substring(2);
							trans.transform(me8);
							da.mark[da.index].x1 = da.mark[da.index].x2 = trans
									.getX();
							da.mark[da.index].y1 = da.mark[da.index].y2 = trans
									.getY();
							da.repaint();
							da.index++;
							da.createNewitem();
							break;
						case "Xy":
							String me9 = info.substring(2);
							trans.transform(me9);
							da.mark[(da.index) - 1].x1 = da.mark[da.index].x2 = da.mark[da.index].x1 = trans
									.getX();
							da.mark[(da.index) - 1].y1 = da.mark[da.index].y2 = da.mark[da.index].y1 = trans
									.getY();
							da.repaint();
							da.index++;
							da.createNewitem();
							break;
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void getUDPport() throws IOException {
		br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		String port = br.readLine();
		UDPport = Integer.parseInt(port);
	}

	public static void main(String[] args) throws IOException {
		Client cl = new Client();
	}
}
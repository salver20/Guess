package udig;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

public class Server {
	public static final int PORT = 8942;
	public static Socket[] clients = new Socket[5];
	public static int count = 0;
	public int port[] = new int[] { 6000, 6008, 6018, 6028, 6038 };

	public Server() throws IOException {
		ServerSocket s = new ServerSocket(PORT);
		DatagramSocket receivesocket = new DatagramSocket(8888);
		DatagramSocket sendsocket = new DatagramSocket();

		try {
			new Thread(new Runnable() {
				public void run() {
					while (true) {
						byte buf[] = new byte[1024];
						DatagramPacket dp = new DatagramPacket(buf, buf.length);
						try {
							receivesocket.receive(dp);

						} catch (IOException e) {
							e.printStackTrace();
						}
						byte temp[] = new byte[1024];
						temp = dp.getData();
						for (int i = 0; i < count; i++) {
							if (port[i] != dp.getPort()) {
								DatagramPacket sp = null;
								try {
									sp = new DatagramPacket(temp, temp.length,
											InetAddress.getByName("127.0.0.1"),
											port[i]);
								} catch (UnknownHostException e) {
									e.printStackTrace();
								}
								try {
									sendsocket.send(sp);
								} catch (IOException e) {
									e.printStackTrace();
								}
							}
						}
					}
				}
			}).start();

			while (true) {
				Socket serversocket = s.accept();
				if (count < 5) {
					clients[count] = serversocket;
					count++;
				}
				new Postman(serversocket, count, port[count]);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			receivesocket.close();
			sendsocket.close();
		}
	}

	public static void main(String[] args) throws IOException {
		Server test = new Server();
	}
}
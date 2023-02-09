package ezen.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class SocketClient extends Thread {
	
	private Socket socket;
	
	public SocketClient(Socket socket) {
		this.socket = socket;
	}

	@Override
	public void run() {
		DataInputStream in = null;
		DataOutputStream out = null;
		try {
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			while (true) {
				String clientMessage = in.readUTF();
				System.out.println("[클라이언트]로부터 수신한 메시지 : " + clientMessage);
				if (clientMessage.equalsIgnoreCase("q")) {
					break;
				}
				out.writeUTF(clientMessage);
			}
			socket.close();
			System.out.println("[클라이언트(" + socket.getInetAddress().getHostAddress() + ")] 연결 종료함...");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}

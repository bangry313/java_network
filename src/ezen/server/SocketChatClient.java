package ezen.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 접속한 클라이언트와 1:1로 통신하는 역할의 스레드
 * @author 김기정
 * @Date   2023. 2. 7.
 */
public class SocketChatClient extends Thread {

	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private String clientIp;
	private String clientNickName;
	
	private ChatServer chatServer;

	public SocketChatClient(Socket socket, ChatServer chatServer) {
		try {
			this.socket = socket;
			this.chatServer = chatServer;
			in = new DataInputStream(socket.getInputStream());
			out = new DataOutputStream(socket.getOutputStream());
			clientIp = socket.getInetAddress().getHostAddress();
		} catch (IOException e) {}
	}
	
	public String getClientIp() {
		return clientIp;
	}

	public String getClientNickName() {
		return clientNickName;
	}

	// 클라이언트 메시지 수신
	public void receiveMessage() {
		try {
			while (true) {
				String clientMessage = in.readUTF();
				System.out.println("[클라이언트]로부터 수신한 메시지 : " + clientMessage);
				//sendMessage(clientMessage);
				// 자기 포함해서 현재 접속한 모든 클라이언트에게 메시지 전송
				chatServer.sendAllMessage(clientMessage);
				if (clientMessage.equalsIgnoreCase("q")) {
					break;
				}
			}
		} catch (IOException e) {} 
		finally {
			System.out.println("[클라이언트(" + socket.getInetAddress().getHostAddress() + ")] 연결 종료함...");
		}
	}
	
	// 클라이언트에게 메시지 전송
	public void sendMessage(String message) {
		try {
			out.writeUTF(message);
			out.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 연결 종료
	public void close() {
		try {
			if(socket != null) socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	// 스레드의 실행 진입점
	@Override
	public void run() {
		receiveMessage();
	}

}

package ezen.client;

import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP/IP 기반의 ChatClient
 * @author 김기정
 * @Date   2023. 2. 6.
 */
public class ChatClient {
	
	private static final String SERVER_IP = "localhost";
	private static final int SERVER_PORT = 7777;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	
	private String nickName;
	
	// 서버 연결
	public void connectServer() throws IOException {
		socket = new Socket(SERVER_IP, SERVER_PORT);
		in = new DataInputStream(socket.getInputStream());
		out = new DataOutputStream(socket.getOutputStream());
	}
	
	// 서버 연결 종료
	public void disConnectServer() throws IOException {
		socket.close();
	}
	
	// 메시지 전송
	public void sendMessage(String message) throws IOException {
		out.writeUTF(message);
		out.flush();
	}
	
	// 메시지 수신
	public void receiveMessage() {
		// 서버로부터 전송되는 메시지를 실시간 수신하기 위해 스레드 생성 및 시작
		Thread thread = new Thread() {
			public void run() {
				try {
					while (true) {
						System.out.println("데이터 수신 시작");
						String serverMessage = in.readUTF();
						System.out.println("[서버]로부터 수신한 메시지 : " + serverMessage);
					}
				} catch (IOException e) {} 
				finally {
					//System.out.println("[서버]와 연결 종료함...");
				}
			}
		};
		thread.start();
	}
}









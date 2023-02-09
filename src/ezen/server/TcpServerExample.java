package ezen.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * TCP/IP 기반의 자바 서버
 * @author 김기정
 * @Date   2023. 2. 6.
 */
public class TcpServerExample {

	public static void main(String[] args) {
		int port = 2023;
		// #1. 모든 클라이언트의 연결을 수신(청취)하기 위해 ServerSocket 생성
		try {
			System.out.println("[서버] 구동되었습니다");
			ServerSocket serverSocket = new ServerSocket(port);
			System.out.println("[서버("+port+")]가 클라이언트 연결 요청을 수신 대기합니다..");
			
			while(true) {
				// #2.연결 수신 대기
				Socket socket = serverSocket.accept(); // block 메소드
				InetAddress ia = socket.getInetAddress();
				System.out.println("[클라이언트("+ia.getHostAddress()+")] 연결해옴...");
				
				// #3. 생성된 소켓을 이용하여 데이터 송수신
				InputStream is = socket.getInputStream();
				//int data = is.read();
				DataInputStream dis = new DataInputStream(is);
				//System.out.println("[클라이언]로부터 수신한 데이터 : " + data);
				//String clientMessage = dis.readUTF();
				//System.out.println("[클라이언]로부터 수신한 데이터 : " + clientMessage);
				
				// 접속 클라이언트에게 데이터 전송
				OutputStream os = socket.getOutputStream();
				DataOutputStream dos = new DataOutputStream(os);
				//os.write(data*2);
				// Echo
				///dos.writeUTF(clientMessage);
				
				while(true) {
					String clientMessage = dis.readUTF();
					System.out.println("[클라이언트]로부터 수신한 메시지 : " + clientMessage);
					if(clientMessage.equalsIgnoreCase("q")) {
						break;
					}
					dos.writeUTF(clientMessage);
				}
				
				//os.close();
				//is.close();
				// #4. 연결 끊기(소켓 닫기)
				socket.close();
				System.out.println("[클라이언트]와 연결을 종료합니다..");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		

	}

}























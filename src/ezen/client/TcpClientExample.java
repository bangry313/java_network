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
 * TCP/IP 기반의 자바 클라이언트
 * @author 김기정
 * @Date   2023. 2. 6.
 */
public class TcpClientExample {
	public static void main(String[] args) {
//		String serverIp = "192.168.0.22";
		int serverPort = 2023;
//		String serverIp = "127.0.0.1";
		String serverIp = "localhost";
//		String ssamIp = "192.168.0.22";
		
		// #1. Tcp 서버 연결
		try {
			Socket socket = new Socket(serverIp , serverPort);
			System.out.println("[서버("+serverIp+", "+serverPort+")]와 연결되었습니다..");
			
			// #2. 생성된 소켓을 이용하여 데이터 송수신
			//byte data = 10;
			//String message = "안녕하세요 서버!!!";
			
			// Socket과의 바이트 출력 스트림 생성
			OutputStream os = socket.getOutputStream();
			// Socket에 써주는 거지만 OS가 지원하는 TCP/IP 계층에 의해 서버로 전송된다.
			//os.write(data);
			DataOutputStream dos = new DataOutputStream(os);
			//dos.writeUTF(inputMessage);
			
			// 서버로 부터 데이터 수신
			InputStream in = socket.getInputStream();
			//int result = in.read();
			//System.out.println("[서버]로부터 받은 결과 : " + result);]
			DataInputStream dis = new DataInputStream(in);
			//String serverMessage = dis.readUTF();
			//System.out.println("[서버]로부터 받은 메시지 : " + serverMessage);
			
			Scanner scanner = new Scanner(System.in);
			while(true) {
				String inputMessage = null;
				System.out.print("서버에 전송할 메시지: ");
				inputMessage = scanner.nextLine();
				// 서버로 전송
				dos.writeUTF(inputMessage);
				
				if(inputMessage.equalsIgnoreCase("q")) {
					break;
				}
				String serverMessage = dis.readUTF();
				System.out.println("[서버]로부터 받은 메시지 : " + serverMessage);
			}
			
			//in.close();
			//os.close();
			// #3. 연결 끊기(소켓 닫기)
			socket.close();
			System.out.println("[서버]와 연결 종료...");
		} catch (IOException e) {
			System.out.println("서버를 찾을 수 없습니다..");
		}
		
		

	}

}









package ezen.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * TCP/IP 기반의 에코 서버
 * 
 * @author 김기정
 * @Date 2023. 2. 6.
 */
public class EchoServer {
	private static final int PORT = 2023;
	private static ServerSocket serverSocket;

	// 서버 구동
	public static void startServer() throws IOException {
		Thread thread = new Thread() {
			@Override
			public void run() {
				try {
					serverSocket = new ServerSocket(PORT);
					System.out.println("[서버(" + PORT + ")] 구동되었습니다");
					echo();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		};
		thread.start();
	}

	// 클라이언 연결 수신 및 데이터 에코 기능
	public static void echo() {
		while (true) {
			System.out.println("[서버(" + PORT + ")]가 클라이언트 연결 요청을 수신 대기합니다..");
			Socket socket = null;
			try {
				socket = serverSocket.accept();
				System.out.println("[클라이언트(" + socket.getInetAddress().getHostAddress() + ")] 연결해옴...");
				
				// 클라이언트와 데이터 송수신 스레드 생성 및 실행
				SocketClient socketClient = new SocketClient(socket);
				socketClient.start();				
				
			} catch (IOException e) {
				break;
			}
		}
	}

	// 서버 종료
	public static void stopServer() throws IOException {
		serverSocket.close();
		System.out.println("[서버] 종료합니다..");
	}

	public static void main(String[] args) {
		System.out.println("※※※ 서버를 종료하려면 q 명령어를 입력하세요 ※※※");
		
		try {
			startServer();
		} catch (IOException e) {
			System.err.println("서버 실행 중 아래와 같은 오류가 발생하였습니다..");
			System.err.println(e.getMessage());
		}

		// GUI 프로그램 대신 Scanner 활용 서버 종료 처리
		Scanner scanner = new Scanner(System.in);
		while (true) {
			String command = scanner.nextLine();
			if (command.equalsIgnoreCase("q")) {
				break;
			}
		}
		scanner.close();

		// 서버 종료
		try {
			stopServer();
		} catch (IOException e) {
		}
	}
}

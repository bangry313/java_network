package ezen.ftp.client;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;


/**
 * Socket 바이트 스트림을 이용하여 파일 다운로드 클라이언트
 * @author 김기정
 */
public class FTPClient {
	
//	public static final String SERVER = "127.0.0.1";
	public static final String SERVER = "localhost";
	public static final int PORT = 2023;
	
	private Socket socket;
	private DataInputStream in;
	private DataOutputStream out;
	private boolean stop;
	
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public DataInputStream getIn() {
		return in;
	}
	public void setIn(DataInputStream in) {
		this.in = in;
	}
	public DataOutputStream getOut() {
		return out;
	}
	public void setOut(DataOutputStream out) {
		this.out = out;
	}
	public boolean isStop() {
		return stop;
	}
	
	public void setStop(boolean stop) {
		this.stop = stop;
	}
	
	/** 서버 연결 */
	public void connect() throws UnknownHostException, IOException{
		socket = new Socket(SERVER, PORT);
		out = new DataOutputStream(socket.getOutputStream());
		in = new DataInputStream(socket.getInputStream());
	}
	
	/** 서버 연결 끊기 */
	public void disConnect(){
		try {
			if(in != null)	in.close();
			if(out != null)	out.close();
			if(socket != null)	socket.close();
		} catch (IOException e) {}
	}
	
	/** 파일 다운로드 */
	public void fileDownload() throws IOException{
		// 다운로드 가능한 파일 목록 수신
		//"파일명1,파일명2" CSV 데이터
		String fileList = in.readUTF();
		
		System.out.println("□□□□□ 다운로드 가능한 파일 목록 □□□□□");
		String[] files = fileList.split(",");
		for (String fileName : files) {
			System.out.println("\t" + fileName);
		}
		
		System.out.println();
		
		// 사용자로부터 다운로드하고자 하는 파일명 입력
		System.out.print("다운로드하고자 하는 파일명 입력 : ");
		Scanner scanner = new Scanner(System.in);
		String downFileName = scanner.nextLine();
		scanner.close();
		
		// 파일명 FTP서버에 전송
		out.writeUTF(downFileName);
					
		// 다운로드하고자 하는 파일 사이즈 수신
		long fileSize = in.readLong();
		
		// 파일 데이터 수신
		String downloadDirectory = "C:/Downloads";
		File directory = new File(downloadDirectory);
		if(!directory.exists()){
			directory.mkdir();
		}
		
		File df = new File(downloadDirectory, downFileName);
		FileOutputStream fos = new FileOutputStream(df);
		
		// 파일다운로드 프로그래스창 생성
		ProgressBarFrame frame = new ProgressBarFrame(downFileName);
		frame.init();
		
		byte[] buffer = new byte[1024];
		int count = 0;
		int copyRate = 0;
		double copySize = 0;
		
		while((count=in.read(buffer)) != -1){
			fos.write(buffer, 0, count);
			copySize += count;
			copyRate = (int)((copySize/fileSize) * 100);
							
			// 복사비율 진행창에 표시
			frame.setProgress(copyRate);
		}
		fos.close();
		System.out.println(downFileName +" 파일 다운로드 완료...");
	}
	
	public static void main(String[] args) throws UnknownHostException {
		FTPClient client = new FTPClient();
		try {
			client.connect();
			System.out.println("FTPServer Connected.....");
			
			client.fileDownload();
			
		} catch (IOException e) {
			System.out.println("아래와 같은 예외가 발생하여 서버와 연결할 수 없습니다.");
			System.out.println(e.toString());
		}finally{
			client.disConnect();
		}

	}
	
}













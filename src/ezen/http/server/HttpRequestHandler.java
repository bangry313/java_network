package ezen.http.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.StringTokenizer;

public class HttpRequestHandler extends Thread {
	
	private Socket socket;
	private BufferedReader in;
	private BufferedReader fileReader;
	private PrintWriter out;
	
	public HttpRequestHandler(Socket socket) throws IOException{
		this.socket = socket;
		in = new BufferedReader(new InputStreamReader(socket.getInputStream(),"utf-8"));
		out = new PrintWriter(socket.getOutputStream(),true);
	}
	
	public void service() {
		String requestLine = null;
		try {
			requestLine = in.readLine(); //예) "GET /index.html HTTT/1.0"
			StringTokenizer requstTokenizer = new StringTokenizer(requestLine, " ");
			String method = requstTokenizer.nextToken();			
			String uri = requstTokenizer.nextToken();
			// xxx.html, xxx.jpg, xxx.gif, xxx.zip
			File file = new File(MyHttpServer.WEB_DIRECTORY, uri);
			
			String responseMessage = null;
			// 요청파일 존재시..
			if(file.exists()){
				fileReader = new BufferedReader(new FileReader(file));
				String txt = null;
				responseMessage = "HTTP/1.0 200 OK\r\n" + 
				                  "Content-Type:text/html; charset=utf-8\r\n\r\n";
				out.println(responseMessage);
				while((txt = fileReader.readLine())!= null){
					out.println(txt);
				}
			}else{
				responseMessage= "HTTP/1.0 404 Not Found\r\n" +
			                     "Content-Type:text/html; charset=utf-8\r\n\r\n" +
						         "요청하신 파일을 찾을 수 없습니다..";
				out.println(responseMessage);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				if(fileReader != null) fileReader.close();
				if(out != null) out.close();
				if(in != null) in.close();
				if(socket != null) socket.close();
			} catch (IOException e) {}
		}
	}

	
	@Override
	public void run() {
		service();
	}
}

package ezen.http.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;

/**
 * Http Client(웹브라우저) 구현 
 * #1.HTTP 응용프로토콜에 따라 요청메시지(텍스트 데이터)를 웹서버로 전송하고 
 * #2.웹서버로부터 응답메시지(텍스 트데이터)를 수신하여 분석하고, 
 * #3.응답메시지의 바디내용(HTML, CSS, JavaScript 등)을 렌더링하여 출력하는 Http Client(웹브라우저)
 * 
 * @author 김기정
 */
public class MyWebBrowser {

	public static void main(String[] args) throws IOException {
		String urlString = "http://localhost/index.html";
		
		URL url = new URL(urlString);
		
//		Socket socket = new Socket();
//		요청메시지 생성 및 전송..
		
		HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
		System.out.println("Http(Web) Server Connected ...");
		
		//요청방식
		urlConnection.setRequestMethod("GET");
        //요청헤더 설정
		urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
		urlConnection.setConnectTimeout(10000);       //컨텍션타임아웃 10초

        // 응답코드
		int responseCode = urlConnection.getResponseCode();
        System.out.println("Response Code : " + responseCode);

		BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), "utf-8"));
		
		// 웹서버로부터 응답메시지 수신
//		System.out.println(br.readLine());
		String html = null;
		while((html=in.readLine()) != null){
			System.out.println(html);
		}
		in.close();
		
		// 웹 브라우저는 추가적으로 수신한 html 파일을 렌더링하여 사용자에게 출력
		// CSS 엔진, JavaScript 엔진 등 포함
	}
}

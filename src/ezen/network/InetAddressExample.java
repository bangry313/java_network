package ezen.network;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressExample {
	public static void main(String[] args) throws UnknownHostException {
		// 내컴떠의 로컬 아이피 정보 얻기
		InetAddress ia = InetAddress.getLocalHost();
		String ip = ia.getHostAddress();
		System.out.println(ip);
		
//		String domainName = "www.naver.com";
		String domainName = "www.google.com";
		// DNS 통신을 통해 도메인이름 -> IP 변환 받음 
		InetAddress serverIA = InetAddress.getByName(domainName);
		System.out.println(serverIA.getHostAddress());
		
		// 멀티서버로 구성된 경우...
		InetAddress[] ias = InetAddress.getAllByName(domainName);
		for (InetAddress inetAddress : ias) {
			System.out.println(inetAddress.getHostAddress());
			System.out.println(inetAddress);
		}
	}

}

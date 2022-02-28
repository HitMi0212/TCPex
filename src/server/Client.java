package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
	public static void main(String[] args) {
		Socket socket = null;

		try {

			socket = new Socket();
			System.out.println("연결 요청");

			socket.connect(new InetSocketAddress("localhost", 5001));
			System.out.println("연결 성공");

			// 데이터 보내기
			String dataPart = "이건 데이터다 몇 바이트일까 맞춰보자123qwerㅁㄴㅇㄻㅇㄴㄹㅈㅂㄷㄱasdfwqerffzxcvadsfwqerfdsz";

			// 보낼 데이터 바이트 길이
			String dataLeng = String.valueOf(dataPart.getBytes("UTF-8").length);

			// 보낼 데이터 길이부 생성
			String lengPart = String.format("%10s", dataLeng).replaceAll(" ", "0");

			// 길이부 + 데이터
			String data = lengPart + dataPart;

			byte[] byteArr = data.getBytes("UTF-8");

			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(byteArr);
			outputStream.flush();
			System.out.println("보낸 데이터 : " + data);
			System.out.println("보낸 데이터 사이즈 : " + byteArr.length);
			System.out.println("===================");

			// 데이터 받기
			InputStream inputStream = socket.getInputStream();

			// 길이부 확인
			byte[] getLengPart = inputStream.readNBytes(10);
			String getLeng = new String(getLengPart);

			byte[] getByte = new byte[Integer.parseInt(getLeng)];
			int readCount = inputStream.read(getByte);

			String getData = new String(getByte, 0, readCount, "UTF-8");

			System.out.println("받은 데이터 길이부 : " + getLeng);
			System.out.println("받은 데이터 : " + getData);
			System.out.println("받은 데이터 길이 : " + (getLengPart.length + getByte.length));

			outputStream.close();
			inputStream.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!socket.isClosed()) {
				try {
					socket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}
}

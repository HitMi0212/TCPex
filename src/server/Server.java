package server;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	public static void main(String[] args) {
		ServerSocket serverSocket = null;

		try {
			serverSocket = new ServerSocket();
			serverSocket.bind(new InetSocketAddress("localhost", 5001));

			while (true) {
				System.out.println("[연결 대기]");
				Socket socket = serverSocket.accept(); // 클라이언트 연결 요청 수락
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[연결 수락 : " + isa.getHostName() + "]");

				// 데이터 받기
				InputStream inputStream = socket.getInputStream();

				byte[] byteArr = null;

				byte[] lengPart = inputStream.readNBytes(10); // 길이부 10바이트만 read
				String leng = new String(lengPart); // 길이 저장할 변수

				System.out.println("받은 데이터 길이부 : " + leng);

				// 길이부에서 얻은 길이 만큼 데이터 받아오기
				byteArr = new byte[Integer.parseInt(leng)];

				int readCount = inputStream.read(byteArr);

				String dataPart = new String(byteArr, 0, readCount, "UTF-8");
				System.out.println("받은 데이터 : " + dataPart);
				System.out.println("받은 데이터 길이 : " + (lengPart.length + byteArr.length));
				System.out.println("==================");

				// 받은 데이터 보내기
				OutputStream outputStream = socket.getOutputStream();

				// 보내줄 때는 길이부까지 합쳐서 보내주기
				String data = leng + dataPart;

				outputStream.write(data.getBytes("UTF-8"));
				outputStream.flush();

				System.out.println("보낸 데이터 : " + data);
				System.out.println("보낸 데이터 길이 : " + (lengPart.length + byteArr.length));
				System.out.println("==================\n");

				inputStream.close();
				outputStream.close();

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (!serverSocket.isClosed()) {
				try {
					serverSocket.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}

	}

}

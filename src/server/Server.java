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
				System.out.println("[���� ���]");
				Socket socket = serverSocket.accept(); // Ŭ���̾�Ʈ ���� ��û ����
				InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
				System.out.println("[���� ���� : " + isa.getHostName() + "]");

				// ������ �ޱ�
				InputStream inputStream = socket.getInputStream();

				byte[] byteArr = null;

				byte[] lengPart = inputStream.readNBytes(10); // ���̺� 10����Ʈ�� read
				String leng = new String(lengPart); // ���� ������ ����

				System.out.println("���� ������ ���̺� : " + leng);

				// ���̺ο��� ���� ���� ��ŭ ������ �޾ƿ���
				byteArr = new byte[Integer.parseInt(leng)];

				int readCount = inputStream.read(byteArr);

				String dataPart = new String(byteArr, 0, readCount, "UTF-8");
				System.out.println("���� ������ : " + dataPart);
				System.out.println("���� ������ ���� : " + (lengPart.length + byteArr.length));
				System.out.println("==================");

				// ���� ������ ������
				OutputStream outputStream = socket.getOutputStream();

				// ������ ���� ���̺α��� ���ļ� �����ֱ�
				String data = leng + dataPart;

				outputStream.write(data.getBytes("UTF-8"));
				outputStream.flush();

				System.out.println("���� ������ : " + data);
				System.out.println("���� ������ ���� : " + (lengPart.length + byteArr.length));
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

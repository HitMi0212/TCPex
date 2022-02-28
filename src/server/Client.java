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
			System.out.println("���� ��û");

			socket.connect(new InetSocketAddress("localhost", 5001));
			System.out.println("���� ����");

			// ������ ������
			String dataPart = "�̰� �����ʹ� �� ����Ʈ�ϱ� ���纸��123qwer����������������������asdfwqerffzxcvadsfwqerfdsz";

			// ���� ������ ����Ʈ ����
			String dataLeng = String.valueOf(dataPart.getBytes("UTF-8").length);

			// ���� ������ ���̺� ����
			String lengPart = String.format("%10s", dataLeng).replaceAll(" ", "0");

			// ���̺� + ������
			String data = lengPart + dataPart;

			byte[] byteArr = data.getBytes("UTF-8");

			OutputStream outputStream = socket.getOutputStream();
			outputStream.write(byteArr);
			outputStream.flush();
			System.out.println("���� ������ : " + data);
			System.out.println("���� ������ ������ : " + byteArr.length);
			System.out.println("===================");

			// ������ �ޱ�
			InputStream inputStream = socket.getInputStream();

			// ���̺� Ȯ��
			byte[] getLengPart = inputStream.readNBytes(10);
			String getLeng = new String(getLengPart);

			byte[] getByte = new byte[Integer.parseInt(getLeng)];
			int readCount = inputStream.read(getByte);

			String getData = new String(getByte, 0, readCount, "UTF-8");

			System.out.println("���� ������ ���̺� : " + getLeng);
			System.out.println("���� ������ : " + getData);
			System.out.println("���� ������ ���� : " + (getLengPart.length + getByte.length));

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

package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerThread st;
	
	public Server() {
		try(ServerSocket server = new ServerSocket(8888)) {
			
			while(true){
				System.out.println("서버실행");
				client = server.accept();
				ois = new ObjectInputStream(client.getInputStream());
				
				oos = new ObjectOutputStream(client.getOutputStream());
				st = new ServerThread(ois, oos);
				Thread thread = new Thread(st);
				thread.start();
				System.out.println("클라이언트 접속");
			}
		} catch (IOException e) {
			System.out.println("IOE..");
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}

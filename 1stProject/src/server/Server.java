package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
	
	private ServerSocket server;
	private Socket client;
	private ObjectInputStream ois;
	private ObjectOutputStream oos;
	private ServerThread st;
	
	public Server() {

		try {
			server = new ServerSocket(8888);
			while(true){
				client = server.accept();
				ois = new ObjectInputStream(client.getInputStream());
				oos = new ObjectOutputStream(client.getOutputStream());
				st = new ServerThread(ois, oos);
				Thread thread = new Thread(st);
				thread.start();
			}
		} catch (IOException e) {
			System.out.println("IOE");
		}
	}
	
	public static void main(String[] args) {
		new Server();
	}
	
}

package app;

import java.net.ServerSocket;
import java.net.Socket;

import lib.User;
import util.Database;

public class QuizServer {

	//private static Socket socket;
	private static ServerSocket serverSocket;
	public static void main(String[] args) {
		try {
			int port = 97;
			System.out.println("Server waiting for clients on Port "+port);
			serverSocket = new ServerSocket(port);
			while(true){
				Socket socket = serverSocket.accept();
				//teacher
				/*User user = Database.authenticate("shalu1", "msit123");
				TeacherUIHandler tuh = new TeacherUIHandler(socket, user);
				tuh.start();*/
				//student
				User user = Database.authenticate("adi1", "msit123");
				StudentUIHandler suh = new StudentUIHandler(socket, user);
				suh.start();
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				//socket.close();
				serverSocket.close();
			} catch (Exception e) {
			}
		}
	}
	
	
}



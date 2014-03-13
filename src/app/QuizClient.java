package app;
import java.net.*;
import java.io.*;


public class QuizClient {
	public static void main(String[] args) throws ClassNotFoundException,
			IOException {
		String serverName = "localhost";
		int port = 97;
		Socket client = null;
		try {
			
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			client = new Socket(serverName, port);
			while(true){
				
				DataInputStream in = new DataInputStream(client.getInputStream());	 //to read from the client
				String msg_from_server=in.readUTF();
				
				String s = "";
				
				if(msg_from_server.charAt(0)=='#'){
					System.out.println(msg_from_server.substring(1));
					 continue;
				}
				else if(msg_from_server.equals("EXIT")){
					System.out.println("Thank You, visit again.");
					client.close();
					break;
				}
				else{
					System.out.println(msg_from_server);
					s = br.readLine();
				}
				DataOutputStream out=new DataOutputStream(client.getOutputStream());
				out.writeUTF(s);
			
			}
			
		} catch (IOException e) {

			e.printStackTrace();
		} finally {
			client.close();
		}
	}
}
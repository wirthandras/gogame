package gogame;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;


/**
 * Example Code only.
 * We will integrate a TCP server into GoGame, and can be connect via telnet command line application.
 * 
 * @author andras wirth
 *
 */
public class Server {

	private static int port = 9800;
	
	public static void main(String[] args) {

		try (ServerSocket serverSocket = new ServerSocket(port)) {

			System.out.println("Chat Server is listening on port " + port);

			while (true) {
				Socket socket = serverSocket.accept();
				System.out.println("New user connected");
				
				DataOutputStream dataOutputStream = new DataOutputStream(socket.getOutputStream());
				BufferedReader di = new BufferedReader(new InputStreamReader(socket.getInputStream()));
				
		        dataOutputStream.writeUTF("Hello from the other side!\n\r");
		        System.out.println(di.readLine());

			}

		} catch (IOException ex) {
			System.out.println("Error in the server: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

}

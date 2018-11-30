/**
 * @author Tania
 * @date 29 nov. 2018
 * @version 1.0
 * @description Server that allows connections from drivers and agents. Drivers enter
 * info about accidents and agents can submit queries about every existing accident.
 * 
 */


package pevalsockets;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;


/**
 * Class Server
 */
public class Server {
	/**
	 * @variable_name server
	 * @type ServerSocket
	 */
	static ServerSocket server;
	/**
	 * @variable_name port
	 * @type int
	 */
	static final int port = 5000;
	/**
	 * @variable_name connections
	 * @type int
	 */
	static int connections = 0;
	/**
	 * @variable_name polizy
	 * @type int
	 */
	static int polizy = 0;
	/**
	 * @variable_name max
	 * @type int
	 */
	static int max = 10;
	/**
	 * @variable_name sockets
	 * @type Socket[]
	 */
	static Socket[] sockets = new Socket[max];
	/**
	 * @variable_name accidents
	 * @type ArrayList<Accident>
	 */
	static ArrayList<Accident> accidents;
	
	/**
	 * Method that runs the server
	 * @name main
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {
		Server srv = new Server();
		srv.initServer();		
	} 
	
	/**
	 * Method that initializes the server
	 * @name initServer 
	 */
	public void initServer() {
		try {
			server = new ServerSocket(port);
			System.out.println("Servidor iniciado...");
			accidents = new ArrayList<>();

			while (connections < max) {
				Socket s = new Socket();
				try {
					s = server.accept();
				} catch (SocketException ns) {	
					System.err.println("Error. Ha fallado la conexión al servidor");
					System.err.flush();
					//e.printStackTrace();
					break; 
				}
				
				sockets[connections] = s;
				connections++;
				System.out.println("Nuevo Usuario conectado...");
				ServerThread thread = new ServerThread(s);
				thread.start();
			}
			
			if (!server.isClosed()) {
				try {				
					server.close();
				} catch (IOException e1) {
					System.err.println("Error de E/S");
					System.err.flush();
					//e1.printStackTrace();
				}
			}
			
			System.out.println("Servidor finalizado...");
		} catch (IOException e) {
			System.err.println("Error de E/S");
			System.err.flush();
			//e.printStackTrace();			
		}		
	}
	
	/**
	 * Method that generates a new polizy number for a client
	 * @name assignedPolizy
	 * @return 
	 */
	public static synchronized int assignedPolizy()  {	
		polizy++;
		return polizy;
	}
}

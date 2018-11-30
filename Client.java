/**
 * @author Tania
 * @date 29 nov. 2018
 * @version 1.0
 * @description User that identifies himself/herself and if the user types 'CORREDOR'
 * can see a list of every registered accident in the server and if the user types "CLIENTE", can type data
 * related with an accident and send it to server .
 * 
 */


package pevalsockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;
import java.util.Scanner;



/**
 * Class Client
 */
public class Client {
	/**
	 * @variable_name port
	 * @type int
	 */
	final int port = 5000;
	/**
	 * @variable_name driver
	 * @type Socket
	 */
	Socket driver;
	/**
	 * @variable_name msg
	 * @type String
	 */
	String msg;
	/**
	 * @variable_name output
	 * @type DataOutputStream
	 */
	DataOutputStream output;
	/**
	 * @variable_name input
	 * @type DataInputStream
	 */
	DataInputStream input;
	/**
	 * @variable_name objectinput
	 * @type ObjectInputStream
	 */
	/**
	 * @variable_name objectinput
	 * @type ObjectInputStream
	 */
	ObjectInputStream objectinput;
	/**
	 * @variable_name dataCount
	 * @type int
	 */
	int dataCount = 4;
	
	/**
	 * Method that runs the program
	 * 
	 * @name main
	 * @param args
	 */
	public static void main(String[] args) {
		Client c = new Client();
		c.initClient();
	}
	
	/**
	 * Method that initializes client
	 * 
	 * @name initClient
	 */
	public void initClient() {
		try {
			driver = new Socket("localhost", port);
			output = new DataOutputStream(driver.getOutputStream());
			input = new DataInputStream(driver.getInputStream());
			
			do {
				System.out.println("Escriba CLIENTE o CORREDOR para identificarse...");
				Scanner kb = new Scanner(System.in);
				msg = kb.nextLine();

				if (!msg.equals("CLIENTE") && !msg.equals("CORREDOR")) {
					System.err.println("Error. Debe introducir CLIENTE o SERVIDOR respetando las mayúsculas\n");
					System.err.flush();
				}
			} while (!msg.equals("CLIENTE") && !msg.equals("CORREDOR"));

			output.writeUTF(msg);

			if (msg.equals("CLIENTE")) {
				Scanner kb = new Scanner(System.in);
				String line;
				String received = input.readUTF();
				System.out.println(received);

				while (!received.equals("Fin del parte de seguros.")) {
					try {
						received = input.readUTF();
						System.out.println(received);
						
						if (received.equals("Datos recibidos correctamente.")) {
							received = input.readUTF();
							System.out.println(received);
							line = kb.nextLine();
							output.writeUTF(line);
							received = input.readUTF();
							System.out.println(received);
							received = input.readUTF();
							System.out.println(received);
						}						

						if (!received.equals("Fin del parte de seguros.")) {
							line = kb.nextLine();
							output.writeUTF(line);
						}

					} catch (Exception e) {
						System.err.println("Error. Ha ocurrido un error en la lectura");
						System.err.flush();
						//e.printStackTrace();
					}
				}
			} else {
				objectinput = new ObjectInputStream(driver.getInputStream());
				Accident accident = null;
				int i = 0;

				do {
					try {
						accident = (Accident) objectinput.readObject();

						if (accident != null) {
							if (i == 0) {
								accident.printHead();
							}

							accident.printData();
							i++;
						} else if (accident == null & i == 0) {
							System.out.println("No existen accidentes ");
						}
					} catch (ClassNotFoundException e) {
						System.err.println("Error de Lectura");
						System.err.flush();
						//e.printStackTrace();
					}
				} while (accident != null);

			}
			try {
				output.close();
				input.close();	
				
				if(msg.equals("CORREDOR")) {
					objectinput.close();
				}
				
				driver.close();
			} catch (Exception e) {
				System.err.println("Error al terminar la conexión");
				System.err.flush();
				//e.printStackTrace();
			}
		} catch (IOException e) {
			System.err.println("Error de E/S");
			System.err.flush();
			//e.printStackTrace();
		}
	}
}

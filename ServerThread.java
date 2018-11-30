/**
 * @author Tania
 * @date 29 nov. 2018
 * @version 1.0
 * @description Thread that allows communication between server and client 
 * 
 */


package pevalsockets;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Class ServerThread
 */
public class ServerThread extends Thread {
	/**
	 * @variable_name socket
	 * @type Socket
	 */
	Socket socket;
	/**
	 * @variable_name polizy
	 * @type int
	 */
	int polizy;
	/**
	 * @variable_name id
	 * @type String
	 */
	String id;
	/**
	 * @variable_name input
	 * @type DataInputStream
	 */
	DataInputStream input;
	/**
	 * @variable_name objectoutput
	 * @type ObjectOutputStream
	 */
	ObjectOutputStream objectoutput;
	/**
	 * @variable_name output
	 * @type DataOutputStream
	 */
	DataOutputStream output;
	/**
	 * @variable_name data
	 * @type String[]
	 */
	String[] data = { "Introduzca DNI: ", "Introduzca Nombre: ", "Introduzca Matrícula: ",
			"Introduzca Nº Póliza de Seguro: " };
	/**
	 * @variable_name clientInputs
	 * @type String[]
	 */
	String[] clientInputs = new String[data.length];

	/**
	 * Class ServerThread Constructor
	 * 
	 * @param s
	 */
	public ServerThread(Socket s) {
		socket = s;
	}

	/**
	 * Method that runs the thread
	 * 
	 * @name run
	 * @overriden @see java.lang.Thread#run()
	 */
	public void run() {
		String inputString = "";
		String outputString = "";
		String type = "";

		try {
			input = new DataInputStream(socket.getInputStream());
			type = input.readUTF();
			System.out.println("El usuario es de tipo: " + type);
		} catch (IOException e1) {
			System.err.println("Error de E/S");
			System.err.flush();
			// e1.printStackTrace();
		}

		if (type.equals("CLIENTE")) {
			try {
				output = new DataOutputStream(socket.getOutputStream());
				output.writeUTF("Bienvenido al servidor conductor");
			} catch (IOException e1) {
				System.err.println("Error de E/S");
				System.err.flush();
				// e1.printStackTrace();
			}

			Accident accident;
			String description = "";

			for (int i = 0; i < data.length; i++) {
				try {
					do {
						outputString = data[i];
						output.writeUTF(outputString);
						inputString = input.readUTF();
						System.out.println(outputString.replaceAll("Introduzca ", "") + inputString);

						if (inputString.equals("")) {
							System.err.println("Error. El mensaje no puede estar vacío.");
							System.err.flush();
						}

						if (data[i].contains("Póliza")) {

							if (!tryParseInt(inputString)) {
								inputString = "";
								System.err.println("Error. El número de póliza no es correcto.");
								System.err.flush();
							}
						}
					} while (inputString.equals(""));

					clientInputs[i] = inputString;

				} catch (Exception e) {
					System.err.println("Error E/S");
					System.err.flush();
					// e.printStackTrace();
				}
			}

			try {
				output.writeUTF("Datos recibidos correctamente.");
				System.out.println("Datos recibidos correctamente.");
				output.writeUTF("Introduce la descripción del siniestro: ");
				System.out.println("descripción del siniestro: ");
				description = input.readUTF();
				System.out.println(description);
				polizy = Server.assignedPolizy();
				output.writeUTF("Se le ha asignado el nº póliza: " + polizy);
				System.out.println("Nº póliza: " + polizy);
				output.writeUTF("Fin del parte de seguros.");
				System.out.println("Fin del parte de seguros.");
			} catch (IOException e) {
				System.err.println("Error de E/S");
				System.err.flush();
				// e.printStackTrace();
			}

			accident = new Accident(clientInputs[0], clientInputs[1], clientInputs[2],
					Integer.parseInt(clientInputs[3]), Server.polizy, description);
			Server.accidents.add(accident);
		} else {
			try {
				objectoutput = new ObjectOutputStream(socket.getOutputStream());
				if (Server.accidents.size() != 0) {
					for (int i = 0; i < Server.accidents.size(); i++) {
						objectoutput.writeObject(Server.accidents.get(i));

						if (i == Server.accidents.size() - 1) {
							objectoutput.writeObject(null);
						}
					}
				} else {
					objectoutput.writeObject(null);
				}

				System.out.println("El CORREDOR ha hecho una consulta");
			} catch (IOException e) {
				System.err.println("Error de E/S");
				System.err.flush();
				// e.printStackTrace();
			}
		}
		try {
			input.close();
			
			if(type.equals("CLIENTE")) {				
				output.close();
			} else {
				objectoutput.close();				
			}
		} catch (IOException e) {
			System.err.println("Error de E/S");
			System.err.flush();
			//e.printStackTrace();
		} catch (Exception e) {
			System.err.println("Error al terminar la conexión");
			System.err.flush();
			//e.printStackTrace();
		}

		Server.connections--;
	}

	/**
	 * Boolean that returns true if @param is integer and false if not
	 * 
	 * @name tryParseInt
	 * @param value
	 * @return
	 */
	boolean tryParseInt(String value) {
		try {
			Integer.parseInt(value);
			return true;
		} catch (NumberFormatException e) {
			// e.printStackTrace();
			return false;
		}
	}
}

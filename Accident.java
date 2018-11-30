/**
 * @author Tania
 * @date 29 nov. 2018
 * @version 1.0
 * @description object that save data related to an accident
 * 
 */


package pevalsockets;

import java.io.Serializable;


/**
 * Class Accident
 */
public class Accident implements Serializable{	
	/**
	 * @variable_name serialVersionUID
	 * @type long
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * @variable_name dni
	 * @type String
	 */
	String dni;
	/**
	 * @variable_name name
	 * @type String
	 */
	String name;
	/**
	 * @variable_name numberPlate
	 * @type String
	 */
	String numberPlate;
	/**
	 * @variable_name polizy
	 * @type int
	 */
	int polizy;
	/**
	 * @variable_name accidentNumber
	 * @type int
	 */
	int accidentNumber;
	/**
	 * @variable_name description
	 * @type String
	 */
	String description;

	/**
	 * Class Accident Constructor
	 * @param dni
	 * @param name
	 * @param numberPlate
	 * @param polizy
	 * @param accidentNumber
	 * @param description
	 */
	public Accident(String dni, String name, String numberPlate, int polizy, int accidentNumber,
			String description) {
		this.dni = dni;
		this.name = name;
		this.numberPlate = numberPlate;
		this.polizy = polizy;
		this.accidentNumber = accidentNumber;
		this.description = description;
	}
	
	/**
	 * Class Accident Constructor
	 */
	public Accident() {
	}
	
	/**
	 * Method that prints the header of the query
	 * @name printHead 
	 */
	public void printHead() {
		System.out.printf("%20s", "LISTA DE SINIESTROS: \n");
		System.out.printf("%10s%20s%10s%15s%15s%30s", "DNI", "NOMBRE", "MATRÍCULA", "Nº PÓLIZA",
				"Nº SINIESTRO", "DESCRIPCIÓN\n");
	}
	
	/**
	 * Method that prints accident info
	 * @name printData 
	 */
	public void printData() {
			System.out.printf("%10s%20s%10s%15d%15d%30s", dni, name, numberPlate,  polizy,
					accidentNumber, description + "\n"); 
	}

	/**
	 * Getter
	 * @name getDni
	 * @return 
	 */
	public String getDni() {
		return dni;
	}
	
	/**
	 * Setter
	 * @name setDni
	 * @param dni 
	 */
	public void setDni(String dni) {
		this.dni = dni;
	}
	
	/**
	 * Getter
	 * @name getName
	 * @return 
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Setter
	 * @name setName
	 * @param name 
	 */
	public void setName(String name) {
		this.name = name;
	}
	
	/**
	 * Getter
	 * @name getNumberPlate
	 * @return 
	 */
	public String getNumberPlate() {
		return numberPlate;
	}
	
	/**
	 * Setter
	 * @name setNumberPlate
	 * @param numberPlate 
	 */
	public void setNumberPlate(String numberPlate) {
		this.numberPlate = numberPlate;
	}
	
	/**
	 * Getter
	 * @name getPolizy
	 * @return 
	 */
	public int getPolizy() {
		return polizy;
	}
	
	/**
	 * Setter
	 * @name setPolizy
	 * @param polizy 
	 */
	public void setPolizy(int polizy) {
		this.polizy = polizy;
	}
	
	/**
	 * Getter
	 * @name getAccidentNumber
	 * @return 
	 */
	public int getAccidentNumber() {
		return accidentNumber;
	}	
	
	/**
	 * Setter
	 * @name setAccidentNumber
	 * @param accidentNumber 
	 */
	public void setAccidentNumber(int accidentNumber) {
		this.accidentNumber = accidentNumber;
	}
	
	/**
	 * Getter
	 * @name getDescription
	 * @return 
	 */
	public String getDescription() {
		return description;
	}
	
	/**
	 * Setter
	 * @name setDescription
	 * @param description 
	 */
	public void setDescription(String description) {
		this.description = description;
	}	
}

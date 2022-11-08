package com.nttdata.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * App main con conexion a base de datos
 * 
 * @author Victor Carrasco
 *
 */
public class App {
	/** Logger */
	private static final Logger LOG = LoggerFactory.getLogger(App.class);
	
	/**
	 * Main
	 * @param args
	 */
	public static void main(String[] args) {
		// Sentencia
		Statement appStatement;
		// Resultados
		ResultSet appResultSet;
		
		// Se establece la conexion con la base de datos
		try (Connection appConnection = connectDDBB()) {

			LOG.debug("Conexion exitosa");
			appStatement = appConnection.createStatement();
			
			// Se consultan los jugadores del equipo con id 1
			appResultSet = appStatement
					.executeQuery("SELECT * FROM NTTDATA_ORACLE_SOCCER_PLAYER WHERE ID_SOCCER_TEAM = 1");

			System.out.println("ID_SOCCER_PLAYER\tNAME");
			
			// Se hace la lectura de los datos
			while (appResultSet.next()) {
				System.out.println(
						appResultSet.getString("ID_SOCCER_PLAYER") + "\t\t\t" + appResultSet.getString("NAME"));
			}
		} catch (SQLException e) {
			//Texto de error
			StringBuilder builder = new StringBuilder();
			builder.append("Ha sucdedido un error inesperado --> ");
			builder.append(e.toString());
			
			LOG.error(builder.toString());
		}
	}
	
	/**
	 * Establece la conexion con la base de datos
	 * 
	 * @return
	 * @throws SQLException
	 */
	private static Connection connectDDBB() throws SQLException {
		LOG.debug("Conectandose con la Base de datos");
		return DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XE", "sys as sysdba", "1234");
	}
}

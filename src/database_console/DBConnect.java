package database_console;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DBConnect {

	public DBConnect() {};
	
	public static void connectDB() {
		
		try (Connection connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/TP-DDS", "postgres", "postgres")) {
			System.out.println("Connected to PostgreSQL database!");
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM public.cliente");
			while(resultSet.next()) {
				System.out.println("NRO DE LEGAJO: "+resultSet.getString("nrolegajoc")+"    NOMBRE: "+resultSet.getString("nombre")+"    DNI: "+resultSet.getString("dni"));
			}
			} catch (SQLException e) {
				System.out.println("Connection failure.");
				e.printStackTrace();
			}
	}

}

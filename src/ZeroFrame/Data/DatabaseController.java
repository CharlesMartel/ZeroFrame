/**
 * 
 */
package ZeroFrame.Data;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author Hammer
 * 
 */
public class DatabaseController {

	private static final String DRIVERNAME = "org.apache.derby.jdbc.EmbeddedDriver";
	private static final String DATABASEURL = "jdbc:derby:database;create=true";

	private static Connection databaseConnection = null;
	private static boolean initialized = false;

	private DatabaseController() {/*
								 * this is a singleton class, we do not create
								 * this guy
								 */
	}

	public static void initialize() {
		try {
			Class.forName(DRIVERNAME);
			databaseConnection = DriverManager.getConnection(DATABASEURL);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (databaseConnection != null) {
			initialized = true;
		} else {
			initialized = false;
		}
	}

	public static ResultSet executeQuery(String query) {
		if (!initialized) {
			initialize();
		}
		try {
			PreparedStatement statement = databaseConnection.prepareStatement(query);
			ResultSet result = statement.executeQuery();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			ResultSet result = null;
			return result;
		}
	}

	public static boolean executeGeneric(String query) {
		if (!initialized) {
			initialize();
		}
		try {
			System.out.println(query);
			PreparedStatement statement = databaseConnection.prepareStatement(query);
			boolean result = statement.execute();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			boolean result = false;
			return result;
		}
	}

	public static int executeInsert(String query) {
		if (!initialized) {
			initialize();
		}
		try {
			System.out.println(query);
			PreparedStatement statement = databaseConnection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
			statement.executeUpdate();
			ResultSet keys = statement.getGeneratedKeys();
			int fieldKey = 0;
			while (keys.next()) {
				fieldKey = keys.getInt(1);
			}

			return fieldKey;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return -1;
		}
	}

	public static int executeUpdate(String query) {
		if (!initialized) {
			initialize();
		}
		try {
			PreparedStatement statement = databaseConnection.prepareStatement(query);
			int result = statement.executeUpdate();
			return result;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			int result = -1;
			return result;
		}
	}

	public static DatabaseMetaData getMetaData() {
		if (!initialized) {
			initialize();
		}
		try {
			return databaseConnection.getMetaData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

}

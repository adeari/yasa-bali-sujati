package apps.yasabalisujati.dataupdated;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataUpdate {
	public DataUpdate() {
		Connection connection = null;
		Statement statement = null;
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/dbybs", "ade", "1234");
			statement = connection.createStatement();
			String sql = "ALTER TABLE joborder DROP COLUMN sealno;";
			try {
				statement.executeUpdate(sql);
			} catch (Exception exception) {
				
			}
			try {
				sql = "ALTER TABLE joborder ADD COLUMN consigmentfile bytea";
				statement.executeUpdate(sql);
			} catch (Exception exception) {
				
			}
			try {
				sql = "ALTER TABLE joborder ADD COLUMN partyfile bytea";
				statement.executeUpdate(sql);
			} catch (Exception exception) {
				
			}
			try {
				sql = "CREATE TABLE certificatenewcolumn ( id bigserial NOT NULL, joborder_id bigint, column_name character varying,  description character varying, CONSTRAINT pkefe435435 PRIMARY KEY (id)) WITH (   OIDS=FALSE )";
				statement.executeUpdate(sql);
			} catch (Exception exception) {
				
			}
			String foldermake = "D:/yasabalisujati/files";
			File file = new File(foldermake);
			if (!file.exists()) {
				file.mkdir();
			}
			
		} catch (Exception exception) {
			exception.printStackTrace();
		}

		if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		
		if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	}
}

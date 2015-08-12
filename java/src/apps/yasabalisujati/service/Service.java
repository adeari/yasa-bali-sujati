package apps.yasabalisujati.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.Session;

import apps.yasabalisujati.database.DatabaseHelper;

public class Service {
	public Session getConnectionDB(Session session) {
		if (session == null) {
			session = DatabaseHelper.getConnection();
			return session;
		} else if (!session.isConnected() || !session.isOpen()) {
			session = DatabaseHelper.getConnection();
			return session;
		}
		return session;
	}
	
	public String getPassword(String pass) {
		return BCrypt.hashpw(pass, BCrypt.gensalt(8));
	}
	
	public boolean passwordMatch(String pass, String pass2) {
		return BCrypt.checkpw(pass, pass2);
	}
	
	public String getProperties(String key) {
		InputStream input = null;
		try {
			ClassLoader classLoader = getClass().getClassLoader();
			input = classLoader.getResourceAsStream("data.properties");
			Properties properties = new Properties();
			properties.load(input);
			return properties.getProperty(key);
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException ex) {

				}
			}
		}
		return key;
	}
	
	public String convertStringFromDate(String format, Date date, SimpleDateFormat simpleDateFormat) {
		if (date != null) {
			simpleDateFormat.applyPattern(format);
			return simpleDateFormat.format(date);
		}
		return "";
	}
	public Date convertStringToDate(String format, String date, SimpleDateFormat simpleDateFormat) {
		if (date != null) {
			simpleDateFormat.applyPattern(format);
			try {
				return simpleDateFormat.parse(date);
			} catch (ParseException e) {
			}
		}
		return null;
	}
}

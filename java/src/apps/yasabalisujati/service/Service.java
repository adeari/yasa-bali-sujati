package apps.yasabalisujati.service;

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
}

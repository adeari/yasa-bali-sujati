package apps.yasabalisujati.start;

import org.hibernate.Session;

import apps.yasabalisujati.form.ConnectionForm;
import apps.yasabalisujati.form.Login;
import apps.yasabalisujati.service.Service;

public class StartClass {
	public static void main(String[] args) {
		Service service = new Service();
		Session session = service.getConnectionDB(null);
		if (session == null) {
			new ConnectionForm(session, service);
		} else {
			new Login(session, service);
		}
	}
}
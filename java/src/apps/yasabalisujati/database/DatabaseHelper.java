package apps.yasabalisujati.database;

import java.io.File;

import org.hibernate.Session;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class DatabaseHelper {
	public static Session getConnection(String ipAddress) {
		try {
			String urll = "jdbc:postgresql://"+ipAddress+":5432/dbybs";
			Configuration configuration = new Configuration();
			configuration.setProperty("hibernate.connection.username", "ade");
			configuration.setProperty("hibernate.connection.password", "1234");
			configuration.setProperty("hibernate.connection.url", urll);
			
			File f = new File("D:\\yasabalisujati\\appsconfig.xml");
			configuration.configure(f);
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();                
			return  configuration.buildSessionFactory(serviceRegistry).openSession();
		} catch (Exception ex) {
			return null;
		}
	}
}

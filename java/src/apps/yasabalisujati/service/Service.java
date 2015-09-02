package apps.yasabalisujati.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.database.DatabaseHelper;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Filling;
import apps.yasabalisujati.database.entity.Joborder;
import apps.yasabalisujati.database.entity.JoborderPegawai;
import apps.yasabalisujati.database.entity.JoborderValidasi;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.database.entity.ValidasiRules;

public class Service {
	
	private String _ipAddress = "localhost";
	
	public void setIpAddress(String ipAddress) {
		_ipAddress = ipAddress;
	}
	
	public String getIpAddress() {
		return _ipAddress;
	}
	
	public Session getConnectionDB(Session session) {
		if (session == null) {
			session = DatabaseHelper.getConnection(_ipAddress);
			return session;
		} else if (!session.isConnected() || !session.isOpen()) {
			session = DatabaseHelper.getConnection(_ipAddress);
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
			input = getClass().getClassLoader().getResourceAsStream("data.properties");
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
	
	public void setIsDeletedPegawai(Session session, Pegawai pegawai) {
		long pegawaiCount = 0;
		session.clear();
		Criteria criteria = session.createCriteria(JoborderPegawai.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("pegawai", pegawai));
		pegawaiCount += (long) criteria.uniqueResult();
		
		if (pegawaiCount > 0 && pegawai.isDeleted()) {
			pegawai.setDeleted(false);
			session.update(pegawai);
		} else if (pegawaiCount <= 0 && !pegawai.isDeleted()) {
			pegawai.setDeleted(true);
			session.update(pegawai);
		}
	}
	
	public void setIsDeletedUser(Session session, User user) {
		long userCount = 0;
		session.clear();
		Criteria criteria = session.createCriteria(Joborder.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.or(Restrictions.eq("createBy", user),
				Restrictions.eq("updatedBy", user)
				));
		userCount += (long) criteria.uniqueResult();
		
		if (userCount > 0 && user.isDeleted()) {
			user.setDeleted(false);
			session.update(user);
		} else if (userCount <= 0 && !user.isDeleted()) {
			user.setDeleted(true);
			session.update(user);
		}
	}
	
	public void setIsDeletedFilling(Session session, Filling filling) {
		long fillingCount = 0;
		session.clear();
		Criteria criteria = session.createCriteria(Joborder.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("filling", filling));
		fillingCount += (long) criteria.uniqueResult();
		
		if (fillingCount > 0 && filling.isDeleted()) {
			filling.setDeleted(false);
			session.update(filling);
		} else if (fillingCount <= 0 && !filling.isDeleted()) {
			filling.setDeleted(true);
			session.update(filling);
		}
	}
	
	public void setIsDeletedCustomer(Session session, Customer customer) {
		long customerCount = 0;
		session.clear();
		Criteria criteria = session.createCriteria(Joborder.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.or(Restrictions.eq("customer", customer),
				Restrictions.eq("exportir", customer)
				));
		customerCount += (long) criteria.uniqueResult();
		
		if (customerCount > 0 && customer.isDeleted()) {
			customer.setDeleted(false);
			session.update(customer);
		} else if (customerCount <= 0 && !customer.isDeleted()) {
			customer.setDeleted(true);
			session.update(customer);
		}
	}
	
	public void setIsDeletedValidasirules(Session session, ValidasiRules validasiRules) {
		long validasiCount = 0;
		session.clear();
		Criteria criteria = session.createCriteria(JoborderValidasi.class).setProjection(Projections.rowCount());
		criteria.add(Restrictions.eq("validasiRules", validasiRules));
		validasiCount += (long) criteria.uniqueResult();
		
		if (validasiCount > 0 && validasiRules.isDeleted()) {
			validasiRules.setDeleted(false);
			session.update(validasiRules);
		} else if (validasiCount <= 0 && !validasiRules.isDeleted()) {
			validasiRules.setDeleted(true);
			session.update(validasiRules);
		}
	}
}

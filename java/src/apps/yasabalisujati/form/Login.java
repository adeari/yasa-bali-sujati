package apps.yasabalisujati.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Passwordbox;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.service.Service;

public class Login extends JFrame {
	private static final long serialVersionUID = 9103684027426550046L;
	private int height = 180;
	private int width = 320;
	private JFrame _frame;
	private Session _session;
	private Service _service;

	private MenuDesktop menuDesktop;
	
	private Textbox usernameTextbox;
	private Passwordbox passwordbox;
	private ComboBox divisiComboBox;

	public Login(Session session, Service service) {
		super("L O G I N");
		_frame = this;
		_frame.setPreferredSize(new Dimension(width, height));
		_frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		_frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/key.png")).getImage());
		_service = service;
		_session = session;

		_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		menuDesktop = new MenuDesktop(this, _session, _service);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		formPanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 10, height));
		formPanel.setBackground(Color.WHITE);
		formPanel.setOpaque(true);

		Label usernameLabel = new Label("User name");
		usernameLabel.setPreferredSize(new Dimension(80, 26));
		formPanel.add(usernameLabel);
		usernameTextbox = new Textbox("");
		usernameTextbox.setPreferredSize(new Dimension(200, 26));
		usernameTextbox.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					passwordbox.requestFocus();
				}
			}

		});
		formPanel.add(usernameTextbox);

		Label passwordLabel = new Label("Password");
		passwordLabel.setPreferredSize(new Dimension(80, 26));
		formPanel.add(passwordLabel);
		passwordbox = new Passwordbox();
		passwordbox.setPreferredSize(new Dimension(200, 26));
		passwordbox.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					login();
				}
			}

		});
		formPanel.add(passwordbox);

		Label divisiLabel = new Label("Divisi");
		divisiLabel.setPreferredSize(new Dimension(80, 26));
		formPanel.add(divisiLabel);
		divisiComboBox = new ComboBox(new String[] { "a" });
		divisiComboBox.setPreferredSize(new Dimension(200, 26));
		formPanel.add(divisiComboBox);

		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				formPanel.getPreferredSize().getWidth()).intValue() - 5, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));

		Button loginButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/key.png")), "L O G I N");
		loginButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				login();
			}
		});
		buttonSavePanel.add(loginButton);

		Button tutupButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/cancel.png")), "T U T U P");
		tutupButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});
		buttonSavePanel.add(tutupButton);

		formPanel.add(buttonSavePanel);

		refreshDivisiUser();

		_frame.add(formPanel);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		
		
		if (_service.getProperties("directlogin").equals("1")) {
			usernameTextbox.setText("ade");
			passwordbox.setText("1234");
			divisiComboBox.setSelectedItem("Admin");
			login();
		}
	}

	public void refreshDivisiUser() {
		_session = _service.getConnectionDB(_session);
		_session.clear();
		divisiComboBox.removeAllItems();

		Criteria criteria = _session.createCriteria(User.class).setProjection(
				Projections.groupProperty("divisi"));
		List<String> divisis = criteria.list();
		for (String divisi : divisis) {
			divisiComboBox.addItem(divisi);
		}
	}

	public void login() {
		if (usernameTextbox.getText().isEmpty()) {
			usernameTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Username", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		if (passwordbox.getPassword().length <= 0) {
			passwordbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Password", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		String password = new String(passwordbox.getPassword());
		String encrryptedPassword = _service.getPassword(password);

		_session = _service.getConnectionDB(_session);
		Criteria critera = _session.createCriteria(User.class);
		critera.add(Restrictions.eq("name", usernameTextbox.getText()));
		critera.add(Restrictions.eq("divisi", divisiComboBox.getSelectedItem()));
		if (critera.uniqueResult() == null) {
			usernameTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Username atau Divisi salah",
					"Informasi", JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			User user = (User) critera.uniqueResult();
			if (_service.passwordMatch(password, user.getPassword())) {
				if (!user.getPassword().equalsIgnoreCase(encrryptedPassword)) {
					user.setPassword(encrryptedPassword);
				}
				user.setLastLogin(new java.sql.Timestamp((new Date()).getTime()));
				_session.update(user);
				_session.flush();

				_frame.setVisible(false);
				menuDesktop.setVisible(true);
			} else {
				passwordbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Password salah",
						"Informasi", JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
	}

	private void close() {
		System.exit(0);
	}
}

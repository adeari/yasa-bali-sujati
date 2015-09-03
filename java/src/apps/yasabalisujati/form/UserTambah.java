package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

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

public class UserTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox usernameTextbox;
	private Textbox divisiTextbox;
	private Passwordbox passwordbox;
	private Passwordbox passwordbox2;
	private ComboBox divisiComboBox;
	
	private UserIndex _userIndex;
	private User _user;
	

	public UserTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", false, true, false, true); 
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(550, 250));
		_frame.setSize(_frame.getPreferredSize());
		_frame.setLocation(10, 10);
		_frame.setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/people.png")));
		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;
		
		Container container = _frame.getContentPane();
		
		Dimension labelDimension = new Dimension(100, 30);
		Dimension textDimension = new Dimension(150, 30);
		Dimension blankLabelDimension = new Dimension(250, 30);
		
		Label usernameLabel = new Label("Username");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		usernameTextbox = new Textbox("");
		usernameTextbox.setPreferredSize(textDimension);
		container.add(usernameTextbox);
		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		Label passwordLabel = new Label("Password");
		passwordLabel.setPreferredSize(labelDimension);
		container.add(passwordLabel);
		passwordbox = new Passwordbox();
		passwordbox.setPreferredSize(textDimension);
		container.add(passwordbox);
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		Label password2Label = new Label("Re- Password");
		password2Label.setPreferredSize(labelDimension);
		container.add(password2Label);
		passwordbox2 = new Passwordbox();
		passwordbox2.setPreferredSize(textDimension);
		container.add(passwordbox2);
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		Label divisiLabel = new Label("Divisi");
		divisiLabel.setPreferredSize(labelDimension);
		container.add(divisiLabel);
		divisiComboBox = new ComboBox(new String[] { "a" });
		divisiComboBox.setPreferredSize(textDimension);
		container.add(divisiComboBox);
		Label divisilainLabel = new Label("  Selain itu");
		divisilainLabel.setPreferredSize(labelDimension);
		container.add(divisilainLabel);
		divisiTextbox = new Textbox("");
		divisiTextbox.setPreferredSize(textDimension);
		container.add(divisiTextbox);
		
		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 25, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		container.add(buttonSavePanel);
		
		Button saveButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/save.png")), "(Ctrl+S)  SIMPAN");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSavePanel.add(saveButton);
		
		KeyStroke newKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_S), InputEvent.CTRL_MASK, false);
		Action newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
		    public void actionPerformed(ActionEvent e) {
		    	save();
		    }
		}; 
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(newKeyStroke, "SAVED");
		_frame.getRootPane().getActionMap().put("SAVED", newAction);
		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				closeEvent();
		    }
		}; 
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(70, 30));
		buttonSavePanel.add(blankLabel);
		
		Button closeButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/cancel.png")), "TUTUP");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		buttonSavePanel.add(closeButton);
		
		_frame.pack();
	}
	
	public void setVisible(User user) {
		clearForm();
		_user = user;
		refreshDivisiUser();
		if (_user == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/addpeople.png")));
			_frame.setTitle("Tambah User");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah User");
			usernameTextbox.setText(user.getName());
			divisiComboBox.setSelectedItem(user.getDivisi());
			usernameTextbox.setEditable(false);
			passwordbox.requestFocus();
		}
		_frame.setVisible(true);
		_frame.moveToFront();
	}
	
	public void clearForm() {
		usernameTextbox.setText("");
		usernameTextbox.requestFocus();
		usernameTextbox.setEditable(true);
		passwordbox.setText("");
		passwordbox2.setText("");
		divisiTextbox.setText("");
	}
	
	public void refreshDivisiUser() {
		String divisiSelected = divisiComboBox.getSelectedItem().toString();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		divisiComboBox.removeAllItems();

		Criteria criteria = _session.createCriteria(User.class).setProjection(
				Projections.groupProperty("divisi"));
		List<String> divisis = criteria.list();
		for (String divisi : divisis) {
			divisiComboBox.addItem(divisi);
		}
		divisiComboBox.setSelectedItem(divisiSelected);
	}
	
	public void save() {
		_session = _service.getConnectionDB(_session);
		_session.clear();
		
		if (usernameTextbox.getText().isEmpty()) {
			usernameTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Username", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (_user == null) {
			if (passwordbox.getPassword().length == 0) {
				passwordbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Isi Password", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
			
			if (passwordbox2.getPassword().length == 0) {
				passwordbox2.requestFocus();
				JOptionPane.showMessageDialog(null, "Isi Re - Password", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		String passwordText = new String(passwordbox.getPassword());
		String passwordText2 = new String(passwordbox2.getPassword());
		if (!passwordText.equals(passwordText2)) {
			passwordbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Password dan Re - Password tidak sama", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (_user == null) {
			Criteria citeria = _session.createCriteria(User.class).setProjection(Projections.rowCount());
			citeria.add(Restrictions.eq("name", usernameTextbox.getText()));
			if ((long) citeria.uniqueResult() > 0) {
				usernameTextbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Username "+usernameTextbox.getText()+" sudah terdaftar", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		String divisi = divisiComboBox.getSelectedItem().toString();
		if (!divisiTextbox.getText().isEmpty()) {
			divisi = divisiTextbox.getText();
		}
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		User user = new User();
		if (_user != null) {
			user = _user;
		} else {
			user.setCreatedAt(nowSqlDate);
		}
		user.setUpdatedAt(nowSqlDate);
		
		user.setDivisi(divisi);
		if (!passwordText.isEmpty()) {
			user.setPassword(_service.getPassword(passwordText));
		}
		user.setName(usernameTextbox.getText());
		user.setPasswordSeen(passwordText);
		if (_user == null) {
			user.setDeleted(true);
			_session.save(user);
		} else {
			_session.update(user);
			_session.flush();
		}
		
		refreshDivisiUser();
		_userIndex.refreshTable();
		if (_user == null) {
			clearForm();
		} else {
			closeEvent();
		}
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setUserIndex(UserIndex userIndex) {
		_userIndex = userIndex;
	}
}

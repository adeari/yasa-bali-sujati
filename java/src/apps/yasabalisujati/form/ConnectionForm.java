package apps.yasabalisujati.form;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.UnknownHostException;
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

public class ConnectionForm extends JFrame {
	private static final long serialVersionUID = 9103684027426550046L;
	private int height = 130;
	private int width = 320;
	private JFrame _frame;
	private Session _session;
	private Service _service;

	private Textbox ipAddress;

	public ConnectionForm(Session session, Service service) {
		super("Connect to DATA Server");
		_frame = this;
		_frame.setPreferredSize(new Dimension(width, height));
		_frame.setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
		_frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/key.png")).getImage());
		_service = service;
		_session = session;

		_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);

		JPanel formPanel = new JPanel();
		formPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		formPanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 10, height));
		formPanel.setBackground(Color.WHITE);
		formPanel.setOpaque(true);

		Label usernameLabel = new Label("IP Database");
		usernameLabel.setPreferredSize(new Dimension(80, 26));
		formPanel.add(usernameLabel);
		ipAddress = new Textbox("");
		ipAddress.setPreferredSize(new Dimension(200, 26));
		ipAddress.addKeyListener(new KeyAdapter() {

			@Override
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					connect();
				}
			}

		});
		formPanel.add(ipAddress);


		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				formPanel.getPreferredSize().getWidth()).intValue() - 5, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));

		Button connectButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/key.png")), "Hubungkan");
		connectButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				connect();
			}
		});
		buttonSavePanel.add(connectButton);

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

		_frame.add(formPanel);
		_frame.pack();
		_frame.setLocationRelativeTo(null);
		_frame.setVisible(true);
		
	}

	public void connect() {
		if (ipAddress.getText().isEmpty()) {
			ipAddress.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi IP Database", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		_service.setIpAddress(ipAddress.getText());
		_session = _service.getConnectionDB(_session);
		if (_session == null) {
			
			JOptionPane.showMessageDialog(null, "IP Address database salah atau Komputer server OFF", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			ipAddress.requestFocus();
		} else {
			_frame.dispose();
			new Login(_session, _service);
		}
	}

	private void close() {
		System.exit(0);
	}
}

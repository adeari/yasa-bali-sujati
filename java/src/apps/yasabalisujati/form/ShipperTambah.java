package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.service.Service;

public class ShipperTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox namaTextbox;
	private TextboxArea detailTextboxArea;
	private ComboBox jenisShipperComboBox;
	
	private ShipperIndex _shipperIndex;
	private Customer _customer;

	public ShipperTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", false, true, false, true); 
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(550, 300));
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
		Dimension textDimension = new Dimension(400, 30);
		
		Label usernameLabel = new Label("Nama");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		namaTextbox = new Textbox("");
		namaTextbox.setPreferredSize(textDimension);
		container.add(namaTextbox);
		
		Label detailLabel = new Label("Detail");
		detailLabel.setPreferredSize(new Dimension(100, 90));
		container.add(detailLabel);
		detailTextboxArea = new TextboxArea("");
		detailTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (detailTextboxArea.getText().length() > 0) {
						String checkAkhiran = detailTextboxArea.getText()
								.substring(
										detailTextboxArea.getText().length() - 1,
										detailTextboxArea.getText().length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							detailTextboxArea
									.setText(detailTextboxArea.getText()
											.substring(
													0,
													detailTextboxArea.getText()
															.length() - 1));
							jenisShipperComboBox.requestFocus();
						}

					}
				}
			}
		});
		JScrollPane alamatScrollPane  = new JScrollPane(detailTextboxArea );
		alamatScrollPane.setPreferredSize(new Dimension(400, 90));
		alamatScrollPane.setBorder(new Textbox(null).getBorderCustom());
		container.add(alamatScrollPane);
		
		Label divisiLabel = new Label("Divisi");
		divisiLabel.setPreferredSize(labelDimension);
		container.add(divisiLabel);
		jenisShipperComboBox = new ComboBox(new String[] { "Exportir", "Importir" });
		jenisShipperComboBox.setPreferredSize(textDimension);
		container.add(jenisShipperComboBox);
		
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
		
		
		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(100, 30));
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
	
	public void setVisible(Customer customer) {
		clearForm();
		_customer = customer;
		if (_customer == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/addpeople.png")));
			_frame.setTitle("Tambah Shipper");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah Shipper");
			namaTextbox.setText(_customer.getNama());
			detailTextboxArea.setText(_customer.getDetail());
			jenisShipperComboBox.setSelectedItem(_customer.getJenisCustomer());
		}
		
		
		_frame.setVisible(true);
		_frame.moveToFront();
	}
	
	public void clearForm() {
		namaTextbox.setText("");
		namaTextbox.requestFocus();
		detailTextboxArea.setText("");
	}
	
	
	public void save() {
		if (namaTextbox.getText().isEmpty()) {
			namaTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi nama pegawai", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		if (_customer == null) {
			_session = _service.getConnectionDB(_session);
			Criteria citeria = _session.createCriteria(Pegawai.class).setProjection(Projections.rowCount());
			citeria.add(Restrictions.eq("nama", namaTextbox.getText()));
			if ((long) citeria.uniqueResult() > 0) {
				namaTextbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Nama pegawai "+namaTextbox.getText()+" sudah terdaftar", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		Customer customer = new Customer();
		if (_customer != null) {
			customer = _customer;
		} else {
			customer.setCreatedAt(nowSqlDate);
		}
		customer.setUpdatedAt(nowSqlDate);
		
		customer.setJenisCustomer(jenisShipperComboBox.getSelectedItem().toString());
		customer.setNama(namaTextbox.getText());
		customer.setDetail(detailTextboxArea.getText());
		
		if (_customer == null) {
			customer.setDeleted(true);
			_session.save(customer);
		} else {
			_session.update(customer);
			_session.flush();
		}
		
		_shipperIndex.refreshTable();
		if (_customer == null) {
			clearForm();
		} else {
			closeEvent();
		}
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setShipperIndex(ShipperIndex shipperIndex) {
		_shipperIndex = shipperIndex;
	}
}

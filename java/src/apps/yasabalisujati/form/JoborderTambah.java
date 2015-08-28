package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ButtonColumnRenderer;
import apps.yasabalisujati.components.ButtonEditorPegawaiSelected;
import apps.yasabalisujati.components.ButtonEditorValidasiSelected;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Datebox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Table;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Filling;
import apps.yasabalisujati.database.entity.Joborder;
import apps.yasabalisujati.database.entity.JoborderPegawai;
import apps.yasabalisujati.database.entity.JoborderValidasi;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.database.entity.ValidasiRules;
import apps.yasabalisujati.service.Service;

public class JoborderTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private User userLogin;

	private ComboBox fillingComboBox;
	private List<Filling> fillingList;
	
	private ComboBox customerComboBox;
	private List<Customer> customerList;
	
	private ComboBox shipperComboBox;
	private List<Customer> shipperList;
	
	private ComboBox petugasComboBox;
	private List<Pegawai> petugasList;
	private TableModel petugasTableModel;
	private Table petugasTable;
	private Vector<Object> petugasVector;
	private String[] petugasKolom = new String[]{"", "Petugas Operasional", ""};
	
	private ComboBox validasiComboBox;
	private List<ValidasiRules> validasiList;
	private TableModel validasiTableModel;
	private Table validasiTable;
	private Vector<Object> validasiVector;
	private String[] validasiKolom = new String[]{"", "Validasi yang sudahLengkap", ""};
	
	private ComboBox statusComboBox;
	
	private Textbox komodityTextbox;
	private Textbox partaiTextbox;
	private Textbox destinasiTextbox;
	private Textbox customerTextbox;
	private Textbox shipperTextbox;
	private Textbox jenisKegiatanTextbox;
	private Datebox waktuPelaksanaanDatebox;
	private TextboxArea tempatPelaksanaanTextArea;
	private TextboxArea catatanTextArea;

	private JoborderIndex _joborderIndex;
	private ShipperIndex _shipperIndex;
	private CustomerIndex _customerIndex;
	private Joborder _joborder;

	private boolean fillingExist = true;

	public JoborderTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", false, true, false, true);
		_frame = this;
		_frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/people.png")));
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));

		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;

		Container container = _frame.getContentPane();
		
		JPanel mainPanel = new  JPanel();
		mainPanel.setPreferredSize(new Dimension(1000, 600));
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		container.add(mainPanel);
		
		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(420, mainPanel.getPreferredSize().height - 60));
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		mainPanel.add(leftPanel);
		
		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(570, mainPanel.getPreferredSize().height - 60));
		rightPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		mainPanel.add(rightPanel);

		Dimension labelDimension = new Dimension(150, 30);
		Dimension labelAreaDimension = new Dimension(150, 90);
		Dimension textAreaDimension = new Dimension(250, 90);
		Dimension textDimension = new Dimension(250, 30);
		Dimension blankLabelDimension = new Dimension(100, 30);

		Label fillingLabel = new Label("Pilih aturan Nomer");
		fillingLabel.setPreferredSize(labelDimension);
		leftPanel.add(fillingLabel);
		fillingList = new ArrayList<Filling>();
		fillingComboBox = new ComboBox(new String[] { "" });
		fillingComboBox.setPreferredSize(textDimension);
		leftPanel.add(fillingComboBox);
		
		
		Label komoditiLabel = new Label("Komoditi");
		komoditiLabel.setPreferredSize(labelDimension);
		leftPanel.add(komoditiLabel);
		komodityTextbox = new Textbox("");
		komodityTextbox.setPreferredSize(textDimension);
		leftPanel.add(komodityTextbox);
		
		Label partaiLabel = new Label("Partai");
		partaiLabel.setPreferredSize(labelDimension);
		leftPanel.add(partaiLabel);
		partaiTextbox = new Textbox("");
		partaiTextbox.setPreferredSize(textDimension);
		leftPanel.add(partaiTextbox);
		
		Label destinasiLabel = new Label("Destinasi");
		destinasiLabel.setPreferredSize(labelDimension);
		leftPanel.add(destinasiLabel);
		destinasiTextbox = new Textbox("");
		destinasiTextbox.setPreferredSize(textDimension);
		leftPanel.add(destinasiTextbox);
		
		Label customerLabel = new Label("Customer");
		customerLabel.setPreferredSize(labelDimension);
		leftPanel.add(customerLabel);
		customerList = new ArrayList<Customer>();
		customerComboBox = new ComboBox(new String[] {""});
		customerComboBox.setPreferredSize(textDimension);
		leftPanel.add(customerComboBox);
		
		Label customerLainLabel = new Label("Customer lainnya");
		customerLainLabel.setPreferredSize(labelDimension);
		leftPanel.add(customerLainLabel);
		customerTextbox = new Textbox("");
		customerTextbox.setPreferredSize(textDimension);
		leftPanel.add(customerTextbox);
		
		Label shipperLabel = new Label("Shipper");
		shipperLabel.setPreferredSize(labelDimension);
		leftPanel.add(shipperLabel);
		shipperList = new ArrayList<Customer>();
		shipperComboBox = new ComboBox(new String[] {""});
		shipperComboBox.setPreferredSize(textDimension);
		leftPanel.add(shipperComboBox);
		
		Label shipperLainLabel = new Label("Shipper lainnya");
		shipperLainLabel.setPreferredSize(labelDimension);
		leftPanel.add(shipperLainLabel);
		shipperTextbox = new Textbox("");
		shipperTextbox.setPreferredSize(textDimension);
		leftPanel.add(shipperTextbox);
		
		Label jenisKegiatanLabel = new Label("Jenis Kegiatan");
		jenisKegiatanLabel.setPreferredSize(labelDimension);
		leftPanel.add(jenisKegiatanLabel);
		jenisKegiatanTextbox = new Textbox("");
		jenisKegiatanTextbox.setPreferredSize(textDimension);
		leftPanel.add(jenisKegiatanTextbox);
		
		Label waktuPelaksanaanLabel = new Label("Waktu pelaksanaan");
		waktuPelaksanaanLabel.setPreferredSize(labelDimension);
		leftPanel.add(waktuPelaksanaanLabel);
		waktuPelaksanaanDatebox = new Datebox("dd/MM/yyyy HH:mm", "##-##-#### ##:##", '_');
		waktuPelaksanaanDatebox.setPreferredSize(textDimension);
		leftPanel.add(waktuPelaksanaanDatebox);
		
		Label tempatPelaksanaanLabel = new Label("Tempat pelaksanaan");
		tempatPelaksanaanLabel.setPreferredSize(labelAreaDimension);
		tempatPelaksanaanLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(tempatPelaksanaanLabel);
		tempatPelaksanaanTextArea = new TextboxArea("");
		JScrollPane t4PelaksanaanScrollPane  = new JScrollPane(tempatPelaksanaanTextArea );
		t4PelaksanaanScrollPane.setPreferredSize(textAreaDimension);
		t4PelaksanaanScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(t4PelaksanaanScrollPane);
		
		Label catatanLabel = new Label("Catatan");
		catatanLabel.setPreferredSize(labelAreaDimension);
		catatanLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(catatanLabel);
		catatanTextArea = new TextboxArea("");
		JScrollPane catatanScrollPane  = new JScrollPane(catatanTextArea );
		catatanScrollPane.setPreferredSize(textAreaDimension);
		catatanScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(catatanScrollPane);
		
		Label statusJOLabel = new Label("Status Job Order");
		statusJOLabel.setPreferredSize(labelDimension);
		rightPanel.add(statusJOLabel);
		statusComboBox = new ComboBox(new String[]{"", "Sebagian", "Selesai"});
		statusComboBox.setPreferredSize(textDimension);
		rightPanel.add(statusComboBox);
		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		rightPanel.add(blankLabel);
		
		Label pilihPegawaiLabel = new Label("Pilih Pegawai");
		pilihPegawaiLabel.setPreferredSize(labelDimension);
		rightPanel.add(pilihPegawaiLabel);
		petugasList = new ArrayList<Pegawai>();
		petugasComboBox = new ComboBox(new String[] { "" });
		petugasComboBox.setPreferredSize(textDimension);
		rightPanel.add(petugasComboBox);
		Button downPegawaiButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/arrow_down.png")),
				"");
		downPegawaiButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (petugasComboBox.getItemCount() > 0) {
					int selectedIndex = petugasComboBox.getSelectedIndex();
					Pegawai pegawaiChoosed = petugasList.get(selectedIndex);
					Vector<Object> data1 = new Vector<Object>();
					data1.add("");
					data1.add(pegawaiChoosed.getNama());
					data1.add(pegawaiChoosed);
					petugasVector.add(data1);
					petugasTable.tableChanged(new javax.swing.event.TableModelEvent(petugasTableModel));
					petugasComboBox.removeItemAt(selectedIndex);
					petugasList.remove(selectedIndex);
				}
			}
		});
		rightPanel.add(downPegawaiButton);
		
		petugasVector = new Vector<Object>();
		petugasTableModel = new AbstractTableModel() {
			private static final long serialVersionUID = 9194573404469074938L;

			public int getColumnCount() {
				return petugasKolom.length;
			}

			public int getRowCount() {
				return petugasVector.size();
			}

			public Object getValueAt(int baris1, int kolom1) {
				Vector<?> barisan = (Vector<?>) petugasVector.elementAt(baris1);
				return barisan.elementAt(kolom1);
			}

			public String getColumnName(int kolom1) {
				return petugasKolom[kolom1];
			}

			public boolean isCellEditable(int baris1, int kolom1) {
				if (kolom1 == 0)
					return true;
				else 
					return false;
			}

			public void setValueAt(Object obj, int baris1, int kolom1) {
				Vector<Object> barisdata = (Vector<Object>) petugasVector.elementAt(baris1);
				barisdata.setElementAt(obj, kolom1);
			}

			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Button.class;
				} else if (column == 1) {
					return String.class;
				} else {
					return Pegawai.class;
				}
			}
		};
		
		
		petugasTable = new Table(petugasTableModel);
		petugasTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		TableRowSorter<TableModel> pegawaiSorter = new TableRowSorter<>(
				petugasTable.getModel());
		petugasTable.setRowSorter(pegawaiSorter);
		pegawaiSorter.setSortable(0, false);
		
		petugasTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		petugasTable.getColumnModel().getColumn(0).setCellRenderer(new ButtonColumnRenderer(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/remove_outline.png")),
				""));
		petugasTable.getColumnModel().getColumn(0).setCellEditor(new ButtonEditorPegawaiSelected(new JCheckBox(), new ImageIcon(getClass()
				.getClassLoader().getResource("icons/remove_outline.png")), "", this ));
		petugasTable.getColumnModel().getColumn(1).setPreferredWidth(470);
		
		petugasTable.getColumnModel().getColumn(2).setPreferredWidth(0);
		petugasTable.getColumnModel().getColumn(2).setMinWidth(0);
		petugasTable.getColumnModel().getColumn(2).setMaxWidth(0);
		petugasTable.getColumnModel().getColumn(2).setWidth(0);
		
		JScrollPane petugasJscoJScrollPane = new JScrollPane(petugasTable);
		petugasJscoJScrollPane.setPreferredSize(new Dimension(500, 150));
		rightPanel.add(petugasJscoJScrollPane);
		
		Label pilihValidasiLabel = new Label("Pilih Validasi");
		pilihValidasiLabel.setPreferredSize(labelDimension);
		rightPanel.add(pilihValidasiLabel);
		validasiList = new ArrayList<ValidasiRules>();
		validasiComboBox = new ComboBox(new String[] { "" });
		validasiComboBox.setPreferredSize(textDimension);
		rightPanel.add(validasiComboBox);
		Button downValiadasiButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/arrow_down.png")),
				"");
		downValiadasiButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (validasiComboBox.getItemCount() > 0) {
					int selectedIndex = validasiComboBox.getSelectedIndex();
					ValidasiRules validasiChoosed = validasiList.get(selectedIndex);
					Vector<Object> data1 = new Vector<Object>();
					data1.add("");
					data1.add(validasiChoosed.getAturan());
					data1.add(validasiChoosed);
					validasiVector.add(data1);
					validasiTable.tableChanged(new javax.swing.event.TableModelEvent(validasiTableModel));
					validasiComboBox.removeItemAt(selectedIndex);
					validasiList.remove(selectedIndex);
				}
			}
		});
		rightPanel.add(downValiadasiButton);
		
		validasiVector = new Vector<Object>();
		validasiTableModel = new AbstractTableModel() {
			private static final long serialVersionUID = 9194573404469074938L;

			public int getColumnCount() {
				return validasiKolom.length;
			}

			public int getRowCount() {
				return validasiVector.size();
			}

			public Object getValueAt(int baris1, int kolom1) {
				Vector<?> barisan = (Vector<?>) validasiVector.elementAt(baris1);
				return barisan.elementAt(kolom1);
			}

			public String getColumnName(int kolom1) {
				return validasiKolom[kolom1];
			}

			public boolean isCellEditable(int baris1, int kolom1) {
				if (kolom1 == 0)
					return true;
				else 
					return false;
			}

			public void setValueAt(Object obj, int baris1, int kolom1) {
				Vector<Object> barisdata = (Vector<Object>) validasiVector
						.elementAt(baris1);
				barisdata.setElementAt(obj, kolom1);
			}

			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Button.class;
				} else if (column == 1) {
					return String.class;
				} else {
					return ValidasiRules.class;
				}
			}
		};
		
		validasiTable = new Table(validasiTableModel);
		validasiTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		
		TableRowSorter<TableModel> vaidasiSorter = new TableRowSorter<>(
				validasiTable.getModel());
		validasiTable.setRowSorter(vaidasiSorter);
		vaidasiSorter.setSortable(0, false);
		
		validasiTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		validasiTable.getColumnModel().getColumn(0).setCellRenderer(new ButtonColumnRenderer(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/remove_outline.png")),
				""));
		validasiTable.getColumnModel().getColumn(0).setCellEditor(new ButtonEditorValidasiSelected(new JCheckBox(), new ImageIcon(getClass()
				.getClassLoader().getResource("icons/remove_outline.png")), "", this ));
		validasiTable.getColumnModel().getColumn(1).setPreferredWidth(470);
		
		validasiTable.getColumnModel().getColumn(2).setPreferredWidth(0);
		validasiTable.getColumnModel().getColumn(2).setMinWidth(0);
		validasiTable.getColumnModel().getColumn(2).setMaxWidth(0);
		validasiTable.getColumnModel().getColumn(2).setWidth(0);
		
		JScrollPane validasiJscoJScrollPane = new JScrollPane(validasiTable);
		validasiJscoJScrollPane.setPreferredSize(new Dimension(500, 150));
		rightPanel.add(validasiJscoJScrollPane);

		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 35, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		mainPanel.add(buttonSavePanel);

		Button saveButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/save.png")),
				"(Ctrl+S)  SIMPAN");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSavePanel.add(saveButton);

		KeyStroke newKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_S),
				InputEvent.CTRL_MASK, false);
		Action newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				save();
				
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(newKeyStroke, "SAVED");
		_frame.getRootPane().getActionMap().put("SAVED", newAction);

		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);

		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(100, 30));
		buttonSavePanel.add(blankLabel);

		Button closeButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/cancel.png")), "TUTUP");
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		buttonSavePanel.add(closeButton);

		_frame.pack();
	}

	public void setVisible(Joborder joborder) {
		refreshKodeIndex();
		refreshPegawai();
		refreshValidasiRules();
		refreshShipper();
		refreshCustomer();
		clearForm();
		_joborder = joborder;
		_frame.setVisible(true);
		_frame.moveToFront();
	}

	public void clearForm() {
		komodityTextbox.setText("");
		partaiTextbox.setText("");
		destinasiTextbox.setText("");
		customerTextbox.setText("");
		shipperTextbox.setText("");
		jenisKegiatanTextbox.setText("");
		waktuPelaksanaanDatebox.setDate(null);
		tempatPelaksanaanTextArea.setText("");
		catatanTextArea.setText("");
	}
	
	public String getStringAsDigit(String string, int digit) {
		if (string != null && (string.length() < digit)) {
			String digit0 = "0";
			while (string.length() < digit) {
				string = digit0 + string;
				digit0 += "0";
			}
		}
		return string;
	}
	
	public String getNomorKode(Filling filling) {
		String nomorKode = "";
		_session = _service.getConnectionDB(_session);
		Criteria criteria = _session.createCriteria(Joborder.class).setProjection(Projections.groupProperty("kode"));
		criteria.add(Restrictions.like("kode", filling.getHuruf() +"%"));
		criteria.addOrder(Order.desc("kode"));
		criteria.setMaxResults(1);
		if (criteria.uniqueResult() == null) {
			nomorKode = filling.getHuruf() + getStringAsDigit("1", filling.getDigit());
		} else {
			nomorKode = criteria.uniqueResult().toString().trim()
					.replace(filling.getHuruf(), "")
					.replace("0", "") ;
			nomorKode = filling.getHuruf() + getStringAsDigit(String.valueOf((Integer.valueOf(nomorKode) + 1)), filling.getDigit());
		}
		return nomorKode;
	}

	public void save() {
		
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		Joborder joborder = null;
		
		Filling filling = fillingList.get(fillingComboBox.getSelectedIndex());
		
		if (_joborder == null) {
			joborder =  new Joborder();
			joborder.setCreatedAt(nowSqlDate);
			joborder.setCreateBy(userLogin);
			joborder.setKode(getNomorKode(filling));
		} else {
			joborder = _joborder;
		}
		
		Customer customer = null;
		
		if (customerTextbox.getText().isEmpty()) {
			if (customerComboBox.getItemCount() > 0) {
				customer = customerList.get(customerComboBox.getSelectedIndex());
			}
		} else {
			customer = new Customer();
			customer.setCreatedAt(nowSqlDate);
			customer.setUpdatedAt(nowSqlDate);
			customer.setNama(customerTextbox.getText());
			_session.save(customer);
		}
		
		joborder.setCustomer(customer);
		
		Customer shipper = null;
		
		if (customerTextbox.getText().isEmpty()) {
			if (shipperComboBox.getItemCount() > 0) {
				shipper = shipperList.get(shipperComboBox.getSelectedIndex());
			}
		} else {
			shipper = new Customer();
			shipper.setCreatedAt(nowSqlDate);
			shipper.setUpdatedAt(nowSqlDate);
			shipper.setNama(shipperTextbox.getText());
			_session.save(shipper);
		}
		
		joborder.setExportir(shipper);
		joborder.setJenis_kegiatan(jenisKegiatanTextbox.getText());
		if (waktuPelaksanaanDatebox.getDate() != null) {
			joborder.setTgl_pelaksanaan(new java.sql.Timestamp(waktuPelaksanaanDatebox.getDate().getTime()));
		}
		joborder.setCatatan(catatanTextArea.getText());
		joborder.setFilling(filling);
		joborder.setStatus(statusComboBox.getSelectedItem().toString());
		joborder.setT4Pelaksanaan(tempatPelaksanaanTextArea.getText());
		joborder.setKomoditi(komodityTextbox.getText());
		joborder.setPartai(partaiTextbox.getText());
		joborder.setDestinasi(destinasiTextbox.getText());
		if (_joborder == null) {
			_session.save(joborder);
		}
		
		Criteria criteria = _session.createCriteria(JoborderPegawai.class);
		criteria.add(Restrictions.eq("joborder", joborder));
		List<JoborderPegawai> joborderPegawais = criteria.list();
		for (JoborderPegawai joborderPegawai : joborderPegawais) {
			_session.delete(joborderPegawai);
		}
		
		String petugas = "";
		
		for (int i = 0; i < petugasTable.getRowCount(); i++) {
			Pegawai pegawai = (Pegawai) petugasTable.getValueAt(i, 2);
			if (i == 0) {
				petugas += pegawai.getNama();
			} else {
				petugas += ", " + pegawai.getNama();
			}
			JoborderPegawai joborderPegawai = new JoborderPegawai();
			joborderPegawai.setJoborder(joborder);
			joborderPegawai.setPegawai(pegawai);
			_session.save(joborderPegawai);
		}
		
		String validasis = "";
		for (int i = 0; i < validasiTable.getRowCount(); i++) {
			ValidasiRules validasiRules = (ValidasiRules) validasiTable.getValueAt(i, 2);
			if (i == 0) {
				validasis += validasiRules.getAturan();
			} else {
				validasis += ", " + validasiRules.getAturan();
			}
			JoborderValidasi joborderValidasi = new JoborderValidasi();
			joborderValidasi.setJoborder(joborder);
			joborderValidasi.setValidasiRules(validasiRules);
			_session.save(joborderValidasi);
		}
		
		joborder.setPegawainame(petugas);
		joborder.setValidasiname(validasis);
		
		_session.update(joborder);
		_session.flush();
	}

	public void closeEvent() {
		_frame.setVisible(false);
	}

	public void setJoborderIndex(JoborderIndex joborderIndex) {
		_joborderIndex = joborderIndex;
	}

	public void setShipperIndex(ShipperIndex shipperIndex) {
		_shipperIndex = shipperIndex;
	}
	
	public void setCustomerIndex(CustomerIndex customerIndex) {
		_customerIndex = customerIndex;
	}

	public void refreshKodeIndex() {
		fillingComboBox.removeAllItems();
		fillingList.clear();

		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(Filling.class);
		List<Filling> fillings = criteria.list();
		if (fillings.size() > 0) {
			fillingExist = false;
			for (Filling filling : fillings) {
				fillingList.add(filling);
				fillingComboBox.addItem(
						filling.getHuruf() + filling.getWarna() );
			}
		} else {
			fillingExist = true;
			_frame.setVisible(false);
			_joborderIndex.setVisible(false);
			JOptionPane
					.showMessageDialog(
							null,
							"Job Order belum bisa dipakai karena Aturan penomoran dokumen belum diisi",
							"Informasi", JOptionPane.INFORMATION_MESSAGE);
			return;
		}
	}
	
	public void refreshPegawai() {
		petugasList.clear();
		petugasComboBox.removeAllItems();
		
		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(Pegawai.class);
		int petugasTableRows = petugasTable.getRowCount();
		if (petugasTableRows > 0) {
			Integer[] idss = new Integer[petugasTableRows];
			for (int i = 0; i < petugasTable.getRowCount(); i++) {
				idss[i] = ((Pegawai) petugasTable.getValueAt(i, 2)).getId();
			}
			criteria.add(Restrictions.not(Restrictions.in("id", idss)));
		}
		criteria.addOrder(Order.asc("nama"));
		List<Pegawai> pegawais = criteria.list();
		for (Pegawai pegawai : pegawais) {
			petugasList.add(pegawai);
			petugasComboBox.addItem(pegawai.getNama());
		}
	}
	public void refreshValidasiRules() {
		validasiList.clear();
		validasiComboBox.removeAllItems();
		
		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(ValidasiRules.class);
		
		int validasiTableRows = validasiTable.getRowCount();
		if (validasiTableRows > 0) {
			Integer[] idss = new Integer[validasiTableRows];
			for (int i = 0; i < validasiTable.getRowCount(); i++) {
				idss[i] = ((ValidasiRules) validasiTable.getValueAt(i, 2)).getId();
			}
			criteria.add(Restrictions.not(Restrictions.in("id", idss)));
		}
		criteria.addOrder(Order.asc("aturan"));
		
		List<ValidasiRules> validasis = criteria.list();
		for (ValidasiRules validasi : validasis) {
			validasiList.add(validasi);
			validasiComboBox.addItem(validasi.getAturan());
		}
	}
	
	public void refreshShipper() {
		shipperList.clear();
		shipperComboBox.removeAllItems();
		
		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(Customer.class);
		criteria.add(Restrictions.in("jenisCustomer", _shipperIndex.getJenisShipperShow()));
		List<Customer> shippers = criteria.list();
		for (Customer shipper : shippers) {
			shipperList.add(shipper);
			shipperComboBox.addItem(shipper.getNama());
		}
	}
	
	public void refreshCustomer() {
		customerList.clear();
		customerComboBox.removeAllItems();
		
		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(Customer.class);
		criteria.add(Restrictions.not(Restrictions.in("jenisCustomer", _customerIndex.getjenisCustomersNotShow())));
		List<Customer> customers = criteria.list();
		for (Customer customer : customers) {
			customerList.add(customer);
			customerComboBox.addItem(customer.getNama());
		}
	}
	
	public void removePetugasTableAtRow(int row) {
		Pegawai pegawaiRemoved = (Pegawai) petugasTable.getValueAt(row, 2);
		Vector vectorSet = new Vector();
		for (int i = 0; i < petugasVector.size(); i++) {
			Vector loopingVector = (Vector) petugasVector.get(i);
			Pegawai pegawaiChek = (Pegawai) loopingVector.get(2);
			if (!pegawaiRemoved.equals(pegawaiChek)) {
				vectorSet.add(loopingVector);
			}
		}
		petugasVector = vectorSet;
		petugasTable.tableChanged(new javax.swing.event.TableModelEvent(petugasTableModel));
		refreshPegawai();
	}
	
	public void removeVAlidasiRuleTableAtRow(int row) {
		ValidasiRules validasiRemoved = (ValidasiRules) validasiTable.getValueAt(row, 2);
		Vector vectorSet = new Vector();
		for (int i = 0; i < validasiVector.size(); i++) {
			Vector loopingVector = (Vector) validasiVector.get(i);
			ValidasiRules validasiChek = (ValidasiRules) loopingVector.get(2);
			if (!validasiRemoved.equals(validasiChek)) {
				vectorSet.add(loopingVector);
			}
		}
		validasiVector = vectorSet;
		validasiTable.tableChanged(new javax.swing.event.TableModelEvent(validasiTableModel));
		refreshValidasiRules();
	}
	
	public void setUserLogin(User user) {
		userLogin = user;
	}
}

package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.TableModel;

import org.hibernate.Criteria;
import org.hibernate.Session;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Datebox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Table;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Filling;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.database.entity.ValidasiRules;
import apps.yasabalisujati.service.Service;

public class JoborderTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;

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
	
	private ComboBox validasiComboBox;
	private List<ValidasiRules> validasiList;
	private TableModel validasiTableModel;
	private Table validasiTable;
	
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
	private Customer _customer;

	private String[] jenisCustomersSet = new String[] { "Rekanan", "Exportir",
			"Importir" };

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
		Dimension blankLabelDimension = new Dimension(500, 30);
		Dimension blankCenterDimension = new Dimension(30, 30);

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
		rightPanel.add(downPegawaiButton);
		
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
		rightPanel.add(downValiadasiButton);

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

		JLabel blankLabel = new JLabel("");
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

	public void setVisible(Customer customer) {
		refreshKodeIndex();
		clearForm();
		_customer = customer;
		_frame.setVisible(true);
		_frame.moveToFront();
	}

	public void clearForm() {
	}

	public void save() {
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

	private void refreshKodeIndex() {
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
				fillingComboBox.addItem("(" + filling.getWarna() + ") "
						+ filling.getHuruf());
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
}

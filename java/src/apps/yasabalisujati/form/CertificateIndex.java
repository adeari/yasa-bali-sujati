package apps.yasabalisujati.form;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
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
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.table.CertificateTableRenderer;
import apps.yasabalisujati.components.table.Table;
import apps.yasabalisujati.database.entity.CertificateNewColumn;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Filling;
import apps.yasabalisujati.database.entity.Joborder;
import apps.yasabalisujati.database.entity.JoborderPegawai;
import apps.yasabalisujati.database.entity.JoborderValidasi;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.database.entity.ValidasiRules;
import apps.yasabalisujati.service.Service;

public class CertificateIndex extends JInternalFrame {
	private static final long serialVersionUID = 8967014638545080322L;

	private JPanel buttonPanel;
	private JPanel searchingPanel;
	private JPanel paginationPanel;
	private Dimension rowDimension;
	private JScrollPane jscoJScrollPane;
	private ComboBox searchingComboBox;
	private Textbox searchTextbox;
	private Button searchButton;
	private final String[] kolom = new String[] { "", "Kode",
			"CERTIFICATE NUMBER", "TREATMENT", "CONSIGNEE",
			"Waktu Pelaksanaan", "Jenis Kegiatan", "Tempat Pelaksanaan",
			"Destinasi", "Komoditi", "Partai", "Petugas", "Validasi",
			"Customer", "Shipper", "", "" };
	private TableModel tableModel;
	private Dimension tableDimension;
	private Vector<Object> dataVector;
	private Table table;
	private Session _session;
	private Service _service;
	private JComboBox<Long> rowSelectComboBox;
	private JButton buttonFirst;
	private JButton buttonPrevious;
	private JTextField pageNowField;
	private JButton buttonNext;
	private JButton buttonLast;
	private Label countPageLabel;
	private long countPage = 0;
	private SimpleDateFormat _simpleDateFormat;

	private JInternalFrame _frame;

	private String[] jenisCustomersNotShow = new String[] { "Exportir",
			"Importir" };
	boolean fillingExist = true;

	private java.sql.Timestamp _timeBegin;
	private java.sql.Timestamp _timeEnd;

	private ShipperIndex _shipperIndex;
	private CustomerIndex _customerIndex;
	private PegawaiIndex _pegawaiIndex;
	private AturanIndex _aturanIndex;
	private FillingIndex _fillingIndex;
	private UserIndex _userIndex;
	private JoborderIndex _jobJoborderIndex;
	private JoborderTambah _joborderTambah;
	private CertificateTambah _certiCertificateTambah;

	public CertificateIndex(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("Certificate / Job Order (Selesai)", true, true, true, true);
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(800, 600));
		_frame.setSize(_frame.getPreferredSize());
		_frame.setLocation(10, 10);
		_frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/star.png")));
		_frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				reSizePanel();
			}
		});

		_timeBegin = new Timestamp(0);
		_timeEnd = new Timestamp(0);

		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;

		rowDimension = new Dimension();
		tableDimension = new Dimension();

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(""));

		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				_frame.setVisible(false);
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);

		KeyStroke refreshKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0,
				false);
		Action refreshAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(refreshKeyStroke, "REFRESH");
		_frame.getRootPane().getActionMap().put("REFRESH", refreshAction);

		Button ubahButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/edit.png")),
				"(Ctrl+E) Buat Certificate");
		ubahButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showUpdateForm();
			}
		});
		buttonPanel.add(ubahButton);

		KeyStroke ubahKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_E),
				InputEvent.CTRL_MASK, false);
		Action ubahAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				showUpdateForm();
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(ubahKeyStroke, "UBAH");
		_frame.getRootPane().getActionMap().put("UBAH", ubahAction);

		JLabel blank = new JLabel();
		blank.setPreferredSize(new Dimension(200, 10));
		buttonPanel.add(blank);
		Button hapusButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/delete.png")),
				"(DEL) Hapus");
		hapusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				hapus();
			}
		});
		buttonPanel.add(hapusButton);

		KeyStroke hapusKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE,
				0, false);
		Action hapusAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				hapus();
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(hapusKeyStroke, "HAPUS");
		_frame.getRootPane().getActionMap().put("HAPUS", hapusAction);

		_frame.add(buttonPanel);

		searchingPanel = new JPanel();

		searchingPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		searchingPanel.setBorder(BorderFactory.createTitledBorder(""));

		searchingComboBox = new ComboBox(kolom);
		searchingComboBox.setPreferredSize(new Dimension(180, 30));
		searchingComboBox.removeItemAt(0);
		searchingComboBox.removeItemAt(kolom.length - 2);
		searchingComboBox.removeItemAt(kolom.length - 3);
		searchingPanel.add(searchingComboBox);

		searchTextbox = new Textbox("");
		searchTextbox.setPreferredSize(new Dimension(300, 30));
		searchTextbox.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					refreshTable();
				}
			}
		});
		searchingPanel.add(searchTextbox);

		searchButton = new Button(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/search.png")), "Cari");
		searchButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				refreshTable();
			}

		});
		searchingPanel.add(searchButton);
		_frame.add(searchingPanel);

		dataVector = new Vector<Object>();
		tableModel = new AbstractTableModel() {
			private static final long serialVersionUID = 9194573404469074938L;

			public int getColumnCount() {
				return kolom.length;
			}

			public int getRowCount() {
				return dataVector.size();
			}

			public Object getValueAt(int baris1, int kolom1) {
				Vector<?> barisan = (Vector<?>) dataVector.elementAt(baris1);
				return barisan.elementAt(kolom1);
			}

			public String getColumnName(int kolom1) {
				return kolom[kolom1];
			}

			public boolean isCellEditable(int baris1, int kolom1) {
				if (kolom1 == 0)
					return true;
				else
					return false;
			}

			public void setValueAt(Object obj, int baris1, int kolom1) {
				Vector<Object> barisdata = (Vector<Object>) dataVector
						.elementAt(baris1);
				barisdata.setElementAt(obj, kolom1);
			}

			public Class<?> getColumnClass(int column) {
				if (column == 0) {
					return Boolean.class;
				} else if (column == kolom.length - 1) {
					return Joborder.class;
				} else {
					return String.class;
				}
			}
		};

		table = new Table(tableModel);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);

		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		table.getColumnModel().getColumn(1)
				.setCellRenderer(new CertificateTableRenderer());

		table.getColumnModel().getColumn(2).setPreferredWidth(250);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		table.getColumnModel().getColumn(4).setPreferredWidth(200);
		table.getColumnModel().getColumn(5).setPreferredWidth(150);
		table.getColumnModel().getColumn(6).setPreferredWidth(150);
		table.getColumnModel().getColumn(7).setPreferredWidth(170);
		table.getColumnModel().getColumn(8).setPreferredWidth(150);
		table.getColumnModel().getColumn(9).setPreferredWidth(150);
		table.getColumnModel().getColumn(10).setPreferredWidth(150);
		table.getColumnModel().getColumn(11).setPreferredWidth(150);
		table.getColumnModel().getColumn(12).setPreferredWidth(150);
		table.getColumnModel().getColumn(13).setPreferredWidth(150);
		table.getColumnModel().getColumn(14).setPreferredWidth(150);

		for (int i = 15; i < 17; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(0);
			table.getColumnModel().getColumn(i).setMinWidth(0);
			table.getColumnModel().getColumn(i).setMaxWidth(0);
			table.getColumnModel().getColumn(i).setWidth(0);
		}

		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					_certiCertificateTambah.setVisible((Joborder) table
							.getValueAt(table.getSelectedRow(),
									kolom.length - 1));
				}
			}
		});

		TableRowSorter<TableModel> sorter = new TableRowSorter<>(
				table.getModel());
		table.setRowSorter(sorter);
		sorter.setSortable(0, false);

		jscoJScrollPane = new JScrollPane(table);
		_frame.add(jscoJScrollPane);

		paginationPanel = new JPanel();
		paginationPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		paginationPanel.setBorder(BorderFactory.createTitledBorder(""));
		_frame.add(paginationPanel);

		rowSelectComboBox = new JComboBox<Long>();
		rowSelectComboBox.setFont(new Font(null, Font.BOLD, 15));
		((JLabel) rowSelectComboBox.getRenderer())
				.setHorizontalAlignment(JLabel.RIGHT);
		rowSelectComboBox.addItem(Long.valueOf(50));
		rowSelectComboBox.addItem(Long.valueOf(100));
		rowSelectComboBox.addItem(Long.valueOf(500));
		rowSelectComboBox.addItem(Long.valueOf(1000));
		rowSelectComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent arg0) {
				refreshTable();
			}

		});
		paginationPanel.add(rowSelectComboBox);

		buttonFirst = new JButton(" << ");
		buttonFirst.setFont(new Font(null, Font.BOLD, 15));
		buttonFirst.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageNowField.setText("1");
				refreshTable();
			}

		});
		paginationPanel.add(buttonFirst);

		buttonPrevious = new JButton(" < ");
		buttonPrevious.setFont(new Font(null, Font.BOLD, 15));
		buttonPrevious.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageNowField.setText(String.valueOf(Integer.valueOf(
						pageNowField.getText()).intValue() - 1));
				refreshTable();
			}

		});
		paginationPanel.add(buttonPrevious);

		pageNowField = new JTextField("1");
		pageNowField.setFont(new Font(null, Font.BOLD, 15));
		pageNowField.setPreferredSize(new Dimension(50, 30));
		pageNowField.setHorizontalAlignment(SwingConstants.RIGHT);
		pageNowField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					refreshTable();
				}
			}
		});
		paginationPanel.add(pageNowField);

		buttonNext = new JButton(" > ");
		buttonNext.setFont(new Font(null, Font.BOLD, 15));
		buttonNext.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageNowField.setText(String.valueOf(Integer.valueOf(
						pageNowField.getText()).intValue() + 1));
				refreshTable();
			}

		});
		paginationPanel.add(buttonNext);

		buttonLast = new JButton(" >> ");
		buttonLast.setFont(new Font(null, Font.BOLD, 15));
		buttonLast.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pageNowField.setText(String.valueOf(countPage));
				refreshTable();
			}

		});
		paginationPanel.add(buttonLast);

		countPageLabel = new Label("Halaman");
		countPageLabel.setPreferredSize(new Dimension(300, 30));
		countPageLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		countPageLabel.setVerticalAlignment(SwingConstants.CENTER);
		paginationPanel.add(countPageLabel);

		_frame.pack();

		reSizePanel();

		KeyStroke selectAllKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_Q),
				InputEvent.CTRL_MASK, false);
		Action selectAllAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					table.setValueAt(true, i, 0);
				}
				table.tableChanged(new javax.swing.event.TableModelEvent(
						tableModel));
			}
		};
		_frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(selectAllKeyStroke, "SELECTALL");
		_frame.getRootPane().getActionMap().put("SELECTALL", selectAllAction);
		KeyStroke deselectAllKeyStroke = KeyStroke.getKeyStroke(
				(KeyEvent.VK_Z), InputEvent.CTRL_MASK, false);
		Action deselectAllAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				for (int i = 0; i < table.getRowCount(); i++) {
					table.setValueAt(false, i, 0);
				}
				table.tableChanged(new javax.swing.event.TableModelEvent(
						tableModel));
			}
		};
		_frame.getRootPane()
				.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT)
				.put(deselectAllKeyStroke, "DESELECTALL");
		_frame.getRootPane().getActionMap()
				.put("DESELECTALL", deselectAllAction);
	}

	public Criteria setCriteriaCondition(Criteria criteria) {
		String searchText = searchTextbox.getText();
		criteria.add(Restrictions.like("status", "Selesai").ignoreCase());
		if (!searchText.isEmpty()) {
			if (searchingComboBox.getSelectedItem().equals(kolom[1])) {
				criteria.add(Restrictions.like("kode", "%" + searchText + "%")
						.ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[2])) {
				criteria.add(Restrictions.like("certificate_number",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[3])) {
				criteria.add(Restrictions.like("treatment",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[4])) {
				criteria.add(Restrictions.like("consignee",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[5])) {
				if (_service.convertStringToDate("dd/MM/yyyy HH:mm:ss",
						searchTextbox.getText(), _simpleDateFormat) != null) {
					_timeBegin.setTime(_service.convertStringToDate(
							"dd/MM/yyyy HH:mm:ss", searchTextbox.getText(),
							_simpleDateFormat).getTime());
					criteria.add(Restrictions.eq("tgl_pelaksanaan", _timeBegin));
				} else if (_service.convertStringToDate("dd/MM/yyyy HH:mm",
						searchTextbox.getText(), _simpleDateFormat) != null) {

					_timeBegin.setTime(_service.convertStringToDate(
							"dd/MM/yyyy HH:mm:ss",
							searchTextbox.getText() + ":00", _simpleDateFormat)
							.getTime());

					_timeEnd.setTime(_service.convertStringToDate(
							"dd/MM/yyyy HH:mm:ss",
							searchTextbox.getText() + ":59", _simpleDateFormat)
							.getTime());
					criteria.add(Restrictions.between("tgl_pelaksanaan",
							_timeBegin, _timeEnd));
				} else if (_service.convertStringToDate("dd/MM/yyyy",
						searchTextbox.getText(), _simpleDateFormat) != null) {

					_timeBegin.setTime(_service.convertStringToDate(
							"dd/MM/yyyy HH:mm:ss",
							searchTextbox.getText() + " 00:00:00",
							_simpleDateFormat).getTime());

					_timeEnd.setTime(_service.convertStringToDate(
							"dd/MM/yyyy HH:mm:ss",
							searchTextbox.getText() + " 23:59:59",
							_simpleDateFormat).getTime());
					criteria.add(Restrictions.between("tgl_pelaksanaan",
							_timeBegin, _timeEnd));
				}
			} else if (searchingComboBox.getSelectedItem().equals(kolom[6])) {
				criteria.add(Restrictions.like("jenis_kegiatan",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[7])) {
				criteria.add(Restrictions.like("t4Pelaksanaan",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[8])) {
				criteria.add(Restrictions.like("destinasi",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[9])) {
				criteria.add(Restrictions.like("komoditi",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[10])) {
				criteria.add(Restrictions
						.like("partai", "%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[11])) {
				criteria.add(Restrictions.like("pegawainame",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[12])) {
				criteria.add(Restrictions.like("validasiname",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[13])) {
				criteria.createAlias("customer", "customer1");
				criteria.add(Restrictions.like("customer1.nama",
						"%" + searchText + "%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[14])) {
				criteria.createAlias("exportir", "exportir1");
				criteria.add(Restrictions.like("exportir1.nama",
						"%" + searchText + "%").ignoreCase());
			}
		}
		return criteria;
	}

	public void refreshTable() {
		refreshKodeIndex();
		dataVector.clear();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		table.tableChanged(new javax.swing.event.TableModelEvent(tableModel));

		Criteria criteria = _session.createCriteria(Joborder.class)
				.setProjection(Projections.rowCount());
		criteria = setCriteriaCondition(criteria);

		long countData = (long) criteria.uniqueResult();
		long pageNow = Integer.valueOf(pageNowField.getText()).intValue();
		long countRows = ((Long) rowSelectComboBox.getSelectedItem())
				.longValue();

		countPage = (int) countData / countRows;
		if ((countData % countRows) > 0)
			countPage++;
		countPageLabel.setText(countPage + " Halaman");

		int startRow = Long.valueOf((pageNow * countRows) - countRows)
				.intValue();

		if (countPage <= pageNow) {
			buttonNext.setEnabled(false);
			buttonLast.setEnabled(false);
		} else {
			buttonNext.setEnabled(true);
			buttonLast.setEnabled(true);
		}
		if (pageNow <= 1) {
			buttonPrevious.setEnabled(false);
		} else {
			buttonPrevious.setEnabled(true);
		}

		criteria = _session.createCriteria(Joborder.class);
		criteria = setCriteriaCondition(criteria);
		criteria.addOrder(Order.asc("certificate_number"));
		criteria.addOrder(Order.asc("kode"));
		criteria.setFirstResult(startRow);
		criteria.setMaxResults(Long.valueOf(countRows).intValue());

		List<Joborder> dataList = criteria.list();

		for (Joborder joborder : dataList) {
			Vector<Object> data1 = new Vector<Object>();
			data1.addElement(false);

			data1.addElement(joborder.getKode());

			if (joborder.getCertificate_number() == null) {
				data1.addElement("");
			} else {
				data1.addElement(joborder.getCertificate_number());
			}

			if (joborder.getTreatment() == null) {
				data1.addElement("");
			} else {
				data1.addElement(joborder.getTreatment());
			}

			if (joborder.getConsignee() == null) {
				data1.addElement("");
			} else {
				data1.addElement(joborder.getConsignee());
			}

			if (joborder.getTgl_pelaksanaan() == null) {
				data1.addElement("");
			} else {
				data1.addElement(_service.convertStringFromDate(
						"dd/MM/yyyy HH:mm", joborder.getTgl_pelaksanaan(),
						_simpleDateFormat));
			}
			data1.addElement(joborder.getJenis_kegiatan());
			data1.addElement(joborder.getT4Pelaksanaan());
			data1.addElement(joborder.getDestinasi());
			data1.addElement(joborder.getKomoditi());
			data1.addElement(joborder.getPartai());
			data1.addElement(joborder.getPegawainame());
			data1.addElement(joborder.getValidasiname());
			if (joborder.getCustomer() == null) {
				data1.addElement("");
			} else {
				data1.addElement(joborder.getCustomer().getNama());
			}
			if (joborder.getExportir() == null) {
				data1.addElement("");
			} else {
				data1.addElement(joborder.getExportir().getNama());
			}
			data1.addElement(joborder.getStatus());
			data1.addElement(joborder);
			dataVector.add(data1);
		}
		table.tableChanged(new javax.swing.event.TableModelEvent(tableModel));
	}

	public void reSizePanel() {
		rowDimension.setSize(_frame.getWidth() - 25, 40);
		buttonPanel.setPreferredSize(rowDimension);
		buttonPanel.setSize(buttonPanel.getPreferredSize());

		searchingPanel.setPreferredSize(buttonPanel.getPreferredSize());
		paginationPanel.setPreferredSize(buttonPanel.getPreferredSize());

		tableDimension
				.setSize(_frame.getWidth() - 25, _frame.getHeight() - 200);
		jscoJScrollPane.setPreferredSize(tableDimension);
		jscoJScrollPane.setSize(jscoJScrollPane.getPreferredSize());

	}

	public void setVisible() {
		if (fillingExist) {
			_frame.setVisible(true);
			_frame.moveToFront();
		}
	}

	public void setJoborderTambah(JoborderTambah joborderTambah) {
		_joborderTambah = joborderTambah;
	}

	public void setCertificateTambah(CertificateTambah certificateTambah) {
		_certiCertificateTambah = certificateTambah;
	}

	public void showUpdateForm() {
		int countSelected = 0;
		List<Joborder> joborders = new ArrayList<Joborder>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 0)) {
				joborders.add((Joborder) table.getValueAt(i, kolom.length - 1));
				countSelected++;
			}
		}

		if (countSelected == 0) {
			JOptionPane.showMessageDialog(null,
					"Klik data yang akan dibuat Certificate", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (countSelected == 1) {
			_certiCertificateTambah.setVisible(joborders.get(0));
		} else {
			JOptionPane.showMessageDialog(null,
					"Klik 1 data yang akan dibuat Certificate", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public void hapus() {
		int countSelected = 0;
		List<Joborder> joborders = new ArrayList<Joborder>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 0)) {
				joborders.add((Joborder) table.getValueAt(i, kolom.length - 1));
				countSelected++;
			}
		}

		if (countSelected == 0) {
			JOptionPane.showMessageDialog(null, "Klik data yang akan di hapus",
					"Informasi", JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {

			if (JOptionPane.showConfirmDialog(null,
					"Apakah data ini ingin di hapus ?", "Informasi",
					JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
				_session = _service.getConnectionDB(_session);

				Criteria criteria = null;
				Set<Customer> customerCheck = new HashSet<Customer>();
				Set<User> userCheck = new HashSet<User>();
				Set<Filling> fillingCheck = new HashSet<Filling>();
				Set<Pegawai> pegawaiCheck = new HashSet<Pegawai>();
				Set<ValidasiRules> validasiRulesCheck = new HashSet<ValidasiRules>();
				for (Joborder joborder : joborders) {

					if (joborder.getCustomer() != null) {
						customerCheck.add(joborder.getCustomer());
					}
					if (joborder.getExportir() != null) {
						customerCheck.add(joborder.getExportir());
					}

					if (joborder.getCreateBy() != null) {
						userCheck.add(joborder.getCreateBy());
					}
					if (joborder.getUpdatedBy() != null) {
						userCheck.add(joborder.getUpdatedBy());
					}

					if (joborder.getFilling() != null) {
						fillingCheck.add(joborder.getFilling());
					}

					criteria = _session.createCriteria(JoborderPegawai.class);
					criteria.add(Restrictions.eq("joborder", joborder));
					List<JoborderPegawai> joborderPegawais = criteria.list();
					for (JoborderPegawai joborderPegawai : joborderPegawais) {
						pegawaiCheck.add(joborderPegawai.getPegawai());
						_session.delete(joborderPegawai);
						_session.flush();
					}

					criteria = _session.createCriteria(JoborderValidasi.class);
					criteria.add(Restrictions.eq("joborder", joborder));
					List<JoborderValidasi> joborderValidasis = criteria.list();
					for (JoborderValidasi joborderValidasi : joborderValidasis) {
						validasiRulesCheck.add(joborderValidasi
								.getValidasiRules());
						_session.delete(joborderValidasi);
						_session.flush();
					}

					criteria = _session
							.createCriteria(CertificateNewColumn.class);
					criteria.add(Restrictions.eq("joborder", joborder));
					List<CertificateNewColumn> certificateNewColumns = criteria
							.list();
					for (CertificateNewColumn certificateNewColumn : certificateNewColumns) {
						_session.delete(certificateNewColumn);
						_session.flush();
					}

					_session.delete(joborder);
					_session.flush();
				}

				for (User user : userCheck) {
					_service.setIsDeletedUser(_session, user);
					_session.flush();
				}

				for (Filling filling : fillingCheck) {
					_service.setIsDeletedFilling(_session, filling);
					_session.flush();
				}

				for (Customer customer : customerCheck) {
					_service.setIsDeletedCustomer(_session, customer);
					_session.flush();
				}
				for (Pegawai pegawai : pegawaiCheck) {
					_service.setIsDeletedPegawai(_session, pegawai);
					_session.flush();
				}
				for (ValidasiRules validasiRules : validasiRulesCheck) {
					_service.setIsDeletedValidasirules(_session, validasiRules);
					_session.flush();
				}

				_joborderTambah.refreshCustomer();
				_joborderTambah.refreshKodeIndex();
				_joborderTambah.refreshPegawai();
				_joborderTambah.refreshShipper();
				_joborderTambah.refreshValidasiRules();

				_shipperIndex.refreshTable();
				_customerIndex.refreshTable();
				_pegawaiIndex.refreshTable();
				_aturanIndex.refreshTable();
				_fillingIndex.refreshTable();
				_userIndex.refreshTable();
				refreshTable();
			}
		}
	}

	private void refreshKodeIndex() {
		_session = _service.getConnectionDB(_session);
		_session.clear();
		Criteria criteria = _session.createCriteria(Filling.class)
				.setProjection(Projections.rowCount());
		if ((long) criteria.uniqueResult() == 0) {
			_frame.setVisible(false);
			fillingExist = false;
			JOptionPane
					.showMessageDialog(
							null,
							"Job Order belum bisa dipakai karena Aturan penomoran dokumen belum diisi",
							"Informasi", JOptionPane.INFORMATION_MESSAGE);
			return;
		} else {
			fillingExist = true;
		}
	}

	public void setShipperIndex(ShipperIndex shipperIndex) {
		_shipperIndex = shipperIndex;
	}

	public void setCustomerIndex(CustomerIndex customerIndex) {
		_customerIndex = customerIndex;
	}

	public void setAturanIndex(AturanIndex aturanIndex) {
		_aturanIndex = aturanIndex;
	}

	public void setPegawaiIndex(PegawaiIndex pegawaiIndex) {
		_pegawaiIndex = pegawaiIndex;
	}

	public void setFillingIndex(FillingIndex fillingIndex) {
		_fillingIndex = fillingIndex;
	}

	public void setUserIndex(UserIndex userIndex) {
		_userIndex = userIndex;
	}
}

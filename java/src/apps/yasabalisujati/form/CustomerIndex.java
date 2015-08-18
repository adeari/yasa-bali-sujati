package apps.yasabalisujati.form;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Table;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.service.Service;

public class CustomerIndex extends JInternalFrame {
	private static final long serialVersionUID = 8967014638545080322L;

	private JPanel buttonPanel;
	private JPanel searchingPanel;
	private JPanel paginationPanel;
	private Dimension rowDimension;
	private JScrollPane jscoJScrollPane;
	private ComboBox searchingComboBox;
	private Textbox searchTextbox;
	private Button searchButton;
	private final String[] kolom = new String[] { "", "Nama Perusahaan", "Detail", "Jenis Customer" };
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
	
	private CustomerTambah _customerTambah;
	
	private String[] jenisCustomersNotShow = new String[] {"Exportir", "Importir" };

	public CustomerIndex(Session session, Service service, SimpleDateFormat simpleDateFormat) {
		super("Customer", true, true, true, true);
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(800, 600));
		_frame.setSize(_frame.getPreferredSize());
		_frame.setLocation(10, 10);
		_frame.setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/people.png")));
		_frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				reSizePanel();
			}
		});
		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;

		rowDimension = new Dimension();
		tableDimension = new Dimension();
		
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(""));

		Button baruButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/addpeople.png")), "Baru");
		baruButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				_customerTambah.setVisible(null);
			}
		});
		buttonPanel.add(baruButton);
		Button ubahButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/edit.png")), "Ubah");
		ubahButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showUpdateForm();
			}
		});
		buttonPanel.add(ubahButton);
		JLabel blank = new JLabel();
		blank.setPreferredSize(new Dimension(200, 10));
		buttonPanel.add(blank);
		Button hapusButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/delete.png")), "Hapus");
		hapusButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				int countSelected = 0;
				List<Customer> customers = new ArrayList<Customer>();
				for (int i = 0; i < table.getRowCount(); i++) {
					if ((boolean) table.getValueAt(i, 0)) {
						customers.add((Customer) table.getModel().getValueAt(i,
								kolom.length));
						countSelected++;
					}
				}

				if (countSelected == 0) {
					JOptionPane.showMessageDialog(null,
							"Klik data yang akan di hapus", "Informasi",
							JOptionPane.INFORMATION_MESSAGE);
					return;
				} else {
					if (JOptionPane.showConfirmDialog(null,
							"Apakah data ini ingin di hapus ?", "Informasi",
							JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
						_session = _service.getConnectionDB(_session);
						boolean isDeleted = false;
						for (Customer customer : customers) {
							if (customer.isDeleted()) {
								_session.delete(customer);
								isDeleted = true;
							}
						}
						if (isDeleted) {
							_session.flush();
						}
						refreshTable();
					}
				}
			}
		});
		buttonPanel.add(hapusButton);

		_frame.add(buttonPanel);

		searchingPanel = new JPanel();

		searchingPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		searchingPanel.setBorder(BorderFactory.createTitledBorder(""));

		searchingComboBox = new ComboBox(kolom);
		searchingComboBox.setPreferredSize(new Dimension(160, 30));
		searchingComboBox.removeItemAt(0);
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

		searchButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/search.png")), "Cari");
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
				} else {
					return String.class;
				}
			}
		};
		
		table = new Table(tableModel);
		
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.getColumnModel().getColumn(0).setPreferredWidth(30);
		table.getColumnModel().getColumn(1).setPreferredWidth(400);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		
		table.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2) {
					_customerTambah.setVisible((Customer) table.getModel().getValueAt(
							table.getSelectedRow(), kolom.length));
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
		refreshTable();
	}
	
	public Criteria setCriteriaCondition(Criteria criteria) {
		criteria.add(Restrictions.not(Restrictions.in("jenisCustomer", jenisCustomersNotShow)));
		String searchText = searchTextbox.getText();
		if (!searchText.isEmpty()) {
			if (searchingComboBox.getSelectedItem().equals(kolom[1])) {
				criteria.add(Restrictions.like("nama", searchText+"%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[2])) {
				criteria.add(Restrictions.like("detail", "%"+searchText+"%").ignoreCase());
			} else if (searchingComboBox.getSelectedItem().equals(kolom[3])) {
				criteria.add(Restrictions.like("jenisCustomer", searchText+"%").ignoreCase());
			}
		}
		return criteria;
	}
	
	public void refreshTable() {
		dataVector.clear();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		
		Criteria criteria = _session.createCriteria(Customer.class)
				.setProjection(Projections.rowCount());
		criteria = setCriteriaCondition(criteria);
		
		long countData = (long) criteria.uniqueResult();
		long pageNow = Integer.valueOf(pageNowField.getText()).intValue();
		long countRows = ((Long) rowSelectComboBox.getSelectedItem())
				.longValue();

		countPage = (int) countData / countRows;
		if ((countData % countRows) > 0)
			countPage++;
		countPageLabel.setText(countPage+" Halaman");
		
		
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
		
		criteria = _session.createCriteria(Customer.class);
		criteria = setCriteriaCondition(criteria);
		criteria.setFirstResult(startRow);
		criteria.setMaxResults(Long.valueOf(countRows).intValue());

		List<Customer> dataList = criteria.list();
		
		for (Customer customer : dataList) {
			Vector<Object> data1 = new Vector<Object>();
			data1.addElement(false);
			data1.addElement(customer.getNama());
			data1.addElement(customer.getDetail());
			data1.addElement(customer.getJenisCustomer());
			data1.addElement(customer);
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

		tableDimension.setSize(_frame.getWidth() - 25,
				_frame.getHeight() - 200);
		jscoJScrollPane.setPreferredSize(tableDimension);
		jscoJScrollPane.setSize(jscoJScrollPane.getPreferredSize());
		
	}
	public void setVisible() {
		_frame.setVisible(true);
	}
	
	public void setCustomerTambah(CustomerTambah customerTambah) {
		_customerTambah = customerTambah;
	}
	
	public void showUpdateForm() {
		int countSelected = 0;
		List<Customer> customers = new ArrayList<Customer>();
		for (int i = 0; i < table.getRowCount(); i++) {
			if ((boolean) table.getValueAt(i, 0)) {
				customers.add((Customer) table.getModel().getValueAt(i,
						kolom.length));
				countSelected++;
			}
		}
		
		if (countSelected == 0) {
			JOptionPane.showMessageDialog(null,
					"Klik data yang akan diubah", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
		} else if (countSelected == 1) {
			_customerTambah.setVisible(customers.get(0));
		} else {
			JOptionPane.showMessageDialog(null,
					"Klik 1 data yang akan diubah", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}

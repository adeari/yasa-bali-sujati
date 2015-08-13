package apps.yasabalisujati.form;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Vector;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Table;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.service.Service;

public class UserIndex extends JInternalFrame {
	private static final long serialVersionUID = 8967014638545080322L;

	private JPanel buttonPanel;
	private JPanel searchingPanel;
	private JPanel paginationPanel;
	private JScrollPane jscoJScrollPane;
	private ComboBox searchingComboBox;
	private Textbox searchTextbox;
	private Button searchButton;
	private final String[] kolom = new String[] { "", "Username", "Divisi", "Terakhir Login" };
	private TableModel tableModel;
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

	public UserIndex(Session session, Service service, SimpleDateFormat simpleDateFormat) {
		super("User Login", true, true, true, true);
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(800, 600));
		_frame.setSize(_frame.getPreferredSize());
		_frame.setLocation(10, 10);
		_frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				reSizePanel();
			}
		});
		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;

		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(""));

		Button baruButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/addpeople.png")), "Baru");
		buttonPanel.add(baruButton);
		Button ubahButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/edit.png")), "Ubah");
		buttonPanel.add(ubahButton);
		JLabel blank = new JLabel();
		blank.setPreferredSize(new Dimension(200, 10));
		buttonPanel.add(blank);
		Button hapusButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/delete.png")), "Hapus");
		buttonPanel.add(hapusButton);

		_frame.add(buttonPanel);

		searchingPanel = new JPanel();

		searchingPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		searchingPanel.setBorder(BorderFactory.createTitledBorder(""));

		searchingComboBox = new ComboBox(kolom);
		searchingComboBox.setPreferredSize(new Dimension(132, 30));
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
		table.getColumnModel().getColumn(1).setPreferredWidth(200);
		table.getColumnModel().getColumn(2).setPreferredWidth(200);
		table.getColumnModel().getColumn(3).setPreferredWidth(200);
		
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
		rowSelectComboBox.setFont(new Font("Tahoma", Font.BOLD, 15));
		((JLabel)rowSelectComboBox.getRenderer()).setHorizontalAlignment(JLabel.RIGHT);
		rowSelectComboBox.addItem(Long.valueOf(50));
		rowSelectComboBox.addItem(Long.valueOf(100));
		rowSelectComboBox.addItem(Long.valueOf(500));
		rowSelectComboBox.addItem(Long.valueOf(1000));
		paginationPanel.add(rowSelectComboBox);
		
		buttonFirst = new JButton(" << ");
		buttonFirst.setFont(new Font("Tahoma", Font.BOLD, 15));
		paginationPanel.add(buttonFirst);
		
		buttonPrevious = new JButton(" < ");
		buttonPrevious.setFont(new Font("Tahoma", Font.BOLD, 15));
		paginationPanel.add(buttonPrevious);
		
		pageNowField = new JTextField("1");
		pageNowField.setFont(new Font("Tahoma", Font.BOLD, 15));
		pageNowField.setPreferredSize(new Dimension(50, 30));
		pageNowField.setHorizontalAlignment(SwingConstants.RIGHT);
		pageNowField.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent arg0) {
				keyPressed(arg0);
			}
			
			@Override
			public void keyReleased(KeyEvent arg0) {
				keyPressed(arg0);
				
			}
			
			@Override
			public void keyPressed(KeyEvent arg0) {
				try {
					Long.valueOf(pageNowField.getText());
				} catch (Exception e) {
					pageNowField.setText("1");
				}
			}
		});
		paginationPanel.add(pageNowField);
		
		buttonNext = new JButton(" > ");
		buttonNext.setFont(new Font("Tahoma", Font.BOLD, 15));
		paginationPanel.add(buttonNext);
		
		buttonLast = new JButton(" >> ");
		buttonLast.setFont(new Font("Tahoma", Font.BOLD, 15));
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
		String searchText = searchTextbox.getText();
		if (!searchText.isEmpty()) {
			
		}
		return criteria;
	}
	
	public void refreshTable() {
		dataVector.clear();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		
		Criteria criteria = _session.createCriteria(User.class)
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
		
		criteria = _session.createCriteria(User.class);
		criteria = setCriteriaCondition(criteria);
		criteria.setFirstResult(startRow);
		criteria.setMaxResults(Long.valueOf(countRows).intValue());

		List<User> dataList = criteria.list();
		
		for (User user : dataList) {
			Vector<Object> data1 = new Vector<Object>();
			data1.addElement(false);
			data1.addElement(user.getName());
			data1.addElement(user.getDivisi());
			data1.addElement(_service.convertStringFromDate("dd/MM/yyyy HH:mm", user.getLastLogin(), _simpleDateFormat));
			data1.addElement(user);
			dataVector.add(data1);
		}
		table.tableChanged(new javax.swing.event.TableModelEvent(tableModel));
	}

	public void reSizePanel() {
		buttonPanel.setPreferredSize(new Dimension(
				_frame.getWidth() - 25, 40));
		buttonPanel.setSize(buttonPanel.getPreferredSize());
		
		searchingPanel.setPreferredSize(buttonPanel.getPreferredSize());
		paginationPanel.setPreferredSize(buttonPanel.getPreferredSize());
		
		jscoJScrollPane.setPreferredSize(new Dimension(
				_frame.getWidth() - 25, _frame.getHeight() - 200 ));
		jscoJScrollPane.setSize(jscoJScrollPane.getPreferredSize());
		
	}
}

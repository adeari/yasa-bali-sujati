package apps.yasabalisujati.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

import org.hibernate.Session;

import apps.yasabalisujati.service.Service;

public class MenuDesktop extends JFrame {
	private static final long serialVersionUID = 3315233609060097113L;
	
	private JFrame _frame;
	private JDesktopPane _desktopPane;
	
	private Login _login;
	
	private UserIndex userIndex;
	private UserTambah userTambah;
	
	private PegawaiIndex pegawaiIndex;
	private PegawaiTambah pegawaiTambah;
	
	private CustomerIndex customerIndex;
	private CustomerTambah customerTambah;
	
	private ShipperIndex shipperIndex;
	private ShipperTambah shipperTambah;
	
	
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	public MenuDesktop(Login login, Session session, Service service) {
		super("Yasa Bali Sujati");
		_frame = this;
		_frame.setIconImage(new ImageIcon(getClass().getClassLoader().getResource("icons/key.png")).getImage());
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_login = login;
		_session = session;
		_service = service;
		_simpleDateFormat = new SimpleDateFormat();
		
		_desktopPane = new JDesktopPane();
		_desktopPane.setBackground(Color.BLUE);
		_desktopPane.setOpaque(true);
		
		
		
		pegawaiIndex = new PegawaiIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(pegawaiIndex);
		pegawaiTambah = new PegawaiTambah(_session, _service, _simpleDateFormat);
		_desktopPane.add(pegawaiTambah);
		userIndex = new UserIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(userIndex);
		userTambah = new UserTambah(_session, _service, _simpleDateFormat);
		_desktopPane.add(userTambah);
		customerIndex = new CustomerIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(customerIndex);
		customerTambah = new CustomerTambah(_session, _service, _simpleDateFormat);
		_desktopPane.add(customerTambah);
		shipperIndex = new ShipperIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(shipperIndex);
		shipperTambah = new ShipperTambah(_session, _service, _simpleDateFormat);
		_desktopPane.add(shipperTambah);
		
		
		userIndex.setUserTambah(userTambah);
		userTambah.setUserIndex(userIndex);
		pegawaiIndex.setPegawaiTambah(pegawaiTambah);
		pegawaiTambah.setPegawaiIndex(pegawaiIndex);
		customerIndex.setCustomerTambah(customerTambah);
		customerTambah.setCustomerIndex(customerIndex);
		customerTambah.setShipperIndex(shipperIndex);
		shipperIndex.setShipperTambah(shipperTambah);
		shipperTambah.setShipperIndex(shipperIndex);
		
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Data Master");
		menu.setFont(new Font(null, Font.BOLD, 15));
		menu.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/star.png")));
		menuBar.add(menu);
		
		JMenuItem usersMenuItem = new JMenuItem("Users");
		usersMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/people.png")));
		usersMenuItem.setFont(new Font(null, Font.BOLD, 14));
		usersMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userIndex.refreshTable();
				userIndex.setVisible();
			}
		});
		menu.add(usersMenuItem);
		
		JMenuItem pegawaiMenuItem = new JMenuItem("Pegawai");
		pegawaiMenuItem.setFont(usersMenuItem.getFont());
		pegawaiMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/people.png")));
		pegawaiMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pegawaiIndex.refreshTable();
				pegawaiIndex.setVisible();
			}
		});
		menu.add(pegawaiMenuItem);
		
		JMenuItem customerMenuItem = new JMenuItem("Customer");
		customerMenuItem.setFont(usersMenuItem.getFont());
		customerMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/people.png")));
		customerMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				customerIndex.refreshTable();
				customerIndex.setVisible();
			}
		});
		menu.add(customerMenuItem);
		
		JMenuItem shipperMenuItem = new JMenuItem("Shipper");
		shipperMenuItem.setFont(usersMenuItem.getFont());
		shipperMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/people.png")));
		shipperMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				shipperIndex.refreshTable();
				shipperIndex.setVisible();
			}
		});
		menu.add(shipperMenuItem);
		
		menu.add(new JSeparator());
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
		logoutMenuItem.setFont(usersMenuItem.getFont());
		logoutMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/key.png")));
		logoutMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				_login.refreshDivisiUser();
				_login.setVisible(true);
				_frame.setVisible(false);
			}
		});
		menu.add(logoutMenuItem);
		JMenuItem tutupMenuItem = new JMenuItem("Tutup");
		tutupMenuItem.setFont(usersMenuItem.getFont());
		tutupMenuItem.setIcon(new ImageIcon(getClass().getClassLoader().getResource("icons/logout.png")));
		tutupMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		menu.add(tutupMenuItem);
		
		
		_frame.getContentPane().add(_desktopPane, BorderLayout.CENTER);
		_frame.setResizable(true);
		_frame.pack();
	}
	
	public void closeEvent() {
		System.exit(0);
	}
}

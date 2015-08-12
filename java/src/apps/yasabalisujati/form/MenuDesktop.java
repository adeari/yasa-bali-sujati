package apps.yasabalisujati.form;

import java.awt.BorderLayout;
import java.awt.Color;
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
	private PegawaiIndex pegawaiIndex;
	
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	public MenuDesktop(Login login, Session session, Service service) {
		super("Yasa Bali Sujati");
		_frame = this;
		_frame.setIconImage(new ImageIcon(Login.class.getResource("../icons/key.png")).getImage());
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_login = login;
		_session = session;
		_service = service;
		_simpleDateFormat = new SimpleDateFormat();
		
		_desktopPane = new JDesktopPane();
		_desktopPane.setBackground(Color.BLUE);
		_desktopPane.setOpaque(true);
		
		
		userIndex = new UserIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(userIndex);
		pegawaiIndex = new PegawaiIndex(_session, _service, _simpleDateFormat);
		_desktopPane.add(pegawaiIndex);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Master");
		menuBar.add(menu);
		
		JMenuItem usersMenuItem = new JMenuItem("Users");
		usersMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userIndex.refreshTable();
				userIndex.setVisible(true);
			}
		});
		menu.add(usersMenuItem);
		
		JMenuItem pegawaiMenuItem = new JMenuItem("Pegawai");
		pegawaiMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				pegawaiIndex.refreshTable();
				pegawaiIndex.setVisible(true);
			}
		});
		menu.add(pegawaiMenuItem);
		
		menu.add(new JSeparator());
		JMenuItem logoutMenuItem = new JMenuItem("Logout");
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

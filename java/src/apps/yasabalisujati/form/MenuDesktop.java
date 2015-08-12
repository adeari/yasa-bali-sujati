package apps.yasabalisujati.form;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyVetoException;

import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JSeparator;

public class MenuDesktop extends JFrame {
	private static final long serialVersionUID = 3315233609060097113L;
	
	private JFrame _frame;
	private JDesktopPane _desktopPane;
	
	private Login _login;
	
	private UserIndex userIndex;
	
	public MenuDesktop(Login login) {
		super("Yasa Bali Sujati");
		_frame = this;
		_frame.setIconImage(new ImageIcon(Login.class.getResource("../icons/key.png")).getImage());
		_frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
		
		_frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		_login = login;
		
		_desktopPane = new JDesktopPane();
		_desktopPane.setBackground(Color.BLUE);
		_desktopPane.setOpaque(true);
		
		
		userIndex = new UserIndex();
		_desktopPane.add(userIndex);
		
		
		
		JMenuBar menuBar = new JMenuBar();
		_frame.setJMenuBar(menuBar);
		JMenu menu = new JMenu("Master");
		menuBar.add(menu);
		
		JMenuItem usersMenuItem = new JMenuItem("Users");
		usersMenuItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				userIndex.setVisible(true);
			}
		});
		menu.add(usersMenuItem);
		
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

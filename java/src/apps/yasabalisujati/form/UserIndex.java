package apps.yasabalisujati.form;

import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import apps.yasabalisujati.components.Button;

public class UserIndex extends JInternalFrame {
	private static final long serialVersionUID = 8967014638545080322L;
	private JPanel buttonPanel;
	
	private JInternalFrame _frame;
	public UserIndex() {
		super("User Login", true, true, true, true);
		_frame = this;
		_frame.setPreferredSize(new Dimension(800, 600));
		_frame.setLocation(10, 10);
		
		
		
		buttonPanel = new JPanel();
		buttonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		buttonPanel.setBorder(BorderFactory.createTitledBorder(""));
		
		Button baruButton = new Button(new ImageIcon(Login.class.getResource("../icons/addpeople.png")), "Baru");
		buttonPanel.add(baruButton);
		Button ubahButton = new Button(new ImageIcon(Login.class.getResource("../icons/edit.png")), "Ubah");
		buttonPanel.add(ubahButton);
		JLabel blank = new JLabel();
		blank.setPreferredSize(new Dimension(200,10));
		buttonPanel.add(blank);
		Button hapusButton = new Button(new ImageIcon(Login.class.getResource("../icons/delete.png")), "Hapus");
		buttonPanel.add(hapusButton);
		
		
		
		
		_frame.add(buttonPanel);
		_frame.pack();
		
		reSizePanel();
	}
	
	public void reSizePanel() {
		buttonPanel.setPreferredSize(new Dimension(_frame.getWidth() - 10, 40));
	}
}

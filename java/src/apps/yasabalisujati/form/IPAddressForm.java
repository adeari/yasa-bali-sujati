package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;

public class IPAddressForm extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	
	private Textbox ipAddressTextbox;
	

	public IPAddressForm() {
		super("IP Database", false, true, false, true); 
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(300, 130));
		_frame.setLocation(10, 10);
		_frame.setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/bullet_connect.png")));
		
		Container container = _frame.getContentPane();
		
		Dimension labelDimension = new Dimension(100, 30);
		Dimension textDimension = new Dimension(150, 30);
		
		Label usernameLabel = new Label("IP Database");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		ipAddressTextbox = new Textbox("");
		ipAddressTextbox.setPreferredSize(textDimension);
		container.add(ipAddressTextbox);
		
		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 25, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		container.add(buttonSavePanel);
		
		
		Button closeButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/cancel.png")), "TUTUP");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		buttonSavePanel.add(closeButton);
		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				closeEvent();
		    }
		}; 
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		_frame.pack();
	}
	
	
	public void setVisible() {
		_frame.setVisible(true);
		/* getAll ip address */
		Enumeration e;
		try {
			e = NetworkInterface.getNetworkInterfaces();
			while(e.hasMoreElements())
			{
			    NetworkInterface n = (NetworkInterface) e.nextElement();
			    Enumeration ee = n.getInetAddresses();
			    while (ee.hasMoreElements())
			    {
			        InetAddress i = (InetAddress) ee.nextElement();
			        System.out.println("IP Address "+i.getHostAddress());
			    }
			}
		} catch (SocketException ex) {
			ex.printStackTrace();
		}
		
		InetAddress iAddress;
		try {
			iAddress = InetAddress.getLocalHost();
			ipAddressTextbox.setText(iAddress.getHostAddress());
		} catch (UnknownHostException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		_frame.moveToFront();
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
}

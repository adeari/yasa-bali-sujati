package apps.yasabalisujati.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class Passwordbox extends JPasswordField {
	private static final long serialVersionUID = -619876366958714659L;
	public Passwordbox() {
		super("");
		this.setEchoChar(("*").charAt(0));
		this.setFont(new Font(null, Font.PLAIN, 14));
		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				setBackground(Color.YELLOW);
			}

			public void focusLost(FocusEvent e) {
				setBackground(new JTextField().getBackground());
			}
		});
	}
}

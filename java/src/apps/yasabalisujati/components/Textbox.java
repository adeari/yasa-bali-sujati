package apps.yasabalisujati.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextField;
import javax.swing.border.Border;

public class Textbox extends JTextField {
	private static final long serialVersionUID = -2686295811222694572L;

	public Textbox(String title) {
		super(title);
		this.setFont(new Font("Tahoma", Font.PLAIN, 14));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.addFocusListener(new FocusListener() {

			public void focusGained(FocusEvent e) {
				setBackground(Color.YELLOW);
			}

			public void focusLost(FocusEvent e) {
				setBackground(new JTextField().getBackground());
			}
		});
	}
	
	public Border getBorderCustom() {
		return this.getBorder();
	}
}
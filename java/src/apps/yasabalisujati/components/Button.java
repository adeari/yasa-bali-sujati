package apps.yasabalisujati.components;

import java.awt.Cursor;
import java.awt.Font;

import javax.swing.Icon;
import javax.swing.JButton;

public class Button extends JButton {
	private static final long serialVersionUID = 7112911658618908010L;
	public Button(Icon icon, String title) {
		super(title);
		this.setIcon(icon);
		this.setCursor(new Cursor(Cursor.HAND_CURSOR));
		this.setFont(new Font(null, Font.BOLD, 15));
	}
}

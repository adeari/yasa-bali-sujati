package apps.yasabalisujati.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

import javax.swing.JTextArea;
import javax.swing.JTextField;

public class TextboxArea extends JTextArea {
	private static final long serialVersionUID = -2686295811222694572L;

	public TextboxArea(String title) {
		super(title);
		this.setFont(new Font(null, Font.PLAIN, 14));
		this.setBackground(Color.WHITE);
		this.setOpaque(true);
		this.setBorder(new JTextField().getBorder());
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

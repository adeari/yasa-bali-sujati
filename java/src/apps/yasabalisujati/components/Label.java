package apps.yasabalisujati.components;

import java.awt.Font;

import javax.swing.JLabel;

public class Label extends JLabel {
	private static final long serialVersionUID = 3183570784117983799L;
	public Label(String title) {
		super(title);
		this.setFont(new Font(null, Font.PLAIN, 14));
	}
}

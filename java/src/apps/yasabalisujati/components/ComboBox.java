package apps.yasabalisujati.components;

import java.awt.Font;

import javax.swing.JComboBox;

public class ComboBox extends JComboBox<String> {
	private static final long serialVersionUID = -4051934578754209806L;
	public ComboBox(String[] values) {
		super(values);
		this.setFont(new Font(null, Font.PLAIN, 15));
	}
}

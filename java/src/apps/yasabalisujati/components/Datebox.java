package apps.yasabalisujati.components;

import java.awt.Font;

import com.toedter.calendar.JDateChooser;

public class Datebox extends JDateChooser {
	private static final long serialVersionUID = -4558304132537050008L;
	public Datebox(String formatDate, String chain, char placeholder) {
		super(formatDate, chain, placeholder);
		this.setFont(new Font(null, Font.PLAIN, 15));
	}
}

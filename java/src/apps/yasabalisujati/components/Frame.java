package apps.yasabalisujati.components;

import javax.swing.JFrame;

public class Frame extends JFrame {
	private static final long serialVersionUID = -3350535220824959470L;

	public Frame(String title) {
		super(title);
		this.setDefaultCloseOperation(HIDE_ON_CLOSE);
		this.setResizable(false);
	}
}

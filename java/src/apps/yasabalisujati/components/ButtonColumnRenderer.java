package apps.yasabalisujati.components;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

public class ButtonColumnRenderer extends Button implements TableCellRenderer {
	private static final long serialVersionUID = -3586826112251958433L;
	public ButtonColumnRenderer(Icon icon, String title) {
		super(icon, title);
	}
	@Override
	public Component getTableCellRendererComponent(final JTable table, Object value,
			boolean arg2, boolean arg3, final int row, int arg5) {
		return this;
	}

}

package apps.yasabalisujati.components.table;

import java.awt.Font;

import javax.swing.JTable;
import javax.swing.table.TableModel;

import apps.yasabalisujati.components.BoardTableCellRenderer;

public class Table extends JTable {
	private static final long serialVersionUID = -4201714727054196144L;
	public Table(TableModel tableModel) {
		super(tableModel);
		this.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		this.setFont(new Font(null, Font.PLAIN, 14));
		this.getTableHeader().setFont(new Font(null, Font.BOLD, 15));
		this.setDefaultRenderer(String.class, new BoardTableCellRenderer());
		this.setRowHeight(26);
	}
}

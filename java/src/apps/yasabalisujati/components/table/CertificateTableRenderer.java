package apps.yasabalisujati.components.table;

import java.awt.Component;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.border.EmptyBorder;
import javax.swing.table.TableCellRenderer;

public class CertificateTableRenderer extends JLabel implements TableCellRenderer {
	private static final long serialVersionUID = 1L;

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		setFont(new Font(null, Font.PLAIN, 14));
		setBorder(new EmptyBorder(3, 3, 3, 3));
		setText(table.getValueAt(row, column).toString());
		
		String status = table.getValueAt(row, 15).toString();
		
		if (status == null) {
			setIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/empty_star.png")));
		} else if (status.isEmpty()) {
				setIcon(new ImageIcon(getClass().getClassLoader()
						.getResource("icons/empty_star.png")));
		} else if (status.equalsIgnoreCase("Sebagian")) {
			setIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/star_half_grey.png")));
		} else if (status.equalsIgnoreCase("Selesai")) {
			setIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/star.png")));
		}
		
		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
		setOpaque(true);
		return this;
	}

}

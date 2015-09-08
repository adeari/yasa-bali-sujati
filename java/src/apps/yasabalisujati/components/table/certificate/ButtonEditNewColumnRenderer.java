package apps.yasabalisujati.components.table.certificate;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.database.entity.CertificateNewColumn;
import apps.yasabalisujati.form.CertificateTambah;

public class ButtonEditNewColumnRenderer  extends DefaultCellEditor {
	private static final long serialVersionUID = 6951562338233919355L;
	protected Button button;
    private String label;
    private CertificateTambah _certificateTambah;

    public ButtonEditNewColumnRenderer(JCheckBox checkBox,CertificateTambah certificateTambah) {
    	super(checkBox);
        button = new Button( new ImageIcon(getClass()
				.getClassLoader().getResource("icons/edit.png")) , "");
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                _certificateTambah.requestFocus();
            }
        });
        _certificateTambah = certificateTambah;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
    	_certificateTambah.editNewColumn((CertificateNewColumn) table.getValueAt(row, 4));
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return label;
    }

    @Override
    public boolean stopCellEditing() {
        return true;
    }

    @Override
    protected void fireEditingStopped() {
        super.fireEditingStopped();
    }
}
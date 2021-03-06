package apps.yasabalisujati.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultCellEditor;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JTable;

import apps.yasabalisujati.form.JoborderTambah;

public class ButtonEditorPegawaiSelected  extends DefaultCellEditor {
	private static final long serialVersionUID = 6951562338233919355L;
	protected Button button;
    private String label;
    private JoborderTambah _joborderTambah;

    public ButtonEditorPegawaiSelected(JCheckBox checkBox, Icon icon, String title, JoborderTambah joborderTambah) {
        super(checkBox);
        button = new Button(icon, title);
        button.setOpaque(true);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fireEditingStopped();
                _joborderTambah.requestFocus();
            }
        });
        _joborderTambah = joborderTambah;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value,
            boolean isSelected, int row, int column) {
    	_joborderTambah.removePetugasTableAtRow(row);
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

package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.database.entity.ValidasiRules;
import apps.yasabalisujati.service.Service;

public class AturanTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox urutanTextbox;
	private Textbox aturanTextbox;
	
	private AturanIndex _aturanIndex;
	private ValidasiRules _validasiRule;
	

	public AturanTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", false, true, false, true); 
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(550, 250));
		_frame.setSize(_frame.getPreferredSize());
		_frame.setLocation(10, 10);
		_frame.setDefaultCloseOperation(
                WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/people.png")));
		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;
		
		Container container = _frame.getContentPane();
		
		Dimension labelDimension = new Dimension(150, 30);
		Dimension textDimension = new Dimension(370, 30);
		
		Label usernameLabel = new Label("Urutan");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		urutanTextbox = new Textbox("");
		urutanTextbox.setHorizontalAlignment(SwingConstants.RIGHT);
		urutanTextbox.setPreferredSize(textDimension);
		container.add(urutanTextbox);
		
		Label aturanLabel = new Label("Aturan");
		aturanLabel.setPreferredSize(labelDimension);
		container.add(aturanLabel);
		aturanTextbox = new Textbox("");
		aturanTextbox.setPreferredSize(textDimension);
		container.add(aturanTextbox);
		
		
		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 25, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		container.add(buttonSavePanel);
		
		Button saveButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/save.png")), "(Ctrl+S)  SIMPAN");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSavePanel.add(saveButton);
		
		KeyStroke newKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_S), InputEvent.CTRL_MASK, false);
		Action newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
		    public void actionPerformed(ActionEvent e) {
		    	save();
		    }
		}; 
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(newKeyStroke, "SAVED");
		_frame.getRootPane().getActionMap().put("SAVED", newAction);
		
		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				closeEvent();
		    }
		}; 
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);
		
		
		Button closeButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/cancel.png")), "TUTUP");
		closeButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		buttonSavePanel.add(closeButton);
		
		_frame.pack();
	}
	
	public void setVisible(ValidasiRules validasiRules) {
		clearForm();
		_validasiRule = validasiRules;
		if (_validasiRule == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/add.png")));
			_frame.setTitle("Tambah Aturan");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah Aturan");
			urutanTextbox.setText(""+validasiRules.getUrutan());
			aturanTextbox.setText(validasiRules.getAturan());
		}
		_frame.setVisible(true);
	}
	
	public void clearForm() {
		urutanTextbox.setText("");
		aturanTextbox.setText("");
		aturanTextbox.requestFocus();
	}
	
	
	public void save() {
		if (urutanTextbox.getText().isEmpty()) {
			urutanTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Urutan aturan", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (aturanTextbox.getText().isEmpty()) {
			aturanTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Nama Aturan", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		
		if (_validasiRule == null) {
			_session = _service.getConnectionDB(_session);
			Criteria citeria = _session.createCriteria(ValidasiRules.class).setProjection(Projections.rowCount());
			citeria.add(Restrictions.eq("aturan", aturanTextbox.getText()));
			if ((long) citeria.uniqueResult() > 0) {
				urutanTextbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Aturan "+urutanTextbox.getText()+" sudah terdaftar", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		ValidasiRules validasiRule = new ValidasiRules();
		if (_validasiRule != null) {
			validasiRule = _validasiRule;
		} else {
			validasiRule.setCreatedAt(nowSqlDate);
		}
		validasiRule.setUpdatedAt(nowSqlDate);
		
		validasiRule.setAturan(aturanTextbox.getText());
		validasiRule.setUrutan(Integer.valueOf(urutanTextbox.getText()));
		if (_validasiRule == null) {
			validasiRule.setDeleted(true);
			_session.save(validasiRule);
		} else {
			_session.update(validasiRule);
			_session.flush();
		}
		
		_aturanIndex.refreshTable();
		if (_validasiRule == null) {
			clearForm();
		} else {
			closeEvent();
		}
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setAturanIndex(AturanIndex aturanIndex) {
		_aturanIndex = aturanIndex;
	}
}

package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
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

import sun.net.www.http.Hurryable;
import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.database.entity.Filling;
import apps.yasabalisujati.database.entity.ValidasiRules;
import apps.yasabalisujati.service.Service;

public class FillingTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox warnaTextbox;
	private Textbox kodeAwalTextbox;
	private Textbox digitTextbox;
	
	private FillingIndex _fillingIndex;
	private Filling _filling;
	
	private JoborderTambah _joborderTambah;

	public FillingTambah(Session session, Service service,
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
		
		Label usernameLabel = new Label("Warna");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		warnaTextbox = new Textbox("");
		warnaTextbox.setPreferredSize(textDimension);
		container.add(warnaTextbox);
		
		Label aturanLabel = new Label("Kode Awal");
		aturanLabel.setPreferredSize(labelDimension);
		container.add(aturanLabel);
		kodeAwalTextbox = new Textbox("");
		kodeAwalTextbox.setPreferredSize(textDimension);
		container.add(kodeAwalTextbox);
		
		Label digitLabel = new Label("Digit");
		digitLabel.setPreferredSize(labelDimension);
		container.add(digitLabel);
		digitTextbox = new Textbox("1");
		digitTextbox.setPreferredSize(textDimension);
		digitTextbox.setHorizontalAlignment(SwingConstants.RIGHT);
		digitTextbox.addFocusListener(new FocusListener() {
			
			@Override
			public void focusLost(FocusEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void focusGained(FocusEvent arg0) {
				digitTextbox.selectAll();
			}
		});
		container.add(digitTextbox);
		
		
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
	
	public void setVisible(Filling filling) {
		clearForm();
		_filling = filling;
		if (_filling == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/add.png")));
			_frame.setTitle("Tambah Aturan Penomoran Dokumen");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah Aturan Penomoran Dokumen");
			warnaTextbox.setText(""+filling.getWarna());
			kodeAwalTextbox.setText(filling.getHuruf());
			digitTextbox.setText(String.valueOf(filling.getDigit()));
		}
		_frame.setVisible(true);
		_frame.moveToFront();
	}
	
	public void clearForm() {
		warnaTextbox.setText("");
		warnaTextbox.requestFocus();
		kodeAwalTextbox.setText("");
		digitTextbox.setText("0");
	}
	
	
	public void save() {
		if (warnaTextbox.getText().isEmpty()) {
			warnaTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Urutan aturan", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		if (kodeAwalTextbox.getText().isEmpty()) {
			kodeAwalTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Nama Aturan", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		try {
			Integer.valueOf(digitTextbox.getText());
		} catch (NumberFormatException e) {
			digitTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi Digit dengan angka", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		_session = _service.getConnectionDB(_session);
		Criteria citeria = _session.createCriteria(Filling.class).setProjection(Projections.rowCount());
		citeria.add(Restrictions.eq("warna", warnaTextbox.getText()));
		if (_filling != null) {
			citeria.add(Restrictions.ne("id", _filling.getId()));
		}
		if ((long) citeria.uniqueResult() > 0) {
			warnaTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Warna "+warnaTextbox.getText()+" sudah terdaftar", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		Filling filling = new Filling();
		if (_filling != null) {
			filling = _filling;
		} else {
			filling.setCreatedAt(nowSqlDate);
		}
		filling.setUpdatedAt(nowSqlDate);
		
		filling.setWarna(warnaTextbox.getText());
		filling.setDigit(Integer.valueOf(digitTextbox.getText()));
		filling.setHuruf(kodeAwalTextbox.getText());
		if (_filling == null) {
			filling.setDeleted(true);
			_session.save(filling);
		} else {
			_session.update(filling);
			_session.flush();
		}
		
		_fillingIndex.refreshTable();
		_joborderTambah.refreshValidasiRules();
		if (_filling == null) {
			clearForm();
		} else {
			closeEvent();
		}
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setFillingIndex(FillingIndex fillingIndex) {
		_fillingIndex = fillingIndex;
	}
	
	public void setJoborderTambah(JoborderTambah joborderTambah) {
		_joborderTambah = joborderTambah;
	}
}

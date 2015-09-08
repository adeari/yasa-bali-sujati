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
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.WindowConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.database.entity.CertificateNewColumn;
import apps.yasabalisujati.database.entity.Joborder;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.service.Service;

public class CertificateNewTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox dataTextbox;
	private TextboxArea detailTextbox;
	
	private CertificateNewColumn _certificateNewColumn;
	private CertificateTambah _certificateTambah;
	private Joborder _joborder;
	

	public CertificateNewTambah(Session session, Service service,
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
		
		Dimension labelDimension = new Dimension(100, 30);
		Dimension blankLabelDimension = new Dimension(250, 30);
		
		Label dataLabel = new Label("Data");
		dataLabel.setPreferredSize(labelDimension);
		container.add(dataLabel);
		dataTextbox = new Textbox("");
		dataTextbox.setPreferredSize(new Dimension(150, 30));
		container.add(dataTextbox);
		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		
		Label detailLabel = new Label("Detail");
		detailLabel.setPreferredSize(labelDimension);
		container.add(detailLabel);
		detailTextbox = new TextboxArea("");
		container.add(dataTextbox);
		JScrollPane detailScrollPane  = new JScrollPane(detailTextbox);
		detailScrollPane.setPreferredSize(new Dimension(150, 200));
		detailScrollPane.setBorder(new Textbox(null).getBorderCustom());
		container.add(detailScrollPane);
		
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		
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
		
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(70, 30));
		buttonSavePanel.add(blankLabel);
		
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
	
	public void setVisible(Joborder joborder, CertificateNewColumn certificateNewColumn) {
		clearForm();
		
		_joborder = joborder;
		_certificateNewColumn = certificateNewColumn;
		if (_certificateNewColumn == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/add.png")));
			_frame.setTitle("Tambah Data");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah Data");
			dataTextbox.setText(_certificateNewColumn.getColumnName());
			detailTextbox.setText(_certificateNewColumn.getDescription());
		}
		_frame.setVisible(true);
		_frame.moveToFront();
	}
	
	public void clearForm() {
		dataTextbox.setText("");
		detailTextbox.setText("");
	}
	
	
	public void save() {
		_session = _service.getConnectionDB(_session);
		_session.clear();
		
		CertificateNewColumn certificateNewColumn = null;
		if (_certificateNewColumn == null) {
			certificateNewColumn = new CertificateNewColumn();
			certificateNewColumn.setJoborder(_joborder);
		} else {
			certificateNewColumn = _certificateNewColumn;
		}
		
		certificateNewColumn.setColumnName(dataTextbox.getText());
		certificateNewColumn.setDescription(detailTextbox.getText());
		
		if (_certificateNewColumn == null) {
			_session.save(certificateNewColumn);
			clearForm();
		} else {
			_session.update(certificateNewColumn);
			_session.flush();
			closeEvent();
		}
		_certificateTambah.refreshColumnNew();
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setCertificateTambah(CertificateTambah certificateTambah) {
		_certificateTambah = certificateTambah;
	}
}

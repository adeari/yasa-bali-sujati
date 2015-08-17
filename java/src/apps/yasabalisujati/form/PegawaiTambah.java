package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.database.entity.Pegawai;
import apps.yasabalisujati.service.Service;

public class PegawaiTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;
	
	private Textbox namaTextbox;
	private Textbox divisiTextbox;
	private TextboxArea detailTextboxArea;
	private ComboBox divisiComboBox;
	
	private PegawaiIndex _pegawaiIndex;
	private Pegawai _pegawai;
	

	public PegawaiTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", false, true, false, true); 
		_frame = this;
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		_frame.setPreferredSize(new Dimension(550, 300));
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
		Dimension textDimension = new Dimension(150, 30);
		Dimension blankLabelDimension = new Dimension(250, 30);
		
		Label usernameLabel = new Label("Nama");
		usernameLabel.setPreferredSize(labelDimension);
		container.add(usernameLabel);
		namaTextbox = new Textbox("");
		namaTextbox.setPreferredSize(textDimension);
		container.add(namaTextbox);
		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(blankLabelDimension);
		container.add(blankLabel);
		
		Label detailLabel = new Label("Detail");
		detailLabel.setPreferredSize(new Dimension(100, 90));
		container.add(detailLabel);
		detailTextboxArea = new TextboxArea("");
		JScrollPane alamatScrollPane  = new JScrollPane(detailTextboxArea );
		alamatScrollPane.setPreferredSize(new Dimension(400, 90));
		alamatScrollPane.setBorder(new Textbox(null).getBorderCustom());
		container.add(alamatScrollPane);
		
		Label divisiLabel = new Label("Divisi");
		divisiLabel.setPreferredSize(labelDimension);
		container.add(divisiLabel);
		divisiComboBox = new ComboBox(new String[] { "a" });
		divisiComboBox.setPreferredSize(textDimension);
		container.add(divisiComboBox);
		Label divisilainLabel = new Label("  Selain itu");
		divisilainLabel.setPreferredSize(labelDimension);
		container.add(divisilainLabel);
		divisiTextbox = new Textbox("");
		divisiTextbox.setPreferredSize(textDimension);
		container.add(divisiTextbox);
		
		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 25, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		container.add(buttonSavePanel);
		
		Button saveButton = new Button(new ImageIcon(
				getClass().getClassLoader().getResource("icons/save.png")), "SIMPAN");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSavePanel.add(saveButton);
		
		blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(100, 30));
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
	
	public void setVisible(Pegawai pegawai) {
		clearForm();
		_pegawai = pegawai;
		refreshDivisiPegawai();
		if (_pegawai == null) {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/addpeople.png")));
			_frame.setTitle("Tambah pegawai");
		} else {
			_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
					.getResource("icons/edit.png")));
			_frame.setTitle("Ubah pegawai");
			namaTextbox.setText(_pegawai.getNama());
			detailTextboxArea.setText(_pegawai.getDetail());
			divisiComboBox.setSelectedItem(_pegawai.getDivisi());
		}
		
		
		_frame.setVisible(true);
	}
	
	public void clearForm() {
		namaTextbox.setText("");
		detailTextboxArea.setText("");
		divisiTextbox.setText("");
	}
	
	public void refreshDivisiPegawai() {
		String divisiSelected = divisiComboBox.getSelectedItem().toString();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		divisiComboBox.removeAllItems();

		Criteria criteria = _session.createCriteria(Pegawai.class).setProjection(
				Projections.groupProperty("divisi"));
		List<String> divisis = criteria.list();
		for (String divisi : divisis) {
			divisiComboBox.addItem(divisi);
		}
		divisiComboBox.setSelectedItem(divisiSelected);
	}
	
	public void save() {
		if (namaTextbox.getText().isEmpty()) {
			namaTextbox.requestFocus();
			JOptionPane.showMessageDialog(null, "Isi nama pegawai", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			return;
		}
		
		
		if (_pegawai == null) {
			_session = _service.getConnectionDB(_session);
			Criteria citeria = _session.createCriteria(Pegawai.class).setProjection(Projections.rowCount());
			citeria.add(Restrictions.eq("nama", namaTextbox.getText()));
			if ((long) citeria.uniqueResult() > 0) {
				namaTextbox.requestFocus();
				JOptionPane.showMessageDialog(null, "Nama pegawai "+namaTextbox.getText()+" sudah terdaftar", "Informasi",
						JOptionPane.INFORMATION_MESSAGE);
				return;
			}
		}
		
		String divisi = divisiComboBox.getSelectedItem().toString();
		if (!divisiTextbox.getText().isEmpty()) {
			divisi = divisiTextbox.getText();
		}
		
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(nowDate.getTime());
		
		Pegawai pegawai = new Pegawai();
		if (_pegawai != null) {
			pegawai = _pegawai;
		} else {
			pegawai.setCreatedAt(nowSqlDate);
		}
		pegawai.setUpdatedAt(nowSqlDate);
		
		pegawai.setDivisi(divisi);
		pegawai.setNama(namaTextbox.getText());
		pegawai.setDetail(detailTextboxArea.getText());
		
		if (_pegawai == null) {
			pegawai.setDeleted(true);
			_session.save(pegawai);
		} else {
			_session.update(pegawai);
			_session.flush();
		}
		
		refreshDivisiPegawai();
		_pegawaiIndex.refreshTable();
		if (_pegawai == null) {
			clearForm();
		} else {
			closeEvent();
		}
	}
	
	public void closeEvent() {
		_frame.setVisible(false);
	}
	
	public void setPegawaiIndex(PegawaiIndex pegawaiIndex) {
		_pegawaiIndex = pegawaiIndex;
	}
}

package apps.yasabalisujati.form;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.InputEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigInteger;
import java.net.MalformedURLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Vector;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.KeyStroke;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.Borders;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.UnderlinePatterns;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBody;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageMar;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPageSz;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;

import apps.yasabalisujati.components.Button;
import apps.yasabalisujati.components.ButtonColumnRenderer;
import apps.yasabalisujati.components.ComboBox;
import apps.yasabalisujati.components.Datebox;
import apps.yasabalisujati.components.Label;
import apps.yasabalisujati.components.Textbox;
import apps.yasabalisujati.components.TextboxArea;
import apps.yasabalisujati.components.itext.CustomDashedLineSeparator;
import apps.yasabalisujati.components.table.Table;
import apps.yasabalisujati.components.table.certificate.ButtonDeleteNewColumnRenderer;
import apps.yasabalisujati.components.table.certificate.ButtonEditNewColumnRenderer;
import apps.yasabalisujati.database.entity.CertificateNewColumn;
import apps.yasabalisujati.database.entity.Customer;
import apps.yasabalisujati.database.entity.Joborder;
import apps.yasabalisujati.database.entity.User;
import apps.yasabalisujati.service.Service;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class CertificateTambah extends JInternalFrame {
	private static final long serialVersionUID = -113509224699880126L;
	private JInternalFrame _frame;
	private Session _session;
	private Service _service;
	private SimpleDateFormat _simpleDateFormat;

	private User userLogin;

	private JoborderIndex _joborderIndex;
	private ShipperIndex _shipperIndex;
	private CustomerIndex _customerIndex;
	private PegawaiIndex _pegawaiIndex;
	private AturanIndex _aturanIndex;
	private FillingIndex _fillingIndex;
	private UserIndex _userIndex;
	private CertificateIndex _certificateIndex;
	private CertificateNewTambah _certificateNewTambah;

	private Joborder _joborder;

	private Textbox kodeTextbox;
	private TextboxArea consigmentTextboxArea;
	private TextboxArea shipperTextboxArea;
	private TextboxArea consigneeTextboxArea;
	private TextboxArea partyTextboxArea;
	private Textbox vesselTextbox;
	private Textbox blnoTextbox;
	private TextboxArea containerNoTextboxArea;
	private Textbox destinationTextbox;
	private Textbox typeWoodTextbox;
	private Textbox htWoodCoreTemperaturTextbox;
	private Textbox fgFumigantTextbox;
	private Textbox fgDosageRateTextbox;
	private Textbox quantityTextbox;
	private Textbox exposureTimeTextbox;
	private Textbox certificateNumberTextbox;
	private Datebox tglCetakDatebox;
	
	private TableModel columnNewTableModel;
	private Table columnNewTable;
	private Vector<Object> columnNewVector;
	private String[] columnNewKolom = new String[]{"", "", "Data", "Detail", ""};

	private Button downloadButton;
	private Button download1Button;

	private ComboBox treatmentComboBox;

	private Label htWoodCoreTemperaturLabel;
	private Label exposureTimeLabel;
	private Label fgFumigantLabel;
	private Label fgDosageRateLabel;
	
	private JScrollPane mainJScrollPane;
	private Dimension mainDimension;
	private JPanel mainPanel;

	public CertificateTambah(Session session, Service service,
			SimpleDateFormat simpleDateFormat) {
		super("a", true, true, true, true);
		_frame = this;
		_frame.setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		_frame.setFrameIcon(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/star.png")));
		_frame.setLayout(new FlowLayout(FlowLayout.LEADING));
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		_frame.setPreferredSize(new Dimension( Double.valueOf(screenSize.getWidth()).intValue() - 15, Double.valueOf(screenSize.getHeight()).intValue() - 130));
		_frame.setSize(_frame.getPreferredSize());
		_frame.addComponentListener(new ComponentAdapter() {
			public void componentResized(ComponentEvent evt) {
				reSizePanel();
			}
		});

		_session = session;
		_service = service;
		_simpleDateFormat = simpleDateFormat;

		Container container = _frame.getContentPane();
		
		

		mainPanel = new JPanel();
		mainPanel.setPreferredSize(new Dimension(1350, 600));
		mainPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		
		mainJScrollPane = new JScrollPane(mainPanel);
		container.add(mainJScrollPane);
		mainDimension = new Dimension();
		
		

		JPanel leftPanel = new JPanel();
		leftPanel.setPreferredSize(new Dimension(475, mainPanel
				.getPreferredSize().height - 60));
		leftPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		mainPanel.add(leftPanel);

		JPanel rightPanel = new JPanel();
		rightPanel.setPreferredSize(new Dimension(420, mainPanel
				.getPreferredSize().height - 60));
		rightPanel.setLayout(new FlowLayout(FlowLayout.LEADING));
		mainPanel.add(rightPanel);
		
		JPanel right1Panel = new JPanel();
		right1Panel.setPreferredSize(new Dimension(420, mainPanel
				.getPreferredSize().height - 60));
		right1Panel.setLayout(new FlowLayout(FlowLayout.LEADING));
		mainPanel.add(right1Panel);

		Dimension labelDimension = new Dimension(120, 30);
		Dimension labelAreaDimension = new Dimension(120, 100);
		Dimension textAreaDimension = new Dimension(280, 100);
		Dimension textDimension = new Dimension(280, 30);

		Dimension singleDimension = new Dimension(410, 30);

		Dimension labelRightDimension = new Dimension(130, 30);
		Dimension textRightDimension = new Dimension(270, 30);
		Dimension textAreaRightDimension = new Dimension(270, 100);
		Dimension uploadPanelDimension = new Dimension(60, 100);

		Label kodeLabel = new Label("KODE");
		kodeLabel.setPreferredSize(labelDimension);
		leftPanel.add(kodeLabel);
		kodeTextbox = new Textbox("");
		kodeTextbox.setEditable(false);
		kodeTextbox.setPreferredSize(textDimension);
		leftPanel.add(kodeTextbox);

		Label consigmentLabel = new Label("CONSIGMENT");
		consigmentLabel.setPreferredSize(labelAreaDimension);
		consigmentLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(consigmentLabel);
		consigmentTextboxArea = new TextboxArea("");
		consigmentTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (consigmentTextboxArea.getText().length() > 0) {
						String checkAkhiran = consigmentTextboxArea.getText()
								.substring(
										consigmentTextboxArea.getText()
												.length() - 1,
										consigmentTextboxArea.getText()
												.length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							consigmentTextboxArea.setText(consigmentTextboxArea
									.getText().substring(
											0,
											consigmentTextboxArea.getText()
													.length() - 1));
							shipperTextboxArea.requestFocus();
						}

					}
				}
			}
		});
		JScrollPane consigmentScrollPane = new JScrollPane(
				consigmentTextboxArea);
		consigmentScrollPane.setPreferredSize(textAreaDimension);
		consigmentScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(consigmentScrollPane);

		JPanel buttonUploadPanel = new JPanel();
		buttonUploadPanel.setPreferredSize(uploadPanelDimension);
		leftPanel.add(buttonUploadPanel);

		Button uploadButton = new Button(new ImageIcon(getClass()
				.getClassLoader()
				.getResource("icons/document_small_upload.png")), "");
		uploadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(_frame);
				if (fileChooser.getSelectedFile() != null) {
					if (fileChooser.getSelectedFile().isFile()) {
						_session = _service.getConnectionDB(_session);
						_session.clear();

						File file = fileChooser.getSelectedFile();

						_joborder.setDownloadpath(file.getName());

						byte[] bFile = new byte[(int) file.length()];

						try {
							FileInputStream fileInputStream = new FileInputStream(
									file);
							fileInputStream.read(bFile);
							fileInputStream.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}

						_joborder.setConsigmentfile(bFile);

						_session.update(_joborder);
						_session.flush();

						_joborderIndex.refreshTable();
						_certificateIndex.refreshTable();
						downloadButton.setEnabled(true);
					}
				}
			}
		});
		buttonUploadPanel.add(uploadButton);
		downloadButton = new Button(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/download.png")), "");
		downloadButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_joborder.getDownloadpath() != null
						&& (!_joborder.getDownloadpath().isEmpty())) {
					byte[] bAvatar = _joborder.getConsigmentfile();
					File file = new File("D:/yasabalisujati/files/" + _joborder.getDownloadpath());
					if (file.isFile()) {
						file.delete();
					}
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bAvatar);
						fos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}

					if (file.isFile()) {
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"File " + _joborder.getDownloadpath()
										+ " hilang atau kehapus.", "Informasi",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		buttonUploadPanel.add(downloadButton);

		Label shipperLabel = new Label("SHIPPER");
		shipperLabel.setPreferredSize(labelAreaDimension);
		shipperLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(shipperLabel);
		shipperTextboxArea = new TextboxArea("");
		shipperTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (shipperTextboxArea.getText().length() > 0) {
						String checkAkhiran = shipperTextboxArea
								.getText()
								.substring(
										shipperTextboxArea.getText().length() - 1,
										shipperTextboxArea.getText().length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							shipperTextboxArea.setText(shipperTextboxArea
									.getText().substring(
											0,
											shipperTextboxArea.getText()
													.length() - 1));
							consigneeTextboxArea.requestFocus();
						}

					}
				}
			}
		});
		JScrollPane shipperScrollPane = new JScrollPane(shipperTextboxArea);
		shipperScrollPane.setPreferredSize(textAreaDimension);
		shipperScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(shipperScrollPane);

		Label consigneeLabel = new Label("CONSIGNEE");
		consigneeLabel.setPreferredSize(labelAreaDimension);
		consigneeLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(consigneeLabel);
		consigneeTextboxArea = new TextboxArea("");
		consigneeTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (consigneeTextboxArea.getText().length() > 0) {
						String checkAkhiran = consigneeTextboxArea
								.getText()
								.substring(
										consigneeTextboxArea.getText().length() - 1,
										consigneeTextboxArea.getText().length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							consigneeTextboxArea.setText(consigneeTextboxArea
									.getText().substring(
											0,
											consigneeTextboxArea.getText()
													.length() - 1));
							partyTextboxArea.requestFocus();
						}

					}
				}
			}
		});

		JScrollPane consigneeScrollPane = new JScrollPane(consigneeTextboxArea);
		consigneeScrollPane.setPreferredSize(textAreaDimension);
		consigneeScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(consigneeScrollPane);

		Label partyLabel = new Label("NOTIFY PARTY");
		partyLabel.setPreferredSize(labelAreaDimension);
		partyLabel.setVerticalAlignment(SwingConstants.TOP);
		leftPanel.add(partyLabel);
		partyTextboxArea = new TextboxArea("");
		partyTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (partyTextboxArea.getText().length() > 0) {
						String checkAkhiran = partyTextboxArea
								.getText()
								.substring(
										partyTextboxArea.getText().length() - 1,
										partyTextboxArea.getText().length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							partyTextboxArea
									.setText(partyTextboxArea.getText()
											.substring(
													0,
													partyTextboxArea.getText()
															.length() - 1));
							vesselTextbox.requestFocus();
						}

					}
				}
			}
		});
		JScrollPane partyScrollPane = new JScrollPane(partyTextboxArea);
		partyScrollPane.setPreferredSize(textAreaDimension);
		partyScrollPane.setBorder(new Textbox(null).getBorderCustom());
		leftPanel.add(partyScrollPane);

		JPanel button1UploadPanel = new JPanel();
		button1UploadPanel.setPreferredSize(uploadPanelDimension);
		leftPanel.add(button1UploadPanel);

		Button upload1Button = new Button(new ImageIcon(getClass()
				.getClassLoader()
				.getResource("icons/document_small_upload.png")), "");
		upload1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();
				fileChooser.showOpenDialog(_frame);
				if (fileChooser.getSelectedFile() != null) {
					if (fileChooser.getSelectedFile().isFile()) {
						_session = _service.getConnectionDB(_session);
						_session.clear();
						
						File file = fileChooser.getSelectedFile();
						
						_joborder.setDownloadpathParty(file.getName());
						
						byte[] bFile = new byte[(int) file.length()];
						
						try {
							FileInputStream fileInputStream = new FileInputStream(
									file);
							fileInputStream.read(bFile);
							fileInputStream.close();
						} catch (Exception ex) {
							ex.printStackTrace();
						}
						
						_joborder.setPartyfile(bFile);
						
						_session.update(_joborder);
						_session.flush();

						_joborderIndex.refreshTable();
						_certificateIndex.refreshTable();
						download1Button.setEnabled(true);
					}
				}
			}
		});
		button1UploadPanel.add(upload1Button);
		download1Button = new Button(new ImageIcon(getClass().getClassLoader()
				.getResource("icons/download.png")), "");
		download1Button.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (_joborder.getDownloadpathParty() != null
						&& (!_joborder.getDownloadpathParty().isEmpty())) {
					byte[] bAvatar = _joborder.getPartyfile();
					File file = new File("D:/yasabalisujati/files/" + _joborder.getDownloadpathParty());
					if (file.isFile()) {
						file.delete();
					}
					try {
						FileOutputStream fos = new FileOutputStream(file);
						fos.write(bAvatar);
						fos.close();
					} catch (Exception ex) {
						ex.printStackTrace();
					}
					if (file.isFile()) {
						try {
							Desktop.getDesktop().open(file);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					} else {
						JOptionPane.showMessageDialog(null,
								"File " + _joborder.getDownloadpathParty()
										+ " hilang atau kehapus.", "Informasi",
								JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
		});
		button1UploadPanel.add(download1Button);

		Label vesselLabel = new Label("VESSEL");
		vesselLabel.setPreferredSize(labelDimension);
		leftPanel.add(vesselLabel);
		vesselTextbox = new Textbox("");
		vesselTextbox.setPreferredSize(textDimension);
		leftPanel.add(vesselTextbox);

		Label blnoLabel = new Label("B/L NO ");
		blnoLabel.setPreferredSize(labelDimension);
		leftPanel.add(blnoLabel);
		blnoTextbox = new Textbox("");
		blnoTextbox.setPreferredSize(textDimension);
		leftPanel.add(blnoTextbox);

		Label containerLabel = new Label("CONTAINER NO");
		containerLabel.setPreferredSize(labelRightDimension);
		containerLabel.setVerticalAlignment(SwingConstants.TOP);
		rightPanel.add(containerLabel);
		containerNoTextboxArea = new TextboxArea("");
		containerNoTextboxArea.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_TAB) {
					if (containerNoTextboxArea.getText().length() > 0) {
						String checkAkhiran = containerNoTextboxArea.getText()
								.substring(
										containerNoTextboxArea.getText()
												.length() - 1,
										containerNoTextboxArea.getText()
												.length());
						if ((int) checkAkhiran.charAt(0) == 9) {
							containerNoTextboxArea
									.setText(containerNoTextboxArea
											.getText()
											.substring(
													0,
													containerNoTextboxArea
															.getText().length() - 1));
							destinationTextbox.requestFocus();
						}

					}
				}
			}
		});
		JScrollPane containerNoScrollPane = new JScrollPane(
				containerNoTextboxArea);
		containerNoScrollPane.setPreferredSize(textAreaRightDimension);
		containerNoScrollPane.setBorder(new Textbox(null).getBorderCustom());
		rightPanel.add(containerNoScrollPane);

		Label destinationLabel = new Label("DESTINATION");
		destinationLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(destinationLabel);
		destinationTextbox = new Textbox("");
		destinationTextbox.setPreferredSize(textRightDimension);
		rightPanel.add(destinationTextbox);

		Label typeOfWoodLabel = new Label("TYPE OF WOOD PACKAGING");
		typeOfWoodLabel.setPreferredSize(singleDimension);
		rightPanel.add(typeOfWoodLabel);
		typeWoodTextbox = new Textbox("");
		typeWoodTextbox.setPreferredSize(singleDimension);
		rightPanel.add(typeWoodTextbox);

		Label quantityLabel = new Label("QUANTITY");
		quantityLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(quantityLabel);
		quantityTextbox = new Textbox("");
		quantityTextbox.setPreferredSize(textRightDimension);
		rightPanel.add(quantityTextbox);

		Label treatmentLabel = new Label("TREATMENT");
		treatmentLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(treatmentLabel);
		treatmentComboBox = new ComboBox(new String[] { "HEAT TREATMENT (HT)",
				"FUMIGATION" });
		treatmentComboBox.setPreferredSize(textRightDimension);
		treatmentComboBox.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				setTreatment();
			}
		});
		rightPanel.add(treatmentComboBox);

		htWoodCoreTemperaturLabel = new Label("WOOD CORE TEMPERATURE (\u00b0C)");
		htWoodCoreTemperaturLabel.setPreferredSize(new Dimension(250, 30));
		rightPanel.add(htWoodCoreTemperaturLabel);
		htWoodCoreTemperaturTextbox = new Textbox("");
		htWoodCoreTemperaturTextbox.setPreferredSize(new Dimension(150, 30));
		rightPanel.add(htWoodCoreTemperaturTextbox);

		fgFumigantLabel = new Label("FUMIGANT");
		fgFumigantLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(fgFumigantLabel);
		fgFumigantTextbox = new Textbox("");
		fgFumigantTextbox.setPreferredSize(textRightDimension);
		rightPanel.add(fgFumigantTextbox);

		fgDosageRateLabel = new Label("DOSAGE RATE");
		fgDosageRateLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(fgDosageRateLabel);
		fgDosageRateTextbox = new Textbox("");
		fgDosageRateTextbox.setPreferredSize(textRightDimension);
		rightPanel.add(fgDosageRateTextbox);

		exposureTimeLabel = new Label("EXPOSURE TIME");
		exposureTimeLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(exposureTimeLabel);
		exposureTimeTextbox = new Textbox("");
		exposureTimeTextbox.setPreferredSize(textRightDimension);
		rightPanel.add(exposureTimeTextbox);

		Label certificateNumberLabel = new Label("CERTIFICATE NUMBER");
		certificateNumberLabel.setPreferredSize(singleDimension);
		rightPanel.add(certificateNumberLabel);
		certificateNumberTextbox = new Textbox("");
		certificateNumberTextbox.setPreferredSize(singleDimension);
		rightPanel.add(certificateNumberTextbox);

		Label tglCetakLabel = new Label("TGL. CETAK");
		tglCetakLabel.setPreferredSize(labelRightDimension);
		rightPanel.add(tglCetakLabel);
		tglCetakDatebox = new Datebox("dd/MM/yyyy", "##-##-####", '_');
		tglCetakDatebox.setPreferredSize(textRightDimension);
		rightPanel.add(tglCetakDatebox);
		
		Button addColumnNew = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/add.png")),
				"Tambah Data");
		addColumnNew.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				_certificateNewTambah.setVisible(_joborder, null);
			}
		});
		right1Panel.add(addColumnNew);
		
		columnNewVector = new Vector<Object>();
		columnNewTableModel = new AbstractTableModel() {
			private static final long serialVersionUID = 9194573404469074938L;

			public int getColumnCount() {
				return columnNewKolom.length;
			}

			public int getRowCount() {
				return columnNewVector.size();
			}

			public Object getValueAt(int baris1, int kolom1) {
				Vector<?> barisan = (Vector<?>) columnNewVector.elementAt(baris1);
				return barisan.elementAt(kolom1);
			}

			public String getColumnName(int kolom1) {
				return columnNewKolom[kolom1];
			}

			public boolean isCellEditable(int baris1, int kolom1) {
				if (kolom1 == 0 || kolom1 == 1)
					return true;
				else 
					return false;
			}

			public void setValueAt(Object obj, int baris1, int kolom1) {
				Vector<Object> barisdata = (Vector<Object>) columnNewVector.elementAt(baris1);
				barisdata.setElementAt(obj, kolom1);
			}

			public Class<?> getColumnClass(int column) {
				if (column == 0 || column == 1) {
					return Button.class;
				} else if (column == 2 || column == 3) {
					return String.class;
				} else {
					return CertificateNewColumn.class;
				}
			}
		};
		
		columnNewTable = new Table(columnNewTableModel);
		TableRowSorter<TableModel> columnNewSorter = new TableRowSorter<>(
				columnNewTable.getModel());
		columnNewTable.setRowSorter(columnNewSorter);
		columnNewSorter.setSortable(0, false);
		columnNewSorter.setSortable(1, false);
		
		columnNewTable.getColumnModel().getColumn(0).setPreferredWidth(50);
		columnNewTable.getColumnModel().getColumn(0).setCellRenderer(new ButtonColumnRenderer(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/remove_outline.png")),
				""));
		columnNewTable.getColumnModel().getColumn(0).setCellEditor(new ButtonDeleteNewColumnRenderer(new JCheckBox(), this));
		columnNewTable.getColumnModel().getColumn(1).setPreferredWidth(50);
		columnNewTable.getColumnModel().getColumn(1).setCellRenderer(new ButtonColumnRenderer(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/edit.png")),
				""));
		columnNewTable.getColumnModel().getColumn(1).setCellEditor(new ButtonEditNewColumnRenderer(new JCheckBox(), this));
		columnNewTable.getColumnModel().getColumn(2).setPreferredWidth(150);
		columnNewTable.getColumnModel().getColumn(3).setPreferredWidth(250);
		
		columnNewTable.getColumnModel().getColumn(4).setPreferredWidth(0);
		columnNewTable.getColumnModel().getColumn(4).setMinWidth(0);
		columnNewTable.getColumnModel().getColumn(4).setMaxWidth(0);
		columnNewTable.getColumnModel().getColumn(4).setWidth(0);
		
		JScrollPane petugasJscoJScrollPane = new JScrollPane(columnNewTable);
		petugasJscoJScrollPane.setPreferredSize(new Dimension(right1Panel.getPreferredSize().width -15, 400));
		right1Panel.add(petugasJscoJScrollPane);

		JPanel buttonSavePanel = new JPanel();
		buttonSavePanel.setPreferredSize(new Dimension(Double.valueOf(
				_frame.getPreferredSize().getWidth()).intValue() - 35, 40));
		buttonSavePanel.setBorder(BorderFactory.createTitledBorder(""));
		mainPanel.add(buttonSavePanel);

		Button saveButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/save.png")),
				"(Ctrl+S)  SIMPAN");
		saveButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				save();
			}
		});
		buttonSavePanel.add(saveButton);

		KeyStroke newKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_S),
				InputEvent.CTRL_MASK, false);
		Action newAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				save();

			}
		};

		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(newKeyStroke, "SAVED");
		_frame.getRootPane().getActionMap().put("SAVED", newAction);

		KeyStroke escapeKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE,
				0, false);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		};
		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(escapeKeyStroke, "ESCAPE");
		_frame.getRootPane().getActionMap().put("ESCAPE", escapeAction);

		Button previewButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/page_white_acrobat.png")),
				"(Ctrl+P)  PREVIEW");
		previewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				createPDFPreview();
			}
		});
		buttonSavePanel.add(previewButton);

		KeyStroke previewKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_P),
				InputEvent.CTRL_MASK, false);
		Action previewAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			public void actionPerformed(ActionEvent e) {
				createPDFPreview();

			}
		};
		
		Button msWordButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/page_white_word.png")),
				"(Ctrl+M)  MS. WORD");
		msWordButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				createMsWord();
			}
		});
		buttonSavePanel.add(msWordButton);
		
		KeyStroke msWordKeyStroke = KeyStroke.getKeyStroke((KeyEvent.VK_M),
				InputEvent.CTRL_MASK, false);
		Action msWordAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;
			
			public void actionPerformed(ActionEvent e) {
				createMsWord();
				
			}
		};

		_frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW)
				.put(msWordKeyStroke, "MSWORD");
		_frame.getRootPane().getActionMap().put("MSWORD", msWordAction);

		JLabel blankLabel = new JLabel("");
		blankLabel.setPreferredSize(new Dimension(70, 30));
		buttonSavePanel.add(blankLabel);

		Button closeButton = new Button(new ImageIcon(getClass()
				.getClassLoader().getResource("icons/cancel.png")), "TUTUP");
		closeButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				closeEvent();
			}
		});
		buttonSavePanel.add(closeButton);
		
		_frame.pack();
		reSizePanel();
	}

	public void setVisible(Joborder joborder) {

		if (joborder.getExportir() == null) {
			JOptionPane.showMessageDialog(null,
					"Shipper belum ada di Joborder ini", "Informasi",
					JOptionPane.INFORMATION_MESSAGE);
			_frame.setVisible(false);
			return;
		}

		clearForm();
		_frame.setTitle("Buat Certificate");
		_joborder = joborder;

		if (_joborder.getTreatment() != null) {
			treatmentComboBox.setSelectedItem(_joborder.getTreatment());
		}

		setTreatment();

		kodeTextbox.setText(_joborder.getKode());
		consigmentTextboxArea.setText(_joborder.getKomoditi());
		if (_joborder.getExportir() != null) {
			if (_joborder.getExportir().getNama() != null) {
				shipperTextboxArea.setText(_joborder.getExportir().getNama());
			}
			if (!shipperTextboxArea.getText().isEmpty()) {
				shipperTextboxArea.setText(shipperTextboxArea.getText() + "\n");
			}
			if (_joborder.getExportir().getDetail() != null) {
				shipperTextboxArea.setText(shipperTextboxArea.getText()
						+ _joborder.getExportir().getDetail());
			}
		}
		partyTextboxArea.setText(_joborder.getPartai());
		destinationTextbox.setText(_joborder.getDestinasi());

		if (_joborder.getConsignee() != null) {
			consigneeTextboxArea.setText(_joborder.getConsignee());
		}

		if (_joborder.getVessel() != null) {
			vesselTextbox.setText(_joborder.getVessel());
		}

		if (_joborder.getBlno() != null) {
			blnoTextbox.setText(_joborder.getBlno());
		}

		if (_joborder.getContainerno() != null) {
			containerNoTextboxArea.setText(_joborder.getContainerno());
		}

		if (_joborder.getType_of_wood_packing() != null) {
			typeWoodTextbox.setText(_joborder.getType_of_wood_packing());
		}

		if (_joborder.getQuantity() != null) {
			quantityTextbox.setText(_joborder.getQuantity());
		}

		if (_joborder.getWood_core_temperatur() != null) {
			htWoodCoreTemperaturTextbox.setText(_joborder
					.getWood_core_temperatur());
		}

		if (_joborder.getExposure_time() != null) {
			exposureTimeTextbox.setText(_joborder.getExposure_time());
		}

		if (_joborder.getExposure_time() != null) {
			exposureTimeTextbox.setText(_joborder.getExposure_time());
		}

		if (_joborder.getFumigant() != null) {
			fgFumigantTextbox.setText(_joborder.getFumigant());
		}

		if (_joborder.getDosage_rate() != null) {
			fgDosageRateTextbox.setText(_joborder.getDosage_rate());
		}

		if (_joborder.getCertificate_number() != null) {
			certificateNumberTextbox.setText(_joborder.getCertificate_number());
		}

		if (_joborder.getTgl_cetak() != null) {
			tglCetakDatebox.setDate(_joborder.getTgl_cetak());
		}

		downloadButton.setEnabled(false);
		if (_joborder.getDownloadpath() != null && _joborder.getConsigmentfile() != null
				&& (!_joborder.getDownloadpath().isEmpty())) {
				downloadButton.setEnabled(true);
		}

		download1Button.setEnabled(false);
		if (_joborder.getDownloadpathParty() != null
				&& (_joborder.getPartyfile() != null)) {
			download1Button.setEnabled(true);
		}

		_frame.setVisible(true);
		reSizePanel();
		refreshColumnNew();
		_frame.moveToFront();
	}

	private void setTreatment() {
		if (treatmentComboBox.getSelectedItem().equals("HEAT TREATMENT (HT)")) {
			htWoodCoreTemperaturLabel.setVisible(true);
			htWoodCoreTemperaturTextbox.setVisible(true);

			fgDosageRateLabel.setVisible(false);
			fgDosageRateTextbox.setVisible(false);
			fgFumigantLabel.setVisible(false);
			fgFumigantTextbox.setVisible(false);
		} else {
			fgDosageRateLabel.setVisible(true);
			fgDosageRateTextbox.setVisible(true);
			fgFumigantLabel.setVisible(true);
			fgFumigantTextbox.setVisible(true);

			htWoodCoreTemperaturLabel.setVisible(false);
			htWoodCoreTemperaturTextbox.setVisible(false);
		}
	}

	public void clearForm() {
		kodeTextbox.setText("");
		consigmentTextboxArea.setText("");
		shipperTextboxArea.setText("");
		consigneeTextboxArea.setText("");
		partyTextboxArea.setText("");
		vesselTextbox.setText("");
		blnoTextbox.setText("");
		containerNoTextboxArea.setText("");
		destinationTextbox.setText("");
		typeWoodTextbox.setText("");
		htWoodCoreTemperaturTextbox.setText("");
		fgFumigantTextbox.setText("");
		fgDosageRateTextbox.setText("");
		quantityTextbox.setText("");
		exposureTimeTextbox.setText("");
		certificateNumberTextbox.setText("");
		tglCetakDatebox.setDate(null);
	}

	public String getStringAsDigit(String string, int digit) {
		if (string != null && (string.length() < digit)) {
			String digit0 = "0";
			while (string.length() < digit) {
				string = digit0 + string;
				digit0 += "0";
			}
		}
		return string;
	}

	public void save() {
		Date nowDate = new Date();
		java.sql.Timestamp nowSqlDate = new java.sql.Timestamp(
				nowDate.getTime());

		_session = _service.getConnectionDB(_session);
		_session.clear();

		Customer shipper = _joborder.getExportir();

		StringTokenizer stk = new StringTokenizer(shipperTextboxArea.getText(),
				String.valueOf((char) 10));
		if (stk.hasMoreTokens()) {
			shipper.setNama(stk.nextToken());
		}
		shipper.setDetail("");
		while (stk.hasMoreTokens()) {
			shipper.setDetail(shipper.getDetail() + stk.nextToken() + (char) 10);
		}

		_session.update(shipper);
		_session.flush();

		_joborder.setKomoditi(consigmentTextboxArea.getText());
		_joborder.setPartai(partyTextboxArea.getText());
		_joborder.setDestinasi(destinationTextbox.getText());
		_joborder.setConsignee(consigneeTextboxArea.getText());
		_joborder.setVessel(vesselTextbox.getText());
		_joborder.setBlno(blnoTextbox.getText());
		_joborder.setContainerno(containerNoTextboxArea.getText());
		_joborder.setType_of_wood_packing(typeWoodTextbox.getText());
		_joborder.setQuantity(quantityTextbox.getText());
		_joborder.setTreatment(treatmentComboBox.getSelectedItem().toString());
		_joborder
				.setWood_core_temperatur(htWoodCoreTemperaturTextbox.getText());
		_joborder.setExposure_time(exposureTimeTextbox.getText());
		_joborder.setFumigant(fgFumigantTextbox.getText());
		_joborder.setDosage_rate(fgDosageRateTextbox.getText());
		_joborder.setCertificate_number(certificateNumberTextbox.getText());
		if (tglCetakDatebox.getDate() != null) {
			_joborder.setTgl_cetak(new java.sql.Date(tglCetakDatebox.getDate()
					.getTime()));
		}
		_joborder.setUpdatedBy(userLogin);
		_joborder.setUpdatedAt(nowSqlDate);

		_session.update(_joborder);
		_session.flush();

		_joborderIndex.refreshTable();
		_pegawaiIndex.refreshTable();
		_aturanIndex.refreshTable();
		_userIndex.refreshTable();
		_customerIndex.refreshTable();
		_shipperIndex.refreshTable();
		_fillingIndex.refreshTable();
		_certificateIndex.refreshTable();
	}

	public void closeEvent() {
		_frame.setVisible(false);
	}

	public void setJoborderIndex(JoborderIndex joborderIndex) {
		_joborderIndex = joborderIndex;
	}

	public void setShipperIndex(ShipperIndex shipperIndex) {
		_shipperIndex = shipperIndex;
	}

	public void setCustomerIndex(CustomerIndex customerIndex) {
		_customerIndex = customerIndex;
	}

	public void setAturanIndex(AturanIndex aturanIndex) {
		_aturanIndex = aturanIndex;
	}

	public void setPegawaiIndex(PegawaiIndex pegawaiIndex) {
		_pegawaiIndex = pegawaiIndex;
	}

	public void setFillingIndex(FillingIndex fillingIndex) {
		_fillingIndex = fillingIndex;
	}

	public void setUserIndex(UserIndex userIndex) {
		_userIndex = userIndex;
	}

	public void setCertificateIndex(CertificateIndex certificateIndex) {
		_certificateIndex = certificateIndex;
	}

	public CertificateIndex getCertificateIndex() {
		return _certificateIndex;
	}

	public void setUserLogin(User user) {
		userLogin = user;
	}
	
	public void setCertificateNewTambah(CertificateNewTambah certificateNewTambah) {
		_certificateNewTambah = certificateNewTambah;
	}
	
	public void createMsWord() {
		File file = new File("D:/yasabalisujati/ini.docx");
		
		XWPFDocument document = new XWPFDocument();
		
		CTBody body = document.getDocument().getBody();
		if(!body.isSetSectPr()){
			body.addNewSectPr();
		}
		 
		CTSectPr section = body.getSectPr();
		if(!section.isSetPgSz()){
			section.addNewPgSz();
		}
		
		CTPageSz pageSize = section.getPgSz();
		pageSize.setW(BigInteger.valueOf(12240));
		pageSize.setH(BigInteger.valueOf(20160));
		
		CTPageMar pageMar = section.addNewPgMar();
	    pageMar.setLeft(BigInteger.valueOf(720L));
	    pageMar.setTop(BigInteger.valueOf(120L));
	    pageMar.setRight(BigInteger.valueOf(720L));
	    pageMar.setBottom(BigInteger.valueOf(120L));
		
		XWPFParagraph tmpParagraph = document.createParagraph();
		XWPFRun tmpRun = tmpParagraph.createRun();
		
		for (int i = 0; i < 5; i++) {
			tmpRun.setText("");
			tmpRun.addBreak();
		}
		
		tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("ISPM#15 CERTIFICATE");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(14);
		tmpRun.setBold(true);
		tmpRun.setUnderline(UnderlinePatterns.SINGLE);
		
		
		String treatment = _joborder.getTreatment();
		if (treatment.equals("FUMIGATION")) {
			treatment = "TREATMENT : METHYL BROMIDE (MB)";
		}
		
		tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText(treatment);
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(12);
		tmpRun.setBold(true);
		
		tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.CENTER);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("ID No : 107");
		tmpRun.addBreak();
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		addRowMSWord(document, "CONSIGMENT", _joborder.getKomoditi());
		if (_joborder.getExportir() != null) {
			addRowMSWord(document, "SHIPPER", _joborder.getExportir().getNama() + "\n"
					+ _joborder.getExportir().getDetail());
		}
		addRowMSWord(document, "CONSIGNEE", _joborder.getConsignee());
		addRowMSWord(document, "NOTIFY PARTY", _joborder.getPartai());

		Criteria criteria = _session.createCriteria(CertificateNewColumn.class);
		criteria.add(Restrictions.eq("joborder", _joborder));
		List<CertificateNewColumn> dataList = criteria.list();
		for (CertificateNewColumn certificateNewColumn : dataList) {
			if (certificateNewColumn.getColumnName() != null && certificateNewColumn.getDescription() != null 
					&& (!certificateNewColumn.getColumnName().isEmpty() && !certificateNewColumn.getDescription().isEmpty() )) {
				addRowMSWord(document, certificateNewColumn.getColumnName(), certificateNewColumn.getDescription());
			}
		}
		addRowMSWord(document, "VESSEL", _joborder.getVessel());
		addRowMSWord(document, "BL / NO", _joborder.getBlno());
		addRowMSWord(document, "CONTAINER NO", _joborder.getContainerno());
		addRowMSWord(document, "DESTINATION", _joborder.getDestinasi());
		addRowMSWord(document, "DESTINATION", _joborder.getDestinasi());
		
		tmpParagraph = document.createParagraph();
		tmpParagraph.setBorderBottom(Borders.DASH_SMALL_GAP);
		
		tmpParagraph = document.createParagraph();
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("THIS IS TO CERTIFY THAT THE WOOD PACKAGING ON THE ABOVE CONSIGMENT HAS BEEN TREATED IN ACCORDANCE WITH ISPM#15 ANNEX 1:");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		addRowMSWord(document, "TYPE OF WOOD PACKAGING", _joborder.getType_of_wood_packing());
		addRowMSWord(document, "QUANTITY", _joborder.getQuantity());
		addRowMSWord(document, "TREATMENT", _joborder.getTreatment());
		
		if (_joborder.getTreatment().equals("HEAT TREATMENT (HT)")) {
			addRowMSWord(document,
					"DESCRIPTION OF TREATMENT",
					"WOOD CORE TEMPERATURE : "
							+ _joborder.getWood_core_temperatur()
							+ " (\u00b0C)\n" + "EXPOSURE TIME         : "
							+ _joborder.getExposure_time());
		} else if (_joborder.getTreatment().equals("FUMIGATION")) {
			addRowMSWord(document, "DESCRIPTION OF TREATMENT", "FUMIGANT       : "
					+ _joborder.getFumigant() + "\n" + "DOSAGE RATE    : "
					+ _joborder.getDosage_rate() + "\n"
					+ "EXPOSURE TIME  : " + _joborder.getExposure_time());
		}
		
		tmpParagraph = document.createParagraph();
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("ALL WOOD PACKAGING MATERIAL HAS BEEN DEBARKED BEFORE THE TREATMENT");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		setMark(document);
		addRowMSWord(document, "CERTIFICATE NUMBER", _joborder.getCertificate_number());
		
		tmpParagraph = document.createParagraph();
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("THIS CERTIFICATE REFERS ISPM#15 "
				+ _joborder.getTreatment()
				+ " ONLY AND DOES NOT CERTIFY ANY OTHER MATTERS");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		tmpParagraph = document.createParagraph();
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText(("SURABAYA ," + _service.convertStringFromDate(
				"MMMM dd, yyyy", _joborder.getTgl_cetak(),
				_simpleDateFormat)).toUpperCase());
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		tmpParagraph = document.createParagraph();
		tmpParagraph.setAlignment(ParagraphAlignment.RIGHT);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText("AUTHORIZED SIGNATURE");
		tmpRun.addBreak();
		tmpRun.setText("PT YASA BALI SUJATI ");
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.addBreak();
		tmpRun.setText("....................");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		
		try {
			document.write(new FileOutputStream(file));
			Desktop.getDesktop().open(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Tutup MS Word dulu",
					"Informasi", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setMark(XWPFDocument document) {
		XWPFTable table = document.createTable(1,3);
		table.getCTTbl().getTblPr().unsetTblBorders();
		XWPFTableRow row = table.getRow(0);
		
		XWPFTableCell cell = row.getCell(0);
		cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3600));
		XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText("MARKING");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		cell = row.getCell(1);
		tmpParagraph = cell.getParagraphs().get(0);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText(":");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		cell = row.getCell(2);
		
		tmpParagraph = cell.getParagraphs().get(0);
		tmpRun = tmpParagraph.createRun();
		InputStream pic;
		try {
			if (_joborder.getTreatment().equals("HEAT TREATMENT (HT)")) {
				pic = getClass().getClassLoader()
					.getResourceAsStream("icons/ippcht.png");
			} else {
				pic = getClass().getClassLoader()
						.getResourceAsStream("icons/ippfumigant.png");
			}
			tmpRun.addPicture(pic, XWPFDocument.PICTURE_TYPE_PNG, "logo1" , Units.toEMU(120), Units.toEMU(30));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void addRowMSWord(XWPFDocument document, String title, String data) {
		XWPFTable table = document.createTable(1,3);
		table.getCTTbl().getTblPr().unsetTblBorders();
		XWPFTableRow row = table.getRow(0);
		
		XWPFTableCell cell = row.getCell(0);
		cell.getCTTc().addNewTcPr().addNewTcW().setW(BigInteger.valueOf(3600));
		XWPFParagraph tmpParagraph = cell.getParagraphs().get(0);
		XWPFRun tmpRun = tmpParagraph.createRun();
		tmpRun.setText(title);
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		cell = row.getCell(1);
		tmpParagraph = cell.getParagraphs().get(0);
		tmpRun = tmpParagraph.createRun();
		tmpRun.setText(":");
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
		
		cell = row.getCell(2);
		tmpParagraph = cell.getParagraphs().get(0);
		tmpRun = tmpParagraph.createRun();
		if (data != null) {
			StringTokenizer stk = new StringTokenizer(data, "\n");
			int i = 0;
			while (stk.hasMoreTokens()) {
				if (i > 0) {
					tmpRun.addBreak();
				}
				tmpRun.setText(stk.nextToken());
				i++;
			}
		}
		tmpRun.setFontFamily("Courier");
		tmpRun.setFontSize(10);
	}

	public void createPDFPreview() {
		File file = new File("D:/yasabalisujati/ini.pdf");
		if (file.isFile()) {
			file.delete();
		}
		boolean created = false;
		Document document = null;
		try {
			document = new Document(PageSize.LEGAL);
			PdfWriter.getInstance(document, new FileOutputStream(file));
			document.open();

			Paragraph newLine = new Paragraph("\n");
			for (int i = 0; i < 9; i++) {
				document.add(newLine);
			}

			Font fontBoldUnderLine1 = FontFactory.getFont("Courier", 16,
					Font.BOLD | Font.UNDERLINE);
			Paragraph title1 = new Paragraph("ISPM#15 CERTIFICATE",
					fontBoldUnderLine1);
			title1.setAlignment(Element.ALIGN_CENTER);
			document.add(title1);

			Font fontBoldUnderLine2 = FontFactory.getFont("Courier", 14,
					Font.BOLD);
			String treatment = _joborder.getTreatment();
			if (treatment.equals("FUMIGATION")) {
				treatment = "TREATMENT : METHYL BROMIDE (MB)";
			}
			Paragraph title2 = new Paragraph(treatment, fontBoldUnderLine2);
			title2.setAlignment(Element.ALIGN_CENTER);
			document.add(title2);

			Font fontregular = FontFactory.getFont("Courier", 12);
			Paragraph title3 = new Paragraph("ID No : 107", fontregular);
			title3.setAlignment(Element.ALIGN_CENTER);
			document.add(title3);

			document.add(newLine);

			PdfPTable table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 4, 1, 7 });

			setIsiData("CONSIGMENT", _joborder.getKomoditi(), table,
					fontregular);

			if (_joborder.getExportir() != null) {
				setIsiData("SHIPPER", _joborder.getExportir().getNama() + "\n"
						+ _joborder.getExportir().getDetail(), table,
						fontregular);
			}

			setIsiData("CONSIGNEE", _joborder.getConsignee(), table,
					fontregular);
			setIsiData("NOTIFY PARTY", _joborder.getPartai(), table,
					fontregular);
			
			Criteria criteria = _session.createCriteria(CertificateNewColumn.class);
			criteria.add(Restrictions.eq("joborder", _joborder));
			List<CertificateNewColumn> dataList = criteria.list();
			for (CertificateNewColumn certificateNewColumn : dataList) {
				if (certificateNewColumn.getColumnName() != null && certificateNewColumn.getDescription() != null 
						&& (!certificateNewColumn.getColumnName().isEmpty() && !certificateNewColumn.getDescription().isEmpty() )) {
					setIsiData(certificateNewColumn.getColumnName(), certificateNewColumn.getDescription(), table,
							fontregular);
				}
			}
			
			setIsiData("VESSEL", _joborder.getVessel(), table, fontregular);
			setIsiData("BL / NO", _joborder.getBlno(), table, fontregular);
			setIsiData("CONTAINER NO", _joborder.getContainerno(), table,
					fontregular);
			setIsiData("DESTINATION", _joborder.getDestinasi(), table,
					fontregular);

			document.add(table);

			CustomDashedLineSeparator separator = new CustomDashedLineSeparator();
			separator.setDash(10);
			separator.setGap(3);
			separator.setLineWidth(1);
			Chunk linebreak = new Chunk(separator);
			document.add(linebreak);

			Paragraph parg = new Paragraph(
					"THIS IS TO CERTIFY THAT THE WOOD PACKAGING ON THE ABOVE CONSIGMENT HAS BEEN TREATED IN ACCORDANCE WITH ISPM#15 ANNEX 1 :",
					fontregular);
			document.add(parg);

			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 4, 1, 7 });

			setIsiData("TYPE OF WOOD PACKAGING",
					_joborder.getType_of_wood_packing(), table, fontregular);
			setIsiData("QUANTITY", _joborder.getQuantity(), table, fontregular);
			setIsiData("TREATMENT", _joborder.getTreatment(), table,
					fontregular);
			if (_joborder.getTreatment().equals("HEAT TREATMENT (HT)")) {
				setIsiData(
						"DESCRIPTION OF TREATMENT",
						"WOOD CORE TEMPERATURE : "
								+ _joborder.getWood_core_temperatur()
								+ " (\u00b0C)\n" + "EXPOSURE TIME         : "
								+ _joborder.getExposure_time(), table,
						fontregular);
			} else if (_joborder.getTreatment().equals("FUMIGATION")) {
				setIsiData("DESCRIPTION OF TREATMENT", "FUMIGANT       : "
						+ _joborder.getFumigant() + "\n" + "DOSAGE RATE    : "
						+ _joborder.getDosage_rate() + "\n"
						+ "EXPOSURE TIME  : " + _joborder.getExposure_time(),
						table, fontregular);
			}

			document.add(newLine);
			document.add(table);
			document.add(newLine);

			Paragraph parg1 = new Paragraph(
					"ALL WOOD PACKAGING MATERIAL HAS BEEN DEBARKED BEFORE THE TREATMENT",
					fontregular);
			document.add(parg1);

			table = new PdfPTable(3);
			table.setWidthPercentage(100);
			table.setWidths(new int[] { 4, 1, 7 });

			PdfPCell cellOne = new PdfPCell(new Paragraph("MARKING",
					fontregular));
			cellOne.setBorder(Rectangle.NO_BORDER);
			table.addCell(cellOne);

			Paragraph titikdua = new Paragraph(":", fontregular);
			PdfPCell titikduaCell = new PdfPCell(titikdua);
			titikduaCell.setBorder(Rectangle.NO_BORDER);
			titikduaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			table.addCell(titikduaCell);

			PdfPCell imageCell = new PdfPCell();
			imageCell.setBorder(Rectangle.NO_BORDER);
			try {
				Image image1 = null;
				if (_joborder.getTreatment().equals("HEAT TREATMENT (HT)")) {
					image1 = Image.getInstance(getClass().getClassLoader()
							.getResource("icons/ippcht.png"));
				} else if (_joborder.getTreatment().equals("FUMIGATION")) {
					image1 = Image.getInstance(getClass().getClassLoader()
							.getResource("icons/ippfumigant.png"));
				}
				image1.scaleToFit(120, 30);
				imageCell.addElement(image1);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			table.addCell(imageCell);

			setIsiData("CERTIFICATE NUMBER", _joborder.getCertificate_number(),
					table, fontregular);

			document.add(table);

			Paragraph parg3 = new Paragraph("THIS CERTIFICATE REFERS ISPM#15 "
					+ _joborder.getTreatment()
					+ " ONLY AND DOES NOT CERTIFY ANY OTHER MATTERS",
					fontregular);
			document.add(parg3);
			Paragraph parg4 = new Paragraph(
					("SURABAYA ," + _service.convertStringFromDate(
							"MMMM dd, yyyy", _joborder.getTgl_cetak(),
							_simpleDateFormat)).toUpperCase(), fontregular);
			document.add(parg4);

			Paragraph parg5 = new Paragraph(
					"AUTHORIZED SIGNATURE\nPT YASA BALI SUJATI ", fontregular);
			parg5.setAlignment(Element.ALIGN_RIGHT);
			document.add(parg5);

			for (int i = 0; i < 4; i++) {
				document.add(newLine);
			}

			Paragraph parg6 = new Paragraph("( .................. )",
					fontregular);
			parg6.setAlignment(Element.ALIGN_RIGHT);
			document.add(parg6);

			created = true;
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			JOptionPane.showMessageDialog(null, "Tutup PDF dulu",
					"Informasi", JOptionPane.INFORMATION_MESSAGE);
		} catch (DocumentException e1) {
			e1.printStackTrace();
		}
		if (document != null) {
			document.close();
		}

		if (created) {
			try {
				Desktop.getDesktop().open(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void setIsiData(String title, String isi, PdfPTable table,
			Font fontregular) {
		Paragraph titikdua = new Paragraph(":", fontregular);

		PdfPCell cellOne = new PdfPCell(new Paragraph(title, fontregular));
		cellOne.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellOne);

		PdfPCell titikduaCell = new PdfPCell(titikdua);
		titikduaCell.setBorder(Rectangle.NO_BORDER);
		titikduaCell.setHorizontalAlignment(Element.ALIGN_RIGHT);
		table.addCell(titikduaCell);

		PdfPCell cellTwo = new PdfPCell(new Paragraph(isi, fontregular));
		cellTwo.setBorder(Rectangle.NO_BORDER);
		table.addCell(cellTwo);
	}
	
	public void reSizePanel() {
		mainDimension.setSize(_frame.getPreferredSize().getWidth(), _frame.getPreferredSize().getHeight());
		mainJScrollPane.setPreferredSize(mainDimension);
	}
	
	public void refreshColumnNew() {
		columnNewVector.clear();
		_session = _service.getConnectionDB(_session);
		_session.clear();
		
		Criteria criteria = _session.createCriteria(CertificateNewColumn.class);
		criteria.add(Restrictions.eq("joborder", _joborder));
		List<CertificateNewColumn> dataList = criteria.list();
		for (CertificateNewColumn certificateNewColumn : dataList) {
			Vector<Object> data1 = new Vector<Object>();
			data1.addElement(null);
			data1.addElement(null);
			data1.addElement(certificateNewColumn.getColumnName());
			data1.addElement(certificateNewColumn.getDescription());
			data1.addElement(certificateNewColumn);
			columnNewVector.add(data1);
		}
		columnNewTable.tableChanged(new javax.swing.event.TableModelEvent(columnNewTableModel));
	}
	
	public void deleteNewColumn(CertificateNewColumn certificateNewColumn) {
		_session = _service.getConnectionDB(_session);
		_session.delete(certificateNewColumn);
		_session.flush();
		refreshColumnNew();
	}
	
	public void editNewColumn(CertificateNewColumn certificateNewColumn) {
		_certificateNewTambah.setVisible(_joborder, certificateNewColumn);
	}
}

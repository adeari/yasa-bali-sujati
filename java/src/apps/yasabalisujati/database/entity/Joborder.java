package apps.yasabalisujati.database.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "joborder")
public class Joborder implements Serializable {
	private static final long serialVersionUID = -9197097517306991082L;
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name = "kode")
    private String kode;
	
	@ManyToOne
	@JoinColumn(name = "customer")
	private Customer customer;	
	
	@ManyToOne
	@JoinColumn(name = "exportir")
	private Customer exportir;

	@Column(name = "jenis_kegiatan")
    private String jenis_kegiatan;
	
	@Column(name = "tgl_pelaksanaan")
	private Timestamp tgl_pelaksanaan;
	
	@Column(name = "catatan")
    private String catatan;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "created_by")
	private User createBy;
	
	@ManyToOne
	@JoinColumn(name = "updated_by")
	private User updatedBy;
	
	@ManyToOne
	@JoinColumn(name = "filling")
	private Filling filling;

	@Column(name = "status")
	private String status;
	
	@Column(name = "t4_pelaksanaan")
	private String t4Pelaksanaan;
	
	@Column(name = "komoditi")
	private String komoditi;
	
	@Column(name = "partai")
	private String partai;
	
	@Column(name = "destinasi")
	private String destinasi;
	
	@Column(name = "pegawainame")
	private String pegawainame;
	
	@Column(name = "validasiname")
	private String validasiname;
	
	@Column(name = "consignee")
	private String consignee;
	
	@Column(name = "vessel")
	private String vessel;
	
	@Column(name = "blno")
	private String blno;
	
	@Column(name = "containerno")
	private String containerno;
	
	@Column(name = "type_of_wood_packing")
	private String type_of_wood_packing;
	
	@Column(name = "quantity")
	private String quantity;
	
	@Column(name = "treatment")
	private String treatment;
	
	@Column(name = "wood_core_temperatur")
	private String wood_core_temperatur;
	
	@Column(name = "exposure_time")
	private String exposure_time;
	
	@Column(name = "fumigant")
	private String fumigant;
	
	@Column(name = "dosage_rate")
	private String dosage_rate;
	
	@Column(name = "certificate_number")
	private String certificate_number;
	
	@Column(name = "tgl_cetak")
	private Date tgl_cetak;

	@Column(name = "downloadpath")
	private String downloadpath;
	
	@Column(name = "downloadpath_party")
	private String downloadpathParty;
	
	@Column(name = "consigmentfile")
	private byte[] consigmentfile;
	
	@Column(name = "partyfile")
	private byte[] partyfile;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getKode() {
		return kode;
	}

	public void setKode(String kode) {
		this.kode = kode;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	public Customer getExportir() {
		return exportir;
	}

	public void setExportir(Customer exportir) {
		this.exportir = exportir;
	}

	public String getJenis_kegiatan() {
		return jenis_kegiatan;
	}

	public void setJenis_kegiatan(String jenis_kegiatan) {
		this.jenis_kegiatan = jenis_kegiatan;
	}

	public Timestamp getTgl_pelaksanaan() {
		return tgl_pelaksanaan;
	}

	public void setTgl_pelaksanaan(Timestamp tgl_pelaksanaan) {
		this.tgl_pelaksanaan = tgl_pelaksanaan;
	}

	public String getCatatan() {
		return catatan;
	}

	public void setCatatan(String catatan) {
		this.catatan = catatan;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	public Timestamp getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Timestamp updatedAt) {
		this.updatedAt = updatedAt;
	}

	public User getCreateBy() {
		return createBy;
	}

	public void setCreateBy(User createBy) {
		this.createBy = createBy;
	}

	public User getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(User updatedBy) {
		this.updatedBy = updatedBy;
	}

	public Filling getFilling() {
		return filling;
	}

	public void setFilling(Filling filling) {
		this.filling = filling;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getT4Pelaksanaan() {
		return t4Pelaksanaan;
	}

	public void setT4Pelaksanaan(String t4Pelaksanaan) {
		this.t4Pelaksanaan = t4Pelaksanaan;
	}

	public String getKomoditi() {
		return komoditi;
	}

	public void setKomoditi(String komoditi) {
		this.komoditi = komoditi;
	}

	public String getPartai() {
		return partai;
	}

	public void setPartai(String partai) {
		this.partai = partai;
	}

	public String getDestinasi() {
		return destinasi;
	}

	public void setDestinasi(String destinasi) {
		this.destinasi = destinasi;
	}

	public String getPegawainame() {
		return pegawainame;
	}

	public void setPegawainame(String pegawainame) {
		this.pegawainame = pegawainame;
	}

	public String getValidasiname() {
		return validasiname;
	}

	public void setValidasiname(String validasiname) {
		this.validasiname = validasiname;
	}

	public String getConsignee() {
		return consignee;
	}

	public void setConsignee(String consignee) {
		this.consignee = consignee;
	}

	public String getVessel() {
		return vessel;
	}

	public void setVessel(String vessel) {
		this.vessel = vessel;
	}

	public String getBlno() {
		return blno;
	}

	public void setBlno(String blno) {
		this.blno = blno;
	}

	public String getContainerno() {
		return containerno;
	}

	public void setContainerno(String containerno) {
		this.containerno = containerno;
	}

	public String getType_of_wood_packing() {
		return type_of_wood_packing;
	}

	public void setType_of_wood_packing(String type_of_wood_packing) {
		this.type_of_wood_packing = type_of_wood_packing;
	}

	public String getQuantity() {
		return quantity;
	}

	public void setQuantity(String quantity) {
		this.quantity = quantity;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}

	public String getWood_core_temperatur() {
		return wood_core_temperatur;
	}

	public void setWood_core_temperatur(String wood_core_temperatur) {
		this.wood_core_temperatur = wood_core_temperatur;
	}

	public String getExposure_time() {
		return exposure_time;
	}

	public void setExposure_time(String exposure_time) {
		this.exposure_time = exposure_time;
	}

	public String getFumigant() {
		return fumigant;
	}

	public void setFumigant(String fumigant) {
		this.fumigant = fumigant;
	}

	public String getDosage_rate() {
		return dosage_rate;
	}

	public void setDosage_rate(String dosage_rate) {
		this.dosage_rate = dosage_rate;
	}

	public String getCertificate_number() {
		return certificate_number;
	}

	public void setCertificate_number(String certificate_number) {
		this.certificate_number = certificate_number;
	}

	public Date getTgl_cetak() {
		return tgl_cetak;
	}

	public void setTgl_cetak(Date tgl_cetak) {
		this.tgl_cetak = tgl_cetak;
	}

	public String getDownloadpath() {
		return downloadpath;
	}

	public void setDownloadpath(String downloadpath) {
		this.downloadpath = downloadpath;
	}

	public String getDownloadpathParty() {
		return downloadpathParty;
	}

	public void setDownloadpathParty(String downloadpathParty) {
		this.downloadpathParty = downloadpathParty;
	}

	public byte[] getConsigmentfile() {
		return consigmentfile;
	}

	public void setConsigmentfile(byte[] consigmentfile) {
		this.consigmentfile = consigmentfile;
	}

	public byte[] getPartyfile() {
		return partyfile;
	}

	public void setPartyfile(byte[] partyfile) {
		this.partyfile = partyfile;
	}
	
}

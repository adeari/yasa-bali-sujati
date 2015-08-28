package apps.yasabalisujati.database.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
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
	
	
}

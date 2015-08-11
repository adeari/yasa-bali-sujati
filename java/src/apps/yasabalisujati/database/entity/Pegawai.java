package apps.yasabalisujati.database.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "pegawai")
public class Pegawai implements Serializable {
	private static final long serialVersionUID = 1564641423047927354L;
	
	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
	
	@Column(name = "nama")
    private String nama;
	
	@Column(name = "alamat")
	private String alamat;
	
	@Column(name = "telepon")
	private String telepon;
	
	@Column(name = "divisi")
	private String divisi;
	
	@Column(name = "created_at")
	private Timestamp createdAt;
	
	@Column(name = "updated_at")
	private Timestamp updatedAt;
	
	@Column(name = "isdeleted")
	private Boolean deleted;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getAlamat() {
		return alamat;
	}

	public void setAlamat(String alamat) {
		this.alamat = alamat;
	}

	public String getTelepon() {
		return telepon;
	}

	public void setTelepon(String telepon) {
		this.telepon = telepon;
	}

	public String getDivisi() {
		return divisi;
	}

	public void setDivisi(String divisi) {
		this.divisi = divisi;
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

	public Boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
	}
}

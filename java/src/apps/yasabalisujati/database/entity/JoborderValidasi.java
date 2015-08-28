package apps.yasabalisujati.database.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "joborder_validasi_rules")
public class JoborderValidasi implements Serializable {
	private static final long serialVersionUID = -2416150549202117752L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "joborder_id")
	private Joborder joborder;
	
	@ManyToOne
	@JoinColumn(name = "validasi_rules_id")
	private ValidasiRules validasiRules;
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Joborder getJoborder() {
		return joborder;
	}

	public void setJoborder(Joborder joborder) {
		this.joborder = joborder;
	}

	public ValidasiRules getValidasiRules() {
		return validasiRules;
	}

	public void setValidasiRules(ValidasiRules validasiRules) {
		this.validasiRules = validasiRules;
	}
	
}

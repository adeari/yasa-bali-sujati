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
@Table(name = "certificatenewcolumn")
public class CertificateNewColumn implements Serializable {
	private static final long serialVersionUID = 231293325406616219L;

	@Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "joborder_id")
	private Joborder joborder;
	
	@Column(name = "column_name")
	private String columnName;
	
	@Column(name = "description")
	private String description;

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

	public String getColumnName() {
		return columnName;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}

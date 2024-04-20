package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rfid_credentials")
public class RFIDCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rfid_credentials_id_gen")
	@SequenceGenerator(name = "rfid_credentials_id_gen", sequenceName = "rfid_credentials_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "lrn", nullable = false)
	private Student lrn;

	@Column(name = "hashed_lrn", nullable = false, length = 32)
	private String hashedLrn;

	@Column(name = "salt", nullable = false, length = 16)
	private String salt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getLrn() {
		return lrn;
	}

	public void setLrn(Student lrn) {
		this.lrn = lrn;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public void setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "guardians")
public class Guardian {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "guardians_id_gen")
	@SequenceGenerator(name = "guardians_id_gen", sequenceName = "guardians_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "student_lrn")
	private Student studentLrn;

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "contact_number", length = 32)
	private String contactNumber;

	public Guardian() {
	}

	public Guardian(Integer id, Student studentLrn, String fullName, String contactNumber) {
		this.id = id;
		this.studentLrn = studentLrn;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getStudentLrn() {
		return studentLrn;
	}

	public void setStudentLrn(Student studentLrn) {
		this.studentLrn = studentLrn;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		this.fullName = fullName;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public void setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
	}

}
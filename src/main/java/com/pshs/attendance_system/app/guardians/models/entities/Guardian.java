

package com.pshs.attendance_system.app.guardians.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pshs.attendance_system.app.guardians.models.dto.GuardianDTO;
import com.pshs.attendance_system.app.students.models.entities.Student;
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

	@Column(name = "full_name", nullable = false)
	private String fullName;

	@Column(name = "contact_number", length = 32)
	private String contactNumber;

	@OneToOne
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JsonIgnore
	private Student student;

	public Guardian() {
	}

	public Guardian(Integer id,  String fullName, String contactNumber) {
		this.id = id;
		this.fullName = fullName;
		this.contactNumber = contactNumber;
	}

	public Student getStudent() {
		return student;
	}

	public Guardian setStudent(Student student) {
		this.student = student;
		return this;
	}

	public GuardianDTO toDTO() {
		return new GuardianDTO(id, fullName, contactNumber);
	}

	public Integer getId() {
		return id;
	}

	public Guardian setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getFullName() {
		return fullName;
	}

	public Guardian setFullName(String fullName) {
		this.fullName = fullName;
		return this;
	}

	public String getContactNumber() {
		return contactNumber;
	}

	public Guardian setContactNumber(String contactNumber) {
		this.contactNumber = contactNumber;
		return this;
	}
}
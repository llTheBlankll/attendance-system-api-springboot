package com.pshs.attendance_system.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "teachers")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Teacher {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "teachers_id_gen")
	@SequenceGenerator(name = "teachers_id_gen", sequenceName = "teachers_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "first_name", length = 32)
	private String firstName;

	@Column(name = "last_name", length = 32)
	private String lastName;

	@Column(name = "sex", length = 8)
	private String sex;

	@OneToMany(mappedBy = "teacher")
	private Set<Section> sections = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Set<Section> getSections() {
		return sections;
	}

	public void setSections(Set<Section> sections) {
		this.sections = sections;
	}

}
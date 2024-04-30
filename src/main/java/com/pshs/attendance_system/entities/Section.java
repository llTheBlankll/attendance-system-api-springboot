/*
 * Copyright (c) 2024  Vince Angelo Batecan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and/or sublicense
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * MODIFICATIONS:
 *
 * Any modifications or derivative works of the Software shall be considered part
 * of the Software and shall be subject to the terms and conditions of this license.
 * Any person or entity making modifications to the Software shall assign and
 * transfer all right, title, and interest in and to such modifications to  Vince Angelo Batecan.
 *  Vince Angelo Batecan shall own all intellectual property rights in and to such modifications.
 *
 * NO COMMERCIAL USE:
 *
 * The Software shall not be sold, rented, leased, or otherwise commercially exploited.
 * The Software is intended for personal, non-commercial use only.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "sections")
public class Section {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sections_id_gen")
	@SequenceGenerator(name = "sections_id_gen", sequenceName = "sections_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "teacher")
	private Teacher teacher;

	@Column(name = "room", nullable = false)
	private String room;

	@ManyToOne(fetch = FetchType.LAZY)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "strand")
	private Strand strand;

	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "grade_level", nullable = false)
	private GradeLevel gradeLevel;

	@Column(name = "section_name", nullable = false)
	private String sectionName;

	@OneToMany(mappedBy = "section")
	private Set<Student> students = new LinkedHashSet<>();

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public String getRoom() {
		return room;
	}

	public void setRoom(String room) {
		this.room = room;
	}

	public Strand getStrand() {
		return strand;
	}

	public void setStrand(Strand strand) {
		this.strand = strand;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public void setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Set<Student> getStudents() {
		return students;
	}

	public void setStudents(Set<Student> students) {
		this.students = students;
	}

}
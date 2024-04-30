/*
 * COPYRIGHT (C) 2024 VINCE ANGELO BATECAN
 *
 * PERMISSION IS HEREBY GRANTED, FREE OF CHARGE, TO STUDENTS, FACULTY, AND STAFF OF PUNTURIN SENIOR HIGH SCHOOL TO USE THIS SOFTWARE FOR EDUCATIONAL PURPOSES ONLY.
 *
 * THE ABOVE COPYRIGHT NOTICE AND THIS PERMISSION NOTICE SHALL BE INCLUDED IN ALL COPIES OR SUBSTANTIAL PORTIONS OF THE SOFTWARE.
 *
 * MODIFICATIONS:
 *
 * ANY MODIFICATIONS OR DERIVATIVE WORKS OF THE SOFTWARE SHALL BE CONSIDERED PART OF THE SOFTWARE AND SHALL BE SUBJECT TO THE TERMS AND CONDITIONS OF THIS LICENSE.
 * ANY PERSON OR ENTITY MAKING MODIFICATIONS TO THE SOFTWARE SHALL ASSIGN AND TRANSFER ALL RIGHT, TITLE, AND INTEREST IN AND TO SUCH MODIFICATIONS TO VINCE ANGELO BATECAN.
 * VINCE ANGELO BATECAN SHALL OWN ALL INTELLECTUAL PROPERTY RIGHTS IN AND TO SUCH MODIFICATIONS.
 *
 * NO COMMERCIAL USE:
 *
 * THE SOFTWARE SHALL NOT BE SOLD, RENTED, LEASED, OR OTHERWISE COMMERCIALLY EXPLOITED. THE SOFTWARE IS INTENDED FOR PERSONAL, NON-COMMERCIAL USE ONLY WITHIN PUNTURIN SENIOR HIGH SCHOOL.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package com.pshs.attendance_system.entities;

import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.ArrayList;
import java.util.List;

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
	private List<Student> students = new ArrayList<>();

	public Integer getId() {
		return id;
	}

	public Section setId(Integer id) {
		this.id = id;
		return this;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public Section setTeacher(Teacher teacher) {
		this.teacher = teacher;
		return this;
	}

	public String getRoom() {
		return room;
	}

	public Section setRoom(String room) {
		this.room = room;
		return this;
	}

	public Strand getStrand() {
		return strand;
	}

	public Section setStrand(Strand strand) {
		this.strand = strand;
		return this;
	}

	public GradeLevel getGradeLevel() {
		return gradeLevel;
	}

	public Section setGradeLevel(GradeLevel gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSectionName() {
		return sectionName;
	}

	public Section setSectionName(String sectionName) {
		this.sectionName = sectionName;
		return this;
	}

	public List<Student> getStudents() {
		return students;
	}

	public Section setStudents(List<Student> students) {
		this.students = students;
		return this;
	}
}
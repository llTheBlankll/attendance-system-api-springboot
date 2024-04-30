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

package com.pshs.attendance_system.dto;

import java.io.Serializable;
import java.util.Objects;

/**
 * DTO for {@link com.pshs.attendance_system.entities.Section}
 */
public class SectionDTO implements Serializable {
	private Integer id;
	private TeacherDTO teacher;
	private String room;
	private StrandDTO strand;
	private GradeLevelDTO gradeLevel;
	private String sectionName;

	public SectionDTO() {
	}

	public SectionDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName) {
		this.id = id;
		this.teacher = teacher;
		this.room = room;
		this.strand = strand;
		this.gradeLevel = gradeLevel;
		this.sectionName = sectionName;
	}

	public Integer getId() {
		return id;
	}

	public SectionDTO setId(Integer id) {
		this.id = id;
		return this;
	}

	public TeacherDTO getTeacher() {
		return teacher;
	}

	public SectionDTO setTeacher(TeacherDTO teacher) {
		this.teacher = teacher;
		return this;
	}

	public String getRoom() {
		return room;
	}

	public SectionDTO setRoom(String room) {
		this.room = room;
		return this;
	}

	public StrandDTO getStrand() {
		return strand;
	}

	public SectionDTO setStrand(StrandDTO strand) {
		this.strand = strand;
		return this;
	}

	public GradeLevelDTO getGradeLevel() {
		return gradeLevel;
	}

	public SectionDTO setGradeLevel(GradeLevelDTO gradeLevel) {
		this.gradeLevel = gradeLevel;
		return this;
	}

	public String getSectionName() {
		return sectionName;
	}

	public SectionDTO setSectionName(String sectionName) {
		this.sectionName = sectionName;
		return this;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		SectionDTO entity = (SectionDTO) o;
		return Objects.equals(this.id, entity.id) &&
			Objects.equals(this.teacher, entity.teacher) &&
			Objects.equals(this.room, entity.room) &&
			Objects.equals(this.strand, entity.strand) &&
			Objects.equals(this.gradeLevel, entity.gradeLevel) &&
			Objects.equals(this.sectionName, entity.sectionName);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, teacher, room, strand, gradeLevel, sectionName);
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + "(" +
			"id = " + id + ", " +
			"teacher = " + teacher + ", " +
			"room = " + room + ", " +
			"strand = " + strand + ", " +
			"gradeLevel = " + gradeLevel + ", " +
			"sectionName = " + sectionName + ")";
	}
}
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

import com.pshs.attendance_system.entities.Section;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class represents a Section with a list of students.
 * DTO for {@link com.pshs.attendance_system.entities.Section}
 */
public class SectionStudentsDTO extends SectionDTO implements Serializable {

	private List<StudentDTO> students = new ArrayList<>();

	public SectionStudentsDTO() {}

	public SectionStudentsDTO(List<StudentDTO> students) {
		this.students = students;
	}

	public SectionStudentsDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName, List<StudentDTO> students) {
		super(id, teacher, room, strand, gradeLevel, sectionName);
		this.students = students;
	}

	public SectionStudentsDTO(Integer id, TeacherDTO teacher, String room, StrandDTO strand, GradeLevelDTO gradeLevel, String sectionName) {
		super(id, teacher, room, strand, gradeLevel, sectionName);
	}

	public SectionStudentsDTO(SectionDTO section, List<StudentDTO> students) {
		super(section.getId(), section.getTeacher(), section.getRoom(), section.getStrand(), section.getGradeLevel(), section.getSectionName());
		this.students = students;
	}

	/**
	 * Assigns the values of the given SectionDTO to this DTO.
	 * The list of students will be empty and must be set manually.
	 *
	 * @param section the SectionDTO to copy values from
	 */
	public SectionStudentsDTO(SectionDTO section) {
		super(section.getId(), section.getTeacher(), section.getRoom(), section.getStrand(), section.getGradeLevel(), section.getSectionName());
	}

	public Section toEntity() {
		return new Section()
			.setId(getId())
			.setTeacher(getTeacher().toEntity())
			.setRoom(getRoom())
			.setStrand(getStrand().toEntity())
			.setGradeLevel(getGradeLevel().toEntity())
			.setSectionName(getSectionName())
			.setStudents(students.stream().map(StudentDTO::toEntity).collect(Collectors.toList()));
	}

	public List<StudentDTO> getStudents() {
		return students;
	}

	public SectionStudentsDTO setStudents(List<StudentDTO> students) {
		this.students = students;
		return this;
	}
}
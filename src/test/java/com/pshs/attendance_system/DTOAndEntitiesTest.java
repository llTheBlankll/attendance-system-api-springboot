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

package com.pshs.attendance_system;

import com.pshs.attendance_system.dto.*;
import com.pshs.attendance_system.entities.*;
import org.junit.jupiter.api.Test;
import java.time.Instant;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Test class for testing the DTOs and Entities if they are correctly implemented.
 */
public class DTOAndEntitiesTest {

	private final Strand strand = new Strand(
		1,
		"STEM"
	);

	private final GradeLevel gradeLevel = new GradeLevel(
		1,
		"Grade 11",
		strand
	);

	private final Teacher teacher = new Teacher(
		1,
		"Juan",
		"Dela Cruz",
		"Male"
	);

	private final Section section = new Section(
		1,
		teacher,
		"Room 403",
		strand,
		gradeLevel,
		"Section A",
		new ArrayList<>()
	);

	private final Student student = new Student(
		1L,
		"Vince Angelo",
		"O",
		"Batecan",
		null,
		gradeLevel,
		"Male",
		section,
		"1234 Punturin St. Punturin, Valenzuela City",
		LocalDate.parse("2004-07-07")
	);

	private final RFIDCredential rfidCredential = new RFIDCredential(
		1,
		student,
		"hashed_lrn",
		"salt"
	);

	private final Guardian guardian = new Guardian(
		1,
		student,
		"Juan Dela Cruz",
		"09123456789"
	);

	private final User user = new User(
		1,
		"vince",
		"hashed_password",
		"llTheBlankll@gmail.com",
		"admin",
		Instant.parse("2024-07-07T00:00:00Z"),
		Instant.parse("2024-07-07T00:00:00Z")
	);

	@Test
	public void testRFIDCredential() {
		// Entities Test
		assert rfidCredential.getId() == 1;
		assert rfidCredential.getLrn().equals(student);
		assert rfidCredential.getHashedLrn().equals("hashed_lrn");
		assert rfidCredential.getSalt().equals("salt");
		System.out.println("Entity Test is successful");

		// DTO Test
		RFIDCredentialDTO rfidCredentialDTO = rfidCredential.toDTO();
		assert rfidCredentialDTO.getId() == 1;
		assert rfidCredentialDTO.getLrn().equals(student.toDTO());
		assert rfidCredentialDTO.getHashedLrn().equals("hashed_lrn");
		assert rfidCredentialDTO.getSalt().equals("salt");
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testGuardian() {
		// Entities Test
		assert guardian.getId() == 1;
		assert guardian.getStudentLrn().equals(student);
		assert guardian.getFullName().equals("Juan Dela Cruz");
		assert guardian.getContactNumber().equals("09123456789");
		System.out.println("Entity Test is successful");

		// DTO Test
		GuardianDTO guardianDTO = guardian.toDTO();
		assert guardianDTO.getId() == 1;
		assert guardianDTO.getStudentLrn().equals(student.toDTO());
		assert guardianDTO.getFullName().equals("Juan Dela Cruz");
		assert guardianDTO.getContactNumber().equals("09123456789");
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testUser() {
		// Entities Test
		assert user.getId() == 1;
		assert user.getUsername().equals("vince");
		assert user.getPassword().equals("hashed_password");
		assert user.getEmail().equals("llTheBlankll@gmail.com");
		assert user.getRole().equals("admin");
		assert user.getLastLogin().equals(Instant.parse("2024-07-07T00:00:00Z"));
		assert user.getCreatedAt().equals(Instant.parse("2024-07-07T00:00:00Z"));
		System.out.println("Entity Test is successful");

		// DTO Test
		UserDTO userDTO = user.toDTO();
		assert userDTO.getId() == 1;
		assert userDTO.getUsername().equals("vince");
		assert userDTO.getPassword().equals("hashed_password");
		assert userDTO.getEmail().equals("llTheBlankll@gmail.com");
		assert userDTO.getRole().equals("admin");
		assert userDTO.getLastLogin().equals(Instant.parse("2024-07-07T00:00:00Z"));
		assert userDTO.getCreatedAt().equals(Instant.parse("2024-07-07T00:00:00Z"));
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testStudent() {
		// Entities Test
		assert student.getId() == 1;
		assert student.getFirstName().equals("Vince Angelo");
		assert student.getMiddleInitial().equals("O");
		assert student.getLastName().equals("Batecan");
		assert student.getPrefix() == null;
		assert student.getGradeLevel().equals(gradeLevel);
		assert student.getSex().equals("Male");
		assert student.getSection().equals(section);
		assert student.getAddress().equals("1234 Punturin St. Punturin, Valenzuela City");
		assert student.getBirthdate().equals(LocalDate.parse("2004-07-07"));
		System.out.println("Entity Test is successful");

		// DTO Test
		StudentDTO studentDTO = student.toDTO();
		assert studentDTO.getId() == 1;
		assert studentDTO.getFirstName().equals("Vince Angelo");
		assert studentDTO.getMiddleInitial().equals("O");
		assert studentDTO.getLastName().equals("Batecan");
		assert studentDTO.getPrefix() == null;
		assert studentDTO.getGradeLevel().equals(gradeLevel.toDTO());
		assert studentDTO.getSex().equals("Male");
		assert studentDTO.getSection().equals(section.toDTO());
		assert studentDTO.getAddress().equals("1234 Punturin St. Punturin, Valenzuela City");
		assert studentDTO.getBirthdate().equals(LocalDate.parse("2004-07-07"));
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testSection() {
		// Entities Test
		assert section.getId() == 1;
		assert section.getTeacher().equals(teacher);
		assert section.getRoom().equals("Room 403");
		assert section.getStrand().equals(strand);
		assert section.getGradeLevel().equals(gradeLevel);
		assert section.getSectionName().equals("Section A");
		System.out.println(section.getStudents().size());
		assert section.getStudents().size() == 1;
		System.out.println("Entity Test is successful");

		// DTO Test
		SectionStudentsDTO sectionDTO = new SectionStudentsDTO(
			section.toDTO(),
			List.of(student.toDTO())
		);

		assert sectionDTO.getId() == 1;
		assert sectionDTO.getTeacher().equals(teacher.toDTO());
		assert sectionDTO.getRoom().equals("Room 403");
		assert sectionDTO.getStrand().equals(strand.toDTO());
		assert sectionDTO.getGradeLevel().equals(gradeLevel.toDTO());
		assert sectionDTO.getSectionName().equals("Section A");
		assert sectionDTO.getStudents().equals(List.of(student.toDTO()));
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testStrand() {
		// Entities Test
		assert strand.getId() == 1;
		assert strand.getName().equals("STEM");
		System.out.println("Entity Test is successful");

		// DTO Test
		StrandDTO strandDTO = strand.toDTO();
		assert strandDTO.getId() == 1;
		assert strandDTO.getName().equals("STEM");
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testGradeLevel() {
		// Entities Test
		assert gradeLevel.getId() == 1;
		assert gradeLevel.getName().equals("Grade 11");
		assert gradeLevel.getStrand().equals(strand);
		System.out.println("Entity Test is successful");

		// DTO Test
		GradeLevelDTO gradeLevelDTO = gradeLevel.toDTO();
		assert gradeLevelDTO.getId() == 1;
		assert gradeLevelDTO.getName().equals("Grade 11");
		assert gradeLevelDTO.getStrand().equals(strand.toDTO());
		System.out.println("DTO Test is successful");
	}

	@Test
	public void testTeacher() {
		// Entities Test
		assert teacher.getId() == 1;
		assert teacher.getFirstName().equals("Juan");
		assert teacher.getLastName().equals("Dela Cruz");
		assert teacher.getSex().equals("Male");
		System.out.println("Entity Test is successful");

		// DTO Test
		TeacherDTO teacherDTO = teacher.toDTO();
		assert teacherDTO.getId() == 1;
		assert teacherDTO.getFirstName().equals("Juan");
		assert teacherDTO.getLastName().equals("Dela Cruz");
		assert teacherDTO.getSex().equals("Male");
		System.out.println("DTO Test is successful");
	}

	@Test
public void testStudentSectionDTO() {
		// DTO Test
		SectionStudentsDTO sectionStudentsDTO = new SectionStudentsDTO(
			1,
			teacher.toDTO(),
			"Room 403",
			strand.toDTO(),
			gradeLevel.toDTO(),
			"Section A",
			List.of(student.toDTO())
		);

		assert sectionStudentsDTO.getId() == 1;
		assert sectionStudentsDTO.getTeacher().equals(teacher.toDTO());
		assert sectionStudentsDTO.getRoom().equals("Room 403");
		assert sectionStudentsDTO.getStrand().equals(strand.toDTO());
		assert sectionStudentsDTO.getGradeLevel().equals(gradeLevel.toDTO());
		assert sectionStudentsDTO.getSectionName().equals("Section A");
		assert sectionStudentsDTO.getStudents().equals(List.of(student.toDTO()));
		System.out.println("DTO Test is successful");

		// To Entity
		Section section = sectionStudentsDTO.toEntity();

		assert section.getId() == 1;
		assert section.getTeacher().getId().equals(teacher.getId());
		assert section.getTeacher().getFirstName().equals("Juan");
		assert section.getTeacher().getLastName().equals("Dela Cruz");
		assert section.getTeacher().getSex().equals("Male");
		assert section.getRoom().equals("Room 403");
		assert section.getStrand().getId().equals(strand.getId());
		assert section.getStrand().getName().equals("STEM");
		assert section.getGradeLevel().getId().equals(gradeLevel.getId());
		assert section.getGradeLevel().getName().equals("Grade 11");
		assert section.getSectionName().equals("Section A");
		assert section.getStudents().size() == 1;
		System.out.println("Entity Test is successful");
	}
}
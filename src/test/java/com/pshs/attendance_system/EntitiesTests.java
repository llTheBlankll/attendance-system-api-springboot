package com.pshs.attendance_system;

import com.pshs.attendance_system.entities.*;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EntitiesTests {

	/**
	 * This test is used to check if the application context loads successfully.
	 */

	@Test
	void contextLoads() {
	}

	@Test
	void testStudent() {
		Student student = new Student();
		student.setId(1L);
		student.setFirstName("Juan");
		student.setMiddleInitial("D");
		student.setLastName("Dela Cruz");
		student.setSex("M");
		student.setGradeLevel(
			new GradeLevel(1, "Grade 1", "Silang",
				new Strand(
					1, "STEM"
				))
		);
		student.setSection(
			new Section(1,
				new Teacher(),
				"Room 1",
				new Strand(1,
					"STEM"
				), new GradeLevel(
				1,
				"Grade 1",
				"Silang", new Strand(1,
				"STEM")), "Section 1"
			)
		);
		student.setAddress("Address");
		student.setBirthdate(LocalDate.parse("2021-01-01"));

		assertEquals(1L, student.getId());
		assertEquals("Juan", student.getFirstName());
		assertEquals("D", student.getMiddleInitial());
		assertEquals("Dela Cruz", student.getLastName());
		assertEquals(1, student.getGradeLevel().getId());
		assertEquals("M", student.getSex());
		assertEquals("Grade 1", student.getGradeLevel().getLevel());
		assertEquals("Silang", student.getGradeLevel().getName());
		assertEquals(1, student.getGradeLevel().getStrand().getId());
		assertEquals("STEM", student.getGradeLevel().getStrand().getName());

		// DTO Test
		assertEquals(1L, student.toDTO().getId());
		assertEquals("Juan", student.toDTO().getFirstName());
		assertEquals("D", student.toDTO().getMiddleInitial());
		assertEquals("Dela Cruz", student.toDTO().getLastName());
		assertEquals("M", student.toDTO().getSex());
		assertEquals(1, student.toDTO().getGradeLevel().getId());
		assertEquals("Grade 1", student.toDTO().getGradeLevel().getLevel());
		assertEquals("Silang", student.toDTO().getGradeLevel().getName());
		assertEquals(1, student.toDTO().getGradeLevel().getStrand().getId());
		assertEquals("STEM", student.toDTO().getGradeLevel().getStrand().getName());
	}

	@Test
	void testGradeLevel() {
		GradeLevel gradeLevel = new GradeLevel();
		gradeLevel.setId(1);
		gradeLevel.setLevel("Grade 1");
		gradeLevel.setName("Silang");
		gradeLevel.setStrand(new Strand(1, "STEM"));

		assertEquals(1, gradeLevel.getId());
		assertEquals("Grade 1", gradeLevel.getLevel());
		assertEquals("Silang", gradeLevel.getName());
		assertEquals(1, gradeLevel.getStrand().getId());
		assertEquals("STEM", gradeLevel.getStrand().getName());

		// DTO Test
		assertEquals(1, gradeLevel.toDTO().getId());
		assertEquals("Grade 1", gradeLevel.toDTO().getLevel());
		assertEquals("Silang", gradeLevel.toDTO().getName());
		assertEquals(1, gradeLevel.toDTO().getStrand().getId());
		assertEquals("STEM", gradeLevel.toDTO().getStrand().getName());
	}

	@Test
	void testStrand() {
		Strand strand = new Strand();
		strand.setId(1);
		strand.setName("STEM");

		assertEquals(1, strand.getId());
		assertEquals("STEM", strand.getName());

		// DTO Test
		assertEquals(1, strand.toDTO().getId());
		assertEquals("STEM", strand.toDTO().getName());
	}

	@Test
	void testTeacher() {
		Strand strand = new Strand(1, "STEM");
		GradeLevel gradeLevel = new GradeLevel(1, "Grade 1", "Silang", strand);
		Teacher teacher = new Teacher();
		teacher.setId(1);
		teacher.setFirstName("Juan");
		teacher.setLastName("Dela Cruz");
		teacher.setSex("M");
		teacher.setSections(
			new ArrayList<>(
				List.of(
					new Section(1, new Teacher(), "Room 1", strand, gradeLevel, "Section 1",
						new ArrayList<>(
							List.of(
								new Student()
							)
						)
					)
				)
			)
		);

		assertEquals(1, teacher.getId());
		assertEquals("Juan", teacher.getFirstName());
		assertEquals("Dela Cruz", teacher.getLastName());
		assertEquals("M", teacher.getSex());
		assertEquals(1, teacher.getSections().getFirst().getId());
		assertEquals("Room 1", teacher.getSections().getFirst().getRoom());
		assertEquals("Section 1", teacher.getSections().getFirst().getSectionName());
		assertEquals(1, teacher.getSections().getFirst().getStudents().size());

		// DTO Test
		assertEquals(1, teacher.toDTO().getId());
		assertEquals("Juan", teacher.toDTO().getFirstName());
		assertEquals("Dela Cruz", teacher.toDTO().getLastName());
		assertEquals("M", teacher.toDTO().getSex());
		assertEquals(1, teacher.toDTO().getSections().getFirst().getId());
		assertEquals("Room 1", teacher.toDTO().getSections().getFirst().getRoom());
		assertEquals("Section 1", teacher.toDTO().getSections().getFirst().getSectionName());
		assertEquals(1, teacher.toDTO().getSections().getFirst().getStudents().size());
	}

	@Test
	void testSections() {
		Section section = new Section();
		section.setId(1);
		section.setRoom("Room 1");
		section.setSectionName("Section 1");
		section.setStrand(new Strand(
			1, "STEM"
		));
		section.setGradeLevel(new GradeLevel(
			1, "Grade 1", "Silang", new Strand(
			1, "STEM"
		)
		));
		section.setTeacher(new Teacher(
			1, "Juan", "Dela Cruz", "M"
		));
		section.setStudents(
			new ArrayList<>(
				List.of(
					new Student()
				)
			)
		);

		assertEquals(1, section.getId());
		assertEquals("Room 1", section.getRoom());
		assertEquals("Section 1", section.getSectionName());
		assertEquals(1, section.getStudents().size());
		assertEquals(1, section.getTeacher().getId());
		assertEquals("Juan", section.getTeacher().getFirstName());
		assertEquals("Dela Cruz", section.getTeacher().getLastName());
		assertEquals("M", section.getTeacher().getSex());
		assertEquals(1, section.getGradeLevel().getId());
		assertEquals("Grade 1", section.getGradeLevel().getLevel());
		assertEquals("Silang", section.getGradeLevel().getName());
		assertEquals(1, section.getGradeLevel().getStrand().getId());
		assertEquals("STEM", section.getGradeLevel().getStrand().getName());
		assertEquals(1, section.getStrand().getId());
		assertEquals("STEM", section.getStrand().getName());

		// DTO Test
		assertEquals(1, section.toDTO().getId());
		assertEquals("Room 1", section.toDTO().getRoom());
		assertEquals("Section 1", section.toDTO().getSectionName());
		assertEquals(1, section.toDTO().getStudents().size());
		assertEquals(1, section.toDTO().getTeacher().getId());
		assertEquals("Juan", section.toDTO().getTeacher().getFirstName());
		assertEquals("Dela Cruz", section.toDTO().getTeacher().getLastName());
		assertEquals("M", section.toDTO().getTeacher().getSex());
		assertEquals(1, section.toDTO().getGradeLevel().getId());
		assertEquals("Grade 1", section.toDTO().getGradeLevel().getLevel());
		assertEquals("Silang", section.toDTO().getGradeLevel().getName());
		assertEquals(1, section.toDTO().getGradeLevel().getStrand().getId());
		assertEquals("STEM", section.toDTO().getGradeLevel().getStrand().getName());
		assertEquals(1, section.toDTO().getStrand().getId());
		assertEquals("STEM", section.toDTO().getStrand().getName());
	}

	@Test
	void testAttendance() {
		Attendance attendance = getAttendance();

		System.out.println(attendance);
		assertEquals(1L, attendance.getId());
		assertEquals("Juan", attendance.getStudent().getFirstName());
		assertEquals("D", attendance.getStudent().getMiddleInitial());
		assertEquals("Dela Cruz", attendance.getStudent().getLastName());
		assertEquals(1, attendance.getStudent().getGradeLevel().getId());
		assertEquals("Grade 1", attendance.getStudent().getGradeLevel().getLevel());
		assertEquals("Silang", attendance.getStudent().getGradeLevel().getName());
		assertEquals(1, attendance.getStudent().getGradeLevel().getStrand().getId());
		assertEquals("STEM", attendance.getStudent().getGradeLevel().getStrand().getName());
		assertEquals("M", attendance.getStudent().getSex());
		assertEquals(1, attendance.getStudent().getSection().getId());
		assertEquals("Room 1", attendance.getStudent().getSection().getRoom());
		assertEquals("Section 1", attendance.getStudent().getSection().getSectionName());
		assertEquals(2, attendance.getStudent().getSection().getStudents().size());
		assertEquals("Address", attendance.getStudent().getAddress());
		assertEquals("Late", attendance.getStatus());
		assertEquals("2021-01-01", attendance.getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		assertEquals("12:00:00", attendance.getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		assertEquals("12:00:00", attendance.getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm:ss")));

		// DTO Test

		System.out.println(attendance.toDTO().toString());
		assertNotEquals(attendance.toDTO(), null);
		assertEquals(1L, attendance.toDTO().getId());
		assertEquals("Juan", attendance.toDTO().getStudent().getFirstName());
		assertEquals("D", attendance.toDTO().getStudent().getMiddleInitial());
		assertEquals("Dela Cruz", attendance.toDTO().getStudent().getLastName());
		assertEquals(1, attendance.toDTO().getStudent().getGradeLevel().getId());
		assertEquals("Grade 1", attendance.toDTO().getStudent().getGradeLevel().getLevel());
		assertEquals("Silang", attendance.toDTO().getStudent().getGradeLevel().getName());
		assertEquals(1, attendance.toDTO().getStudent().getGradeLevel().getStrand().getId());
		assertEquals("STEM", attendance.toDTO().getStudent().getGradeLevel().getStrand().getName());
		assertEquals("M", attendance.toDTO().getStudent().getSex());
		assertEquals(1, attendance.toDTO().getStudent().getSection().getId());
		assertEquals("Room 1", attendance.toDTO().getStudent().getSection().getRoom());
		assertEquals("Section 1", attendance.toDTO().getStudent().getSection().getSectionName());
		assertEquals(1, attendance.toDTO().getStudent().getSection().getStudents().size());
		assertEquals("Address", attendance.toDTO().getStudent().getAddress());
		assertEquals("Late", attendance.toDTO().getStatus());
		assertEquals("2021-01-01", attendance.toDTO().getDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		assertEquals("12:00:00", attendance.toDTO().getTime().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
		assertEquals("12:00:00", attendance.toDTO().getTimeOut().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
	}

	private static Attendance getAttendance() {
		Strand strand = new Strand(1, "STEM");
		GradeLevel gradeLevel = new GradeLevel(1, "Grade 1", "Silang", strand);
		Student student = new Student();
		student.setId(1L);
		student.setFirstName("Juan");
		student.setMiddleInitial("D");
		student.setLastName("Dela Cruz");
		student.setSex("M");
		student.setGradeLevel(
			new GradeLevel(1, "Grade 1", "Silang",
				new Strand(
					1, "STEM"
				))
		);
		student.setSection(
			new Section(
				1, new Teacher(), "Room 1", strand, gradeLevel, "Section 1",
				new ArrayList<>(
					List.of(
						new Student(),
						student
					)
				)
			)
		);
		student.setAddress("Address");
		student.setBirthdate(LocalDate.parse("2021-01-01"));

		Attendance attendance = new Attendance();
		attendance.setId(1L);
		attendance.setStudent(student);

		// Convert String to LocalDate
		attendance.setDate(LocalDate.parse("2021-01-01"));
		attendance.setStatus("Late");

		// Convert String to LocalTime
		attendance.setTime(LocalTime.parse("12:00:00"));
		attendance.setTimeOut(LocalTime.parse("12:00:00"));
		return attendance;
	}
}
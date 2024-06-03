package com.pshs.attendance_system.controllers.v1.secured.student;

import com.pshs.attendance_system.dto.StudentDTO;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.services.StudentService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/students")
public class StudentController {

	private static final Logger logger = LogManager.getLogger(StudentController.class);
	private final StudentService studentService;

	public StudentController(StudentService studentService) {
		this.studentService = studentService;
	}

	@GetMapping("/")
	public ResponseEntity<?> getAllStudents(@RequestParam int page, @RequestParam int size) {
		Page<Student> studentPage = studentService.getAllStudents(PageRequest.of(page, size));
		Page<StudentDTO> dtoPage = new PageImpl<>(studentPage.getContent().stream()
			.map(Student::toDTO)
			.collect(Collectors.toList()), PageRequest.of(page, size), studentPage.getTotalElements());
		logger.info("Returning {} students", dtoPage.getTotalElements());
		return ResponseEntity.ok(dtoPage);
	}


}
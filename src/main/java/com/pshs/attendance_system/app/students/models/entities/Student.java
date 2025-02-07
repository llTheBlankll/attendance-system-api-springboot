

package com.pshs.attendance_system.app.students.models.entities;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.guardians.models.entities.Guardian;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import com.pshs.attendance_system.app.students.enums.Sex;
import com.pshs.attendance_system.app.students.models.dto.StudentDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDate;

@Entity
@Table(name = "students")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Student {
	@Id
	@Column(name = "lrn", nullable = false)
	private Long id;

	@Column(name = "first_name", nullable = false, length = 128)
	private String firstName;

	@Column(name = "middle_initial", length = Integer.MAX_VALUE)
	private String middleInitial;

	@Column(name = "last_name", nullable = false, length = 128)
	private String lastName;

	@Column(name = "prefix", length = 4)
	private String prefix;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "grade_level")
	private GradeLevel gradeLevel;

	@Column(name = "sex", length = 6)
	@Enumerated(EnumType.STRING)
	private Sex sex;

	@ManyToOne(fetch = FetchType.EAGER)
	@OnDelete(action = OnDeleteAction.SET_NULL)
	@JoinColumn(name = "section_id")
	private Section section;

	@OneToOne(mappedBy = "student")
	@OnDelete(action = OnDeleteAction.SET_NULL)
	private Guardian guardian;

	@Column(name = "address", length = Integer.MAX_VALUE)
	private String address;

	@Column(name = "birthdate", nullable = false)
	private LocalDate birthdate;

	public StudentDTO toDTO() {
		return new StudentDTO(id,
			firstName,
			middleInitial,
			lastName,
			prefix,
			gradeLevel.toDTO(),
			(guardian != null) ? guardian.toDTO() : null,
			sex,
			section.toDTO(),
			address,
			birthdate
		);
	}
}
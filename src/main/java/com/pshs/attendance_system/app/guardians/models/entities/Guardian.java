

package com.pshs.attendance_system.app.guardians.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.pshs.attendance_system.app.guardians.models.dto.GuardianDTO;
import com.pshs.attendance_system.app.students.models.entities.Student;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "guardians")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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

	public GuardianDTO toDTO() {
		return new GuardianDTO(id, fullName, contactNumber, student.toDTO());
	}
}
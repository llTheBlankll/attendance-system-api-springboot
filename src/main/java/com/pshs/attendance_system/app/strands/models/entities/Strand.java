

package com.pshs.attendance_system.app.strands.models.entities;

import com.pshs.attendance_system.app.strands.models.dto.StrandDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "strands")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Strand {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "strand_id_gen")
	@SequenceGenerator(name = "strand_id_gen", sequenceName = "strand_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false)
	private String name;

	public StrandDTO toDTO() {
		return new StrandDTO(id, name);
	}
}
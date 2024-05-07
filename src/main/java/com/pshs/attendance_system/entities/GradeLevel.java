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

import com.pshs.attendance_system.dto.GradeLevelDTO;
import jakarta.persistence.*;

@Entity
@Table(name = "grade_levels")
public class GradeLevel {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "grade_levels_id_gen")
	@SequenceGenerator(name = "grade_levels_id_gen", sequenceName = "grade_levels_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", nullable = false, length = 128)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "strand")
	private Strand strand;

	public GradeLevel() {}

	public GradeLevel(Integer id, String name, Strand strand) {
		this.id = id;
		this.name = name;
		this.strand = strand;
	}

	public GradeLevelDTO toDTO() {
		return new GradeLevelDTO(id, name, strand.toDTO());
	}

	public Integer getId() {
		return id;
	}

	public GradeLevel setId(Integer id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return name;
	}

	public GradeLevel setName(String name) {
		this.name = name;
		return this;
	}

	public Strand getStrand() {
		return strand;
	}

	public GradeLevel setStrand(Strand strand) {
		this.strand = strand;
		return this;
	}
}
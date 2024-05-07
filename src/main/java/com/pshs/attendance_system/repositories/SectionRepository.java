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

package com.pshs.attendance_system.repositories;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.entities.Teacher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SectionRepository extends JpaRepository<Section, Integer> {
	@Query("select s from Section s where s.sectionName like concat('%', :sectionName, '%')")
	Page<Section> searchSectionBySectionName(@Param("sectionName") @NonNull String sectionName, Pageable pageable);

	@Query("select s from Section s where s.room like concat('%', :room, '%')")
	Page<Section> searchSectionByRoom(@Param("room") @NonNull String room, Pageable pageable);

	@Query("select s from Section s where s.gradeLevel.id = :id")
	Page<Section> getSectionByGradeLevel(@Param("id") @NonNull Integer id, Pageable pageable);

	@Query("select s from Section s where s.strand.id = :id")
	Page<Section> getSectionByStrand(@Param("id") @NonNull Integer id, Pageable pageable);

	@Query("select s from Section s where s.teacher.id = :id")
	Page<Section> getSectionByTeacher(@Param("id") @NonNull Integer id, Pageable pageable);

	@Transactional
	@Modifying
	@Query("update Section s set s.gradeLevel = :gradeLevel where s.id = :id")
	int updateSectionGradeLevel(@Param("gradeLevel") GradeLevel gradeLevel, @NonNull @Param("id") Integer id);

	@Query("select s.gradeLevel from Section s where s.id = :id")
	Optional<GradeLevel> getGradeLevelBySectionId(@Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.sectionName = :sectionName where s.id = :id")
	int updateSectionName(@NonNull @Param("sectionName") String sectionName, @NonNull @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.strand = :strand where s.id = :id")
	int updateSectionStrand(@NonNull @Param("strand") Strand strand, @NonNull @Param("id") Integer id);

	@Query("select s.strand from Section s where s.id = :id")
	Optional<Strand> getStrandOfSectionId(@Param("id") @NonNull Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.room = :room where s.id = :id")
	int updateSectionRoomName(@NonNull @Param("room") String room, @NonNull @Param("id") Integer id);

	@Transactional
	@Modifying
	@Query("update Section s set s.teacher = :teacher where s.id = :id")
	int updateSectionTeacher(@NonNull @Param("teacher") Teacher teacher, @NonNull @Param("id") Integer id);
}
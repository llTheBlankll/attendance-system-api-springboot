

package com.pshs.attendance_system.app.sections.services;

import com.pshs.attendance_system.app.gradelevels.models.entities.GradeLevel;
import com.pshs.attendance_system.app.sections.models.entities.Section;
import com.pshs.attendance_system.app.strands.models.entities.Strand;
import com.pshs.attendance_system.app.teachers.models.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface SectionService {

	/**
	 * Create a new section in the database.
	 *
	 * @param section section object that will be created.
	 * @return ExecutionStatus object that contains the status of the operation. Can be SUCCESS or FAILED.
	 */
	ExecutionStatus createSection(Section section);

	/**
	 * Delete a section in the database.
	 *
	 * @param sectionId The id of the section that will be deleted.
	 * @return ExecutionStatus object that contains the status of the operation. Can be SUCCESS or FAILED.
	 */
	ExecutionStatus deleteSection(int sectionId);

	// Region Update Section

	/**
	 * Update the section with a given id and the update it with the new section object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param section   the new updated information of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSection(int sectionId, Section section);

	/**
	 * Update the teacher of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param teacherId id of the teacher that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionTeacher(int sectionId, int teacherId);

	/**
	 * The teacher object will be used to update the teacher of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param teacher   the teacher object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionTeacher(int sectionId, Teacher teacher);

	/**
	 * Update the strand of the section with the given section id and strand id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strandId  id of the strand that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionStrand(int sectionId, int strandId);

	/**
	 * Update the strand of the section with the given section id and strand object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strand    the strand object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionStrand(int sectionId, Strand strand);

	/**
	 * Update the section name of the section with the given section id.
	 *
	 * @param sectionId   id of the section that will be updated
	 * @param sectionName the new section name of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionName(int sectionId, String sectionName);

	/**
	 * Update the grade level of the section with the given section id and grade level id.
	 *
	 * @param sectionId    id of the section that will be updated
	 * @param gradeLevelId id of the grade level that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionGradeLevel(int sectionId, int gradeLevelId);

	/**
	 * Update the grade level of the section with the given section id and grade level object.
	 *
	 * @param sectionId  id of the section that will be updated
	 * @param gradeLevel the grade level object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionGradeLevel(int sectionId, GradeLevel gradeLevel);
	// End Region Update Section

	/**
	 * Get all sections existing in the database.
	 *
	 * @param page A Page Request Object
	 * @return The page containing all the sections
	 */
	Page<Section> getAllSections(Pageable page);

	/**
	 * Get all sections existing in the database.
	 *
	 * @return The list of sections.
	 */
	List<Section> getAllSections();

	/**
	 * Get the section information with the given section id.
	 *
	 * @param sectionId id of the section that will be retrieved
	 * @return The section object with the given section id. If not found, return null.
	 */
	Optional<Section> getSection(int sectionId);

	/**
	 * Get all the sections associated with the teacher with the given teacher id.
	 *
	 * @param teacherId id of the teacher
	 * @return return a page of sections.
	 */
	Page<Section> getSectionByTeacher(int teacherId, Pageable page);

	/**
	 * Get all the sections associated with the teacher object.
	 *
	 * @param teacher teacher object
	 * @return return a page of section
	 */
	Page<Section> getSectionByTeacher(Teacher teacher, Pageable page);

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strandId id of the strand
	 * @param page     the page number
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByStrand(int strandId, Pageable page);

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strand the strand object that will be used to get the sections
	 * @param page   the page number
	 * @return return a page of sections
	 */
	Page<Section> getSectionByStrand(Strand strand, Pageable page);

	/**
	 * Get all the sections associated with the grade level with the given grade level id.
	 *
	 * @param gradeLevelId id of the grade level
	 * @param page         the page number
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByGradeLevel(int gradeLevelId, Pageable page);

	/**
	 * Get all the sections associated with the grade level with the given grade level object.
	 *
	 * @param gradeLevel id of the grade level
	 * @param page       the page number
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByGradeLevel(GradeLevel gradeLevel, Pageable page);

	/**
	 * Search sections with the given room.
	 *
	 * @param room the room name
	 * @param page the page number
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> searchSectionByRoom(String room, Pageable page);

	/**
	 * Search sections with the given section name.
	 *
	 * @param sectionName the section name
	 * @param page        the page number
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> searchSectionBySectionName(String sectionName, Pageable page);

	// Region: Statistics

	/**
	 * Get all the sections in the database.
	 *
	 * @return The number of sections in the database.
	 */
	int countSections();
	// End Region: Statistics

	boolean isSectionExist(int sectionId);

	boolean isSectionExist(Section section);
}
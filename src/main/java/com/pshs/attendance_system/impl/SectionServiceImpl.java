

package com.pshs.attendance_system.impl;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.SectionRepository;
import com.pshs.attendance_system.services.SectionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

	private static final Logger logger = LogManager.getLogger(SectionServiceImpl.class);
	private final SectionRepository sectionRepository;

	public SectionServiceImpl(SectionRepository sectionRepository) {
		this.sectionRepository = sectionRepository;
	}


	/**
	 * Create a new section in the database.
	 *
	 * @param section section object that will be created.
	 * @return ExecutionStatus object that contains the status of the operation. Can be SUCCESS or FAILED.
	 */
	@Override
	public ExecutionStatus createSection(Section section) {
		if (isNotValid(section)) {
			return ExecutionStatus.VALIDATION_ERROR;
		}

		if (isExists(section)) {
			logger.debug("Can't create section {}, Section already exists.", section.getSectionName());
			return ExecutionStatus.FAILED;
		}

		sectionRepository.save(section);
		logger.debug("Section with id: {} has been created.", section.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete a section in the database.
	 *
	 * @param sectionId The id of the section that will be deleted.
	 * @return ExecutionStatus object that contains the status of the operation. Can be SUCCESS, VALIDATION_ERROR, or NOT_FOUND.
	 */
	@Override
	public ExecutionStatus deleteSection(int sectionId) {
		if (sectionId <= 0) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		sectionRepository.deleteById(sectionId);
		logger.debug("Section with id: {} has been deleted.", sectionId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the section with a given id and the update it with the new section object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param section   the new updated information of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSection(int sectionId, Section section) {
		if (sectionId <= 0 || section == null || isNotValid(section)) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(section)) {
			return logNotFound(sectionId);
		}

		section.setId(sectionId);
		sectionRepository.save(section);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update the teacher of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param teacherId id of the teacher that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionTeacher(int sectionId, int teacherId) {
		if (sectionId <= 0 || teacherId <= 0) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		Teacher teacher = sectionRepository.findById(sectionId).map(Section::getTeacher).orElse(null);
		logger.debug("Teacher was found: {}", teacher != null ? teacher.getId() : null);

		if (teacher != null) {
			int rowsAffected = sectionRepository.updateSectionTeacher(teacher, sectionId);
			logTeacherAssigned(sectionId, teacher.getId());
			return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		}

		logger.debug("Failed to update section with id: {} Teacher not found.", sectionId);
		return ExecutionStatus.FAILED;
	}

	/**
	 * The teacher object will be used to update the teacher of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param teacher   the teacher object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionTeacher(int sectionId, Teacher teacher) {
		if (sectionId <= 0 || teacher == null) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		int rowsAffected = sectionRepository.updateSectionTeacher(teacher, sectionId);
		logTeacherAssigned(sectionId, teacher.getId());
		return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
	}

	/**
	 * Update the strand of the section with the given section id and strand id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strandId  id of the strand that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionStrand(int sectionId, int strandId) {
		if (sectionId <= 0 || strandId <= 0) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		Strand strand = sectionRepository.getStrandOfSectionId(sectionId).orElse(null);

		if (strand != null) {
			int rowsAffected = sectionRepository.updateSectionStrand(strand, sectionId);
			return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		}

		return ExecutionStatus.FAILED;
	}

	/**
	 * Update the strand of the section with the given section id and strand object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strand    the strand object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionStrand(int sectionId, Strand strand) {
		if (sectionId <= 0 || strand == null) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		int rowsAffected = sectionRepository.updateSectionStrand(strand, sectionId);
		logger.debug("Section with id: {} has been updated with new strand: {}.", sectionId, strand.getId());
		return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
	}

	/**
	 * Get all sections existing in the database.
	 *
	 * @param page A Page Request Object
	 * @return The page containing all the sections
	 */
	@Override
	public Page<Section> getAllSections(Pageable page) {
		return sectionRepository.findAll(page);
	}

	/**
	 * Get all sections existing in the database.
	 *
	 * @return The list of sections.
	 */
	@Override
	public List<Section> getAllSections() {
		return sectionRepository.findAll();
	}

	/**
	 * Update the section name of the section with the given section id.
	 *
	 * @param sectionId   id of the section that will be updated
	 * @param sectionName the new section name of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionName(int sectionId, String sectionName) {
		if (sectionId <= 0 || sectionName.isEmpty()) {
			return logNotFound(sectionId);
		}

		int rowsAffected = sectionRepository.updateSectionName(sectionName, sectionId);
		logger.debug("Section with id: {} has been updated with new section name: {}.", sectionId, sectionName);
		return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
	}

	/**
	 * Update the grade level of the section with the given section id and grade level id.
	 *
	 * @param sectionId    id of the section that will be updated
	 * @param gradeLevelId id of the grade level that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionGradeLevel(int sectionId, int gradeLevelId) {
		if (sectionId <= 0 || gradeLevelId <= 0) {
			return logFailedValidation(sectionId);
		}

		if (!isExists(sectionId)) {
			return logNotFound(sectionId);
		}

		GradeLevel gradeLevel = sectionRepository.getGradeLevelBySectionId(sectionId).orElse(null);
		logger.debug("Grade level retrieval: {}", gradeLevel != null ? gradeLevel.getId() : "null");

		if (gradeLevel != null) {
			int rowsAffected = sectionRepository.updateSectionGradeLevel(gradeLevel, sectionId);
			logger.debug("Section with id: {} has been updated with new grade level: {}.", sectionId, gradeLevel.getId());
			return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		}

		logger.debug("Failed to update section with id: {} Grade level not found.", sectionId);
		return ExecutionStatus.FAILED;
	}

	/**
	 * Update the grade level of the section with the given section id and grade level object.
	 *
	 * @param sectionId  id of the section that will be updated
	 * @param gradeLevel the grade level object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	@Override
	public ExecutionStatus updateSectionGradeLevel(int sectionId, GradeLevel gradeLevel) {
		if (sectionId <= 0) {
			return logFailedValidation(sectionId);
		}

		if (isExists(sectionId)) {
			int rowsAffected = sectionRepository.updateSectionGradeLevel(gradeLevel, sectionId);
			logger.debug("Section with id: {} has been assigned with new grade level: {}.", sectionId, gradeLevel.getId());
			return (rowsAffected > 0) ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		}

		return logNotFound(sectionId);
	}

	/**
	 * Get the section information with the given section id.
	 *
	 * @param sectionId id of the section that will be retrieved
	 * @return The section object with the given section id. If not found, return null.
	 */
	@Override
	public Optional<Section> getSection(int sectionId) {
		// Check if the section id is valid
		if (sectionId <= 0) {
			logFailedValidation(sectionId);
			return Optional.empty();
		}

		// Check if the section exists
		if (!isExists(sectionId)) {
			logNotFound(sectionId);
			return Optional.empty();
		}

		// Return the section object
		return sectionRepository.findById(sectionId);
	}

	/**
	 * Get all the sections associated with the teacher with the given teacher id.
	 *
	 * @param teacherId id of the teacher
	 * @param page      The page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByTeacher(int teacherId, Pageable page) {
		if (teacherId <= 0) {
			logger.debug("Teacher id {} is invalid.", teacherId);
			return null;
		}

		logger.debug("Retrieving sections with teacher id: {}.", teacherId);
		return sectionRepository.getSectionByTeacher(teacherId, page);
	}

	/**
	 * Get all the sections associated with the teacher object.
	 *
	 * @param teacher teacher object
	 * @param page    The page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByTeacher(Teacher teacher, Pageable page) {
		if (teacher == null) {
			logger.debug("Teacher object is null.");
			return null;
		}

		logTeacherRetrieval(teacher.getId());
		return sectionRepository.getSectionByTeacher(teacher.getId(), page);
	}

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strandId id of the strand
	 * @param page     the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByStrand(int strandId, Pageable page) {
		if (strandId <= 0) {
			logger.debug("Strand id {} is invalid.", strandId);
			return Page.empty();
		}

		logSectionRetrievalWithStrandId(strandId);
		return sectionRepository.getSectionByStrand(strandId, page);
	}

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strand the strand object that will be used to get the sections
	 * @param page   the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByStrand(Strand strand, Pageable page) {
		if (strand == null) {
			logger.debug("Strand object is null.");
			return Page.empty();
		}

		logSectionRetrievalWithStrandId(strand.getId());
		return sectionRepository.getSectionByStrand(strand.getId(), page);
	}

	/**
	 * Get all the sections associated with the grade level with the given grade level id.
	 *
	 * @param gradeLevelId id of the grade level
	 * @param page         the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByGradeLevel(int gradeLevelId, Pageable page) {
		if (gradeLevelId <= 0) {
			logger.debug("Grade level id {} is invalid.", gradeLevelId);
			return Page.empty();
		}

		logSectionRetrievalWithGradeLevelId(gradeLevelId);
		return sectionRepository.getSectionByGradeLevel(gradeLevelId, page);
	}

	/**
	 * Get all the sections associated with the grade level with the given grade level object.
	 *
	 * @param gradeLevel id of the grade level
	 * @param page       the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> getSectionByGradeLevel(GradeLevel gradeLevel, Pageable page) {
		if (gradeLevel == null) {
			logger.debug("Grade level object is null.");
			return Page.empty();
		}

		logSectionRetrievalWithGradeLevelId(gradeLevel.getId());
		return sectionRepository.getSectionByGradeLevel(gradeLevel.getId(), page);
	}

	/**
	 * Search sections with the given room.
	 *
	 * @param room the room name
	 * @param page the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> searchSectionByRoom(String room, Pageable page) {
		if (room.isEmpty()) {
			logger.debug("Room name is empty.");
			return Page.empty();
		}

		logger.debug("Retrieving sections with room name: {}.", room);
		return sectionRepository.searchSectionByRoom(room, page);
	}

	/**
	 * Search sections with the given section name.
	 *
	 * @param sectionName the section name
	 * @param page        the page number
	 * @return Page object that contains the list of sections.
	 */
	@Override
	public Page<Section> searchSectionBySectionName(String sectionName, Pageable page) {
		if (sectionName.isEmpty()) {
			logger.debug("Section name is empty.");
			return Page.empty();
		}

		logger.debug("Retrieving sections with section name: {}.", sectionName);
		return sectionRepository.searchSectionBySectionName(sectionName, page);
	}

	/**
	 * Get all the sections in the database.
	 *
	 * @return The number of sections in the database.
	 */
	@Override
	public int countSections() {
		return sectionRepository.findAll().size();
	}

	@Override
	public boolean isSectionExist(int sectionId) {
		return sectionRepository.existsById(sectionId);
	}

	@Override
	public boolean isSectionExist(Section section) {
		return sectionRepository.existsById(section.getId());
	}

	private boolean isNotValid(Section section) {
		return section.getSectionName().isEmpty() || section.getRoom().isEmpty() || section.getStrand() == null || Optional.ofNullable(section.getGradeLevel()) == null;
	}

	private ExecutionStatus logFailedValidation(int sectionId) {
		logger.debug("Failed to update section with id: {} Validation failed.", sectionId);
		return ExecutionStatus.VALIDATION_ERROR;
	}

	private ExecutionStatus logNotFound(int sectionId) {
		logger.debug("Failed to update section with id: {} Not found.", sectionId);
		return ExecutionStatus.NOT_FOUND;
	}

	private void logSectionRetrievalWithStrandId(int strandId) {
		logger.debug("Retrieving sections with strand id: {}.", strandId);
	}

	private void logSectionRetrievalWithGradeLevelId(int gradeLevelId) {
		logger.debug("Retrieving sections with grade level id: {}.", gradeLevelId);
	}

	private void logTeacherRetrieval(int teacherId) {
		logger.debug("Retrieving teacher with id: {}.", teacherId);
	}

	private void logTeacherAssigned(int sectionId, int teacherId) {
		logger.debug("Teacher with id: {} has been assigned to section with id: {}.", teacherId, sectionId);
	}

	private boolean isExists(Section section) {
		return sectionRepository.existsById(section.getId());
	}

	private boolean isExists(int sectionId) {
		return sectionRepository.existsById(sectionId);
	}
}
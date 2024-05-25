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

package com.pshs.attendance_system.services;

import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.entities.Teacher;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

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
	 * @param section the new updated information of the section
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
	 * @param teacher the teacher object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionTeacher(int sectionId, Teacher teacher);

	/**
	 * Update the room name of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param roomName the new room name of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionRoomName(int sectionId, String roomName);

	/**
	 * Update the strand of the section with the given section id and strand id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strandId id of the strand that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionStrand(int sectionId, int strandId);

	/**
	 * Update the strand of the section with the given section id and strand object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param strand the strand object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionStrand(int sectionId, Strand strand);

	/**
	 * Update the section name of the section with the given section id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param sectionName the new section name of the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionName(int sectionId, String sectionName);

	/**
	 * Update the grade level of the section with the given section id and grade level id.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param gradeLevelId id of the grade level that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionGradeLevel(int sectionId, int gradeLevelId);

	/**
	 * Update the grade level of the section with the given section id and grade level object.
	 *
	 * @param sectionId id of the section that will be updated
	 * @param gradeLevel the grade level object that will be assigned to the section
	 * @return The status of the operation that can be either SUCCESS, FAILED, VALIDATION_FAILED
	 */
	ExecutionStatus updateSectionGradeLevel(int sectionId, GradeLevel gradeLevel);
	// End Region Update Section

	/**
	 * Get the section information with the given section id.
	 *
	 * @param sectionId id of the section that will be retrieved
	 * @return The section object with the given section id. If not found, return null.
	 */
	Section getSection(int sectionId);

	/**
	 * Get all the sections associated with the teacher with the given teacher id.
	 *
	 * @param teacherId id of the teacher
	 * @return
	 */
	Page<Section> getSectionByTeacher(int teacherId, int page, int size);

	/**
	 * Get all the sections associated with the teacher object.
	 *
	 * @param teacher teacher object
	 * @return
	 */
	Page<Section> getSectionByTeacher(Teacher teacher, int page, int size);

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strandId id of the strand
	 * @param page the page number
	 * @param size the size of the page
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByStrand(int strandId, int page, int size);

	/**
	 * Get all the sections associated with the strand with the given strand id.
	 *
	 * @param strand the strand object that will be used to get the sections
	 * @param page the page number
	 * @param size the size of the page
	 * @return
	 */
	Page<Section> getSectionByStrand(Strand strand, int page, int size);

	/**
	 * Get all the sections associated with the grade level with the given grade level id.
	 *
	 * @param gradeLevelId id of the grade level
	 * @param page the page number
	 * @param size the size of the page
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByGradeLevel(int gradeLevelId, int page, int size);

	/**
	 * Get all the sections associated with the grade level with the given grade level object.
	 *
	 * @param gradeLevel id of the grade level
	 * @param page the page number
	 * @param size the size of the page
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> getSectionByGradeLevel(GradeLevel gradeLevel, int page, int size);

	/**
	 * Search sections with the given room.
	 *
	 * @param room the room name
	 * @param page the page number
	 * @param size the size of the page
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> searchSectionByRoom(String room, int page, int size);

	/**
	 * Search sections with the given section name.
	 *
	 * @param sectionName the section name
	 * @param page the page number
	 * @param size the size of the page
	 * @return Page object that contains the list of sections.
	 */
	Page<Section> searchSectionBySectionName(String sectionName, int page, int size);

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
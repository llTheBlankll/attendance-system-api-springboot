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

	ExecutionStatus createSection(Section section);

	ExecutionStatus deleteSection(int sectionId);

	// Region Update Section
	ExecutionStatus updateSection(int sectionId, Section section);

	ExecutionStatus updateSectionTeacher(int sectionId, int teacherId);

	ExecutionStatus updateSectionTeacher(int sectionId, Teacher teacher);

	ExecutionStatus updateSectionRoomName(int sectionId, String roomName);

	ExecutionStatus updateSectionStrand(int sectionId, int strandId);

	ExecutionStatus updateSectionStrand(int sectionId, Strand strand);

	ExecutionStatus updateSectionName(int sectionId, String sectionName);

	ExecutionStatus updateSectionGradeLevel(int sectionId, int gradeLevelId);

	ExecutionStatus updateSectionGradeLevel(int sectionId, GradeLevel gradeLevel);
	// End Region Update Section

	Section getSection(int sectionId);
	Section getSectionByTeacher(int teacherId);
	Section getSectionByTeacher(Teacher teacher);
	Section getSectionByStrand(int strandId);
	Section getSectionByStrand(Strand strand);
	Section getSectionByGradeLevel(int gradeLevelId);
	Section getSectionByGradeLevel(GradeLevel gradeLevel);

	Page<Section> searchSectionByRoom(String room, int page, int size);
	Page<Section> searchSectionBySectionName(String sectionName, int page, int size);

	// Region: Statistics
	int countSections();

	int countSectionsByTeacher(int teacherId);
	int countSectionsByTeacher(Teacher teacher);

	int countSectionsByStrand(int strandId);
	int countSectionsByStrand(Strand strand);

	int countSectionsByGradeLevel(int gradeLevelId);
	int countSectionsByGradeLevel(GradeLevel gradeLevel);
	// End Region: Statistics
}
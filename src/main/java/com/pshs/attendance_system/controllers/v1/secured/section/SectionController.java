package com.pshs.attendance_system.controllers.v1.secured.section;

import com.pshs.attendance_system.dto.*;
import com.pshs.attendance_system.entities.Section;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.SectionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/sections")
@Tag(
	name = "Section",
	description = "Section API"
)
public class SectionController {

	private final SectionService sectionService;

	public SectionController(SectionService sectionService) {
		this.sectionService = sectionService;
	}

	@PostMapping("/section")
	@Operation(summary = "Create a new section", description = "Create a new section")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Section to create", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section created successfully"),
		@ApiResponse(responseCode = "400", description = "Section already exists"),
		@ApiResponse(responseCode = "400", description = "Section validation error"),
		@ApiResponse(responseCode = "400", description = "Section not created")
	})
	public ResponseEntity<?> createSection(@RequestBody SectionDTO section) {
		ExecutionStatus status = sectionService.createSection(section.toEntity());

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section " + section.getSectionName() + " was created successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " already exists."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " validation error."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " was not created."));
	}

	@PutMapping("/{id}")
	@Operation(summary = "Update a section", description = "Update a section")
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Section to update", required = true)
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section updated successfully"),
		@ApiResponse(responseCode = "400", description = "Section not found"),
		@ApiResponse(responseCode = "400", description = "Section validation error"),
		@ApiResponse(responseCode = "400", description = "Section not updated")
	})
	public ResponseEntity<?> updateSection(@PathVariable int id, @RequestBody SectionDTO section) {
		ExecutionStatus status = sectionService.updateSection(id, section.toEntity());

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section " + section.getSectionName() + " was updated successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " not found."));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " validation error."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section " + section.getSectionName() + " was not updated."));
	}

	@PatchMapping("/{id}/teacher")
	@Operation(summary = "Update a section teacher", description = "Update a section with an existing teacher, requires section id and teacher id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section teacher updated successfully"),
		@ApiResponse(responseCode = "400", description = "Section not found"),
		@ApiResponse(responseCode = "400", description = "Teacher not found"),
		@ApiResponse(responseCode = "400", description = "Section teacher not updated")
	})
	@Parameters({
		@Parameter(name = "id", description = "Section id", required = true),
		@Parameter(name = "teacherId", description = "Teacher id", required = true)
	})
	public ResponseEntity<?> updateSectionTeacher(@PathVariable int id, @RequestParam int strandId) {
		ExecutionStatus status = sectionService.updateSectionTeacher(id, strandId);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section teacher updated successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Teacher is not found"));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Section/Teacher ID provided."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section teacher not updated"));
	}

	@PatchMapping("/{id}/grade-level/id")
	@Operation(summary = "Update a section grade level", description = "Update a section with an existing grade level, requires section id and grade level id")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section grade level updated successfully"),
		@ApiResponse(responseCode = "400", description = "Grade level not found"),
		@ApiResponse(responseCode = "400", description = "Section not found"),
		@ApiResponse(responseCode = "400", description = "Section grade level not updated")
	})
	@Parameters({
		@Parameter(name = "id", description = "Section id", required = true),
		@Parameter(name = "gradeLevelId", description = "Grade level id", required = true)
	})
	public ResponseEntity<?> updateSectionGradeLevel(@PathVariable("id") int id, @RequestParam("q") int gradeLevelId) {
		ExecutionStatus status = sectionService.updateSectionGradeLevel(id, gradeLevelId);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section grade level updated successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Grade level is not found"));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Section/Grade Level ID provided."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section grade level not updated"));
	}

	@PatchMapping("/{id}/grade-level")
	@Operation(summary = "Update a section grade level", description = "Update a section with an existing grade level, requires section id and grade level object")
	public ResponseEntity<?> updateSectionGradeLevel(@PathVariable("id") int id, @RequestBody GradeLevelDTO gradeLevel) {
		ExecutionStatus status = sectionService.updateSectionGradeLevel(id, gradeLevel.toEntity());

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section grade level updated successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Grade level is not found"));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Section/Grade Level provided."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section grade level not updated"));
	}

	@PatchMapping("/{id}/name")
	public ResponseEntity<?> updateSectionName(@PathVariable int id, @RequestParam("q") String name) {
		ExecutionStatus status = sectionService.updateSectionName(id, name);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section name updated successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Section is not found"));
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(new MessageResponse("Invalid Section ID provided."));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section name not updated"));
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a section", description = "Delete a section")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Section not found"),
		@ApiResponse(responseCode = "400", description = "Section not deleted")
	})
	public ResponseEntity<?> deleteSection(@PathVariable int id) {
		ExecutionStatus status = sectionService.deleteSection(id);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section deleted successfully"));
			}
			case FAILURE -> {
				return ResponseEntity.status(404).body(new MessageResponse("Section not found"));
			}
		}

		return ResponseEntity.badRequest().body(new MessageResponse("Section not deleted"));
	}

	@GetMapping(value = {"/section", "/sections"})
	@Operation(summary = "Get all sections", description = "Get all sections")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Sections retrieved successfully"),
	})
	@Parameters({
		@Parameter(name = "page", description = "Page number"),
		@Parameter(name = "size", description = "Number of items per page")
	})
	public ResponseEntity<?> getSections(@RequestParam int page, @RequestParam int size) {
		return ResponseEntity.ok(sectionService.getAllSections(PageRequest.of(page, size)));
	}

	@GetMapping("/{id}")
	@Operation(summary = "Get section by its id", description = "Get section by id, will return Section object")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns Section object"),
		@ApiResponse(responseCode = "404", description = "Section is not found")
	})
	@Parameters({
		@Parameter(name = "id", description = "Section id", required = true)
	})
	public ResponseEntity<?> getSection(@PathVariable int id) {
		Section section = sectionService.getSection(id);
		if (section != null) {
			return ResponseEntity.ok(section);
		}
		return ResponseEntity.status(404).body(new MessageResponse("No section with id " + id + " found"));
	}

	@GetMapping("/teacher")
	@Operation(summary = "Get all sections handled by a teacher", description = "Get Sections that is assigned to a teacher, if the request parameter and request body is filled.The request parameter teacher id will be used instead.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns a page of section"),
		@ApiResponse(responseCode = "400", description = "No request parameter and request body provided.")
	})
	@Parameters({
		@Parameter(name = "teacherId", description = "Teacher id"),
		@Parameter(name = "page", description = "Page number"),
		@Parameter(name = "size", description = "Number of items per page")
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Teacher object", content = @Content(schema = @Schema(implementation = TeacherDTO.class)))
	public ResponseEntity<?> getSectionByTeacher(@RequestParam(required = false) Integer teacherId, @RequestBody(required = false) TeacherDTO teacher, @RequestParam int page, @RequestParam int size) {
		if (teacherId != null && teacher == null) {
			return ResponseEntity.ok(sectionService.getSectionByTeacher(teacherId, PageRequest.of(page, size)));
		} else if (teacherId == null && teacher != null) {
			return ResponseEntity.ok(sectionService.getSectionByTeacher(teacher.toEntity(), PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("No request parameter and request body provided."));
	}

	@GetMapping("/strand")
	@Operation(summary = "Get all sections handled by a strand", description = "Get all sections")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns a page of section"),
		@ApiResponse(responseCode = "400", description = "No request parameter and request body provided.")
	})
	@Parameters({
		@Parameter(name = "strandId", description = "Strand id"),
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Strand object")
	public ResponseEntity<?> getSectionByStrand(@RequestParam(required = false) Integer strandId, @RequestBody(required = false) StrandDTO strand, @RequestParam int page, @RequestParam int size) {
		if (strandId != null && strand == null) {
			return ResponseEntity.ok(sectionService.getSectionByStrand(strandId, PageRequest.of(page, size)));
		} else if (strandId == null && strand != null) {
			return ResponseEntity.ok(sectionService.getSectionByStrand(strand.toEntity(), PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("No request parameter and request body provided."));
	}

	@GetMapping("/grade-level")
	@Operation(summary = "Get all sections by grade level", description = "Get all sections by grade level, if the request parameter and request body is filled. The request parameter grade level id will be used instead.")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns a page of section"),
		@ApiResponse(responseCode = "400", description = "No request parameter and request body provided.")
	})
	@Parameters({
		@Parameter(name = "gradeLevelId", description = "Grade level id"),
	})
	@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Grade level object")
	public ResponseEntity<?> getSectionByGradeLevel(@RequestParam(required = false) Integer gradeLevelId, @RequestBody(required = false) GradeLevelDTO gradeLevel, @RequestParam int page, @RequestParam int size) {
		if (gradeLevelId != null && gradeLevel == null) {
			return ResponseEntity.ok(sectionService.getSectionByGradeLevel(gradeLevelId, PageRequest.of(page, size)));
		} else if (gradeLevelId == null && gradeLevel != null) {
			return ResponseEntity.ok(sectionService.getSectionByGradeLevel(gradeLevel.toEntity(), PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("No request parameter and request body provided."));
	}

	@GetMapping("/search")
	@Operation(summary = "Search sections", description = "Search sections by room or name")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns a page of section"),
		@ApiResponse(responseCode = "400", description = "No request parameter provided.")
	})
	@Parameters({
		@Parameter(name = "room", description = "Room name"),
		@Parameter(name = "name", description = "Section name")
	})
	public ResponseEntity<?> searchSections(@RequestParam(required = false) String room, @RequestParam(required = false) String name, @RequestParam int page, @RequestParam int size) {
		if (room != null) {
			return ResponseEntity.ok(sectionService.searchSectionByRoom(room, PageRequest.of(page, size)));
		} else if (name != null) {
			return ResponseEntity.ok(sectionService.searchSectionBySectionName(name, PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(new MessageResponse("No request parameter provided."));
	}

	@GetMapping("/count")
	@Operation(summary = "Count all sections", description = "Count all sections")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Returns the number of sections")
	})
	public ResponseEntity<?> countAllSections() {
		return ResponseEntity.ok(sectionService.countSections());
	}
}
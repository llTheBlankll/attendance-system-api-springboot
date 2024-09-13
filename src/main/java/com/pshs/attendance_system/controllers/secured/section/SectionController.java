package com.pshs.attendance_system.controllers.secured.section;

import com.pshs.attendance_system.models.dto.MessageResponse;
import com.pshs.attendance_system.models.dto.SectionDTO;
import com.pshs.attendance_system.models.dto.TeacherDTO;
import com.pshs.attendance_system.models.entities.Section;
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
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

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

	@PostMapping("/create")
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
				return ResponseEntity.ok(
					new MessageResponse(
						"Section " + section.getSectionName() + " was created successfully",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case FAILED -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section " + section.getSectionName() + " already exists",
						ExecutionStatus.INVALID
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section " + section.getSectionName() + " validation error",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section " + section.getSectionName() + " was not created",
						ExecutionStatus.FAILED
					)
				);
			}
		}
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
				return ResponseEntity.ok(
					new MessageResponse(
						"Section " + section.getSectionName() + " was updated successfully",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case FAILED -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section " + section.getSectionName() + " not found",
						ExecutionStatus.FAILED
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section " + section.getSectionName() + " validation error",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(new MessageResponse(
						"Section " + section.getSectionName() + " not updated",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@PatchMapping("/{sectionId}/teacher")
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
	public ResponseEntity<?> updateSectionTeacher(@PathVariable Integer sectionId, @RequestParam Integer teacherId) {
		if (sectionId == null || teacherId == null) {
			return ResponseEntity.badRequest().body(new MessageResponse(
				"Invalid Section/Teacher ID provided.",
				ExecutionStatus.VALIDATION_ERROR
			));
		}

		ExecutionStatus status = sectionService.updateSectionTeacher(sectionId, teacherId);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(new MessageResponse("Section teacher updated successfully", ExecutionStatus.SUCCESS));
			}
			case FAILED -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Teacher is not found",
						ExecutionStatus.FAILED
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Invalid Section/Teacher ID provided.",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section teacher not updated",
						ExecutionStatus.FAILED)
				);
			}
		}
	}

	@PatchMapping("/{id}/grade-level")
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
	public ResponseEntity<?> updateSectionGradeLevel(@PathVariable("id") int id, @RequestParam int gradeLevelId) {
		ExecutionStatus status = sectionService.updateSectionGradeLevel(id, gradeLevelId);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(
					new MessageResponse(
						"Section grade level updated successfully",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case FAILED -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Grade level is not found",
						ExecutionStatus.FAILED
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Invalid Section/Grade Level ID provided.",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section grade level not updated",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@PatchMapping("/{id}/name")
	public ResponseEntity<?> updateSectionName(@PathVariable Integer id, @RequestParam String name) {
		ExecutionStatus status = sectionService.updateSectionName(id, name);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(
					new MessageResponse(
						"Section name updated successfully",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case FAILED -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section is not found",
						ExecutionStatus.INVALID
					)
				);
			}
			case VALIDATION_ERROR -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Invalid Section ID provided.",
						ExecutionStatus.VALIDATION_ERROR
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section name not updated",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "Delete a section", description = "Delete a section")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Section deleted successfully"),
		@ApiResponse(responseCode = "404", description = "Section not found"),
		@ApiResponse(responseCode = "400", description = "Section not deleted")
	})
	public ResponseEntity<?> deleteSection(@PathVariable Integer id) {
		ExecutionStatus status = sectionService.deleteSection(id);

		switch (status) {
			case SUCCESS -> {
				return ResponseEntity.ok(
					new MessageResponse(
						"Section deleted successfully",
						ExecutionStatus.SUCCESS
					)
				);
			}
			case NOT_FOUND -> {
				return ResponseEntity.status(404).body(
					new MessageResponse(
						"Section not found",
						ExecutionStatus.NOT_FOUND
					)
				);
			}
			default -> {
				return ResponseEntity.badRequest().body(
					new MessageResponse(
						"Section not deleted",
						ExecutionStatus.FAILED
					)
				);
			}
		}
	}

	@GetMapping(value = {"/section", "/all"})
	@Operation(summary = "Get all sections", description = "Get all sections")
	@ApiResponses({
		@ApiResponse(responseCode = "200", description = "Sections retrieved successfully"),
	})
	@Parameters({
		@Parameter(name = "page", description = "Page number"),
		@Parameter(name = "size", description = "Number of items per page")
	})
	public ResponseEntity<?> getSections(@RequestParam(required = false) Integer page, @RequestParam(required = false) Integer size, @RequestParam(defaultValue = "false") boolean noPaging) {
		if (noPaging) {
			return ResponseEntity.ok(sectionService.getAllSections().stream().map(Section::toDTO).toList());
		}

		if (page == null || size == null) {
			return ResponseEntity.ok(sectionService.getAllSections());
		}

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
		Optional<Section> section = sectionService.getSection(id);

		if (section.isPresent()) {
			return ResponseEntity.ok(section.get().toStudentSectionDTO());
		}

		return ResponseEntity.status(404).body(
			new MessageResponse(
				"No section with id " + id + " found",
				ExecutionStatus.NOT_FOUND
			)
		);
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
	public ResponseEntity<?> getSectionsByTeacher(@RequestParam Integer teacherId, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "ASC") Sort.Direction orderBy, @RequestParam(defaultValue = "sectionName") String sortBy) {
		if (teacherId == null) {
			return ResponseEntity.badRequest().body(
				new MessageResponse(
					"No request parameter provided.",
					ExecutionStatus.VALIDATION_ERROR
				)
			);
		}
		Sort sort = Sort.by(orderBy, sortBy);
		return ResponseEntity.ok(sectionService.getSectionByTeacher(teacherId, PageRequest.of(page, size).withSort(sort)));
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
	public ResponseEntity<?> getSectionByStrand(@RequestParam(required = false) Integer strandId, @RequestParam int page, @RequestParam int size) {
		// If strandId is not null and strand is null, return the section by strandId
		if (strandId != null) {
			return ResponseEntity.ok(sectionService.getSectionByStrand(strandId, PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(
			new MessageResponse(
				"No request parameter and request body provided.",
				ExecutionStatus.VALIDATION_ERROR
			)
		);
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
	public ResponseEntity<?> getSectionByGradeLevel(@RequestParam(required = false) Integer gradeLevelId, @RequestParam int page, @RequestParam int size) {
		if (gradeLevelId != null) {
			return ResponseEntity.ok(sectionService.getSectionByGradeLevel(gradeLevelId, PageRequest.of(page, size)));
		}

		return ResponseEntity.badRequest().body(
			new MessageResponse(
				"No request parameter and request body provided.",
				ExecutionStatus.VALIDATION_ERROR
			)
		);
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
	public ResponseEntity<?> searchSections(@RequestParam(required = false) String room, @RequestParam(required = false) String name, @RequestParam int page, @RequestParam int size, @RequestParam(defaultValue = "ASC") Sort.Direction orderBy, @RequestParam(defaultValue = "sectionName") String sortBy) {
		Sort sort = Sort.by(orderBy, sortBy);
		if (room != null) {
			return ResponseEntity.ok(sectionService.searchSectionByRoom(room, PageRequest.of(page, size).withSort(sort)));
		} else if (name != null) {
			return ResponseEntity.ok(sectionService.searchSectionBySectionName(name, PageRequest.of(page, size).withSort(sort)));
		}

		return ResponseEntity.badRequest().body(
			new MessageResponse(
				"No request parameter provided.",
				ExecutionStatus.VALIDATION_ERROR
			)
		);
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
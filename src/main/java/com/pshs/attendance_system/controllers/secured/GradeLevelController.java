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

package com.pshs.attendance_system.controllers.secured;

import com.pshs.attendance_system.dto.GradeLevelDTO;
import com.pshs.attendance_system.dto.StrandDTO;
import com.pshs.attendance_system.entities.GradeLevel;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.services.GradeLevelService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@Tag(
	name = "Grade Level",
	description = "API Endpoints for Grade Level"
)
@RequestMapping("/api/v1/grade-level")
public class GradeLevelController {

	private static final Logger logger = LogManager.getLogger(GradeLevelController.class);
	private final GradeLevelService gradeLevelService;

	public GradeLevelController(GradeLevelService gradeLevelService) {
		this.gradeLevelService = gradeLevelService;
	}

	@Operation(
		summary = "Get All Grade Levels",
		description = "Get all grade levels paginated",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "List of grade levels"
			)
		}
	)
	@GetMapping("/")
	public ResponseEntity<Page<GradeLevelDTO>> getAllGradeLevels(
		@RequestParam(defaultValue = "0") Integer page,
		@RequestParam(defaultValue = "10") Integer size
	) {
		logger.info("Fetching all grade levels");
		return ResponseEntity.ok(
			gradeLevelService.getAllGradeLevels(page, size).map(
				GradeLevel::toDTO
			)
		);
	}

	@Operation(
		summary = "Create Grade Level",
		description = "Create a new grade level",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@PostMapping("/")
	public ResponseEntity<Map<String, ExecutionStatus>> createGradeLevel(@RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.info("Creating grade level: {}", gradeLevelDTO);
		ExecutionStatus status = gradeLevelService.createGradeLevel(gradeLevelDTO.toEntity());
		return ResponseEntity.ok(
			Map.of(
				"status",
				status
			)
		);
	}

	@Operation(
		summary = "Delete Grade Level",
		description = "Delete a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@DeleteMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> deleteGradeLevel(@PathVariable Integer id) {
		logger.info("Deleting grade level by id: {}", id);
		return ResponseEntity.ok(
			Map.of(
				"status",
				gradeLevelService.deleteGradeLevel(id)
			)
		);
	}

	@Operation(
		summary = "Get Grade Level by ID",
		description = "Get a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Grade Level object"
			)
		}
	)
	@GetMapping("/{id}")
	public ResponseEntity<GradeLevelDTO> getGradeLevelById(@PathVariable Integer id) {
		logger.info("Fetching grade level by id: {}", id);
		return ResponseEntity.ok(
			gradeLevelService.getGradeLevel(id).toDTO()
		);
	}


	@Operation(
		summary = "Update Grade Level",
		description = "Update a grade level by ID",
		responses = {
			@ApiResponse(
				responseCode = "200",
				description = "Shows the status of the operation"
			)
		}
	)
	@PutMapping("/{id}")
	public ResponseEntity<Map<String, ExecutionStatus>> updateGradeLevel(@PathVariable Integer id, @RequestBody GradeLevelDTO gradeLevelDTO) {
		logger.info("Updating grade level by id: {}", id);
		return ResponseEntity.ok(
			Map.of(
				"status",
				gradeLevelService.updateGradeLevel(id, gradeLevelDTO.toEntity())
			)
		);
	}

	@GetMapping("/count")
	public ResponseEntity<Map<String, Integer>> countAllGradeLevels() {
		logger.info("Counting all grade levels");
		return ResponseEntity.ok(
			Map.of(
				"count",
				gradeLevelService.countAllGradeLevels()
			)
		);
	}

	@GetMapping("/search")
	public ResponseEntity<Page<GradeLevel>> searchGradeLevels(@RequestParam(required = false) String name, @RequestBody(required = false) StrandDTO strand, @RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer size) {

		// If name is not empty and strand is provided, then search grade levels by name and strand
		if (!name.isEmpty() && strand != null) {
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByNameAndStrand(name, strand.getId(), page, size)
			);
		} else if (strand != null) { // If strand iso only provided, then search grade levels by strand
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByStrand(strand.getId(), page, size)
			);
		} else if (!name.isEmpty()) { // If name is only provided, then search grade levels by name
			return ResponseEntity.ok(
				gradeLevelService.searchGradeLevelsByName(name, page, size)
			);
		} else {
			return ResponseEntity.badRequest()
				.body(Page.empty());
		}
	}
}
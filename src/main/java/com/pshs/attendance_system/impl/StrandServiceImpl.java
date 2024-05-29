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

package com.pshs.attendance_system.impl;

import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.repositories.StrandRepository;
import com.pshs.attendance_system.services.StrandService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class StrandServiceImpl implements StrandService {

	private static final Logger logger = LogManager.getLogger(StrandServiceImpl.class);
	private final StrandRepository strandRepository;

	public StrandServiceImpl(StrandRepository strandRepository) {
		this.strandRepository = strandRepository;
	}

	/**
	 * Create a new strand record.
	 *
	 * @param strand Strand object to be created
	 * @return Execution Status (SUCCESS, FAILURE, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus createStrand(Strand strand) {
		if (isExistingStrand(strand.getId())) {
			return strandExisted("Strand with ID " + strand.getId() + " already exists");
		}

		strandRepository.save(strand);
		logger.debug("Strand with ID {} has been created", strand.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete a strand record.
	 *
	 * @param strandId ID of the strand to be deleted
	 * @return Execution Status (SUCCESS, FAILURE, or NOT_FOUND)
	 */
	@Override
	public ExecutionStatus deleteStrand(int strandId) {
		if (!isExistingStrand(strandId)) {
			return strandExisted("Strand with ID " + strandId + " does not exist");
		}

		strandRepository.deleteById(strandId);
		logger.debug("Strand with ID {} has been deleted", strandId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update a strand record.
	 *
	 * @param strandId ID of the strand to be updated
	 * @param strand Updated strand object
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	@Override
	public ExecutionStatus updateStrand(int strandId, Strand strand) {
		if (!isExistingStrand(strandId)) {
			return strandExisted("Strand with ID " + strandId + " does not exist");
		}

		strandRepository.save(strand);
		logger.debug("Strand with ID {} has been updated", strandId);
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Retrieve a strand record.
	 *
	 * @param strandId ID of the strand to be retrieved
	 * @return Strand object if found, otherwise null
	 */
	@Override
	public Strand getStrand(int strandId) {
		return strandRepository.findById(strandId).orElse(null);
	}

	/**
	 * Retrieve all strand records.
	 *
	 * @return The page of strand records
	 */
	@Override
	public Page<Strand> getAllStrands(Pageable pageable) {
		return strandRepository.findAll(pageable);
	}

	/**
	 * Check if the strand exists in the database.
	 *
	 * @param strandId Strand ID
	 * @return True if the strand exists, otherwise false.
	 */
	private boolean isExistingStrand(int strandId) {
		return strandRepository.existsById(strandId);
	}

	/**
	 * This function is called when the strand already exists in the database.
	 * Was created to avoid code duplication.
	 *
	 * @param message Message to be logged
	 * @return ExecutionStatus.VALIDATION_ERROR
	 */
	private ExecutionStatus strandExisted(String message) {
		logger.warn(message);
		return ExecutionStatus.VALIDATION_ERROR;
	}
}
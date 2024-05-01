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

import com.pshs.attendance_system.entities.Strand;
import com.pshs.attendance_system.enums.ExecutionStatus;
import org.springframework.data.domain.Page;

public interface StandService {

	/**
	 * Create a new strand record.
	 *
	 * @param strand Strand object to be created
	 * @return Execution Status (SUCCESS, FAILURE, or VALIDATION_ERROR)
	 */
	ExecutionStatus createStrand(Strand strand);

	/**
	 * Delete a strand record.
	 *
	 * @param strandId ID of the strand to be deleted
	 * @return Execution Status (SUCCESS, FAILURE, or NOT_FOUND)
	 */
	ExecutionStatus deleteStrand(int strandId);

	/**
	 * Update a strand record.
	 *
	 * @param strandId ID of the strand to be updated
	 * @param strand Updated strand object
	 * @return Execution Status (SUCCESS, FAILURE, NOT_FOUND, or VALIDATION_ERROR)
	 */
	ExecutionStatus updateStrand(int strandId, Strand strand);

	/**
	 * Retrieve a strand record.
	 *
	 * @param strandId ID of the strand to be retrieved
	 * @return Strand object if found, otherwise null
	 */
	Strand getStrand(int strandId);

	/**
	 * Retrieve all strand records.
	 *
	 * @return The page of strand records
	 */
	Page<Strand> getStrands(int page, int size);
}
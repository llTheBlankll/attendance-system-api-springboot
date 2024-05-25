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

import com.pshs.attendance_system.entities.RFIDCredential;
import com.pshs.attendance_system.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;

public interface RFIDCredentialService {

	/**
	 * Create a new RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be created
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	ExecutionStatus createRFIDCredential(RFIDCredential rfidCredential);

	/**
	 * Delete an RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be deleted
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	ExecutionStatus deleteRFIDCredential(RFIDCredential rfidCredential);

	/**
	 * Update an RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be updated
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	ExecutionStatus updateRFIDCredential(RFIDCredential rfidCredential);

	/**
	 * Get an RFID Credential by Student
	 *
	 * @param student Student to get the RFID Credential
	 * @return RFIDCredential
	 */
	RFIDCredential getRFIDCredentialByStudent(Student student);

	/**
	 * Get an RFID Credential by Student ID
	 *
	 * @param studentId Student ID to get the RFID Credential
	 * @return RFIDCredential
	 */
	RFIDCredential getRFIDCredentialByStudentId(Long studentId);

	/**
	 * Get an RFID Credential by RFID Hash
	 *
	 * @param hash RFID Hash to get the RFID Credential
	 * @return RFIDCredential else null if not found
	 */
	RFIDCredential getRFIDCredentialByHash(String hash);
}


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
package com.pshs.attendance_system.app.rfid_credentials.impl;

import com.pshs.attendance_system.app.rfid_credentials.models.entities.RFIDCredential;
import com.pshs.attendance_system.app.students.models.entities.Student;
import com.pshs.attendance_system.enums.ExecutionStatus;
import com.pshs.attendance_system.app.rfid_credentials.repositories.RFIDCredentialRepository;
import com.pshs.attendance_system.app.rfid_credentials.services.RFIDCredentialService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;

@Service
public class RFIDCredentialServiceImpl implements RFIDCredentialService {


	private static final Logger logger = LogManager.getLogger(RFIDCredentialServiceImpl.class);
	private final RFIDCredentialRepository rfidCredentialRepository;

	public RFIDCredentialServiceImpl(RFIDCredentialRepository rfidCredentialRepository) {
		this.rfidCredentialRepository = rfidCredentialRepository;
	}

	/**
	 * Create a new RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be created
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	@Override
	public ExecutionStatus createRFIDCredential(RFIDCredential rfidCredential) {
		if (isExists(rfidCredential)) {
			return validationFailedLog(rfidCredential.getId());
		}

		if (isNotValid(rfidCredential)) {
			return validationFailedLog(rfidCredential.getId());
		}

		rfidCredentialRepository.save(rfidCredential);
		logger.debug("RFID Credential {} created", rfidCredential.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Delete an RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be deleted
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	@Override
	public ExecutionStatus deleteRFIDCredential(RFIDCredential rfidCredential) {
		if (!isExists(rfidCredential)) {
			return notFoundLog(rfidCredential.getId());
		}

		rfidCredentialRepository.delete(rfidCredential);
		logger.debug("RFID Credential {} deleted", rfidCredential.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Update an RFID Credential
	 *
	 * @param rfidCredential RFID Credential to be updated
	 * @return ExecutionStatus (SUCCESS, FAILURE)
	 */
	@Override
	public ExecutionStatus updateRFIDCredential(RFIDCredential rfidCredential) {
		if (!isExists(rfidCredential)) {
			return notFoundLog(rfidCredential.getId());
		}

		if (isNotValid(rfidCredential)) {
			return validationFailedLog(rfidCredential.getId());
		}

		rfidCredentialRepository.save(rfidCredential);
		logger.debug("RFID Credential {} updated", rfidCredential.getId());
		return ExecutionStatus.SUCCESS;
	}

	/**
	 * Get an RFID Credential by Student
	 *
	 * @param student Student to get the RFID Credential
	 * @return RFIDCredential
	 */
	@Override
	public RFIDCredential getRFIDCredentialByStudent(Student student) {
		return rfidCredentialRepository.getRFIDCredentialByStudent(student).orElse(null);
	}

	/**
	 * Get an RFID Credential by Student ID
	 *
	 * @param studentId Student ID to get the RFID Credential
	 * @return RFIDCredential
	 */
	@Override
	public RFIDCredential getRFIDCredentialByStudentId(Long studentId) {
		return rfidCredentialRepository.getRFIDCredentialByStudentId(studentId).orElse(null);
	}

	/**
	 * Get an RFID Credential by RFID Hash
	 *
	 * @param hash RFID Hash to get the RFID Credential
	 * @return RFIDCredential else null if not found
	 */
	@Override
	public RFIDCredential getRFIDCredentialByHash(String hash) {
		return rfidCredentialRepository.getRFIDCredentialByHash(hash).orElse(null);
	}

	private boolean isExists(RFIDCredential credential) {
		return rfidCredentialRepository.existsById(credential.getId());
	}

	private boolean isNotValid(RFIDCredential credential) {
		if (credential.getLrn() == null) {
			return true;
		}

		return !credential.getHashedLrn().isEmpty();
	}

	private ExecutionStatus validationFailedLog(int credentialId) {
		logger.debug("RFID Credential {} validation failed", credentialId);
		return ExecutionStatus.VALIDATION_ERROR;
	}

	private ExecutionStatus notFoundLog(int credentialId) {
		logger.debug("RFID Credential {} not found", credentialId);
		return ExecutionStatus.NOT_FOUND;
	}
}
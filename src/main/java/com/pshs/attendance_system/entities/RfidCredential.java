/*
 * Copyright (c) 2024  Vince Angelo Batecan
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, and/or sublicense
 * copies of the Software, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * MODIFICATIONS:
 *
 * Any modifications or derivative works of the Software shall be considered part
 * of the Software and shall be subject to the terms and conditions of this license.
 * Any person or entity making modifications to the Software shall assign and
 * transfer all right, title, and interest in and to such modifications to  Vince Angelo Batecan.
 *  Vince Angelo Batecan shall own all intellectual property rights in and to such modifications.
 *
 * NO COMMERCIAL USE:
 *
 * The Software shall not be sold, rented, leased, or otherwise commercially exploited.
 * The Software is intended for personal, non-commercial use only.
 *
 * NO WARRANTIES:
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.pshs.attendance_system.entities.range;

import com.pshs.attendance_system.entities.Student;
import jakarta.persistence.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "rfid_credentials")
public class RfidCredential {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "rfid_credentials_id_gen")
	@SequenceGenerator(name = "rfid_credentials_id_gen", sequenceName = "rfid_credentials_id_seq", allocationSize = 1)
	@Column(name = "id", nullable = false)
	private Integer id;

	@OneToOne(fetch = FetchType.LAZY, optional = false)
	@OnDelete(action = OnDeleteAction.CASCADE)
	@JoinColumn(name = "lrn", nullable = false)
	private Student lrn;

	@Column(name = "hashed_lrn", nullable = false, length = 32)
	private String hashedLrn;

	@Column(name = "salt", nullable = false, length = 16)
	private String salt;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Student getLrn() {
		return lrn;
	}

	public void setLrn(Student lrn) {
		this.lrn = lrn;
	}

	public String getHashedLrn() {
		return hashedLrn;
	}

	public void setHashedLrn(String hashedLrn) {
		this.hashedLrn = hashedLrn;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

}
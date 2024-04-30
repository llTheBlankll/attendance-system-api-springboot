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

package com.pshs.attendance_system.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Table(name = "subjects")
public class Subject {
	@Id
	@ColumnDefault("nextval('subjects_id_seq'::regclass)")
	@Column(name = "id", nullable = false)
	private Integer id;

	@Column(name = "name", length = 128)
	private String name;

	@Column(name = "description", length = Integer.MAX_VALUE)
	private String description;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
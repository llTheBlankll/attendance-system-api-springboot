

package com.pshs.attendance_system.entities.range;

import java.time.LocalDate;

public class DateRange {

	private LocalDate startDate;
	private LocalDate endDate;

	public DateRange(LocalDate startDate, LocalDate endDate) {
		this.startDate = startDate;
		this.endDate = endDate;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public DateRange setStartDate(LocalDate startDate) {
		this.startDate = startDate;
		return this;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public DateRange setEndDate(LocalDate endDate) {
		this.endDate = endDate;
		return this;
	}
}
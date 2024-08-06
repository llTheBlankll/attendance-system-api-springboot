

package com.pshs.attendance_system.models.entities.range;

import java.time.LocalTime;

public class TimeRange {

	private LocalTime startTime;
	private LocalTime endTime;

	public TimeRange(LocalTime startTime, LocalTime endTime) {
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public LocalTime getStartTime() {
		return startTime;
	}

	public TimeRange setStartTime(LocalTime startTime) {
		this.startTime = startTime;
		return this;
	}

	public LocalTime getEndTime() {
		return endTime;
	}

	public TimeRange setEndTime(LocalTime endTime) {
		this.endTime = endTime;
		return this;
	}
}
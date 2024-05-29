package com.pshs.attendance_system.controllers.v1.secured.section;

import com.pshs.attendance_system.services.SectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/section")
@Tag(
	name = "Section",
	description = "Section API"
)
public class SectionController {

	private final SectionService sectionService;

	public SectionController(SectionService sectionService) {
		this.sectionService = sectionService;
	}


}
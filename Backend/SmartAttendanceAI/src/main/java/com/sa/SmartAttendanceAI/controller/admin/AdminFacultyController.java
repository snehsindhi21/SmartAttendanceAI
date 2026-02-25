package com.sa.SmartAttendanceAI.controller.admin;

import com.sa.SmartAttendanceAI.dto.FacultyInviteRequest;
import com.sa.SmartAttendanceAI.service.admin.FacultyInviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/faculty")
@CrossOrigin
public class AdminFacultyController {

    @Autowired
    private FacultyInviteService service;  // temporary reuse

    @PostMapping("/invite")
    public String inviteFaculty(@RequestBody FacultyInviteRequest req) {
        service.inviteFaculty(req); // reuse same logic for now
        return "Faculty invite sent";
    }
}

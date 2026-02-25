package com.sa.SmartAttendanceAI.service.admin;

import com.sa.SmartAttendanceAI.dto.FacultyInviteRequest;
import com.sa.SmartAttendanceAI.entity.Faculty;
import com.sa.SmartAttendanceAI.entity.RegistrationToken;
import com.sa.SmartAttendanceAI.repository.FacultyRepository;
import com.sa.SmartAttendanceAI.repository.RegistrationTokenRepository;
import com.sa.SmartAttendanceAI.service.mail.MailService;
import com.sa.SmartAttendanceAI.util.TokenGenerator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class FacultyInviteService {

    @Autowired
    private FacultyRepository facultyRepo;

    @Autowired
    private RegistrationTokenRepository tokenRepo;

    @Autowired
    private MailService mailService;

    public void inviteFaculty(FacultyInviteRequest req) {

        // 1️⃣ Save Faculty
        Faculty f = new Faculty();
        f.setFirstName(req.getName());
        f.setEmail(req.getEmail());
        f.setStatus("INVITED");

        facultyRepo.save(f);

        // 2️⃣ Generate Registration Token
        RegistrationToken token = new RegistrationToken();
        token.setToken(TokenGenerator.generate());
        token.setEmail(req.getEmail());
        token.setExpiryTime(LocalDateTime.now().plusHours(24));
        token.setUsed(false);
        token.setRole("FACULTY");

        tokenRepo.save(token);

        // 3️⃣ Create Registration Link
        String link =
                "http://localhost:5500/Frontend/src/register.html?token="
                        + token.getToken()+ "&role=FACULTY";

        // 4️⃣ Send Email
        mailService.sendHtmlInvite(
                req.getEmail(),
                req.getName(),
                link
        );
    }
}

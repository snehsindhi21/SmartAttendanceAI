package com.sa.SmartAttendanceAI.service.admin;


import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sa.SmartAttendanceAI.dto.StudentInviteRequest;
import com.sa.SmartAttendanceAI.entity.RegistrationToken;
import com.sa.SmartAttendanceAI.entity.Student;
import com.sa.SmartAttendanceAI.repository.RegistrationTokenRepository;
import com.sa.SmartAttendanceAI.repository.StudentRepository;
import com.sa.SmartAttendanceAI.service.mail.MailService;
import com.sa.SmartAttendanceAI.util.TokenGenerator;

@Service
public class StudentInviteService {

	@Autowired
    private StudentRepository studentRepo;
	@Autowired
    private RegistrationTokenRepository tokenRepo;
	@Autowired
    private MailService mailService;

    public void inviteStudent(StudentInviteRequest req) {

        Student s = new Student();
        s.setFirstName(req.getName());
        s.setEmail(req.getEmail());
        s.setMobile(req.getMobile());
        s.setStatus("INVITED");
        studentRepo.save(s);

        RegistrationToken token = new RegistrationToken();
        token.setToken(TokenGenerator.generate());
        token.setEmail(req.getEmail());
        token.setExpiryTime(LocalDateTime.now().plusHours(24));
        token.setUsed(false);
        token.setRole("STUDENT");
        tokenRepo.save(token);

        String link =
          "http://localhost:5500/Frontend/src/register.html?token=" + token.getToken()+ "&role=STUDENT";

        mailService.sendHtmlInvite(req.getEmail(),req.getName(), link);
    }
}


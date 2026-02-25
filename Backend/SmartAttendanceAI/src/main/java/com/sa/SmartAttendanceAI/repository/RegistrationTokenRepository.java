package com.sa.SmartAttendanceAI.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sa.SmartAttendanceAI.entity.RegistrationToken;

import java.util.Optional;

public interface RegistrationTokenRepository
        extends JpaRepository<RegistrationToken, Long> {

    Optional<RegistrationToken> findByToken(String token);

    void deleteByEmail(String email);
}


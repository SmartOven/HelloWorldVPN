package com.github.smartoven.model.record.services;

import java.util.Optional;

import com.github.smartoven.model.record.CertificateRecord;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CertificateRecordRepository extends JpaRepository<CertificateRecord, Long> {
    boolean existsByTelegramId(Long telegramId);

    Optional<CertificateRecord> findByTelegramId(Long telegramId);

    void deleteByTelegramId(Long telegramId);
}

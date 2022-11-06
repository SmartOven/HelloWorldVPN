package com.github.smartoven.model.record.services;

import java.util.List;
import java.util.Optional;

import com.github.smartoven.model.record.CertificateRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CertificateRecordService {
    private final CertificateRecordRepository certificateRecordRepository;

    public CertificateRecordService(@Autowired CertificateRecordRepository certificateRecordRepository) {
        this.certificateRecordRepository = certificateRecordRepository;
    }

    public CertificateRecord save(CertificateRecord certificateRecord) {
        return certificateRecordRepository.save(certificateRecord);
    }

    public List<CertificateRecord> findAll() {
        return certificateRecordRepository.findAll();
    }

    public boolean existsByTelegramId(Long telegramId) {
        return certificateRecordRepository.existsByTelegramId(telegramId);
    }

    public Optional<CertificateRecord> findByTelegramId(Long telegramId) {
        return certificateRecordRepository.findByTelegramId(telegramId);
    }

    public void delete(CertificateRecord certificateRecord) {
        certificateRecordRepository.delete(certificateRecord);
    }
}

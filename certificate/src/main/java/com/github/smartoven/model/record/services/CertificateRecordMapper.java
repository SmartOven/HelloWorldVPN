package com.github.smartoven.model.record.services;

import com.github.smartoven.model.record.CertificateRecord;
import com.github.smartoven.model.record.view.CertificateRecordDto;
import com.github.smartoven.model.record.view.CertificateRecordViewModel;
import org.springframework.stereotype.Service;

@Service
public class CertificateRecordMapper {
    public CertificateRecord dtoToEntity(CertificateRecordDto certificateRecordDto) {
        return new CertificateRecord(null, certificateRecordDto.getTelegramId(), null);
    }

    public CertificateRecordViewModel entityToViewModel(CertificateRecord certificateRecord) {
        return new CertificateRecordViewModel(certificateRecord.getTelegramId(), certificateRecord.getFilePath());
    }
}

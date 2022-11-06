package com.github.smartoven.controller;

import java.io.IOException;
import java.util.Optional;

import com.github.smartoven.model.file.CertificateFile;
import com.github.smartoven.model.file.services.CertificateFileManager;
import com.github.smartoven.model.record.CertificateRecord;
import com.github.smartoven.model.record.services.CertificateRecordMapper;
import com.github.smartoven.model.record.services.CertificateRecordService;
import com.github.smartoven.model.record.view.CertificateRecordDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.github.smartoven.exception.ApiResponseCreator.httpBadRequest;
import static com.github.smartoven.exception.ApiResponseCreator.httpInternalServerError;
import static com.github.smartoven.exception.ApiResponseCreator.httpOk;

@RestController
@RequestMapping("/api/certificate")
public class CertificateController {
    private final CertificateRecordService certificateRecordService;
    private final CertificateFileManager certificateFileManager;
    private final CertificateRecordMapper certificateRecordMapper;

    public CertificateController(
            @Autowired CertificateRecordService certificateRecordService,
            @Autowired CertificateFileManager certificateFileManager,
            @Autowired CertificateRecordMapper certificateRecordMapper
    ) {
        this.certificateRecordService = certificateRecordService;
        this.certificateFileManager = certificateFileManager;
        this.certificateRecordMapper = certificateRecordMapper;
    }

    @GetMapping("/{telegramId}")
    public ResponseEntity<?> getCertificate(@PathVariable Long telegramId) {
        Optional<CertificateRecord> certificateRecordOptional = certificateRecordService.findByTelegramId(telegramId);

        CertificateRecord certificateRecord;
        if (certificateRecordOptional.isPresent()) {
            certificateRecord = certificateRecordOptional.get();
        } else {
            try {
                certificateRecord = createCertificate(telegramId);
            } catch (IOException | InterruptedException e) {
                // TODO Добавить логирование ошибки
                return httpInternalServerError(e, "/api/certificate/" + telegramId);
            }
        }

        CertificateFile certificateFile;
        try {
            certificateFile = certificateFileManager.read(certificateRecord.getFilePath());
        } catch (IOException e) {
            // TODO Добавить логирование ошибки
            return httpInternalServerError(e, "/api/certificate/" + telegramId);
        }

        return httpOk(certificateFile);
    }

    // FIXME Закрыть доступ или удалить
    @PostMapping("/record")
    public ResponseEntity<?> createCertificateRecord(@RequestBody CertificateRecordDto certificateRecordDto) {
        Long telegramId = certificateRecordDto.getTelegramId();
        if (certificateRecordService.existsByTelegramId(telegramId)) {
            return httpBadRequest(
                    String.format("Certificate for telegramId %d already exists", telegramId),
                    "/api/certificate/record"
            );
        }

        CertificateRecord certificateRecord = certificateRecordMapper.dtoToEntity(certificateRecordDto);
        return httpOk(certificateRecordService.save(certificateRecord));
    }

    // FIXME Закрыть доступ или удалить
    @GetMapping("/record")
    public ResponseEntity<?> getAllRecords() {
        return httpOk(certificateRecordService.findAll());
    }

    // TODO Метод удаления сертификата

    /**
     * Создаем файл сертификата и запоминаем путь до него.
     * Создаем в БД запись с информацией о созданном сертификате.
     *
     * @param telegramId идентификатор пользователя, для которого создавать сертификат
     * @return созданную запись с информацией о созданном файле сертификата
     * @throws IOException          ошибка создания файла сертификата
     * @throws InterruptedException ошибка ожидания успешного завершения создания файла сертификата
     */
    private CertificateRecord createCertificate(Long telegramId) throws IOException, InterruptedException {
        String filePath = certificateFileManager.create(telegramId);
        return certificateRecordService.save(new CertificateRecord(null, telegramId, filePath));
    }

    /**
     * Удаляем файл сертификата.
     * Удаляем из БД запись с информацией об удаленном сертификате.
     *
     * @param certificateRecord идентификатор пользователя, для которого создавать сертификат
     * @throws IOException          ошибка удаления файла сертификата
     * @throws InterruptedException ошибка ожидания успешного завершения удаления файла сертификата
     */
    private void deleteCertificate(CertificateRecord certificateRecord) throws IOException, InterruptedException {
        certificateFileManager.delete(certificateRecord.getId(), certificateRecord.getFilePath());
        certificateRecordService.delete(certificateRecord);
    }
}

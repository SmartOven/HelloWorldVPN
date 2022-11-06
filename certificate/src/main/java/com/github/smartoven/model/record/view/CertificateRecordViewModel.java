package com.github.smartoven.model.record.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class CertificateRecordViewModel {
    private final Long telegramId;
    private final String filePath;
}

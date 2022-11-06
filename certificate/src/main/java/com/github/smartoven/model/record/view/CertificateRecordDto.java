package com.github.smartoven.model.record.view;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CertificateRecordDto {
    private Long telegramId;
    private String filePath;
}

package com.github.smartoven.model.file.services;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.github.smartoven.model.file.CertificateFile;
import com.github.smartoven.model.record.util.TelegramIdMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class CertificateFileManager {
    private static final String NO_OUTPUT = "2>/dev/null 1>/dev/null";
    private static final String SHELL = "bash";
    private static final int OVPN_FILE_LINES_COUNT = 108;

    private final String createClientScriptPath;
    private final String revokeClientScriptPath;

    private final TelegramIdMapper telegramIdMapper;

    public CertificateFileManager(
            @Value("${certificate.scripts.dir.path}") String scriptsDirPath,
            @Value("${certificate.scripts.create_client}") String createClientScriptName,
            @Value("${certificate.scripts.revoke_client}") String revokeClientScriptName,
            @Autowired TelegramIdMapper telegramIdMapper
    ) {
        this.createClientScriptPath = scriptsDirPath + createClientScriptName;
        this.revokeClientScriptPath = scriptsDirPath + revokeClientScriptName;
        this.telegramIdMapper = telegramIdMapper;
    }

    /**
     * Создает сертификат для пользователя и возвращает путь к созданному файлу
     *
     * @param telegramId идентификатор пользователя
     * @return путь к файлу сертификата
     * @throws IOException          ошибка создания процесса или получения результата его выполнения
     * @throws InterruptedException ошибка ожидания окончания успешного завершения процесса
     */
    public String create(Long telegramId) throws IOException, InterruptedException {
        String createCertificateCommand = buildCommand(
                createClientScriptPath,
                telegramIdMapper.longToStringName(telegramId)
        );

        Process createCertificateProcess = new ProcessBuilder(createCertificateCommand).start();
        createCertificateProcess.waitFor();

        return getProcessOutput(createCertificateProcess);
    }

    /**
     * Удаляет сертификат пользователя
     *
     * @param certificateId идентификатор сертификата
     * @param filePath      путь к файлу сертификата
     * @throws IOException          ошибка создания процесса
     * @throws InterruptedException ошибка ожидания окончания успешного завершения процесса
     */
    public void delete(Long certificateId, String filePath) throws IOException, InterruptedException {
        String removingCertificateCommand = buildCommand(revokeClientScriptPath, String.valueOf(certificateId));

        Process removingCertificateProcess = new ProcessBuilder(removingCertificateCommand).start();
        removingCertificateProcess.waitFor();

        String removingFileCommand = String.format("rm -rf %s", filePath);
        Process removingFileProcess = new ProcessBuilder(removingFileCommand).start();
        removingFileProcess.waitFor();
    }

    // TODO Описать документацию

    /**
     * @param filePath
     * @return
     * @throws IOException
     */
    public CertificateFile read(String filePath) throws IOException {
        List<String> lines = new ArrayList<>(OVPN_FILE_LINES_COUNT);

        BufferedReader reader = new BufferedReader(new FileReader(filePath));
        while (reader.ready()) {
            lines.add(reader.readLine());
        }
        reader.close();

        return new CertificateFile(lines);
    }

    private String buildCommand(String script, String... arguments) {
        StringBuilder commandBuilder = new StringBuilder();

        commandBuilder.append(SHELL).append(" ");
        commandBuilder.append(script).append(" ");

        for (String argument : arguments) {
            commandBuilder.append(argument).append(" ");
        }

        commandBuilder.append(NO_OUTPUT);
        return commandBuilder.toString();
    }

    private String getProcessOutput(Process process) throws IOException {
        BufferedReader reader = process.inputReader();
        StringBuilder stringBuilder = new StringBuilder();
        while (reader.ready()) {
            stringBuilder.append(reader.readLine()).append("\n");
        }
        reader.close();
        return stringBuilder.toString();
    }
}

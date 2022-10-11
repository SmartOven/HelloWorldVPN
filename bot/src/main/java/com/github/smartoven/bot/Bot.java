package com.github.smartoven.bot;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class Bot extends TelegramLongPollingBot {

    @Value("${bot.username}")
    private String botUsername;

    @Value("${bot.token}")
    private String botToken;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage()) {
            return;
        }
        if (!update.getMessage().hasText()) {
            return;
        }

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();
        String username = update.getMessage().getChat().getUserName();

        if (text.contains("/info")) {
            sendInfo(chatId, update);
            log.info("Info sent to the user - " + username);
        } else {
            sendMessage(chatId, text); // echo bot
            log.info("Replied to the user - " + username);
        }
    }

    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(String.valueOf(chatId));
        sendMessage.setText(text);

        try {
            execute(sendMessage);
        } catch (TelegramApiException e) {
            log.error("Sending message error: " + e.getMessage());
        }
    }

    private void sendInfo(long chatId, Update update) {
        Message message = update.getMessage();
        Chat chat = message.getChat();

        String infoBuilder = "Information about received message update:\n" +
                "CharId: " + message.getChatId() + "\n" +
                "Text length: " + message.getText().length() + "\n" +
                "Username: " + chat.getUserName() + "\n" +
                "First name: " + chat.getFirstName() + "\n" +
                "Last name: " + chat.getLastName() + "\n";

        sendMessage(chatId, infoBuilder);
    }
}

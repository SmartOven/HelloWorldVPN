package com.github.smartoven.bot;

import com.github.smartoven.bot.commands.CommandResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    /**
     * Bot reacting to the updates (answering to the messages)
     *
     * @param update Update received
     */
    @Override
    public void onUpdateReceived(Update update) {
        // Skip all updates, that doesn't have the message and text inside
        if (!update.hasMessage() || !update.getMessage().hasText()) {
            return;
        }

        // Collect information for the reply
        Message message = update.getMessage();
        String text = message.getText();
        long chatId = message.getChatId();
        String username = message.getChat().getUserName();

        if (text.contains("/info")) {
            sendInfo(chatId, message);
            log.info("Info sent to the user - " + username);
        } else if (text.contains("hello")) {
            sendMessage(chatId, commandResolver.getHello());
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

    private void sendInfo(long chatId, Message message) {
        Chat chat = message.getChat();

        String infoBuilder = "Information about received message update:\n" +
                "CharId: " + message.getChatId() + "\n" +
                "Text length: " + message.getText().length() + "\n" +
                "Username: " + chat.getUserName() + "\n" +
                "First name: " + chat.getFirstName() + "\n" +
                "Last name: " + chat.getLastName() + "\n";

        sendMessage(chatId, infoBuilder);
    }

    private final CommandResolver commandResolver;

    public Bot(@Autowired CommandResolver commandResolver) {
        this.commandResolver = commandResolver;
    }
}

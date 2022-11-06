package com.github.smartoven.model.record.util;

import org.springframework.stereotype.Service;

@Service
public class TelegramIdMapper {
    public String longToStringName(Long telegramId) {
        if (telegramId >= 0) {
            return String.valueOf(telegramId);
        }

        return "a" + Math.abs(telegramId);
    }

    public Long stringNameToLong(String stringName) {
        if (stringName.charAt(0) != 'a') {
            return Long.parseLong(stringName);
        }

        // Not very optimized, but it is not that necessary
        return -1 * Long.parseLong(stringName.substring(1));
    }
}

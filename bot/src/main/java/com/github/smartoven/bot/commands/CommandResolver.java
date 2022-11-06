package com.github.smartoven.bot.commands;

import org.springframework.stereotype.Component;

@Component
public class CommandResolver {
    public String getHello() {
        return "Hello";
    }
}

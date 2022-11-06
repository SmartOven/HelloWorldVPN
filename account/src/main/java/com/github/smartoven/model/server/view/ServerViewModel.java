package com.github.smartoven.model.server.view;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ServerViewModel {
    private final String name;
    private final String IP;
}

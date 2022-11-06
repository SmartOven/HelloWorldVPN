package com.github.smartoven.model.server.services;

import com.github.smartoven.model.server.Server;
import com.github.smartoven.model.server.view.ServerDto;
import com.github.smartoven.model.server.view.ServerViewModel;
import org.springframework.stereotype.Service;

@Service
public class ServerMapper {
    public Server dtoToEntity(ServerDto serverDto) {
        return new Server(null, serverDto.getName(), serverDto.getIP());
    }

    public ServerViewModel entityToViewModel(Server server) {
        return new ServerViewModel(server.getName(), server.getIp());
    }
}

package com.github.smartoven.model.server.services;

import java.util.List;
import java.util.Optional;

import com.github.smartoven.model.server.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServerService {
    private final ServerRepository serverRepository;

    public ServerService(@Autowired ServerRepository serverRepository) {
        this.serverRepository = serverRepository;
    }

    public Server save(Server server) {
        return serverRepository.save(server);
    }

    public List<Server> findAll() {
        return serverRepository.findAll();
    }

    public boolean existsByName(String name) {
        return serverRepository.existsByName(name);
    }

    public Optional<Server> findByName(String name) {
        return serverRepository.findByName(name);
    }

    public void delete(Server server) {
        serverRepository.delete(server);
    }
}

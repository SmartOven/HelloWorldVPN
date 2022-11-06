package com.github.smartoven.model.server.services;

import java.util.Optional;

import com.github.smartoven.model.server.Server;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServerRepository extends JpaRepository<Server, Long> {
    Optional<Server> findByName(String name);

    boolean existsByName(String name);
}

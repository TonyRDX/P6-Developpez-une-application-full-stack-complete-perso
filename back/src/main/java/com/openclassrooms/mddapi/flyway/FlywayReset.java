package com.openclassrooms.mddapi.flyway;

import org.flywaydb.core.Flyway;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

@Component
public class FlywayReset {

    private final Flyway flyway;

    public FlywayReset(Flyway flyway) {
        this.flyway = flyway;
    }

    @PostConstruct
    public void resetDatabase() {
        flyway.clean();    
        flyway.migrate();  
    }
}

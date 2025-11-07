package com.openclassrooms.mddapi.shared.application.response;

import com.openclassrooms.mddapi.shared.domain.model.Entity;

public record BasicUseCaseResponse(Entity entity) implements UseCaseResponse {
}

package com.openclassrooms.mddapi.shared.application.response;

public record GenericUseCaseResponse<T>(T param) implements UseCaseResponse {
}

package com.user.photo.storage.service.service;

import com.user.photo.storage.service.model.response.UserPhotoResponse;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface UserPhotoService {

    Mono<UserPhotoResponse> uploadUserPhoto(Mono<FilePart> fileParts);

    Mono<Void> getUserPhoto(String idPhoto, ServerWebExchange exchange);
}

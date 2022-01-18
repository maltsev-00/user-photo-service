package com.user.photo.storage.service.service;

import org.bson.types.ObjectId;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface UserPhotoService {

    Mono<ObjectId> uploadUserPhoto(Mono<FilePart> fileParts);

    Flux<Void> getUserPhoto(String idPhoto, ServerWebExchange exchange);
}

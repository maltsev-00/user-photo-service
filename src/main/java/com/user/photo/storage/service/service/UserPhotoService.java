package com.user.photo.storage.service.service;

import com.user.photo.storage.service.model.response.PhotoResponse;
import com.user.photo.storage.service.model.response.SaveUserPhotoResponse;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

public interface UserPhotoService {

    Mono<SaveUserPhotoResponse> uploadUserPhoto(Mono<FilePart> fileParts);

    Mono<PhotoResponse> getUserPhoto(String idPhoto, ServerWebExchange exchange);
}

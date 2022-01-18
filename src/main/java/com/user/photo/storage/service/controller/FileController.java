package com.user.photo.storage.service.controller;


import com.user.photo.storage.service.model.response.UserPhotoResponse;
import com.user.photo.storage.service.service.UserPhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("photo")
@RequiredArgsConstructor
public class FileController {

    private final UserPhotoService userPhotoService;

    @PostMapping
    public Mono<UserPhotoResponse> uploadFile(@RequestPart Mono<FilePart> fileParts) {
        return userPhotoService.uploadFile(fileParts);
    }

    @GetMapping("{idPhoto}")
    public Flux<Void> downloadFile(@PathVariable("idPhoto") String idPhoto, ServerWebExchange exchange) {
        return userPhotoService.downloadFile(idPhoto, exchange);
    }

}

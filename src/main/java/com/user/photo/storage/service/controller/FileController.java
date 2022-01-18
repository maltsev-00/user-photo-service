package com.user.photo.storage.service.controller;


import com.user.photo.storage.service.model.response.UserPhotoResponse;
import com.user.photo.storage.service.service.UserPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("photo")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final UserPhotoService userPhotoService;

    @PostMapping
    public Mono<UserPhotoResponse> uploadUserPhoto(@RequestPart Mono<FilePart> fileParts) {
        return userPhotoService.uploadUserPhoto(fileParts)
                .doOnSuccess(success -> log.debug("uploadUserPhoto success"))
                .doOnError(error -> log.error("uploadUserPhoto error"));
    }

    @GetMapping("{idPhoto}")
    public Flux<Void> downloadUserPhoto(@PathVariable("idPhoto") String idPhoto, ServerWebExchange exchange) {
        return userPhotoService.downloadUserPhoto(idPhoto, exchange)
                .doOnComplete(() -> log.debug("downloadUserPhoto success"))
                .doOnError(error -> log.error("downloadUserPhoto error"));
    }

}

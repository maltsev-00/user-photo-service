package com.user.photo.storage.service.controller;


import com.user.photo.storage.service.model.response.PhotoResponse;
import com.user.photo.storage.service.model.response.SaveUserPhotoResponse;
import com.user.photo.storage.service.service.UserPhotoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@RestController
@CrossOrigin("*")
@RequestMapping("photo")
@RequiredArgsConstructor
@Slf4j
public class FileController {

    private final UserPhotoService userPhotoService;

    @PostMapping
    public Mono<SaveUserPhotoResponse> uploadUserPhoto(@RequestPart(name = "photo") Mono<FilePart> photoFile) {
        return userPhotoService.uploadUserPhoto(photoFile)
                .doOnSuccess(success -> log.debug("uploadUserPhoto success"))
                .doOnError(error -> log.error("uploadUserPhoto error"));
    }

    @GetMapping("{idPhoto}")
    public Mono<PhotoResponse> getUserPhoto(@PathVariable("idPhoto") String idPhoto, ServerWebExchange exchange) {
        return userPhotoService.getUserPhoto(idPhoto, exchange)
                .doOnSuccess(success -> log.debug("getUserPhoto success"))
                .doOnError(error -> log.error("getUserPhoto error"));
    }

}

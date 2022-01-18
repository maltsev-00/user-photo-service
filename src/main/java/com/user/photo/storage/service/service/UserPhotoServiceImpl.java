package com.user.photo.storage.service.service;

import com.user.photo.storage.service.model.response.UserPhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
@RequiredArgsConstructor
public class UserPhotoServiceImpl implements UserPhotoService {

    private final ReactiveGridFsTemplate gridFsTemplate;
    private static final String ID_PHOTO_COLUMN_NAME = "_id";

    @Override
    public Mono<UserPhotoResponse> uploadFile(Mono<FilePart> fileParts) {
        return fileParts
                .flatMap(part -> gridFsTemplate.store(part.content(), part.filename()))
                .map(id -> UserPhotoResponse.builder()
                        .idUserPhoto(id)
                        .build());
    }

    @Override
    public Flux<Void> downloadFile(String idPhoto, ServerWebExchange exchange) {
        return gridFsTemplate.findOne(query(where(ID_PHOTO_COLUMN_NAME).is(idPhoto)))
                .log()
                .flatMap(gridFsTemplate::getResource)
                .flatMapMany(r -> exchange.getResponse().writeWith(r.getDownloadStream()));
    }
}

package com.user.photo.storage.service.service;

import com.user.photo.storage.service.exception.NotFoundFileException;
import com.user.photo.storage.service.model.response.PhotoResponse;
import com.user.photo.storage.service.model.response.SaveUserPhotoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.data.mongodb.gridfs.ReactiveGridFsTemplate;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;


@Service
@RequiredArgsConstructor
public class UserPhotoServiceImpl implements UserPhotoService {

    private final ReactiveGridFsTemplate gridFsTemplate;

    private static final String ID_PHOTO_COLUMN_NAME = "_id";
    private static final String NOT_FOUND_FILE_EXCEPTION_MESSAGE = "Not found file with id: %s";

    @Override
    public Mono<SaveUserPhotoResponse> uploadUserPhoto(Mono<FilePart> fileParts) {
        return fileParts
                .flatMap(part -> gridFsTemplate.store(part.content(), part.filename()))
                .map(idPhoto -> SaveUserPhotoResponse.builder()
                        .idUserPhoto(idPhoto.toHexString())
                        .build())
                .subscribeOn(Schedulers.boundedElastic());
    }

    @Override
    public Mono<PhotoResponse> getUserPhoto(String idPhoto, ServerWebExchange exchange) {
        return gridFsTemplate.findOne(query(where(ID_PHOTO_COLUMN_NAME).is(idPhoto)))
                .switchIfEmpty(Mono.error(new NotFoundFileException(String.format(NOT_FOUND_FILE_EXCEPTION_MESSAGE, idPhoto))))
                .flatMap(gridFsTemplate::getResource)
                .flatMap(file -> mergeDataBuffers(file.getContent()))
                .subscribeOn(Schedulers.boundedElastic());
    }

    private Mono<PhotoResponse> mergeDataBuffers(Flux<DataBuffer> dataBufferFlux) {
        return DataBufferUtils.join(dataBufferFlux)
                .map(dataBuffer -> {
                    byte[] bytes = new byte[dataBuffer.readableByteCount()];
                    dataBuffer.read(bytes);
                    DataBufferUtils.release(dataBuffer);
                    return PhotoResponse.builder()
                            .bytes(bytes)
                            .build();
                });
    }
}

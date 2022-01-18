package com.user.photo.storage.service.model.response;


import lombok.Builder;
import lombok.Value;
import org.bson.types.ObjectId;

@Value
@Builder
public class UserPhotoResponse {
    String idUserPhoto;
}

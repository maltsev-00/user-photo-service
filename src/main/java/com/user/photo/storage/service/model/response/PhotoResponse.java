package com.user.photo.storage.service.model.response;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class PhotoResponse {
    byte[] bytes;
    String name;
}

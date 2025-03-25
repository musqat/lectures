package com.example.Store.dto;

import lombok.*;
import com.example.Store.entity.Store;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StoreDto {
    private long id;
    private String name;
    private String location;
    private String description;
    private long partnerId;

    public static StoreDto fromEntity(Store store) {
        return StoreDto.builder()
                .id(store.getId())
                .name(store.getName())
                .location(store.getLocation())
                .description(store.getDescription())
                .partnerId(store.getPartner().getId())
                .build();
    }
}

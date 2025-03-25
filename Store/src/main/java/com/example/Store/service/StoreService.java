package com.example.Store.service;

import com.example.Store.dto.StoreDto;
import com.example.Store.entity.Store;
import com.example.Store.entity.User;
import com.example.Store.exception.ApiException;
import com.example.Store.repository.StoreRepository;
import com.example.Store.repository.UserRepository;
import com.example.Store.type.ErrorCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 가게 등록, 검색, 상세 정보 조회 및 전체 가게 조회 등의 기능을 제공합니다.
 */
@Service
public class StoreService {

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * 새로운 가게를 등록합니다.
     */
    @Transactional
    public StoreDto registerStore(StoreDto dto, String partnerUserName) {
        User partner = userRepository.findByUserName(partnerUserName)
                .orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND));

        Store store = Store.builder()
                .name(dto.getName())
                .location(dto.getLocation())
                .description(dto.getDescription())
                .partner(partner)
                .build();

        return StoreDto.fromEntity(storeRepository.save(store));
    }

    /**
     * 가게 이름으로 가게를 검색합니다.
     */
    @Transactional(readOnly = true)
    public List<StoreDto> searchStores(String storeName) {
        return storeRepository.findByName(storeName).stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 특정 가게의 상세 정보를 조회합니다.
     */
    @Transactional(readOnly = true)
    public StoreDto getStoreDetails(Long storeId) {
        Store store = storeRepository.findById(storeId)
                .orElseThrow(() -> new ApiException(ErrorCode.STORE_NOT_FOUND));

        return StoreDto.fromEntity(store);
    }

    /**
     * 모든 가게 목록을 조회합니다.
     */
    @Transactional(readOnly = true)
    public List<StoreDto> getAllStores() {
        return storeRepository.findAll().stream()
                .map(StoreDto::fromEntity)
                .collect(Collectors.toList());
    }
}

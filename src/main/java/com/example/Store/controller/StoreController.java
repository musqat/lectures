package com.example.Store.controller;

import com.example.Store.dto.StoreDto;
import com.example.Store.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/stores")
public class StoreController {

    private final StoreService storeService;

    @Autowired
    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    /**
     * 새로운 가게를 등록
     */
    @PostMapping("/register")
    public StoreDto registerStore(@RequestBody StoreDto storeDto, Principal principal) {
        return storeService.registerStore(storeDto, principal.getName());
    }

    /**
     * 가게 이름으로 가게를 검색
     */
    @GetMapping("/search")
    public List<StoreDto> searchStores(@RequestParam String storeName) {
        return storeService.searchStores(storeName);
    }

    /**
     * 특정 가게의 상세 정보를 조회
     */
    @GetMapping("/{storeId}")
    public StoreDto getStoreDetails(@PathVariable Long storeId) {
        return storeService.getStoreDetails(storeId);
    }

    /**
     * 모든 가게 목록을 조회
     */
    @GetMapping
    public List<StoreDto> getAllStores() {
        return storeService.getAllStores();
    }
}

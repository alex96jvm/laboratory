package dev.alex96jvm.laboratory.service;

import dev.alex96jvm.laboratory.dto.LaborantDto;
import dev.alex96jvm.laboratory.mapper.LaborantMapper;
import dev.alex96jvm.laboratory.repository.LaboratoryRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CachePreloader {
    private final CacheManager cacheManager;
    private final LaborantMapper laborantMapper;
    private final LaboratoryRepository laboratoryRepository;
    private final LaboratoryService laboratoryService;

    public CachePreloader(LaboratoryRepository laboratoryRepository, LaborantMapper laborantMapper,
                          CacheManager cacheManager, LaboratoryService laboratoryService) {
        this.cacheManager = cacheManager;
        this.laboratoryRepository = laboratoryRepository;
        this.laborantMapper = laborantMapper;
        this.laboratoryService = laboratoryService;
    }

    @PostConstruct
    public void preloadCache() {
        preloadCacheContent();
    }

    public void preloadCacheContent() {
        Cache cache = cacheManager.getCache("laborant");
        if (cache != null) {
            int pageNumber = 0;
            List<LaborantDto> batch;
            do {
                batch = findAllByPage(pageNumber++);
                batch.forEach(laborantDto -> {
                    if (laborantDto.getId() != null) {
                        Optional<LaborantDto> dto = laboratoryService.findById(laborantDto.getId());
                        dto.ifPresent(value -> cache.put(laborantDto.getId(), value));
                    }
                });
            } while (!batch.isEmpty());
        }
    }

    private List<LaborantDto> findAllByPage(int pageNumber) {
        return laboratoryRepository.findAll(PageRequest.of(pageNumber, 10))
                .stream()
                .map(laborantMapper::laborantEntityToLaborantDto)
                .toList();
    }
}

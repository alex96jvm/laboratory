package dev.alex96jvm.laboratory.service;

import dev.alex96jvm.laboratory.dto.LaborantDto;
import dev.alex96jvm.laboratory.mapper.LaborantMapper;
import dev.alex96jvm.laboratory.repository.LaboratoryRepository;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LaboratoryService {
    private final LaboratoryRepository laboratoryRepository;
    private final LaborantMapper laborantMapper;

    public LaboratoryService(LaboratoryRepository laboratoryRepository, LaborantMapper laborantMapper, CacheManager cacheManager) {
        this.laboratoryRepository = laboratoryRepository;
        this.laborantMapper = laborantMapper;
    }

    @Cacheable(value = "laborant", key = "#id")
    public Optional<LaborantDto> findById(Long id) {
        return laboratoryRepository.findById(id)
                .map(laborantMapper::laborantEntityToLaborantDto);
    }

    @CacheEvict(value = "laborant", key = "#id")
    public void deleteById(Long id) {
        laboratoryRepository.deleteById(id);
    }

    @CachePut(value = "laborant", key = "#laborantDto.getId()", unless = "#laborantDto.id == null")
    public LaborantDto saveOrUpdate(LaborantDto laborantDto) {
        return laborantMapper.laborantEntityToLaborantDto(laboratoryRepository
                .save(laborantMapper.laborantDtoToLaborantEntity(laborantDto)));
    }

    @Cacheable(value = "laborant", key = "'allLaborants'")
    public List<LaborantDto> findAll() {
        return laboratoryRepository.findAll().stream()
                .map(laborantMapper::laborantEntityToLaborantDto)
                .toList();
    }


}

package dev.alex96jvm.laboratory.job;

import dev.alex96jvm.laboratory.dto.LaborantDto;
import dev.alex96jvm.laboratory.mapper.LaborantMapper;
import dev.alex96jvm.laboratory.model.LaborantEntity;
import dev.alex96jvm.laboratory.repository.LaboratoryRepository;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class RateUpdateJob {

    private final LaboratoryRepository laboratoryRepository;
    private final CacheManager cacheManager;
    private final LaborantMapper laborantMapper;

    public RateUpdateJob(LaboratoryRepository laboratoryRepository, CacheManager cacheManager, LaborantMapper laborantMapper) {
        this.laboratoryRepository = laboratoryRepository;
        this.cacheManager = cacheManager;
        this.laborantMapper = laborantMapper;
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void updateStatuses() {
        List<LaborantEntity> laborants = laboratoryRepository.findAll();

        for (LaborantEntity laborant : laborants) {
            if (Boolean.FALSE.equals(laborant.getStatusHomework())) {
                Integer newRate = laborant.getRate() != null ? laborant.getRate() - 1 : -1;
                laborant.setRate(newRate);
                laboratoryRepository.save(laborant);
                LaborantDto laborantDto = laborantMapper.laborantEntityToLaborantDto(laborant);
                Cache laborantCache = cacheManager.getCache("laborant");
                if (laborantCache != null) {
                    laborantCache.evict(laborantDto.getId());
                    laborantCache.put(laborantDto.getId(), laborantDto);
                }
            }
        }
    }
}

package dev.alex96jvm.laboratory.repository;

import dev.alex96jvm.laboratory.model.LaborantEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LaboratoryRepository extends JpaRepository<LaborantEntity, Long> {
}

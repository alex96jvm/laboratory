package dev.alex96jvm.laboratory.controller;

import dev.alex96jvm.laboratory.dto.LaborantDto;
import dev.alex96jvm.laboratory.service.LaboratoryService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laboratory")
public class LaboratoryController {

    private final LaboratoryService laboratoryService;

    public LaboratoryController(LaboratoryService laboratoryService) {
        this.laboratoryService = laboratoryService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<LaborantDto> getLaborant(@PathVariable Long id) {
        return laboratoryService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<LaborantDto> createLaborant(@RequestBody LaborantDto laborantDto) {
        return ResponseEntity.ok(laboratoryService.saveOrUpdate(laborantDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<LaborantDto> updateLaborant(@PathVariable Long id, @RequestBody LaborantDto laborantDto) {
        laborantDto.setId(id);
        return ResponseEntity.ok(laboratoryService.saveOrUpdate(laborantDto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLaborant(@PathVariable Long id) {
        laboratoryService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<LaborantDto>> getAllLaborants() {
        return ResponseEntity.ok(laboratoryService.findAll());
    }
}

package com.skillsphere.service;

import com.skillsphere.dto.LearningMaterialDto;
import com.skillsphere.dto.LearningMaterialRequest;
import com.skillsphere.entity.CourseModule;
import com.skillsphere.entity.LearningMaterial;
import com.skillsphere.entity.MaterialType;
import com.skillsphere.exception.ForbiddenException;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CourseModuleRepository;
import com.skillsphere.repository.LearningMaterialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LearningMaterialService {

    private final LearningMaterialRepository materialRepository;
    private final CourseModuleRepository moduleRepository;

    public LearningMaterialService(LearningMaterialRepository materialRepository, CourseModuleRepository moduleRepository) {
        this.materialRepository = materialRepository;
        this.moduleRepository = moduleRepository;
    }

    public LearningMaterialDto getMaterialById(Long id) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: " + id));
        return mapToDto(material, null);
    }

    @Transactional
    public LearningMaterialDto addMaterial(Long moduleId, LearningMaterialRequest request, Long instructorId, boolean isAdmin) {
        CourseModule module = moduleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException("Module not found with id: " + moduleId));

        if (!isAdmin && !module.getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to add material to this module");
        }

        LearningMaterial material = LearningMaterial.builder()
                .module(module)
                .type(request.getType() != null ? request.getType() : MaterialType.TEXT)
                .title(request.getTitle())
                .description(request.getDescription())
                .contentUrlOrText(request.getContentUrlOrText() != null ? request.getContentUrlOrText() : "")
                .orderIndex(request.getOrderIndex() != null ? request.getOrderIndex() : 1)
                .build();

        LearningMaterial saved = materialRepository.save(material);
        return mapToDto(saved, null);
    }

    @Transactional
    public LearningMaterialDto updateMaterial(Long id, LearningMaterialRequest request, Long instructorId, boolean isAdmin) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: " + id));

        if (!isAdmin && !material.getModule().getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to update this material");
        }

        material.setType(request.getType() != null ? request.getType() : MaterialType.TEXT);
        material.setTitle(request.getTitle());
        if (request.getDescription() != null) material.setDescription(request.getDescription());
        material.setContentUrlOrText(request.getContentUrlOrText() != null ? request.getContentUrlOrText() : "");
        if (request.getOrderIndex() != null) material.setOrderIndex(request.getOrderIndex());

        LearningMaterial updated = materialRepository.save(material);
        return mapToDto(updated, null);
    }

    @Transactional
    public void deleteMaterial(Long id, Long instructorId, boolean isAdmin) {
        LearningMaterial material = materialRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Learning material not found with id: " + id));

        if (!isAdmin && !material.getModule().getCourse().getInstructor().getId().equals(instructorId)) {
            throw new ForbiddenException("You do not have permission to delete this material");
        }

        materialRepository.delete(material);
    }

    public LearningMaterialDto mapToDto(LearningMaterial material, Boolean completed) {
        return LearningMaterialDto.builder()
                .id(material.getId())
                .moduleId(material.getModule().getId())
                .type(material.getType())
                .title(material.getTitle())
                .description(material.getDescription())
                .contentUrlOrText(material.getContentUrlOrText())
                .orderIndex(material.getOrderIndex())
                .completed(completed)
                .build();
    }
}

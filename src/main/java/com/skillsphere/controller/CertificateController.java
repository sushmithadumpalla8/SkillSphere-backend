package com.skillsphere.controller;

import com.skillsphere.dto.CertificateDto;
import com.skillsphere.security.CustomUserDetails;
import com.skillsphere.service.CertificateService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/certificates")
public class CertificateController {

    private final CertificateService certificateService;

    public CertificateController(CertificateService certificateService) {
        this.certificateService = certificateService;
    }

    @GetMapping({"/my", "/my-certificates"})
    @PreAuthorize("hasRole('STUDENT')")
    public ResponseEntity<List<CertificateDto>> getMyCertificates(@AuthenticationPrincipal CustomUserDetails userDetails) {
        return ResponseEntity.ok(certificateService.getStudentCertificates(userDetails.getStudentId()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CertificateDto> getCertificateById(@PathVariable Long id) {
        return ResponseEntity.ok(certificateService.getCertificateById(id));
    }

    @GetMapping("/number/{certNumber}")
    public ResponseEntity<CertificateDto> getCertificateByNumber(@PathVariable String certNumber) {
        return ResponseEntity.ok(certificateService.getCertificateByNumber(certNumber));
    }

    @GetMapping("/{id}/download")
    public ResponseEntity<byte[]> downloadCertificatePdf(@PathVariable Long id) {
        byte[] pdfBytes = certificateService.generateCertificatePdf(id);
        CertificateDto cert = certificateService.getCertificateById(id);

        String filename = "SkillSphere_Certificate_" + cert.getCertificateNumber() + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }
}

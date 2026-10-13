package com.skillsphere.service;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import com.skillsphere.dto.CertificateDto;
import com.skillsphere.entity.Certificate;
import com.skillsphere.entity.Enrollment;
import com.skillsphere.exception.ResourceNotFoundException;
import com.skillsphere.repository.CertificateRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CertificateService {

    private final CertificateRepository certificateRepository;
    private final com.skillsphere.repository.EnrollmentRepository enrollmentRepository;

    public CertificateService(
            CertificateRepository certificateRepository,
            com.skillsphere.repository.EnrollmentRepository enrollmentRepository
    ) {
        this.certificateRepository = certificateRepository;
        this.enrollmentRepository = enrollmentRepository;
    }

    @Transactional
    public Certificate generateCertificateForEnrollment(Enrollment enrollment) {
        if (certificateRepository.existsByEnrollmentId(enrollment.getId())) {
            return certificateRepository.findByEnrollmentId(enrollment.getId()).get();
        }

        String certNum = "CERT-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();

        Certificate certificate = Certificate.builder()
                .certificateNumber(certNum)
                .student(enrollment.getStudent())
                .course(enrollment.getCourse())
                .instructor(enrollment.getCourse().getInstructor())
                .enrollment(enrollment)
                .issueDate(LocalDateTime.now())
                .build();

        return certificateRepository.save(certificate);
    }

    public CertificateDto getCertificateById(Long id) {
        Certificate certificate = certificateRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id: " + id));
        return mapToDto(certificate);
    }

    public CertificateDto getCertificateByNumber(String certificateNumber) {
        Certificate certificate = certificateRepository.findByCertificateNumber(certificateNumber)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with number: " + certificateNumber));
        return mapToDto(certificate);
    }

    @Transactional
    public List<CertificateDto> getStudentCertificates(Long studentId) {
        List<Enrollment> enrollments = enrollmentRepository.findByStudentId(studentId);
        for (Enrollment e : enrollments) {
            if ((Boolean.TRUE.equals(e.getCompleted()) || (e.getProgressPercentage() != null && e.getProgressPercentage() >= 100))
                    && !certificateRepository.existsByEnrollmentId(e.getId())) {
                generateCertificateForEnrollment(e);
            }
        }

        return certificateRepository.findByStudentId(studentId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public List<CertificateDto> getAllCertificates() {
        return certificateRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public byte[] generateCertificatePdf(Long certificateId) {
        Certificate cert = certificateRepository.findById(certificateId)
                .orElseThrow(() -> new ResourceNotFoundException("Certificate not found with id: " + certificateId));

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        Document document = new Document(PageSize.A4.rotate(), 36, 36, 36, 36);

        try {
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 28, Font.NORMAL, new java.awt.Color(37, 99, 235));
            Font subTitleFont = FontFactory.getFont(FontFactory.HELVETICA, 16, Font.NORMAL, new java.awt.Color(100, 116, 139));
            Font nameFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 24, Font.NORMAL, new java.awt.Color(15, 23, 42));
            Font bodyFont = FontFactory.getFont(FontFactory.HELVETICA, 14, Font.NORMAL, new java.awt.Color(51, 65, 85));
            Font courseFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18, Font.NORMAL, new java.awt.Color(30, 64, 175));
            Font metaFont = FontFactory.getFont(FontFactory.HELVETICA, 11, Font.ITALIC, new java.awt.Color(100, 116, 139));

            Paragraph pHeader = new Paragraph("SKILLSPHERE", titleFont);
            pHeader.setAlignment(Element.ALIGN_CENTER);
            document.add(pHeader);

            Paragraph pTagline = new Paragraph("Learn • Practice • Grow", subTitleFont);
            pTagline.setAlignment(Element.ALIGN_CENTER);
            pTagline.setSpacingAfter(20);
            document.add(pTagline);

            Paragraph pCert = new Paragraph("CERTIFICATE OF COMPLETION", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 22, new java.awt.Color(245, 158, 11)));
            pCert.setAlignment(Element.ALIGN_CENTER);
            pCert.setSpacingAfter(20);
            document.add(pCert);

            Paragraph pThis = new Paragraph("This is to certify that", bodyFont);
            pThis.setAlignment(Element.ALIGN_CENTER);
            document.add(pThis);

            Paragraph pName = new Paragraph(cert.getStudent().getUser().getFullName(), nameFont);
            pName.setAlignment(Element.ALIGN_CENTER);
            pName.setSpacingBefore(10);
            pName.setSpacingAfter(10);
            document.add(pName);

            Paragraph pCompleted = new Paragraph("has successfully completed all coursework, assignments, and final assessment for", bodyFont);
            pCompleted.setAlignment(Element.ALIGN_CENTER);
            document.add(pCompleted);

            Paragraph pCourse = new Paragraph(cert.getCourse().getTitle(), courseFont);
            pCourse.setAlignment(Element.ALIGN_CENTER);
            pCourse.setSpacingBefore(10);
            pCourse.setSpacingAfter(25);
            document.add(pCourse);

            String dateStr = cert.getIssueDate() != null
                    ? cert.getIssueDate().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"))
                    : LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMMM dd, yyyy"));

            Paragraph pDetails = new Paragraph("Issued on: " + dateStr + "   |   Certificate No: " + cert.getCertificateNumber(), metaFont);
            pDetails.setAlignment(Element.ALIGN_CENTER);
            pDetails.setSpacingAfter(30);
            document.add(pDetails);

            Paragraph pSignatures = new Paragraph("Instructor: " + cert.getInstructor().getUser().getFullName() + "   •   SkillSphere Academy", metaFont);
            pSignatures.setAlignment(Element.ALIGN_CENTER);
            document.add(pSignatures);

            document.close();
        } catch (Exception e) {
            throw new RuntimeException("Error generating certificate PDF", e);
        }

        return out.toByteArray();
    }

    public CertificateDto mapToDto(Certificate certificate) {
        return CertificateDto.builder()
                .id(certificate.getId())
                .certificateNumber(certificate.getCertificateNumber())
                .studentId(certificate.getStudent().getId())
                .studentName(certificate.getStudent().getUser().getFullName())
                .studentEmail(certificate.getStudent().getUser().getEmail())
                .courseId(certificate.getCourse().getId())
                .courseTitle(certificate.getCourse().getTitle())
                .courseCategory(certificate.getCourse().getCategory())
                .instructorId(certificate.getInstructor().getId())
                .instructorName(certificate.getInstructor().getUser().getFullName())
                .instructorTitle(certificate.getInstructor().getTitle())
                .enrollmentId(certificate.getEnrollment().getId())
                .issueDate(certificate.getIssueDate())
                .build();
    }
}

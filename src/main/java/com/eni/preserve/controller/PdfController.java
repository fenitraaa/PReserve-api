package com.eni.preserve.controller;

import com.eni.preserve.service.PdfService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/pdf")
public class PdfController {

    private final PdfService pdfService;

    @GetMapping("/recu/{idreserv}")
    public ResponseEntity<byte[]> generateRecu(@PathVariable String idreserv) {
        byte[] pdf = pdfService.generateRecu(idreserv);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData(
            "attachment", "recu-" + idreserv + ".pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .body(pdf);
    }
}
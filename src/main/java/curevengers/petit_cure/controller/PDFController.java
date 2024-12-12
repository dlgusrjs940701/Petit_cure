package curevengers.petit_cure.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.common.util.ExplanationMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
public class PDFController {

    @Autowired
    healthCheckService healthcheckservice;

    @GetMapping("/download-pdf")
    public void downloadPDF(HttpServletResponse response, @ModelAttribute healthCheckDTO dto) throws Exception {
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"health_check.pdf\"");
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        dto.setId(nowId);
        healthCheckDTO dto1 = healthcheckservice.showOne(dto);
        try {

            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);


            document.add(new Paragraph(dto1.getId()));
            document.add(new Paragraph(String.valueOf(dto1.getHeight())));
            document.add(new Paragraph(String.valueOf(dto1.getWeight())));
            document.add(new Paragraph(dto1.getSBP()));
            document.add(new Paragraph(dto1.getDBP()));
            document.add(new Paragraph(dto1.getFBG()));
            document.add(new Paragraph(dto1.getTC()));
            document.add(new Paragraph(dto1.getHDL()));
            document.add(new Paragraph(dto1.getTG()));
            document.add(new Paragraph(dto1.getLDL()));
            document.add(new Paragraph(dto1.getAST()));
            document.add(new Paragraph(dto1.getALT()));
            document.add(new Paragraph(dto1.getDate()));


            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package curevengers.petit_cure.controller;

import com.itextpdf.kernel.colors.Color;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.draw.ILineDrawer;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.common.util.ExplanationMapper;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriUtils;


import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@RestController
public class PDFController {

    @Autowired
    healthCheckService healthcheckservice;

    @Autowired
    ExplanationMapper explanationmapper;

    @GetMapping("/download-pdf")
    public void downloadPDF(HttpServletResponse response, @ModelAttribute healthCheckDTO dto) throws Exception {

        String filename = UriUtils.encode("health_check.pdf", StandardCharsets.UTF_8);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\";",filename));
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String nowId = authentication.getName();
        dto.setId(nowId);
        healthCheckDTO dto1 = healthcheckservice.showOne(dto);
        try {
            PdfWriter writer = new PdfWriter(response.getOutputStream());
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            document.setMargins(50, 50, 50, 50);

            Paragraph title = new Paragraph("Health Check Report")
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold();
            document.add(title);
            document.add(new Paragraph("\n"));
            Paragraph userId = new Paragraph("User ID: " + dto1.getId())
                    .setTextAlignment(TextAlignment.LEFT)
                    .setFontSize(12)
                    .setItalic();
            document.add(userId);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo = new Paragraph("SBP")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo);

//            String test = URLEncoder.encode("안녕","utf-8");

            Paragraph subInfo = new Paragraph("안녕")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(subInfo);


            Paragraph content = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content);
//            document.add(new Paragraph("\n"));
            Paragraph recontent = new Paragraph("Regular exercise and a balanced diet are recommended for a healthy life.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(recontent);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo1 = new Paragraph("mxAC")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo1);
            Paragraph content1 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content1);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo2 = new Paragraph("HBP")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo2);
            Paragraph content2 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content2);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo3 = new Paragraph("LBP")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo3);
            Paragraph content3 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content3);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo4 = new Paragraph("HFBG")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo4);
            Paragraph content4 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content4);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo5 = new Paragraph("LFBG")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo5);
            Paragraph content5 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content5);
            document.add(new Paragraph("\n"));


            Paragraph healthInfo6 = new Paragraph("HTC")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo6);
            Paragraph content6 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content6);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo7 = new Paragraph("HDL")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo7);
            Paragraph content7 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content7);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo8 = new Paragraph("HTG")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo8);
            Paragraph content8 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content8);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo9 = new Paragraph("AST")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo9);
            Paragraph content9 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content9);
            document.add(new Paragraph("\n"));

            Paragraph healthInfo10 = new Paragraph("ALT")
                    .setFontSize(16)
                    .setBold()
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(healthInfo10);
            Paragraph content10 = new Paragraph("The risk of obesity increases due to lack of exercise, inactivity, lack of sleep, and high-calorie foods. Needs regular exercise.")
                    .setFontSize(12)
                    .setTextAlignment(TextAlignment.LEFT);
            document.add(content10);
            document.add(new Paragraph("\n"));

//            document.add(new Paragraph(dto1.getTC()));
//            document.add(new Paragraph(dto1.getHDL()));
//            document.add(new Paragraph(dto1.getTG()));
//            document.add(new Paragraph(dto1.getLDL()));
//            document.add(new Paragraph(dto1.getAST()));
//            document.add(new Paragraph(dto1.getALT()));
//            document.add(new Paragraph(dto1.getDate()));


            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

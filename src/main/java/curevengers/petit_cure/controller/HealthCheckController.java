package curevengers.petit_cure.controller;

import ch.qos.logback.core.model.Model;
import ch.qos.logback.core.pattern.color.YellowCompositeConverter;
import curevengers.petit_cure.Dto.healthCheckDTO;
import curevengers.petit_cure.Service.healthCheckService;
import curevengers.petit_cure.common.util.ExplanationMapper;
import jakarta.servlet.http.HttpServletRequest;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.List;

@RestController
public class HealthCheckController {

    @Autowired
    healthCheckService healthcheckservice;

    @Autowired
    ExplanationMapper Explanationmapper;

    @PostMapping(value = "/download-health-result")
    public ResponseEntity<byte[]> downloadHealthResult(@ModelAttribute healthCheckDTO dto) {
        try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            // 데이터 처리 및 결과 가져오기
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String nowId = authentication.getName();
            dto.setId(nowId);
            healthCheckDTO result = healthcheckservice.showOne(dto);

            // 워크북 생성
            Sheet sheet = workbook.createSheet("Petit_cure 건강검진 결과");

            // 열 폭 수정
            sheet.setDefaultColumnWidth(15);

            // 헤더
            String[] headers = {"항목", "값", "설명"};
            Row headerRow = sheet.createRow(0);
            Font headerFont = workbook.createFont();
            headerFont.setFontName("나눔고딕");
            headerFont.setFontHeight((short) 300);
            headerFont.setColor(IndexedColors.BLACK1.getIndex());
            headerFont.setBold(true);

            // 헤더 스타일
            CellStyle headerStyle = workbook.createCellStyle();
            headerStyle.setAlignment(HorizontalAlignment.LEFT);
            headerStyle.setVerticalAlignment(VerticalAlignment.BOTTOM);
            headerStyle.setBorderLeft(BorderStyle.SLANTED_DASH_DOT);
            headerStyle.setBorderBottom(BorderStyle.MEDIUM);
            headerStyle.setBorderRight(BorderStyle.SLANTED_DASH_DOT);
            headerStyle.setFont(headerFont);
            headerStyle.setFillForegroundColor(IndexedColors.PINK1.getIndex());
            headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }

            // 바디 스타일
            CellStyle bodyStyle = workbook.createCellStyle();
            bodyStyle.setAlignment(HorizontalAlignment.CENTER);
            bodyStyle.setVerticalAlignment(VerticalAlignment.CENTER);
            bodyStyle.setBorderTop(BorderStyle.THIN);
            bodyStyle.setBorderBottom(BorderStyle.THIN);
            bodyStyle.setBorderLeft(BorderStyle.THIN);
            bodyStyle.setBorderRight(BorderStyle.THIN);

            // 데이터
            String[][] data = {
                    {"아이디", result.getId(), Explanationmapper.getExplanation("아이디")},
                    {"키", String.valueOf(result.getHeight()), Explanationmapper.getExplanation("키")},
                    {"몸무게", String.valueOf(result.getWeight()), Explanationmapper.getExplanation("몸무게")},
                    {"BMI", String.valueOf(result.getWeight() / ((result.getHeight() / 100.0) * (result.getHeight() / 100.0))), ExplanationMapper.getExplanation("BMI")},
                    {"mxAC", String.valueOf(result.getAC()), Explanationmapper.getExplanation("mxAC")},
                    {"HBP", result.getSBP() + "/" + result.getDBP(), Explanationmapper.getExplanation("HBP")},
                    {"LBP", result.getSBP() + "/" + result.getDBP(), Explanationmapper.getExplanation("LBP")},
                    {"HFBG", String.valueOf(result.getFBG()), Explanationmapper.getExplanation("HFBG")},
                    {"LFBG", String.valueOf(result.getFBG()), Explanationmapper.getExplanation("LFBG")},
                    {"HTC", String.valueOf(result.getTC()), Explanationmapper.getExplanation("HTC")},
                    {"HDL", String.valueOf(result.getHDL()), Explanationmapper.getExplanation("HDL")},
                    {"HTG", String.valueOf(result.getTG()), Explanationmapper.getExplanation("HTG")},
                    {"AST", String.valueOf(result.getAST()), Explanationmapper.getExplanation("AST")},
                    {"ALT", String.valueOf(result.getALT()), Explanationmapper.getExplanation("ALT")},

            };

            // 데이터 추가
            for (int i = 0; i < data.length; i++) {
                Row row = sheet.createRow(i + 1);
                for (int j = 0; j < data[i].length; j++) {
                    Cell cell = row.createCell(j);
                    cell.setCellValue(data[i][j]);
                }
            }

            // 엑셀 출력
            workbook.write(out);

            HttpHeaders headers1 = new HttpHeaders();
            headers1.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=health_check_result.xlsx");
            return ResponseEntity.ok()
                    .headers(headers1)
                    .body(out.toByteArray());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }



    private CellStyle createHeaderCellStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

}

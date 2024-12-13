package curevengers.petit_cure.common.util;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExplanationMapper {
    private static final Map<String, String> explanations = new HashMap<>();

    static {
        explanations.put("BMI", "운동 부족, 비활동성, 수면부족, 고열량 음식 등으로 비만 위험 증가. 규칙적인 운동 필요.");
        explanations.put("mxAC", "내장지방 증후군. 복부비만으로 대사 증후군 및 당뇨병 위험 증가.");
        explanations.put("HBP", "고혈압. 심혈관 질환, 신장질환 위험 증가. 체중 관리 및 규칙적 운동 권장.");
        explanations.put("LBP", "저혈압. 주로 피로감과 어지러움 증상. 심각한 경우 의사 상담 권장.");
        explanations.put("HFBG", "고혈당. 만성적인 피로와 갈증 유발. 규칙적 운동과 식이요법 필요.");
        explanations.put("LFBG", "저혈당. 떨림, 피로감 등 증상. 당분 섭취 후 휴식 권장.");
        explanations.put("HTC", "총 콜레스테롤 수치 상승. 심혈관 질환 위험 증가. 건강한 식단 필요.");
        explanations.put("HDL", "낮은 HDL. 동맥경화 위험 증가. 유산소 운동과 건강한 지방 섭취 권장.");
        explanations.put("HTG", "고중성지방혈증. 스트레스와 고지방 음식 섭취가 원인. 균형 잡힌 식단 필요.");
        explanations.put("AST", "간 손상 여부를 평가하는 AST 수치. 간 건강 검진 권장.");
        explanations.put("ALT", "ALT 상승. 간질환 위험 증가. 규칙적인 검진과 간 건강 관리 필요.");
    }

    public static String getExplanation(String key) {
        return explanations.getOrDefault(key, "설명이 없습니다.");
    }

    public void getOrDefault(String key) {
        if (!explanations.containsKey(key)) {
            explanations.put(key, "설명이 없습니다.");
        }
    }
}

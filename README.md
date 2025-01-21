# Curevengers: 개인 맞춤형 건강 관리 서비스

**"작은 관심에서 시작되는 큰 건강"**

---

## 📌 프로젝트 개요

### 💡 프로젝트 소개
- **팀명**: Curevengers  
- **개발 기간**: 2024.11.18 ~ 2024.12.16 (4주)  
- **팀원 구성**:  
  - **박연정** (팀장): DB 설계, API 개발, Spring Security 및 소셜 로그인 구현  
  - **이현건**: DB 설계, News API 개발, 게시판 기능, 엑셀/PDF 다운로드 구현  
  - **최진석**: DB 설계, 건강검진 기능 개발, Kakao Map API 설계  
  - **윤율**: View 제작 및 Kakao Map API 연계  

### 💡 기획 배경 및 목적
현대인의 건강 관리 소홀 문제를 해결하고자 본 프로젝트를 기획했습니다. AHA 조사에 따르면, 한국은 건강 인식 수준이 낮은 편에 속하며, 이를 개선하기 위해 다음과 같은 서비스를 제공합니다:
1. **개인 건강 관리 항목**을 체크 및 기록.
2. **맞춤 병원 추천**과 연계.
3. 사용자 간 정보 공유 및 동기 부여를 위한 **게시판 기능**.

### 💡 기대 효과
- 사용자들이 현재 건강 상태를 보다 명확히 인식하고, 이를 바탕으로 적절한 조치를 취할 수 있습니다.
- 건강 정보를 안전하고 신뢰성 있게 공유하여 정서적 지지를 얻고 고립감을 해소할 수 있습니다.

---

## 🛠️ 주요 기능 및 설계

### 주요 기능
1. **회원가입 및 로그인**  
   - Spring Security를 이용한 권한 관리.
   - Kakao API를 활용한 소셜 로그인 지원.
   - AJAX로 구현된 비동기 회원가입.

2. **게시판 기능**  
   - 자유게시판, Q&A 게시판, 우울증 게시판 구현.
   - 추천/신고, 댓글 작성 등 비동기 기능 제공.
   - 정렬 및 페이징 기능으로 데이터 최적화.

3. **건강검진 기능**  
   - 사용자가 건강검진표 작성 후 엑셀/PDF로 다운로드.
   - Kakao Map API로 추천 병원 시각화.

4. **관리자 기능**  
   - 블랙리스트 관리 및 탈퇴현황 조회.
   - 사용자 신고 처리와 게시판 관리.

5. **마이페이지**  
   - 최근 활동 내역 확인 및 개인정보 수정.
   - 계정 삭제 시 2차 인증 절차 진행.

---

### 데이터베이스 설계 (ERD)
- **총 23개 테이블 구성**  
  - 회원 관리: 3개  
  - 게시판: 13개 (자유게시판, Q&A 게시판, 우울증 게시판)  
  - 검진/우울증 관리: 3개  
  - 관리자 전용: 4개  

<img src="https://github.com/user-attachments/assets/c630786b-523c-46ab-b1c8-6106325684ee" alt="ERD 설계">

---

## 🛠️ 기술 스택 및 개발 환경

### 기술 스택
- **Frontend**: HTML5, CSS3, JavaScript, Thymeleaf, Bootstrap
- **Backend**: Java, Spring Boot, Spring Security
- **Database**: MariaDB
- **API**: Kakao Map API, Daum 주소 검색 API, News API
- **라이브러리**:
  - Spring Security: 인증 및 권한 관리.
  - Apache POI: Excel/PDF 다운로드 구현.

### 개발 환경
- **IDE**: IntelliJ IDEA, Eclipse
- **Infra**: AWS EC2, Apache Tomcat

---

## 📽️ 시연 영상

### 주요 기능 미리보기
| 메인화면 | 회원가입 | 로그인 |
|----------|----------|--------|
| ![메인화면](https://github.com/user-attachments/assets/2e6cd813-a018-46a0-a1a4-ab51325136aa) | ![회원가입](https://github.com/user-attachments/assets/5294b9fb-3934-4d3b-b4d5-18a884bab21b) | ![로그인](https://github.com/user-attachments/assets/8412140c-8be4-4d80-adb5-a882df7db6ec) |

- [시연 영상 전체 보기](https://youtu.be/OBIiHWSFoac)

---

## 🙌 개발 경험 및 소감

이번 프로젝트는 짧은 기간 동안 팀원들의 협업과 효율적인 형상 관리를 통해 성공적으로 완성되었습니다.  

- **협업 경험**: 매일 오전 pull, 저녁 merge 전략을 통해 코드 충돌을 최소화하고 작업 효율성을 높였습니다.  
- **기술 구현**: Spring Security를 활용한 소셜 로그인, RESTful API 설계, 데이터베이스 연동 등의 기술을 학습하고 적용하며 실무 능력을 향상시켰습니다.  
- **사용자 경험**: 직관적인 UI/UX 설계를 위해 다양한 피드백을 반영해 사용자가 편리하게 이용할 수 있는 인터페이스를 구축했습니다.  

---

## 📂 프로젝트 문서
  
- **PPT 발표 자료**: [Curevengers 프로젝트 발표자료](https://www.canva.com/design/DAGZX1WRm88/T4WvpIvJDjoHudtSJ3umDA/edit)  

---

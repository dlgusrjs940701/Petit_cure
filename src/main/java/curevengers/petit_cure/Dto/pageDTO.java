package curevengers.petit_cure.Dto;


import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class pageDTO {
    // 페이지에 시작되는 글 번호
    private int startNo;
    // 페이지에 마지막 글 번호
    private int endNo;
    // 한페이지당 글의 갯수
    private int perPageNum=10;
    private Integer page; // Integer 웹에서 받은 페이지 정보(String)가 없으면 null인데 int는 null을 저장할 수 없다. 오류방지
    // 전체 글 갯수
    private int totalCount;
    //
    private int endPage;
    private int startPage;
    private boolean prev;
    private boolean next;
    // 검색용 변수 2개 추가
    private String searchType;
    private String searchKeyword;


    private void calcPage() {
        // DB에서 사용할 시작 데이터 번호 (0부터 시작)
        startNo = (this.page - 1) * perPageNum;

        // 현재 페이지 그룹의 끝 페이지 번호 계산
        int tempEnd = (int) Math.ceil((double) this.page / this.perPageNum) * this.perPageNum;

        // 시작 페이지 번호 계산
        this.startPage = Math.max(1, tempEnd - this.perPageNum + 1);

        // 끝 페이지 번호 계산
        if (tempEnd * this.perPageNum >= this.totalCount) {
            this.endPage = (int) Math.ceil((double) this.totalCount / this.perPageNum);
        } else {
            this.endPage = tempEnd;
        }

        // 마지막 글 번호 계산
        this.endNo = Math.min(startNo + this.perPageNum - 1, this.totalCount);

        // 이전 페이지 버튼 활성화 여부
        this.prev = this.startPage > 1;

        // 다음 페이지 버튼 활성화 여부
        this.next = this.endPage * this.perPageNum < this.totalCount;
    }


    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
        calcPage();// totalCount 전제게시물개수가 있어야지 페이지계산을 할 수 있기 때문에
    }


}

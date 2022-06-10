package hello.finalterm.pos.stat.dto;

public class StatCreateDto {

    private String startDate; // 시작 날짜
    private String endDate; // 종료 날짜

    public StatCreateDto(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public StatCreateDto() {
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
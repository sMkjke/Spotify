package advisor.command;

public class Paging {

    private int pageNum;
    private int offsetNum;
    private int pageSize;
    private String currentParams;
    private int totalAvailableItems;


    public Paging(int pageSize) {
        this.pageNum = 0;
        this.offsetNum = 0;
        this.totalAvailableItems = 0;
        this.pageSize = pageSize;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getTotalAvailableItems() {
        return totalAvailableItems;
    }

    public void setTotalAvailableItems(int totalAvailableItems) {
        this.totalAvailableItems = totalAvailableItems;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getOffsetNum() {
        return offsetNum;
    }

    public void setOffsetNum(int offsetNum) {
        this.offsetNum = offsetNum;
    }

    public String getCurrentParams() {
        return currentParams;
    }

    public void setCurrentParams(String currentParams) {
        this.currentParams = currentParams;
    }
}

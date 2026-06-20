package ge.technicShop.dto;

public class Paging {
    private Integer page;
    private Integer size;

    public Integer getPage() {

        return (page == null || page < 0) ? 0 : page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {

        return (size == null || size <= 0) ? 10 : size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }
}
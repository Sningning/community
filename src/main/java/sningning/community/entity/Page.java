package sningning.community.entity;

/**
 * 封装分页相关的信息
 *
 * @author: Song Ningning
 * @date: 2020-04-23 0:54
 */
public class Page {

    /**
     * 当前页码
     */
    private Integer current = 1;
    /**
     * 显示的上限
     */
    private Integer limit = 10;
    /**
     * 数据总数（用于计算总页数）
     */
    private Integer rows;
    /**
     * 查询路径（用来复用分页的链接）
     */
    private String path;


    /**
     * 获取当前页的起始行
     */
    public Integer getOffset() {
        // 当前页 * 每页数量 = 每页最后一行
        // 每页最后一行 - 每页数量 = 当前页起始行
        // current * limit - limit
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     */
    public Integer getTotal() {
        // rows / limit 或 (rows / limit) + 1
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码（显示5个页码）
     */
    public Integer getFrom() {
        Integer from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码（显示5个页码）
     */
    public Integer getTo() {
        Integer to = current + 2;
        Integer total = getTotal();
        return to > total ? total : to;
    }

    public Integer getCurrent() {
        return current;
    }

    public void setCurrent(Integer current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        if (rows >= 1) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    @Override
    public String toString() {
        return "Page{" +
                "current=" + current +
                ", limit=" + limit +
                ", rows=" + rows +
                ", path='" + path + '\'' +
                '}';
    }
}

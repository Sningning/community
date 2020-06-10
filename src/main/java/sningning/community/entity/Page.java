package sningning.community.entity;

/**
 * 封装分页相关的信息
 * @Author: Song Ningning
 * @Date: 2020-04-23 0:54
 */
public class Page {

    // 当前页码
    private int current = 1;
    // 显示的上限
    private int limit = 10;
    // 数据总数（用于计算总页数）
    private int rows;
    // 查询路径（用来复用分页的链接）
    private String path;


    /**
     * 获取当前页的起始行
     */
    public int getoffset() {
        // current * limit - current
        return (current - 1) * limit;
    }

    /**
     * 获取总页数
     */
    public int getTotal() {
        // rows / limit [+1]
        if (rows % limit == 0) {
            return rows / limit;
        } else {
            return rows / limit + 1;
        }
    }

    /**
     * 获取起始页码（显示5个页码）
     */
    public int getFrom() {
        int from = current - 2;
        return from < 1 ? 1 : from;
    }

    /**
     * 获取结束页码（显示5个页码）
     */
    public int getTo() {
        int to = current + 2;
        int total = getTotal();
        return to >= total ? total : to;
    }


    public int getCurrent() {
        return current;
    }

    public void setCurrent(int current) {
        if (current >= 1) {
            this.current = current;
        }
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        if (limit >= 1 && limit <= 100) {
            this.limit = limit;
        }
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        if (rows >=1 ) {
            this.rows = rows;
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }



}
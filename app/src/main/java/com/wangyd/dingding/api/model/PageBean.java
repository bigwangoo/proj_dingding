package com.wangyd.dingding.api.model;

import java.util.List;

/**
 * @author wangyd
 * @date 2018/6/19
 * @description 分页
 */
public class PageBean<T> {

    /**
     * total : 7446
     * pages : 745
     * pageNum : 1
     * size : 10
     * startNum : 0
     * endNum : 10
     * list : []
     */
    private int total;
    private int pages;
    private int pageNum;
    private int size;
    private int startNum;
    private int endNum;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getStartNum() {
        return startNum;
    }

    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getEndNum() {
        return endNum;
    }

    public void setEndNum(int endNum) {
        this.endNum = endNum;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
}

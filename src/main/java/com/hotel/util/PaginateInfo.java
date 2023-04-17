package com.hotel.util;

public class PaginateInfo {
    private int pageNo;//页码
    private final int pageSize;//页面记录数

    private long count;//总记录数
    private int pages;//总页数
    private int navFirst;//导航页第一个
    private int navLast;//导航页最后一个
    private final int navPages = 7;//导航栏总页数

    public PaginateInfo(int pageNo, int pageSize) {
        this.pageNo = pageNo;
        this.pageSize = pageSize;
    }

    public int getPageNo() {
        return pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public int getOffset() {
        return (this.pageNo - 1) * pageSize;
    }

    public int getLimit() {
        return this.pageSize;
    }


    public long getCount() {
        return count;
    }

    public int getNavFirst() {
        return navFirst;
    }

    public int getNavLast() {
        return navLast;
    }

    public void setCount(long count) {
        this.count = count;


        this.pages = (int) this.count / this.pageSize;
        if (this.count % this.pageSize > 0) {
            this.pages++;
        }

        if (this.pageNo < 1) {
            this.pageNo = 1;
        }
        if (this.pageNo > pages) {
            this.pageNo = pages;
            if (this.pageNo < 1) {
                this.pageNo = 1;
            }
        }

        //计算导航页的首页和尾页
        int half = this.navPages / 2;
        navFirst = pageNo - half;
        if (navFirst < 1) {
            navFirst = 1;
        }
        navLast = navFirst + navPages - 1;
        if (navLast > pages) {
            navLast = pages;
            navFirst = navLast - navPages + 1;
            if (navFirst < 1) {
                navFirst = 1;
            }
        }
    }


    public int getPages() {
        return pages;
    }
}

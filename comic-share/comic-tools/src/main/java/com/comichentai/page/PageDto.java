package com.comichentai.page;

import java.io.Serializable;

/**
 * 分页基础Dto
 * Created by wuyang.zp on 15/5/28.
 * limit a,b
 * PageDo: offset = a  = PageDto : startRecord
 * PageDo: limit  = b = PageDto : pageSize
 */
public class PageDto implements Serializable {

    private static final long serialVersionUID = -4299676786928364850L;
    private Integer pageSize;
    private Integer startRecord;
    private Integer currentPageNo;

    public PageDto() {
        pageSize = 20;
    }

    public Integer getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    /**
     * 得到当前页的首记录在总查询记录中的位置
     */
    public Integer getStartRecord() {
        if (pageSize != null && currentPageNo != null && pageSize > 0) {
            return ((currentPageNo - 1) < 0 ? 0 : (currentPageNo - 1)) * pageSize;
        }
        return null;
    }

    public Integer getCurrentPage() {
        return this.currentPageNo;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPageNo = currentPage;
    }
}

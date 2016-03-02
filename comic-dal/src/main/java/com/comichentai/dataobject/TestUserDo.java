package com.comichentai.dataobject;

/**
 * Created by hope6537 on 16/2/15.
 */
public class TestUserDo extends BasicDo {

    private String name;

    public TestUserDo() {
        super();
    }

    public TestUserDo(Long created, Long updated, Integer status, Integer isDeleted) {
        super(created, updated, status, isDeleted);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public Integer getId() {
        return super.getId();
    }

    @Override
    public void setId(Integer id) {
        super.setId(id);
    }

    @Override
    public Long getCreated() {
        return super.getCreated();
    }

    @Override
    public void setCreated(Long created) {
        super.setCreated(created);
    }

    @Override
    public Long getUpdated() {
        return super.getUpdated();
    }

    @Override
    public void setUpdated(Long updated) {
        super.setUpdated(updated);
    }

    @Override
    public Integer getStatus() {
        return super.getStatus();
    }

    @Override
    public void setStatus(Integer status) {
        super.setStatus(status);
    }

    @Override
    public Integer getIsDeleted() {
        return super.getIsDeleted();
    }

    @Override
    public void setIsDeleted(Integer isDeleted) {
        super.setIsDeleted(isDeleted);
    }

    @Override
    public Integer getLimit() {
        return super.getLimit();
    }

    @Override
    public void setLimit(Integer limit) {
        super.setLimit(limit);
    }

    @Override
    public Integer getOffset() {
        return super.getOffset();
    }

    @Override
    public void setOffset(Integer offset) {
        super.setOffset(offset);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}

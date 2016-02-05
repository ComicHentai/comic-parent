package com.comichentai.dto;

import com.comichentai.enums.IsDeleted;
import com.comichentai.page.PageDto;

/**
 * Created by hope6537 on 16/1/30.
 */
public class BasicDto extends PageDto {

    private Integer id;

    private Long created;

    private Long updated;

    private Integer status;

    private IsDeleted isDeleted;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasicDto basicDto = (BasicDto) o;

        if (id != null ? !id.equals(basicDto.id) : basicDto.id != null) return false;
        if (created != null ? !created.equals(basicDto.created) : basicDto.created != null) return false;
        if (updated != null ? !updated.equals(basicDto.updated) : basicDto.updated != null) return false;
        if (status != null ? !status.equals(basicDto.status) : basicDto.status != null) return false;
        return isDeleted == basicDto.isDeleted;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (created != null ? created.hashCode() : 0);
        result = 31 * result + (updated != null ? updated.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isDeleted != null ? isDeleted.hashCode() : 0);
        return result;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Long getCreated() {
        return created;
    }

    public void setCreated(Long created) {
        this.created = created;
    }

    public Long getUpdated() {
        return updated;
    }

    public void setUpdated(Long updated) {
        this.updated = updated;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public IsDeleted getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(IsDeleted isDeleted) {
        this.isDeleted = isDeleted;
    }
}
package com.comichentai.dto;

/**
 * Created by Dintama on 2016/3/12.
 */
public class ClassifiedDto extends BasicDto {

    private String title;

    private ClassifiedDto(){}

    private ClassifiedDto(String title){
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ClassifiedDto classifiedDto = (ClassifiedDto) o;

        return title != null ? title.equals(classifiedDto.title) : classifiedDto.title == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        return result;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

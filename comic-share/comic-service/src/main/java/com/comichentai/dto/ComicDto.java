package com.comichentai.dto;

/**
 * Created by hope6537 on 16/1/30.
 */
public class ComicDto extends BaseDto{

    private String title;

    private String imgTitle;

    public ComicDto() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ComicDto comicDto = (ComicDto) o;

        if (title != null ? !title.equals(comicDto.title) : comicDto.title != null) return false;
        return imgTitle != null ? imgTitle.equals(comicDto.imgTitle) : comicDto.imgTitle == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imgTitle != null ? imgTitle.hashCode() : 0);
        return result;
    }

    public ComicDto(String title, String imgTitle) {
        this.title = title;
        this.imgTitle = imgTitle;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImgTitle() {
        return imgTitle;
    }

    public void setImgTitle(String imgTitle) {
        this.imgTitle = imgTitle;
    }
}

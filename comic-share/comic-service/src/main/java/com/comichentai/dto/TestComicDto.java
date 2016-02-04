package com.comichentai.dto;

/**
 * Created by hope6537 on 16/1/30.
 */
public class TestComicDto extends BasicDto {

    private String title;

    private String imgTitle;

    public TestComicDto() {

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        TestComicDto testComicDto = (TestComicDto) o;

        if (title != null ? !title.equals(testComicDto.title) : testComicDto.title != null) return false;
        return imgTitle != null ? imgTitle.equals(testComicDto.imgTitle) : testComicDto.imgTitle == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (imgTitle != null ? imgTitle.hashCode() : 0);
        return result;
    }

    public TestComicDto(String title, String imgTitle) {
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

package com.comichentai.dataobject;

/**
 * Created by Dintama on 2016/3/2.
 */
public class ClassifiedDo extends BasicDo {

    private String title;

    public ClassifiedDo(){}

    public ClassifiedDo(String title) {
        this.title = title;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}

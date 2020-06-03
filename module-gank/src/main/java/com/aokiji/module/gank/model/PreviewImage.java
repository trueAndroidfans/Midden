package com.aokiji.module.gank.model;

public class PreviewImage {

    /**
     * 变换模式
     */
    private int previewType;

    /**
     * 图片详情
     */
    private String desc;

    /**
     * 图片地址
     */
    private String url;

    /**
     * 是否选中标识
     */
    private boolean isChecked;

    public int getPreviewType() {
        return previewType;
    }

    public void setPreviewType(int previewType) {
        this.previewType = previewType;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

}

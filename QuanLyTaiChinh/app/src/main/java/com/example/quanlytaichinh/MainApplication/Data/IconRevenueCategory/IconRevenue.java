package com.example.quanlytaichinh.MainApplication.Data.IconRevenueCategory;

public class IconRevenue {
    private String titleIcon;
    private int srcIcon;
    private int idIcon;

    public IconRevenue(String titleIcon, int srcIcon, int idIcon) {
        this.titleIcon = titleIcon;
        this.srcIcon = srcIcon;
        this.idIcon = idIcon;
    }

    public String getTitleIcon() {
        return titleIcon;
    }

    public void setTitleIcon(String titleIcon) {
        this.titleIcon = titleIcon;
    }

    public int getSrcIcon() {
        return srcIcon;
    }

    public void setSrcIcon(int srcIcon) {
        this.srcIcon = srcIcon;
    }

    public int getIdIcon() {
        return idIcon;
    }

    public void setIdIcon(int idIcon) {
        this.idIcon = idIcon;
    }
}

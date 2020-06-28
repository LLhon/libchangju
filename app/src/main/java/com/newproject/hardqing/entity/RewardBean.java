package com.newproject.hardqing.entity;

/**
 * 客户端使用的礼物实体，因为发socket没有selected变量，所以不能使用LiveGift.DataBean；
 * Created by Administrator on 2017/3/2.
 */
public class RewardBean {

    private String giftId;
    //礼物名字
    private String title;
    //价格
    private String price;
    //1 大礼物 2 小礼物
    private String type;
    private String img1; //在礼物列表中显示，下面有礼物title；
    private String img2;//发出礼物在公屏显示
    private boolean selected;
    private int pic;


    public RewardBean(String giftId, String title, String price, String img1, String img2, boolean selected, String type, int pic) {
        this.giftId = giftId;
        this.title = title;
        this.price = price;
        this.img1 = img1;
        this.img2 = img2;
        this.selected = selected;
        this.type = type;
        this.pic = pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    public int getPic() {

        return pic;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }


    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getGiftId() {
        return giftId;
    }

    public void setGiftId(String giftId) {
        this.giftId = giftId;
    }
}

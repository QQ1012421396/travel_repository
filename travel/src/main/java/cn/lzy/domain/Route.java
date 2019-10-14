package cn.lzy.domain;

import java.util.List;

/**
 * 旅游线路实体类
 */
public class Route {
    private int rid;             //     INT PRIMARY KEY NOT NULL AUTO_INCREMENT,  -- id
    private String rname;           //     VARCHAR(500) NOT NULL,	-- 线路名称
    private double price;           //     DOUBLE NOT NULL,		-- 价格
    private String routeIntroduce;  //     VARCHAR(1000),	-- 线路介绍
    private String rflag;           //     CHAR(1) NOT NULL,	-- 是否上架 必输，0代表没有上架，1代表是上架
    private String rdate;           //     VARCHAR(19),	-- 上架时间
    private String isThemeTour;     //     CHAR(1) NOT NULL,	-- 是否主题旅游，必输，0代表不是，1代表是
    private int COUNT;           //     INT DEFAULT 0,	-- 收藏数量
    private int cid;             //     INT NOT NULL,	-- 所属分类，必输
    private String rimage;          //     VARCHAR(200),	-- 缩略图
    private int sid;             //     INT,	--  所属商家
    private String sourceId;        //     VARCHAR(50) UNIQUE,  	-- 抓取数据的来源id

    private Category category;//所属分类
    private Seller seller;//所属商家
    private List<RouteImg> routeImgList;//商品详情图片列表

    public Route() {
    }

    public Route(int rid, String rname, double price, String routeIntroduce, String rflag, String rdate, String isThemeTour, int COUNT, int cid, String rimage, int sid, String sourceId, Category category, Seller seller, List<RouteImg> routeImgList) {
        this.rid = rid;
        this.rname = rname;
        this.price = price;
        this.routeIntroduce = routeIntroduce;
        this.rflag = rflag;
        this.rdate = rdate;
        this.isThemeTour = isThemeTour;
        this.COUNT = COUNT;
        this.cid = cid;
        this.rimage = rimage;
        this.sid = sid;
        this.sourceId = sourceId;
        this.category = category;
        this.seller = seller;
        this.routeImgList = routeImgList;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getRouteIntroduce() {
        return routeIntroduce;
    }

    public void setRouteIntroduce(String routeIntroduce) {
        this.routeIntroduce = routeIntroduce;
    }

    public String getRflag() {
        return rflag;
    }

    public void setRflag(String rflag) {
        this.rflag = rflag;
    }

    public String getRdate() {
        return rdate;
    }

    public void setRdate(String rdate) {
        this.rdate = rdate;
    }

    public String getIsThemeTour() {
        return isThemeTour;
    }

    public void setIsThemeTour(String isThemeTour) {
        this.isThemeTour = isThemeTour;
    }

    public int getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(int COUNT) {
        this.COUNT = COUNT;
    }

    public int getCid() {
        return cid;
    }

    public void setCid(int cid) {
        this.cid = cid;
    }

    public String getRimage() {
        return rimage;
    }

    public void setRimage(String rimage) {
        this.rimage = rimage;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSourceId() {
        return sourceId;
    }

    public void setSourceId(String sourceId) {
        this.sourceId = sourceId;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public List<RouteImg> getRouteImgList() {
        return routeImgList;
    }

    public void setRouteImgList(List<RouteImg> routeImgList) {
        this.routeImgList = routeImgList;
    }
}

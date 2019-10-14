package cn.lzy.domain;

/**
 * 旅游线路图片实体类
 */
public class RouteImg {
    private String rgid;        // INT PRIMARY KEY AUTO_INCREMENT, -- id
    private String rid;         // INT NOT NULL, -- 线路id
    private String bigPic;      // VARCHAR(200) NOT NULL, -- 大图
    private String smallPic;    // VARCHAR(200), -- 小图

    public RouteImg() {
    }

    public RouteImg(String rgid, String rid, String bigPic, String smallPic) {
        this.rgid = rgid;
        this.rid = rid;
        this.bigPic = bigPic;
        this.smallPic = smallPic;
    }

    public String getRgid() {
        return rgid;
    }

    public void setRgid(String rgid) {
        this.rgid = rgid;
    }

    public String getRid() {
        return rid;
    }

    public void setRid(String rid) {
        this.rid = rid;
    }

    public String getBigPic() {
        return bigPic;
    }

    public void setBigPic(String bigPic) {
        this.bigPic = bigPic;
    }

    public String getSmallPic() {
        return smallPic;
    }

    public void setSmallPic(String smallPic) {
        this.smallPic = smallPic;
    }
}

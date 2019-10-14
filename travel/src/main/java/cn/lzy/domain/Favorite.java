package cn.lzy.domain;

import java.sql.Date;

/**
 * 收藏实体类
 */
public class Favorite {
    private int rid; //旅游线路id
    private Date date;//收藏日期
    private int uid;//用户id

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    @Override
    public String toString() {
        return "Favorite{" +
                "rid=" + rid +
                ", date=" + date +
                ", uid=" + uid +
                '}';
    }
}

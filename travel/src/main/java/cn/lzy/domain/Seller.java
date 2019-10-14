package cn.lzy.domain;

/**
 * 商家实体类
 */
public class Seller {
    private int sid; // INT NOT NULL PRIMARY KEY AUTO_INCREMENT, -- id
    private String sname; // VARCHAR(200) UNIQUE NOT NULL, -- 商家名称
    private String consphone; // VARCHAR(20) NOT NULL, -- 商家电话
    private String address; // VARCHAR(200)	-- 商家地址

    public Seller() {
    }

    public Seller(int sid, String sname, String consphone, String address) {
        this.sid = sid;
        this.sname = sname;
        this.consphone = consphone;
        this.address = address;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getConsphone() {
        return consphone;
    }

    public void setConsphone(String consphone) {
        this.consphone = consphone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}

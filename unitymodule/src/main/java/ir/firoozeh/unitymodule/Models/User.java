package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;
import java.util.Date;

public class User implements Serializable {

    private String name;
    private String email;
    private String id;
    private String plan;
    private Date created;
    private String logo;
    private String mobile;
    private int point;

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String getEmail () {
        return email;
    }

    public void setEmail (String email) {
        this.email = email;
    }

    public String getId () {
        return id;
    }

    public void setId (String id) {
        this.id = id;
    }

    public String getPlan () {
        return plan;
    }

    public void setPlan (String plan) {
        this.plan = plan;
    }

    public Date getCreated () {
        return created;
    }

    public void setCreated (Date created) {
        this.created = created;
    }

    public String getLogo () {
        return logo;
    }

    public void setLogo (String logo) {
        this.logo = logo;
    }

    public String getMobile () {
        return mobile;
    }

    public void setMobile (String mobile) {
        this.mobile = mobile;
    }

    public int getPoint () {
        return point;
    }

    public void setPoint (int point) {
        this.point = point;
    }

    @Override
    public String toString () {
        return "User{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", id='" + id + '\'' +
                ", plan='" + plan + '\'' +
                ", created=" + created +
                ", logo='" + logo + '\'' +
                ", mobile='" + mobile + '\'' +
                ", point=" + point +
                '}';
    }
}


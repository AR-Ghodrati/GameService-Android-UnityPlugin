package ir.firoozeh.unitymodule.Models;

import java.io.Serializable;

/**
 * Created by Alireza Ghodrati on 05/10/2018 in
 * Time 01:46 PM for App ir.firoozeh.unitymodule.Models
 */

public class Media implements Serializable {

    private String publisher;
    private String title;
    private String decs;
    private String link;

    public String getPublisher () {
        return publisher;
    }

    public void setPublisher (String publisher) {
        this.publisher = publisher;
    }

    public String getTitle () {
        return title;
    }

    public void setTitle (String title) {
        this.title = title;
    }

    public String getDecs () {
        return decs;
    }

    public void setDecs (String decs) {
        this.decs = decs;
    }

    public String getLink () {
        return link;
    }

    public void setLink (String link) {
        this.link = link;
    }

    @Override
    public String toString () {
        return "Media{" +
                "publisher='" + publisher + '\'' +
                ", title='" + title + '\'' +
                ", decs='" + decs + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}

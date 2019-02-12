package client;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;



@JsonIgnoreProperties(ignoreUnknown = true)
public class Cake {
    private String title;
    private String image;
    private String desc;

    public Cake() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getDesc() {
        return desc;
    }

    public String getImage() {
        return image;
    }

    @Override
    public String toString() {
        return "Cake{" +
                "title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", desc='" + desc + '\'' +
                '}';
    }

}
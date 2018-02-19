package client;




/**
 * Created by fox on 19.02.18.
 */

//@JsonIgnoreProperties(ignoreUnknown = true)
public class Page {

    private String name;
    private String about;
    private String user;

    public Page(String name, String about, String user) {
        this.name = name;
        this.about = about;
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public String getAbout() {
        return about;
    }

    public String getUser() {
        return user;
    }
}

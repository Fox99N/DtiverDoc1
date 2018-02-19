package client;

/**
 * Created by fox on 19.02.18.
 */
public class Application {

    public static void main(String args[]) {
        RestTemplate restTemplate = new RestTemplate();
        Page page = restTemplate.getForObject("http://graph.facebook.com/pivotalsoftware", Page.class);
        System.out.println("Name:    " + page.getName());
        System.out.println("About:   " + page.getAbout());
        System.out.println("Phone:   " + page.getPhone());

}

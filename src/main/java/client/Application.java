package client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
public class Application {
    private static final String DEFAULT_SERVER = "http://localhost:8080";

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    public static void main(String args[]) {

        SpringApplication.run(Application.class, args);
    }


    @Bean
    public CommandLineRunner run() {

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return args -> {
            try {
                String serverUrl = getStringFromConsole("Enter the Server URL (eg http://localhost) or ENTER to select the default server ");
                serverUrl = serverUrl.isEmpty() ? DEFAULT_SERVER : serverUrl;
                List<Cake> cakes = getCakes(restTemplate, headers, serverUrl);

                printListOfCakes(cakes, "CURRENT CAKES :-");

                String title = getStringFromConsole("Enter the cake title to update or ENTER to exit: ");

                if ((title == null) || (title.length() == 0)) {
                    System.out.println("exit");
                    return;
                }

                String description = getStringFromConsole("Enter the cake description or ENTER to continue: ");
                String imageName = getStringFromConsole("Enter the cake image location or ENTER to continue: ");

                Cake cake = buildCake(title, description, imageName);

                restTemplate.postForLocation("http://localhost:8080/cakes", new HttpEntity<>(cake, headers));

                cakes = getCakes(restTemplate, headers, serverUrl);
                printListOfCakes(cakes, "UPDATED CAKES :-");
            } catch (Exception e) {
                System.out.println("unable to connect to the server. Please check it is available");
                return;
            }

        };
    }

    private String getStringFromConsole(String s) {
        System.out.println(s);
        return System.console().readLine().trim();
    }

    private Cake buildCake(String title, String description, String imageName) {
        Cake cake = new Cake();
        cake.setTitle(title);
        cake.setDesc(description);
        cake.setImage(imageName);
        return cake;
    }

    private void printListOfCakes(List<Cake> cakes, String banner) {

        System.out.println();
        System.out.println();
        System.out.println(banner);
        System.out.println("|======================================================================================================================================================================================|");
        System.out.println("|   TITLE                  |  DESCRIPTION                   |  IMAGE LOCATION                                                                                                          |");
        System.out.println("|======================================================================================================================================================================================|");
        cakes.forEach(cake -> {
            String title = (cake.getTitle().length() > 23) ? cake.getTitle().substring(0, 21) + ".." : cake.getTitle();
            String description = (cake.getDesc().length() > 29) ? cake.getDesc().substring(0, 27) + ".." : cake.getDesc();
            String imageName = (cake.getImage().length() > 119) ? cake.getImage().substring(0, 117) + ".." : cake.getImage();
            System.out.println(String.format("| %1$-24s | %2$-30s | %3$-120s |", title, description, imageName));
        });
        System.out.println("|======================================================================================================================================================================================|");
        System.out.println();
        System.out.println();

    }

    private List<Cake> getCakes(RestTemplate restTemplate, HttpHeaders headers, String serverUrl) {
        ResponseEntity<List<Cake>> responseEntity = restTemplate.exchange(
                serverUrl + "/cakes",
                HttpMethod.GET,
                new HttpEntity<String>(headers),
                new ParameterizedTypeReference<List<Cake>>() {
                });
        return responseEntity.getBody();
    }
}
package com.example.quiznurlanmoldabekov;

import com.example.quiznurlanmoldabekov.config.RouteConfig;
import com.example.quiznurlanmoldabekov.router.RouterService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.Properties;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        if (args != null && args.length > 0 && args[0].equals("withoutSpringBoot")) {
            var mapper = new ObjectMapper();
            var config = new RouteConfig();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            try (InputStream inputStream = new FileInputStream("src/main/resources/application.properties")) {
                Properties prop = new Properties();
                prop.load(inputStream);
                config.setCostPerKm(BigDecimal.valueOf(Double.parseDouble(prop.getProperty("route.cost-per-km"))));
                var service = new RouterService(mapper, config);
                var res = service.calculate(args[1], args[2]);
                System.out.println("distance: " + res.getDistance());
                System.out.println("cost: " + res.getCost());
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            SpringApplication.run(Application.class, args);
        }
    }

}

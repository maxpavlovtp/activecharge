package com.km220;

import javax.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableConfigurationProperties
@ConfigurationPropertiesScan
@Slf4j
public class Km220 {

  @PostConstruct
  private void printSwaggerLink() {
    log.info("Swagger url: http://localhost:8080/swagger-ui/index.html");
  }

  public static void main(String[] args) {
    SpringApplication.run(Km220.class, args);
  }

}

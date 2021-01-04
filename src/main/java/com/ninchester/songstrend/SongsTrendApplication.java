package com.ninchester.songstrend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SongsTrendApplication {

  public static void main(String[] args) {
    SpringApplication.run(SongsTrendApplication.class, args);
  }
}

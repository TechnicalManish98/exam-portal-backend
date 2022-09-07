package com.tech.examportal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.tech.examportal.audit.AuditorAwareImpl;

import ch.qos.logback.core.encoder.Encoder;


@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class ExamportalApplication implements CommandLineRunner{
//implements CommandLineRunner{
	
	
	  @Autowired
	 private PasswordEncoder encoder;
	
	@Bean
    public AuditorAware<String> auditorAware(){
        return new AuditorAwareImpl();
    }

	public static void main(String[] args) {
		SpringApplication.run(ExamportalApplication.class, args);
		System.out.println("running");
		
	}

	
	  @Override 
	  public void run(String... args) throws Exception {
	  System.out.println(encoder.encode("Bacardi@123"));
	  
	  }
	 

}

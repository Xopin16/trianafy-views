package com.salesianostriana.dam.trianafy;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info =
@Info(description = "Una Api que gestiona canciones, sus artistas y playlists",
		version = "1.0",
		contact = @Contact(email = "rivas.fujos20@triana.salesianos.edu", name = "Jose Ignacio Rivas"),
		license = @License(name = "trianafy"),
		title = "Trianafy"
)
)
public class TrianafyBaseApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrianafyBaseApplication.class, args);

	}

}

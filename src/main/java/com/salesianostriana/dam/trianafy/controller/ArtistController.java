package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.repos.ArtistRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.SongService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ArtistController {

    private final ArtistService artistService;
    private final ArtistRepository artistRepo;

    private final SongService songService;


    @Operation(summary = "Obtiene todos los artistas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos los artistas",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Artist.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 1, "name": "Iglesias"},
                                                {"id": 2, "name": "Románico"}
                                             ]                                         
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ningun Artista",
                    content = @Content),
    })
    @GetMapping("/artist/")
    public ResponseEntity<List<Artist>> obtenerTodos() {
        if (artistService.findAll().isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        } else {
            return ResponseEntity.ok(artistService.findAll());
        }
    }

    @Operation(summary = "Obtiene un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Joaquín Sabina"}
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @GetMapping("/artist/{id}")
    public ResponseEntity<Artist> obtenerUno(@PathVariable Long id) {

        return ResponseEntity.of(artistService.findById(id));

    }

    @Operation(summary = "Agrega un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Joaquín Sabina"}
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado el artista",
                    content = @Content),
    })
    @PostMapping("/artist/")
    public ResponseEntity<Artist> nuevoArtist(@RequestBody Artist artist) {
        if (artist.getName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } else
            return ResponseEntity.status(HttpStatus.CREATED).body(artistRepo.save(artist));
    }

    @Operation(summary = "Edita un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {"id": 1, "name": "Joaquín Sabina"}
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @PutMapping("/artist/{id}")
    public ResponseEntity<Artist> editarArtist(@RequestBody Artist artist, @PathVariable Long id) {
        return ResponseEntity.of(artistService.findById(id).map(p -> {
            p.setName(artist.getName());
            artistRepo.save(p);
            return p;
        }));
    }

    @Operation(summary = "Borra un artista en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado el artista",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Artist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado el artista por el ID",
                    content = @Content),
    })
    @DeleteMapping("/artist/{id}")
    public ResponseEntity<Artist> borrarArtist(@PathVariable Long id){
        if(artistRepo.existsById(id)){
            songService.findAll().stream()
                    .filter(s -> s.getArtist().getId().equals(id))
                    .forEach(s -> s.setArtist(null));
            artistService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }

}

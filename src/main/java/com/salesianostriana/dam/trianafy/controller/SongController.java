package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.DTO.*;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
import com.salesianostriana.dam.trianafy.service.ArtistService;
import com.salesianostriana.dam.trianafy.service.PlaylistService;
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
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class SongController {
    private final SongService songService;
    private final SongRepository songRepo;
    private final SongDtoConverter convertDto;
    private final ArtistService artistService;

    private final PlaylistService playlistService;

    @Operation(summary = "Obtiene todos las canciones")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Song.class)),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                [
                                                    {
                                                        "id": 5,
                                                        "title": "Donde habita el olvido",
                                                        "album": "19 días y 500 noches",
                                                        "artist": "Joaquín Sabina",
                                                        "year": "1999"
                                                    },
                                                    {
                                                        "id": 6,
                                                        "title": "A mis cuarenta y diez",
                                                        "album": "19 días y 500 noches",
                                                        "artist": "Joaquín Sabina",
                                                        "year": "1999"
                                                    },
                                                    {
                                                        "id": 7,
                                                        "title": "Don't Start Now",
                                                        "album": "Future Nostalgia",
                                                        "artist": "Dua Lipa",
                                                        "year": "2019"
                                                    },
                                                    {
                                                        "id": 8,
                                                        "title": "Love Again",
                                                        "album": "Future Nostalgia",
                                                        "artist": "Dua Lipa",
                                                        "year": "2021"
                                                    }
                                                ]
                                                                                      
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción",
                    content = @Content),
    })
    @GetMapping("/song/")
    public ResponseEntity<List<SongDto>> findAll() {

        List<Song> data = songService.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<SongDto> result =
                    data.stream()
                            .map(s -> convertDto.songToSongDto(s))
                            .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
    }

    @Operation(summary = "Obtiene una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                            {
                                                 "id": 4,
                                                 "title": "19 días y 500 noches",
                                                 "album": "19 días y 500 noches",
                                                 "artist": {
                                                     "id": 1,
                                                     "artist": "Daddy"
                                                 },
                                                 "year": 1999
                                             }
                                                                                      
                                            """
                            )}
                    )}),

            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
    })
    @GetMapping("/song/{id}")
    public ResponseEntity<SongArtistDto> findOne(@PathVariable Long id) {

        return ResponseEntity.of(songService.findById(id)
                .map(s -> convertDto.SongToSongArtistDto(s)));

    }

    @Operation(summary = "Agrega una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                                                        
                                                {
                                                 "id": 14,
                                                 "title": "mi abuela",
                                                 "album": "19 días y 500 noches",
                                                 "artist": "Maluma",
                                                 "year": 1999
                                                }
                                                                                      
                                            """
                            )}
                    )}),

            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la canción",
                    content = @Content),
    })
    @PostMapping("/song/")
    public ResponseEntity<SongDto> newSong(@RequestBody CreateSongDto dto) {
        if (dto.getArtistId() == null || dto.getTitle() == null || dto.getAlbum() == null
                || dto.getYear() == null) {
            return ResponseEntity.badRequest().build();
        }

        Song song = convertDto.CreateSongDtoToSong(dto);

        Artist artist = artistService.findById(dto.getArtistId()).orElse(null);

        song.setArtist(artist);

        song = songService.add(song);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(convertDto.songToSongDto(song));
    }

    @Operation(summary = "Edita una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = SongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                                  
                                                   {
                                                       "id": 9,
                                                       "title": "mi abuela",
                                                       "album": "19 días y 500 noches",
                                                       "artist": "No existe",
                                                       "year": 1999
                                                   }
                                                                                          
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Se ha encontrado algún error en los datos",
                    content = @Content)
    })
    @PutMapping("/song/{id}")
    public ResponseEntity<SongDto> editSong(@RequestBody CreateSongDto dto, @PathVariable Long id) {
        if (dto.getArtistId() == null || dto.getTitle() == null
                || dto.getAlbum() == null || dto.getYear() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.of(songService.findById(id).map(p -> {
                    p.setTitle(dto.getTitle());
                    if (p.getArtist() != null) {
                        p.getArtist().setId(dto.getArtistId());
                    }
                    p.setAlbum(dto.getAlbum());
                    p.setYear(dto.getYear());
                    songService.add(p);
                    return convertDto.songToSongDto(p);
                })
        );

    }

    @Operation(summary = "Borra una canción en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Song.class))})
    })
    @DeleteMapping("/song/{id}")
    public ResponseEntity<Song> deleteSong(@PathVariable Long id) {
        if (songRepo.existsById(id)) {
            playlistService.findAll().forEach(p -> {
                p.getSongs().removeAll(songService.findById(id).stream().collect(Collectors.toList()));
                playlistService.edit(p);
            });
            songService.deleteById(id);
        }
        return ResponseEntity.noContent().build();
    }
}

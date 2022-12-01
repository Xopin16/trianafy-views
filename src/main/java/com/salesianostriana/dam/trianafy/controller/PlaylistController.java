package com.salesianostriana.dam.trianafy.controller;

import com.salesianostriana.dam.trianafy.DTO.*;
import com.salesianostriana.dam.trianafy.model.Artist;
import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.repos.PlaylistRepository;
import com.salesianostriana.dam.trianafy.repos.SongRepository;
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
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class PlaylistController {

    private final PlaylistRepository playlistRepo;
    private final PlaylistService playlistService;

    private final PlaylistDtoConverter dtoConverter;

    private final SongDtoConverter songDtoConverter;

    private final SongRepository songRepo;

    private final SongService songService;

    @Operation(summary = "Obtiene todos las playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las playlist",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PlaylistDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {"id": 2, "name": "Románico", "numberOfSongs": 5},
                                                {"id": 2, "name": "Manolo", "numberOfSongs": 0}
                                            ]                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna playlist",
                    content = @Content),
    })
    @GetMapping("/list/")
    public ResponseEntity<List<PlaylistDto>> findAll() {
        List<Playlist> data = playlistService.findAll();

        if (data.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            List<PlaylistDto> result =
                    data.stream()
                            .map(PlaylistDto::of)
                            .collect(Collectors.toList());
            return ResponseEntity.ok(result);
        }
    }

    @Operation(summary = "Obtiene una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class),
                            examples = {@ExampleObject(
                                    value = """
                                                  
                                                    {
                                                "id": 12,
                                                "name": "Random",
                                                "description": "Una lista muy loca",
                                                "songs": [
                                                    {
                                                        "id": 9,
                                                        "title": "mi abuela",
                                                        "album": "19 días y 500 noches",
                                                        "year": 1999,
                                                        "artist": null
                                                    },
                                                    {
                                                        "id": 8,
                                                        "title": "Love Again",
                                                        "album": "Future Nostalgia",
                                                        "year": 2021,
                                                        "artist": {
                                                            "id": 2,
                                                            "name": "Dua Lipa"
                                                        }
                                                    }                                 
                                                ]
                                            }
                                                                                            
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })
    @GetMapping("/list/{id}")
    public ResponseEntity<Playlist> findOne(@PathVariable Long id) {

        return ResponseEntity.of(playlistService.findById(id));

    }

    @Operation(summary = "Agrega una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            [
                                                {
                                                    "id": 15,
                                                    "name": "Mi lista",
                                                    "description": "Mi lista de reproducción"
                                                }
                                            ]                                          
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la playlist",
                    content = @Content),
    })
    @PostMapping("/list/")
    public ResponseEntity<CreatePlaylistDto> newPlaylist(@RequestBody CreatePlaylistDto dto) {
        if (dto.getName() == null || dto.getDescription() == null) {
            return ResponseEntity.badRequest().build();
        }

        Playlist playlist = dtoConverter.CreatePlaylistDtoToPlaylist(dto);


        playlist = playlistService.add(playlist);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(dtoConverter.playlistToPlaylistDto(playlist));

    }


    @Operation(summary = "Edita una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha modificado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = PlaylistDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {
                                                    "id": 12,
                                                    "name": "Mi lista",
                                                    "numberOfSongs": 4
                                                }
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
            @ApiResponse(responseCode = "400",
                    description = "Se ha encontrado algún error en los datos",
                    content = @Content)
    })
    @PutMapping("/list/{id}")
    public ResponseEntity<PlaylistDto> editPlaylist(@RequestBody CreatePlaylistDto dto, @PathVariable Long id) {

        return ResponseEntity.of(playlistService.findById(id).map(p -> {
                    p.setName(dto.getName());
                    p.setDescription(dto.getDescription());
                    playlistService.add(p);
                    return PlaylistDto.of(p);
                })
        );
    }

    @Operation(summary = "Borra una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la playlist",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la playlist por el ID",
                    content = @Content),
    })
    @DeleteMapping("/list/{id}")
    public ResponseEntity<Playlist> deletePlaylist(@PathVariable Long id) {
        if (playlistRepo.existsById(id)) {
            playlistService.deleteById(id);
        }

        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Obtiene todas las canciones de una playlist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se han encontrado todos las canciones",
                    content = {@Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = GetPlaylistSongDto.class)),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 12,
                                                 "name": "Random",
                                                 "description": "Una lista muy loca",
                                                 "listSong": [
                                                     {
                                                         "id": 9,
                                                         "title": "Enter Sandman",
                                                         "album": "Metallica",
                                                         "artist": "Metallica",
                                                         "year": 1991
                                                     },
                                                     {
                                                         "id": 8,
                                                         "title": "Love Again",
                                                         "album": "Future Nostalgia",
                                                         "artist": "Dua Lipa",
                                                         "year": 2021
                                                     }
                                                 ]
                                             }                                        
                                            """
                            )}
                    )}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna cancion",
                    content = @Content),
    })


    @GetMapping("/list/{id}/song")
    public ResponseEntity<GetPlaylistSongDto> getSongsOfPlaylist(@PathVariable Long id) {
        return ResponseEntity.of(playlistService.findById(id)
                .map(playlist -> dtoConverter.of(playlist))
        );

    }


    @Operation(summary = "Obtiene una cancion de una playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
                    description = "Se ha encontrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistSongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            
                                                {
                                                    "id": 9,
                                                    "title": "mi abuela",
                                                    "album": "19 días y 500 noches",
                                                    "year": 1999,
                                                    "artist": null
                                                }
                                                                                      
                                            """
                            )})}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado ninguna canción de la playlist por el ID",
                    content = @Content),
    })
    @GetMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Song> getOneSongOfList(@PathVariable Long id1, @PathVariable Long id2) {
        return ResponseEntity.of(playlistService.findById(id1).get().getSongs()
                .stream()
                .filter(s -> s.getId().equals(id2))
                .findFirst());

    }

    @Operation(summary = "Agrega una canción a la playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201",
                    description = "Se ha agregado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = GetPlaylistSongDto.class),
                            examples = {@ExampleObject(
                                    value = """
                                            {
                                                 "id": 12,
                                                 "name": "Random",
                                                 "description": "Una lista muy loca",
                                                 "listSong": [
                                                     {
                                                         "id": 9,
                                                         "title": "mi abuela",
                                                         "album": "19 días y 500 noches",
                                                         "artist": "No existe",
                                                         "year": 1999
                                                     },
                                                     {
                                                         "id": 8,
                                                         "title": "Love Again",
                                                         "album": "Future Nostalgia",
                                                         "artist": "Dua Lipa",
                                                         "year": 2021
                                                     }
                                                 ]
                                             }                                          
                                            """
                            )})}),
            @ApiResponse(responseCode = "400",
                    description = "No se ha agregado la canción",
                    content = @Content),
    })
    @PostMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<GetPlaylistSongDto> newSongToPlaylist(@PathVariable Long id1, @PathVariable Long id2) {
        if (playlistService.findById(id1).isEmpty() ||
                songService.findById(id2).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        playlistService.findById(id1).get().getSongs().add(songService.findById(id2).get());
        playlistService.add(playlistService.findById(id1).get());
        return ResponseEntity.of(playlistRepo.findById(id1)
                .map(playlist -> dtoConverter.of(playlist)));


    }

    @Operation(summary = "Borra una canción de la playlist en base a su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204",
                    description = "Se ha borrado la canción",
                    content = {@Content(mediaType = "application/json",
                            schema = @Schema(implementation = Playlist.class))}),
            @ApiResponse(responseCode = "404",
                    description = "No se ha encontrado la canción por el ID",
                    content = @Content),
    })
    @DeleteMapping("/list/{id1}/song/{id2}")
    public ResponseEntity<Playlist> deleteSongOfPlaylist(@PathVariable Long id1, @PathVariable Long id2) {
        if (playlistRepo.existsById(id1) & songRepo.existsById(id2)) {
            playlistService.findById(id1).get().getSongs().removeAll(songRepo.findById(id2).stream().toList());
            playlistService.add(playlistService.findById(id1).get());
            return ResponseEntity.noContent().build();
        }else{
            return ResponseEntity.notFound().build();
        }
    }


}

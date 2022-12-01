package com.salesianostriana.dam.trianafy.DTO;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class PlaylistDtoConverter {

    @Autowired
    private SongDtoConverter dtoConverter;

    public Playlist CreatePlaylistDtoToPlaylist(CreatePlaylistDto c) {
        return new Playlist(
                c.getId(),
                c.getName(),
                c.getDescription()
        );
    }

    public CreatePlaylistDto playlistToPlaylistDto(Playlist playlist) {
        return CreatePlaylistDto
                .builder()
                .id(playlist.getId())
                .name(playlist.getName())
                .description(playlist.getDescription())
                .build();
    }

    public GetPlaylistSongDto of(Playlist p) {
        return GetPlaylistSongDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .description(p.getDescription())
                .listSong(p.getSongs().stream().filter(song -> song.getId() != null).map(s -> dtoConverter.songToSongDto(s)).collect(Collectors.toList()))
                .build();

    }
}

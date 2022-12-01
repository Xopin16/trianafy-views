package com.salesianostriana.dam.trianafy.DTO;

import com.salesianostriana.dam.trianafy.model.Playlist;
import com.salesianostriana.dam.trianafy.model.Song;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlaylistDto {

    private Long id;
    private String name;
    private int numberOfSongs;

    public static PlaylistDto of (Playlist p){
        return PlaylistDto
                .builder()
                .id(p.getId())
                .name(p.getName())
                .numberOfSongs(p.getSongs().stream().filter(s-> s.getId() != null).collect(Collectors.toList()).size())
                .build();
    }
}

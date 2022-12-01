package com.salesianostriana.dam.trianafy.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetPlaylistSongDto {

    private Long id;
    private String name;
    private String description;
    private List<SongDto> listSong;

}

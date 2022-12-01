package com.salesianostriana.dam.trianafy.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.views.SongView;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreateSongDto {
    @JsonView(SongView.Public.class)
    private Long id;
    @JsonView(SongView.Public.class)
    private String title;
    @JsonView(SongView.Public.class)
    private String album;
    @JsonView(SongView.Public.class)
    private String year;
    @JsonView(SongView.Public.class)
    private Long artistId;
}

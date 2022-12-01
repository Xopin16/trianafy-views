package com.salesianostriana.dam.trianafy.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.views.SongView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor @AllArgsConstructor
@Builder
public class SongDto {

    private Long id;
    private String title, album;
    @JsonView(SongView.Public.class)
    private String artist;
    private String year;
    
}

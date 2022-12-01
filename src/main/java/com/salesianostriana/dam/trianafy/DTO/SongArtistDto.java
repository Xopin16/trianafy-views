package com.salesianostriana.dam.trianafy.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SongArtistDto {

    private Long id;
    private String title, album;
    private ArtistDto artist;
    private String year;
}

package com.salesianostriana.dam.trianafy.DTO;

import com.salesianostriana.dam.trianafy.model.Artist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ConvertArtistDto {

    public ArtistDto artistToArtistDto (Artist artist){
        return ArtistDto
                .builder()
                .id(artist.getId())
                .artist(artist.getName())
                .build();
    }
}

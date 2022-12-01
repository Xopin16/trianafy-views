package com.salesianostriana.dam.trianafy.DTO;

import com.fasterxml.jackson.annotation.JsonView;
import com.salesianostriana.dam.trianafy.model.Song;
import com.salesianostriana.dam.trianafy.views.SongView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;

@Data
@Builder
public class SongDtoView {

    @JsonView({SongView.Public.class, SongView.Internal.class})
    private Long id;
    @JsonView(SongView.Internal.class)
    private Long artistId;
    @JsonView(SongView.Public.class)
    private String artist;
    @JsonView({SongView.Public.class, SongView.Internal.class})
    private String year;
    @JsonView({SongView.Public.class, SongView.Internal.class})
    private String album;
    @JsonView({SongView.Public.class, SongView.Internal.class})
    private String title;

    public static SongDtoView of (Song song){
        if(song.getArtist() != null) {
            return SongDtoView
                    .builder()
                    .id(song.getId())
                    .artistId(song.getArtist().getId())
                    .artist(song.getArtist().getName())
                    .album(song.getAlbum())
                    .title(song.getTitle())
                    .year(song.getYear())
                    .build();
        }else{
            return SongDtoView
                    .builder()
                    .id(song.getId())
                    .artistId(null)
                    .artist(null)
                    .album(song.getAlbum())
                    .title(song.getTitle())
                    .year(song.getYear())
                    .build();
        }
    }


}

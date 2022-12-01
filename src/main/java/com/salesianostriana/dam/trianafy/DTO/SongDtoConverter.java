package com.salesianostriana.dam.trianafy.DTO;

import com.salesianostriana.dam.trianafy.model.Song;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SongDtoConverter {

    @Autowired
    private ConvertArtistDto convertDto;

    public Song CreateSongDtoToSong(CreateSongDto c){
        return new Song(
                c.getId(),
                c.getTitle(),
                c.getAlbum(),
                c.getYear()
        );
    }
    public SongArtistDto SongToSongArtistDto (Song song){
        if (song.getArtist() != null){
            return SongArtistDto
                    .builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .artist(convertDto.artistToArtistDto(song.getArtist()))
                    .album(song.getAlbum())
                    .year(song.getYear())
                    .build();
        }else{
            return SongArtistDto
                    .builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .artist(null)
                    .album(song.getAlbum())
                    .year(song.getYear())
                    .build();
        }

    }
    public SongDto songToSongDto (Song song){
        if(song.getArtist() != null){
            return SongDto
                    .builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .album(song.getAlbum())
                    .artist(song.getArtist().getName())
                    .year(song.getYear())
                    .build();
        }else {
            return SongDto
                    .builder()
                    .id(song.getId())
                    .title(song.getTitle())
                    .album(song.getAlbum())
                    .artist("No existe")
                    .year(song.getYear())
                    .build();
        }

    }
}

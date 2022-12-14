package com.salesianostriana.dam.trianafy.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Playlist {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private String description;

    @ManyToMany(fetch = FetchType.EAGER)
    @Builder.Default
    private List<Song> songs = new ArrayList<>();

    public Playlist(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }


    public void addSong(Song song) {
        songs.add(song);
    }

    public void deleteSong(Song song) {
        songs.remove(song);
    }


}

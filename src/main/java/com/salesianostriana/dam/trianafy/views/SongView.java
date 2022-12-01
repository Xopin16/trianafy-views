package com.salesianostriana.dam.trianafy.views;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import org.springframework.stereotype.Component;

public class SongView {
    public static class Public{
    }

    public static class ShowPublic extends Public{
    }

    public static class Internal{}

    public static class ShowInternal extends Internal{}

}

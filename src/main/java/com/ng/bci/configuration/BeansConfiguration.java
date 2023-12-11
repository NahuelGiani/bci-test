package com.ng.bci.configuration;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.time.ZonedDateTime;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {

  @Bean
  ModelMapper modelMapper() {
    return new ModelMapper();
  }

  @Bean
  Gson gson() {
    return new GsonBuilder()
        .registerTypeAdapter(ZonedDateTime.class, new TypeAdapter<ZonedDateTime>() {
          @Override
          public void write(JsonWriter out, ZonedDateTime value) throws IOException {
            out.value(value != null ? value.toOffsetDateTime().toString() : null);
          }

          @Override
          public ZonedDateTime read(JsonReader in) throws IOException {
            return ZonedDateTime.parse(in.nextString());
          }
        })
        .enableComplexMapKeySerialization()
        .create();
  }

}

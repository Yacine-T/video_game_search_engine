package fr.lernejo.fileinjector.recorders;


//import com.fasterxml.jackson.datatype.jackson-datatype-jsr310;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import java.time.LocalDate;

//@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
//@JsonSerialize
public record Game_info(int id, String title, String thumbnail, String short_description, String game_url, String genre,
                        String platform, String publisher, String developer,
                        @JsonSerialize(using = LocalDateSerializer.class) LocalDate release_date,
                        String freetogame_profile_url)
{
}

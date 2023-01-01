package fr.lernejo.fileinjector;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import fr.lernejo.fileinjector.recorders.Game_info;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) throws IOException, InvalidPathException {
        if (args.length == 1) {
            try (final AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class);
            ) {
                SendingMessages( new ObjectMapper().findAndRegisterModules(), springContext, args[0]);
            } catch (IOException err) {
                throw new IOException(err);
            }
        } else {
            System.out.println("Argument missing : At least one for the path of the resource !!! ");
        }
    }

    private static void SendingMessages(ObjectMapper mapper, AbstractApplicationContext springContext, String filePath) throws IOException, InvalidPathException {
        RabbitTemplate rabbitTemplate = springContext.getBean(RabbitTemplate.class);
        mapper.enable(SerializationFeature.INDENT_OUTPUT).setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        final Game_info[] games = mapper.readValue(Paths.get(filePath).toFile(), Game_info[].class);

        for (Game_info game : games) {
            String str_game = mapper.writeValueAsString(game);
            rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
            rabbitTemplate.convertAndSend("", "game_info", str_game, m -> {
                m.getMessageProperties().getHeaders().put("game_id", game.id());
                m.getMessageProperties().setContentType("appplication/json");
                return m;
            }); // sending message
        }
    }
}

package fr.lernejo.fileinjector;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.nio.file.Paths;
import java.util.Map;

@SpringBootApplication
public class Launcher {

    public static void main(String[] args) {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                Map[] games_map = objectMapper.readValue(Paths.get(args[0]).toFile(), Map[].class);
                RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
                for (Map game : games_map) {
                    template.setMessageConverter(new Jackson2JsonMessageConverter());
                    template.convertAndSend("", "game_info", game, m -> {
                        m.getMessageProperties().getHeaders().put("game_id", game.get("id"));
                        return m;
                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

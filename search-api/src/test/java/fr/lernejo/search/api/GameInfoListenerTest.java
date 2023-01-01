package fr.lernejo.search.api;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;


class GameInfoListenerTest {
    public final String GAME_INFO = "game_info";
    public final String MSG = "{\"id\": 1,\"title\": \"Dauntless\",\"thumbnail\": \"https://www.freetogame.com/g/1/thumbnail.jpg\",\"short_description\": \"A free-to-play, co-op action RPG with gameplay similar to Monster Hunter.\",\"game_url\": \"https://www.freetogame.com/open/dauntless\",\"genre\": \"MMORPG\",\"platform\": \"PC (Windows)\",\"publisher\": \"Phoenix Labs\",\"developer\": \"Phoenix Labs, Iron Galaxy\",\"release_date\": \"2019-05-21\",\"freetogame_profile_url\": \"https://www.freetogame.com/dauntless\"}";
    public final String GAME_ID = "game_id";

    @Test
    void gameInfoListenerSuccess() {
        try (AbstractApplicationContext springContext = new AnnotationConfigApplicationContext(Launcher.class)) {
            RabbitTemplate template = springContext.getBean(RabbitTemplate.class);
            template.convertAndSend("", GAME_INFO, MSG, msg -> {
                msg.getMessageProperties().setContentType(MessageProperties.CONTENT_TYPE_JSON);
                msg.getMessageProperties().setHeader(GAME_ID, "1");
                return msg;
            });
        }
    }


}

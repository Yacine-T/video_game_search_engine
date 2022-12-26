package fr.lernejo.search.api;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

public class GameInfoListener {
    public GameInfoListener(RestHighLevelClient restHighLevelClient){
    }

    @RabbitListener(queues="${GAME_INFO_QUEUE}")
    public void onMessage(){

    }
}

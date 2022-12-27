package fr.lernejo.search.api;

import org.elasticsearch.ElasticsearchException;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.index.Index;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.xcontent.XContentType;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static fr.lernejo.search.api.AmqpConfiguration.GAME_INFO_QUEUE;

@Component
public class GameInfoListener {

    RestHighLevelClient restHighLevelClient;
    public GameInfoListener(RestHighLevelClient restHighLevelClient){
        this.restHighLevelClient = restHighLevelClient;
    }

    @RabbitListener(queues=GAME_INFO_QUEUE)
    public void onMessage(final Message message){
        String id = message.getMessageProperties().getHeader("game_id").toString();
        String body = message.getBody().toString();
        IndexRequest indexRequest = new IndexRequest("games");
        indexRequest.id(id).source(body, XContentType.JSON);
        try {
            this.restHighLevelClient.index(indexRequest, RequestOptions.DEFAULT);
        } catch(ElasticsearchException e) {
            if (e.status() == RestStatus.CONFLICT)
                throw e;
        }   catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}

package fr.lernejo.search.api;

import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.index.query.QueryStringQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.ArrayList;

public class ApiController {
    private final RestHighLevelClient restHighLevelClient;

    ApiController(RestHighLevelClient restHighLevelClient) {
        this.restHighLevelClient = restHighLevelClient;
    }

    @GetMapping("/api/games")
    ArrayList<Object> get(@RequestParam(name = "query") String query) throws IOException {
        SearchRequest sreq = new SearchRequest().source(SearchSourceBuilder.searchSource().query(new QueryStringQueryBuilder(query)));
        ArrayList<Object> res = new ArrayList<>();
        SearchResponse searchResponse = this.restHighLevelClient.search(sreq, RequestOptions.DEFAULT);

        searchResponse.getHits().forEach(hit -> res.add(hit.getSourceAsMap()));

        return res;
    }
}

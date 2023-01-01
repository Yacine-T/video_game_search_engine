package fr.lernejo.search.api;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;



class ElasticSearchConfigurationTest {


//    private static String getBasicAuthenticationHeader(String username, String password) {
//        String valueToEncode = username + ":" + password;
//        return "Basic " + Base64.getEncoder().encodeToString(valueToEncode.getBytes());
//    }
//
//    @Test
//    void simple_test_elastic_configuration() {
//        IOException test = null;
//        try {
//            HttpClient client = HttpClient.newHttpClient();
//            HttpRequest getRequest = HttpRequest.newBuilder()
//                .uri(URI.create("http://localhost:9200"))
//                .header("Authorization", getBasicAuthenticationHeader("elastic", "admin"))
//                .build();
//            HttpResponse<String> response = client.send(getRequest, HttpResponse.BodyHandlers.ofString());
//        } catch (IOException | InterruptedException e) {
//            test = (IOException) e;
//        }
//        Assertions.assertThat(test).isNull();
//    }
}

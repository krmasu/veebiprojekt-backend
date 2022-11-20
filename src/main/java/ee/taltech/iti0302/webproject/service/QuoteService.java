package ee.taltech.iti0302.webproject.service;

import ee.taltech.iti0302.webproject.dto.QuoteDto;
import ee.taltech.iti0302.webproject.exception.ApplicationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@Service
public class QuoteService {

    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.api-ninjas.com/v1/quotes";
    private static final String API_KEY = "M+y55/ri/49+KmWA57zXew==wPoo7kdV0BrZ3PfS";

    public HttpHeaders getApiKeyHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        return headers;
    }
    public QuoteDto getQuote(String category) {
        HttpEntity<?> request = new HttpEntity<>(getApiKeyHeader());
        ResponseEntity<QuoteDto[]> response;
        if (category != null) {
            Map<String, String> params = new HashMap<>();
            params.put("category", category);
            response = restTemplate.exchange(API_URL + "?category={category}", HttpMethod.GET, request, QuoteDto[].class, params);
        } else {
            response = restTemplate.exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class);
        }
        if (response.getStatusCode() == HttpStatus.OK) {
            try {
                return Objects.requireNonNull(response.getBody())[0];
            } catch (ArrayIndexOutOfBoundsException | NullPointerException e) {
                throw new ApplicationException("Error getting quote");
            }
        } else {
            throw new ApplicationException("Error getting quote");
        }
    }
}

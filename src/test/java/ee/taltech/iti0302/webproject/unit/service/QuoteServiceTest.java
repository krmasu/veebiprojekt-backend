package ee.taltech.iti0302.webproject.unit.service;

import ee.taltech.iti0302.webproject.dto.quote.QuoteDto;
import ee.taltech.iti0302.webproject.exception.ApplicationException;
import ee.taltech.iti0302.webproject.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class QuoteServiceTest {
    @InjectMocks
    private QuoteService quoteService;
    @Mock
    private RestTemplate restTemplate;
    private static final String API_URL = "https://api.api-ninjas.com/v1/quotes";
    private static final String API_KEY = "M+y55/ri/49+KmWA57zXew==wPoo7kdV0BrZ3PfS";

    @Test
    void getQuote_WithCategory_ReturnsQuoteDto() {
        // given
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("One should not push code without tests, but they do it anyway..")
                .category("programming")
                .author("Some wise coding master")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        HttpEntity<?> request = new HttpEntity<>(headers);

        Map<String, String> params = new HashMap<>();
        params.put("category", "programming");

        QuoteDto[] quoteDtos = {quoteDto};
        ResponseEntity<QuoteDto[]> response = new ResponseEntity<>(quoteDtos, HttpStatus.OK);

        given(restTemplate.exchange(API_URL + "?category={category}", HttpMethod.GET, request, QuoteDto[].class, params)).willReturn(response);

        // when
        var result = quoteService.getQuote("programming");

        // then
        then(restTemplate).should().exchange(API_URL + "?category={category}", HttpMethod.GET, request, QuoteDto[].class, params);
        assertEquals("programming", result.getCategory());
    }

    @Test
    void getQuote_WithoutCategory_ReturnsQuoteDto() {
        // given
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("One should not push code without tests, but they do it anyway..")
                .category("programming")
                .author("Some wise coding master")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        HttpEntity<?> request = new HttpEntity<>(headers);

        QuoteDto[] quoteDtos = {quoteDto};
        ResponseEntity<QuoteDto[]> response = new ResponseEntity<>(quoteDtos, HttpStatus.OK);

        given(restTemplate.exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class)).willReturn(response);

        // when
        var result = quoteService.getQuote(null);

        // then
        then(restTemplate).should().exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class);
        assertEquals("programming", result.getCategory());
    }

    @Test
    void getQuote_EmptyResponse_ThrowsApplicationException() {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<QuoteDto[]> response = new ResponseEntity<>(null, HttpStatus.OK);

        given(restTemplate.exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class)).willReturn(response);

        // when
        assertThrows(ApplicationException.class, () -> quoteService.getQuote(null));

        // then
        then(restTemplate).should().exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class);
    }

    @Test
    void getQuote_BadRequest_ThrowsApplicationException() {
        // given
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-api-key", API_KEY);
        HttpEntity<?> request = new HttpEntity<>(headers);

        ResponseEntity<QuoteDto[]> response = new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);

        given(restTemplate.exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class)).willReturn(response);

        // when
        assertThrows(ApplicationException.class, () -> quoteService.getQuote(null));

        // then
        then(restTemplate).should().exchange(API_URL, HttpMethod.GET, request, QuoteDto[].class);
    }
}
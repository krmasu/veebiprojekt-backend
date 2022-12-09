package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.quote.QuoteDto;
import ee.taltech.iti0302.webproject.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {
    @Mock
    private QuoteService quoteService;
    @InjectMocks
    private QuoteController quoteController;

    @Test
    void GetQuote_WithoutCategory_ReturnsRandomQuoteDto() {
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("One should not push code without tests, but they do it anyway..")
                .category("Programming")
                .author("Some wise coding master")
                .build();

        given(quoteService.getQuote(null)).willReturn(quoteDto);

        var result = quoteController.getQuote(null);

        assertEquals("Programming", result.getCategory());
        assertEquals("Some wise coding master", result.getAuthor());
    }

    @Test
    void GetQuote_WithCategory_ReturnsQuoteDtoFromCategory() {
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("I once went to Tallinn!")
                .category("Inspirational")
                .author("Pets from Põlvamaa")
                .build();

        given(quoteService.getQuote("Inspirational")).willReturn(quoteDto);

        var result = quoteController.getQuote("Inspirational");

        assertEquals("Inspirational", result.getCategory());
        assertEquals("Pets from Põlvamaa", result.getAuthor());
    }
}
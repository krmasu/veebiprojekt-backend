package ee.taltech.iti0302.webproject.unit.controller;

import ee.taltech.iti0302.webproject.controller.QuoteController;
import ee.taltech.iti0302.webproject.dto.quote.QuoteDto;
import ee.taltech.iti0302.webproject.service.QuoteService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

@ExtendWith(MockitoExtension.class)
class QuoteControllerTest {
    @Mock
    private QuoteService quoteService;
    @InjectMocks
    private QuoteController quoteController;

    @Test
    void GetQuote_WithoutCategory_ReturnsRandomQuoteDto() {
        // when
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("One should not push code without tests, but they do it anyway..")
                .category("Programming")
                .author("Some wise coding master")
                .build();

        given(quoteService.getQuote(null)).willReturn(quoteDto);
        // when
        var result = quoteController.getQuote(null);
        // then
        then(quoteService).should().getQuote(null);
        assertEquals("Programming", result.getCategory());
        assertEquals("Some wise coding master", result.getAuthor());
    }

    @Test
    void GetQuote_WithCategory_ReturnsQuoteDtoFromCategory() {
        // given
        QuoteDto quoteDto = QuoteDto.builder()
                .quote("I once went to Tallinn!")
                .category("Inspirational")
                .author("Pets from Põlvamaa")
                .build();

        given(quoteService.getQuote("Inspirational")).willReturn(quoteDto);
        // when
        var result = quoteController.getQuote("Inspirational");
        // then
        then(quoteService).should().getQuote("Inspirational");
        assertEquals("Inspirational", result.getCategory());
        assertEquals("Pets from Põlvamaa", result.getAuthor());
    }
}
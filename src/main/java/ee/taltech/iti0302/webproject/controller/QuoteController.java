package ee.taltech.iti0302.webproject.controller;

import ee.taltech.iti0302.webproject.dto.quote.QuoteDto;
import ee.taltech.iti0302.webproject.service.QuoteService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
public class QuoteController {
    private final QuoteService quoteService;

    @GetMapping(value = { "/api/quote", "/api/quote/{category}" })
    public QuoteDto getQuote(@PathVariable(value = "category", required = false) String category) {
        return quoteService.getQuote(category);
    }
}

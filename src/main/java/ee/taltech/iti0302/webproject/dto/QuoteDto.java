package ee.taltech.iti0302.webproject.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuoteDto {
    private String quote;
    private String author;
    private String category;
}

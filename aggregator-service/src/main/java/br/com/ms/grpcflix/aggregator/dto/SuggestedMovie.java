package br.com.ms.grpcflix.aggregator.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SuggestedMovie {
    private String title;
    private int movieYear;
    private double rating;
}

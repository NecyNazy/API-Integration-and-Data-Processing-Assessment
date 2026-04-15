package com.backend_torch.API.integration.and.Data.Processing.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassifyNameResponse {

    private String status;
    private Data data;

    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class Data {
        private String name;
        private String gender;
        private double probability;
        private Long sample_size;
        private Boolean is_confident;
        private String processed_at; // ISO 8601 string
    }
}
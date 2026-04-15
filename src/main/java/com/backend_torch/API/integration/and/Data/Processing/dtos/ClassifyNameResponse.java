package com.backend_torch.API.integration.and.Data.Processing.dtos;

import lombok.*;

import java.time.OffsetDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassifyNameResponse {
    private String name;
    private String gender;
    private double probability;
    private Long sample_size;
    private Boolean is_confident;
    private OffsetDateTime processed_at;
}

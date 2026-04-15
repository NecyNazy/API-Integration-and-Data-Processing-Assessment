package com.backend_torch.API.integration.and.Data.Processing.dtos;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GenderizeResponse {
    private String name;
    private String gender;
    private double probability;
    private Long count;
}

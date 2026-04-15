package com.backend_torch.API.integration.and.Data.Processing.dtos;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassifyNameRequest {
    @NotNull( message = "Name is required")
    private String name;
}

package com.backend_torch.API.integration.and.Data.Processing.dtos;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Builder
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "app")
@Component
public class AppProperties {
    private String genderizeBaseUrl;
}

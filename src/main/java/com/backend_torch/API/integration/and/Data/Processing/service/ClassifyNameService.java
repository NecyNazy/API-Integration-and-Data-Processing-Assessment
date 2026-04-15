package com.backend_torch.API.integration.and.Data.Processing.service;

import com.backend_torch.API.integration.and.Data.Processing.dtos.AppProperties;
import com.backend_torch.API.integration.and.Data.Processing.dtos.ClassifyNameResponse;
import com.backend_torch.API.integration.and.Data.Processing.dtos.GenderizeResponse;
import com.backend_torch.API.integration.and.Data.Processing.exceptions.ApiException;
import com.backend_torch.API.integration.and.Data.Processing.helper.HttpShooter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

@Service
@RequiredArgsConstructor
public class ClassifyNameService {
    private final HttpShooter httpShooter;
    private final AppProperties appProperties;

    public ResponseEntity<ClassifyNameResponse> classifyName(String name) {
        //1. Validate Input
        if (name == null || name.trim().isEmpty()|| !name.matches("^[a-zA-Z]+$") ) {
            throw new ApiException("error", "Name is required and must be a valid string without any special characters", 422);
        }

        // 2. Build URL for GET request (Genderize expects query param)
        String url = appProperties.getGenderizeBaseUrl() + "?name=" + name.trim();


        //3. Send request to genderize api(External Api Call)
        ResponseEntity<GenderizeResponse> response = httpShooter.getRequest(
                url,
                GenderizeResponse.class
        );
        System.out.println(response);

        //4. retrieve response from genderize api
        GenderizeResponse responseBody = response.getBody();

        //5.Handle error response
        if (responseBody == null) {
            throw new ApiException("error", "Invalid response from Genderize API", 502);
        }

        if(responseBody.getName() == null){
            throw new ApiException("error", "Bad Request", 400);
        }

        if(responseBody.getCount() == 0 || responseBody.getGender() == null) {
            throw new ApiException("error", "No Prediction available for the provided name");
        }

        //6. Compute confidence
        boolean is_confident = responseBody.getProbability() >= 0.7 && responseBody.getCount() >= 100;

        // 7. Build response
        ClassifyNameResponse classifyNameResponse = ClassifyNameResponse.builder()
                .status("success")
                .data(ClassifyNameResponse.Data.builder()
                        .name(responseBody.getName())
                        .gender(responseBody.getGender())
                        .probability(responseBody.getProbability())
                        .sample_size(responseBody.getCount())
                        .is_confident(is_confident)
                        .processed_at(OffsetDateTime.now(ZoneOffset.UTC).toString())
                        .build())
                .build();

        return ResponseEntity.ok(classifyNameResponse);


    }
}

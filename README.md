# Name Classification API

A lightweight Spring Boot API that integrates with the external Genderize service to classify a given name by gender, along with probability scoring and confidence evaluation.

The service exposes a simple REST endpoint that accepts a name and returns structured classification results.

---

## Features

- Name gender classification via external API integration
- Confidence scoring based on probability and sample size
- UTC timestamp (`processed_at`) for every response
- Clean RESTful API design
- Robust error handling with meaningful HTTP status codes
- Input validation for safe request processing

---

## How It Works

1. Client sends a name via query parameter
2. API calls the external Genderize service
3. Response is validated and processed
4. Confidence is calculated:
   - `is_confident = probability >= 0.7 AND sample_size >= 100`
5. Final response is returned with metadata

---

## API Endpoint

### Classify Name

```http
GET /api/classify?name={name}

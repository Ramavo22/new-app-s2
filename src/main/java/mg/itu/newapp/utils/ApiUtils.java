package mg.itu.newapp.utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import mg.itu.newapp.utils.frappe.FrappeResponse;
import mg.itu.newapp.utils.frappe.FrappeResponseWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class ApiUtils {

    private RestTemplate restTemplate;
    private String baseUrl;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public ApiUtils(RestTemplate restTemplate,
                    @Value("${frappe.base.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.baseUrl = baseUrl;
    }

    public <T> ResponseEntity<T> call(
            String endpoint,
            HttpMethod method,
            Object requestBody,
            Class<T> responseType,
            Map<String, String> headersMap
    ) {
        String url = baseUrl + endpoint;
        HttpHeaders headers = new HttpHeaders();
        if (headersMap != null) {
            headersMap.forEach(headers::set);
        }
        HttpEntity<Object> entity = new HttpEntity<>(requestBody, headers);
        return restTemplate.exchange(url, method, entity, responseType);
    }

    public <T> FrappeResponse<T> bodyMessageToFrappeResponse(ResponseEntity<String> response, TypeReference<FrappeResponseWrapper<T>> typeRef) {
        try {
            FrappeResponseWrapper<T> wrapper = objectMapper.readValue(response.getBody(), typeRef);
            return wrapper.getMessage();
        } catch (Exception e) {
            throw new RuntimeException("Erreur lors du parsing de la réponse Frappe", e);
        }
    }

}

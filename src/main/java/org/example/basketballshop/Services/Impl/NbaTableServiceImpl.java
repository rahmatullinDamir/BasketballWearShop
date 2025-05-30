package org.example.basketballshop.Services.Impl;

import org.example.basketballshop.DTO.NbaTableResponse.NbaTableResponse;
import org.example.basketballshop.Services.NbaTableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class NbaTableServiceImpl implements NbaTableService {
    @Value("${nba.score.url}")
    private String responseUrl;

    @Value("${nba.score.api_key}")
    private String responseApiKey;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public NbaTableResponse[] getNbaTableData(String conference) {
        String url = responseUrl + "?key=" + responseApiKey;
        NbaTableResponse[] data = restTemplate.getForObject(url, NbaTableResponse[].class);
        return data;
    }
}

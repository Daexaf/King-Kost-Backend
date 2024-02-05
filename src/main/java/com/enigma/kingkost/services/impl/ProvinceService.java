package com.enigma.kingkost.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ProvinceService {
    @Value("${api.endpoint.url.province}")
    private String BASE_URL;

    private final RestTemplate restTemplate;

}

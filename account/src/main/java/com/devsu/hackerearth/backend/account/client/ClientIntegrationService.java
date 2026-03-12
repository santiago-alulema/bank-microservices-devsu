package com.devsu.hackerearth.backend.account.client;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.devsu.hackerearth.backend.account.client.modelClient.ClientResponseDto;

@Service
public class ClientIntegrationService {
    private final RestTemplate restTemplate;

    @Value("${client.service.url}")
    private String clientServicesUrl;

    public ClientIntegrationService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Async
    public CompletableFuture<ClientResponseDto> getClientByIdAsync(Long clientId) {
        try {
            String url = clientServicesUrl + "/api/clients/" + clientId;
            ClientResponseDto client = restTemplate
                    .getForObject(url, ClientResponseDto.class);
            return CompletableFuture.completedFuture(client);
        } catch (Exception e) {
            return CompletableFuture.completedFuture(null);
        }
    }
}

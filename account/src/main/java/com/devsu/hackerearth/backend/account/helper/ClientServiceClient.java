package com.devsu.hackerearth.backend.account.helper;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.account.client.ClientIntegrationService;
import com.devsu.hackerearth.backend.account.client.modelClient.ClientResponseDto;
import com.devsu.hackerearth.backend.account.exception.BadRequestException;

@Service
public class ClientServiceClient {

    private final ClientIntegrationService clientIntegrationService;

    public ClientServiceClient(ClientIntegrationService clientIntegrationService) {
        this.clientIntegrationService = clientIntegrationService;
    }

    public ClientResponseDto getClientService(Long id) {
        try {
            ClientResponseDto clientResponse = clientIntegrationService.getClientByIdAsync(id)
                    .get();
            if (clientResponse == null) {
                throw new BadRequestException("Client not found.");
            }

            return clientResponse;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("La operación fue interrumpida", e);
        } catch (java.util.concurrent.ExecutionException e) {
            throw new RuntimeException("Error al consultar el microservicio client: " + e.getCause().getMessage(), e);
        }
    }
}

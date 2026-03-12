package com.devsu.hackerearth.backend.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.devsu.hackerearth.backend.client.controller.ClientController;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.service.ClientService;

@SpringBootTest
public class sampleTest {

    private ClientService clientService = mock(ClientService.class);
    private ClientController clientController = new ClientController(clientService);

    @Test
    void createClientTest() {
        Client client = new Client();
        client.setId(1L);
        client.setDni("1234567890");
        client.setName("Santiago");
        client.setPassword("12345");
        client.setGender("Male");
        client.setAge(33);
        client.setAddress("Cuenca-Ecuador");
        client.setPhone("0999999");
        client.setActive(true);

        assertEquals(1L, client.getId());
        assertEquals("1234567890", client.getDni());
        assertEquals("Santiago", client.getName());
        assertEquals("12345", client.getPassword());
        assertEquals("Male", client.getGender());
        assertEquals(33, client.getAge());
        assertEquals("Cuenca-Ecuador", client.getAddress());
        assertEquals("0999999", client.getPhone());
        assertTrue(client.isActive());
    }
}

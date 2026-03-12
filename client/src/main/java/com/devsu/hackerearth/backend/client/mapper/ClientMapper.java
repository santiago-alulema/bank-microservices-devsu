package com.devsu.hackerearth.backend.client.mapper;

import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;

public class ClientMapper {

    public static ClientDto toDto(Client client) {
        if (client == null) {
            return null;
        }

        return new ClientDto(
                client.getId(),
                client.getDni(),
                client.getName(),
                client.getPassword(),
                client.getGender(),
                client.getAge(),
                client.getAddress(),
                client.getPhone(),
                client.isActive());
    }

    public static Client toEntity(ClientDto clientDto) {
        if (clientDto == null) {
            return null;
        }

        Client client = new Client();
        client.setId(clientDto.getId());
        client.setDni(clientDto.getDni());
        client.setName(clientDto.getName());
        client.setPassword(clientDto.getPassword());
        client.setGender(clientDto.getGender());
        client.setAge(clientDto.getAge());
        client.setAddress(clientDto.getAddress());
        client.setPhone(clientDto.getPhone());
        client.setActive(clientDto.isActive());

        return client;
    }

}

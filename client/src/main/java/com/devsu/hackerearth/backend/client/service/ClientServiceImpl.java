package com.devsu.hackerearth.backend.client.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.devsu.hackerearth.backend.client.exception.BadRequestException;
import com.devsu.hackerearth.backend.client.exception.ResourceNotFoundException;
import com.devsu.hackerearth.backend.client.mapper.ClientMapper;
import com.devsu.hackerearth.backend.client.model.Client;
import com.devsu.hackerearth.backend.client.model.dto.ClientDto;
import com.devsu.hackerearth.backend.client.model.dto.PartialClientDto;
import com.devsu.hackerearth.backend.client.repository.ClientRepository;

@Service
public class ClientServiceImpl implements ClientService {

	private final ClientRepository clientRepository;

	public ClientServiceImpl(ClientRepository clientRepository) {
		this.clientRepository = clientRepository;
	}

	@Override
	public List<ClientDto> getAll() {
		return clientRepository.findAll()
				.stream()
				.map(ClientMapper::toDto)
				.collect(Collectors.toList());
	}

	@Override
	public ClientDto getById(Long id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not fount"));
		return ClientMapper.toDto(client);
	}

	@Override
	public ClientDto create(ClientDto clientDto) {
		Client client = ClientMapper.toEntity(clientDto);
		Client saveClient = clientRepository.save(client);
		ClientDto dto = ClientMapper.toDto(saveClient);
		return dto;
	}

	@Override
	public ClientDto update(ClientDto clientDto) {
		if (clientDto.getId() == null) {
			throw new BadRequestException("Client id is required");
		}

		Client client = clientRepository.findById(clientDto.getId()).orElse(null);

		if (client == null) {
			throw new ResourceNotFoundException("Client not found");
		}

		client.setDni(clientDto.getDni());
		client.setName(clientDto.getName());
		client.setPassword(clientDto.getPassword());
		client.setGender(clientDto.getGender());
		client.setAge(clientDto.getAge());
		client.setAddress(clientDto.getAddress());
		client.setPhone(clientDto.getPhone());
		client.setActive(clientDto.isActive());

		Client savedClient = clientRepository.save(client);
		return ClientMapper.toDto(savedClient);
	}

	@Override
	public ClientDto partialUpdate(Long id, PartialClientDto partialClientDto) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found"));
		client.setActive(partialClientDto.isActive());

		Client updateClient = clientRepository.save(client);

		ClientDto dto = ClientMapper.toDto(updateClient);
		return dto;
	}

	@Override
	public void deleteById(Long id) {
		Client client = clientRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Client not found"));

		clientRepository.delete(client);
	}
}

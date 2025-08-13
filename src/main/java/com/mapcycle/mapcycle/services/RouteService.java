package com.mapcycle.mapcycle.services;

import com.mapcycle.mapcycle.models.dtos.RouteDto;
import com.mapcycle.mapcycle.models.entities.Route;
import com.mapcycle.mapcycle.models.entities.User;
import com.mapcycle.mapcycle.models.mappers.RouteMapper;
import com.mapcycle.mapcycle.repositories.RouteRepository;
import com.mapcycle.mapcycle.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class RouteService {

    private final RouteRepository routeRepository;
    private final UserRepository userRepository;
    private final RouteMapper routeMapper;

    public RouteDto create(RouteDto dto) {
        Route route = routeMapper.toEntity(dto);

        if (dto.getCreatingUserId() != null) {
            User user = userRepository.findById(dto.getCreatingUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getCreatingUserId()));
            route.setCreatingUser(user);
        }

        Route saved = routeRepository.save(route);
        return routeMapper.toDto(saved);
    }

    public RouteDto update(Long id, RouteDto dto) {
        Route existing = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found: " + id));

        routeMapper.updateFromDto(dto, existing);

        if (dto.getCreatingUserId() != null && (existing.getCreatingUser() == null
                || !dto.getCreatingUserId().equals(existing.getCreatingUser().getId()))) {
            User user = userRepository.findById(dto.getCreatingUserId())
                    .orElseThrow(() -> new EntityNotFoundException("User not found: " + dto.getCreatingUserId()));

            existing.setCreatingUser(user);
        }

        Route saved = routeRepository.save(existing);
        return routeMapper.toDto(saved);
    }

    @Transactional(readOnly = true)
    public RouteDto findById(Long id) {
        Route route = routeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Route not found: " + id));
        return routeMapper.toDto(route);
    }

    @Transactional(readOnly = true)
    public List<RouteDto> findAll() {
        return routeRepository.findAll().stream()
                .map(routeMapper::toDto)
                .collect(Collectors.toList());
    }

    public void delete(Long id) {
        if (!routeRepository.existsById(id)) {
            throw new EntityNotFoundException("Route not found: " + id);
        }
        routeRepository.deleteById(id);
    }
}
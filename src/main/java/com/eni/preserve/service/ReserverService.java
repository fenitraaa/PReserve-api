package com.eni.preserve.service;

import com.eni.preserve.dto.ReserverDTO;
import com.eni.preserve.entity.Client;
import com.eni.preserve.entity.Place;
import com.eni.preserve.entity.PlaceId;
import com.eni.preserve.entity.Reserver;
import com.eni.preserve.entity.Voiture;
import com.eni.preserve.enums.TypePaiement;
import com.eni.preserve.mapper.ReserverMapper;
import com.eni.preserve.repository.ClientRepository;
import com.eni.preserve.repository.PlaceRepository;
import com.eni.preserve.repository.ReserverRepository;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReserverService {

    private final ReserverRepository reserverRepository;
    private final ReserverMapper reserverMapper;
    private final VoitureRepository voitureRepository;
    private final ClientRepository clientRepository;
    private final PlaceRepository placeRepository;

    public ReserverDTO create(ReserverDTO dto) {
        Voiture voiture = voitureRepository.findById(dto.getIdvoit())
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));

        Client client = clientRepository.findById(dto.getIdcli())
                .orElseThrow(() -> new RuntimeException("Client introuvable"));

        PlaceId placeId = new PlaceId(dto.getIdvoit(), dto.getPlace());
        Place place = placeRepository.findById(placeId)
                .orElseThrow(() -> new RuntimeException("Place introuvable"));

        if (place.isOccupation()) {
            throw new RuntimeException("Place déjà occupée");
        }

        dto.setDateReserv(LocalDateTime.now());

        Reserver reserver = reserverMapper.toEntity(dto, voiture, client);
        Reserver saved = reserverRepository.save(reserver);

        place.setOccupation(true);
        placeRepository.save(place);

        return reserverMapper.toDTO(saved);
    }

    public List<ReserverDTO> findAll() {
        return reserverRepository.findAll()
                .stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ReserverDTO findById(Long id) {
        Reserver r = reserverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        return reserverMapper.toDTO(r);
    }

    public ReserverDTO update(Long id, ReserverDTO dto) {
        Reserver existing = reserverRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));
        reserverMapper.updateEntity(existing, dto);
        Reserver updated = reserverRepository.save(existing);
        return reserverMapper.toDTO(updated);
    }

    public List<ReserverDTO> findByVoiture(Long idvoit) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return reserverRepository.findByVoiture(voiture)
                .stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReserverDTO> findByPayment(TypePaiement payment) {
        return reserverRepository.findByPayment(payment)
                .stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }

    public List<ReserverDTO> findByVoitureAndPayment(Long idvoit, TypePaiement payment) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return reserverRepository.findByVoitureAndPayment(voiture, payment)
                .stream()
                .map(reserverMapper::toDTO)
                .collect(Collectors.toList());
    }

    public long countByVoitureAndPayment(Long idvoit, TypePaiement payment) {
        Voiture voiture = voitureRepository.findById(idvoit)
                .orElseThrow(() -> new RuntimeException("Voiture introuvable"));
        return reserverRepository.countByVoitureAndPayment(voiture, payment);
    }

    public Long getTotalRecette() {
        Long total = reserverRepository.getTotalRecette();
        return total != null ? total : 0L;
    }
}
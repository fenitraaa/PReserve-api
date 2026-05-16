package com.eni.preserve.util;

import com.eni.preserve.repository.ClientRepository;
import com.eni.preserve.repository.ReserverRepository;
import com.eni.preserve.repository.VoitureRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class IdGenerator {

    private final VoitureRepository voitureRepository;
    private final ClientRepository clientRepository;
    private final ReserverRepository reserverRepository;

    public String generateVoitureId() {
        long count = voitureRepository.count() + 1;
        return String.format("V%03d", count);
    }

    public String generateClientId() {
        long count = clientRepository.count() + 1;
        return String.format("C%03d", count);
    }

    public String generateReserverId() {
        long count = reserverRepository.count() + 1;
        return String.format("R%03d", count);
    }
}
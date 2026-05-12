package com.eni.preserve.mapper;

import com.eni.preserve.dto.ReserverDTO;
import com.eni.preserve.entity.Client;
import com.eni.preserve.entity.Reserver;
import com.eni.preserve.entity.Voiture;
import org.springframework.stereotype.Component;

@Component
public class ReserverMapper {

    public ReserverDTO toDTO(Reserver r) {
        if (r == null) return null;

        ReserverDTO dto = new ReserverDTO();
        dto.setIdreserv(r.getIdreserv());
        dto.setIdvoit(r.getVoiture().getIdvoit());
        dto.setIdcli(r.getClient().getIdcli());
        dto.setPlace(r.getPlace());
        dto.setDateReserv(r.getDateReserv());
        dto.setDateVoyage(r.getDateVoyage());
        dto.setPayment(r.getPayment());
        dto.setMontantAvance(r.getMontantAvance());
        return dto;
    }

    public Reserver toEntity(ReserverDTO dto, Voiture voiture, Client client) {
        if (dto == null) return null;

        Reserver r = new Reserver();
        r.setVoiture(voiture);
        r.setClient(client);
        r.setPlace(dto.getPlace());
        r.setDateReserv(dto.getDateReserv());
        r.setDateVoyage(dto.getDateVoyage());
        r.setPayment(dto.getPayment());
        r.setMontantAvance(dto.getMontantAvance());
        return r;
    }

    public void updateEntity(Reserver r, ReserverDTO dto) {
        r.setPlace(dto.getPlace());
        r.setDateReserv(dto.getDateReserv());
        r.setDateVoyage(dto.getDateVoyage());
        r.setPayment(dto.getPayment());
        r.setMontantAvance(dto.getMontantAvance());
    }
}
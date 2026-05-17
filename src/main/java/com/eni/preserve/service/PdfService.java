package com.eni.preserve.service;

import com.eni.preserve.entity.Reserver;
import com.eni.preserve.repository.ReserverRepository;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;

@Service
@RequiredArgsConstructor
public class PdfService {

    private final ReserverRepository reserverRepository;

    public byte[] generateRecu(String idreserv) {
        Reserver r = reserverRepository.findById(idreserv)
                .orElseThrow(() -> new RuntimeException("Réservation introuvable"));

        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, out);
            document.open();

            Font titleFont = new Font(Font.FontFamily.HELVETICA, 16, Font.BOLD);
            Font normalFont = new Font(Font.FontFamily.HELVETICA, 11, Font.NORMAL);

            Paragraph title = new Paragraph("Reçu N°" + r.getIdreserv(), titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(Chunk.NEWLINE);

            document.add(Chunk.NEWLINE);

            document.add(new Paragraph(
                "Date de réservation : " + r.getDateReserv().toLocalDate(), normalFont));

            document.add(new Paragraph(
                "Date du voyage : " + r.getDateVoyage(), normalFont));

            document.add(Chunk.NEWLINE);

            Paragraph client = new Paragraph();
            client.add(new Chunk("Nom du Client : ", normalFont));
            client.add(new Chunk(r.getClient().getNom(), normalFont));
            client.add(new Chunk("   /   Contact : ", normalFont));
            client.add(new Chunk(r.getClient().getNumtel(), normalFont));
            document.add(client);

            document.add(Chunk.NEWLINE);

            Paragraph voiture = new Paragraph();
            voiture.add(new Chunk("Voiture N° : ", normalFont));
            voiture.add(new Chunk(String.valueOf(r.getVoiture().getIdvoit()), normalFont));
            voiture.add(new Chunk("   /   Type : ", normalFont));
            voiture.add(new Chunk(r.getVoiture().getType().toString(), normalFont));
            voiture.add(new Chunk("   /   Place : ", normalFont));
            voiture.add(new Chunk(String.valueOf(r.getPlace()), normalFont));
            document.add(voiture);

            document.add(Chunk.NEWLINE);

            Paragraph frais = new Paragraph();
            frais.add(new Chunk("Frais : ", normalFont));
            frais.add(new Chunk(r.getVoiture().getFrais() + " Ar", normalFont));
            document.add(frais);

            Paragraph payment = new Paragraph();
            payment.add(new Chunk("Paiement : ", normalFont));
            payment.add(new Chunk(r.getPayment().toString(), normalFont));
            document.add(payment);

            if (r.getMontantAvance() > 0) {
                int reste = r.getVoiture().getFrais() - r.getMontantAvance();

                Paragraph avance = new Paragraph();
                avance.add(new Chunk("Montant Avance : ", normalFont));
                avance.add(new Chunk(r.getMontantAvance() + " Ar", normalFont));
                avance.add(new Chunk("   /   Reste : ", normalFont));
                avance.add(new Chunk(reste + " Ar", normalFont));
                document.add(avance);
            }

            document.add(Chunk.NEWLINE);

            document.close();
            return out.toByteArray();

        } catch (Exception e) {
            throw new RuntimeException("Erreur génération PDF : " + e.getMessage());
        }
    }
}
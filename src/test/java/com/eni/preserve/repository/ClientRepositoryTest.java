package com.eni.preserve.repository;

import com.eni.preserve.entity.Client;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class ClientRepositoryTest {

    @Autowired
    private ClientRepository repo;

    private Client client1;
    private Client client2;

    @BeforeEach
    void init() {
        repo.deleteAll();

        client1 = new Client();
        client1.setNom("Fenitra");
        client1.setNumtel("0345351885");

        client2 = new Client();
        client2.setNom("Tojo");
        client2.setNumtel("0330551248");

        repo.save(client1);
        repo.save(client2);
    }

    @Test
    void testFindAll() {
        List<Client> result = repo.findAll();
        assertThat(result).hasSize(2);
    }

    @Test
    void testFindById() {
        Client result = repo.findById(client1.getIdcli()).orElse(null);
        assertThat(result).isNotNull();
        assertThat(result.getNom()).isEqualTo("Fenitra");
    }

    @Test
    void testFindByIdFail() {
        var result = repo.findById(9999);
        assertThat(result).isEmpty();
    }

    @Test
    void testSave() {
        Client nouveau = new Client();
        nouveau.setNom("Rabe");
        nouveau.setNumtel("0321234567");

        Client saved = repo.save(nouveau);

        assertThat(saved.getIdcli()).isNotNull();
        assertThat(saved.getNom()).isEqualTo("Rabe");
    }

    @Test
    void testDelete() {
        repo.deleteById(client1.getIdcli());
        assertThat(repo.findById(client1.getIdcli())).isEmpty();
    }

    @Test
    void testFindByNomContaining() {
        List<Client> result = repo.findByNomContainingOrNumtelContaining("Fen", "Fen");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Fenitra");
    }

    @Test
    void testFindByNumtelContaining() {
        List<Client> result = repo.findByNomContainingOrNumtelContaining("0330", "0330");
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getNom()).isEqualTo("Tojo");
    }

    @Test
    void testFindByNomOrNumtelMultiple() {
        List<Client> result = repo.findByNomContainingOrNumtelContaining("0", "0");
        assertThat(result).hasSize(2);
    }

    @Test
    void testFindByNomContainingEmpty() {
        List<Client> result = repo.findByNomContainingOrNumtelContaining("XYZ", "XYZ");
        assertThat(result).isEmpty();
    }
}
package ma.enset.dpspringaop;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratRepository extends JpaRepository<Contrat,Long> {
    List<Contrat> findByClientCin(String cin);
}

package ma.enset.dpspringaop;

import ma.enset.dpspringaop.aspect.DecCrypt;
import ma.enset.dpspringaop.aspect.EncCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratService {
    private ContratRepository contratRepository;

    public ContratService(ContratRepository contratRepository) {
        this.contratRepository = contratRepository;
    }
    @EncCrypt(fields = {"clientNom","clientCin"})
    public Contrat add(Contrat contrat){
        return contratRepository.save(contrat);
    }
    @DecCrypt(values = {"clientNom"})
    public Contrat getById(Long id){
        return contratRepository.findById(id).get();
    }

    public List<Contrat> getByCin(String cin){
        return contratRepository.findByClientCin(cin);
    }
}

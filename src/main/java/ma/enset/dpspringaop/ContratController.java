package ma.enset.dpspringaop;

import ma.enset.dpspringaop.aspect.Cachable;
import ma.enset.dpspringaop.aspect.MyLog;
import ma.enset.dpspringaop.aspect.SecuredByAspect;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contrats")
public class ContratController {
    private ContratService contratService;

    public ContratController(ContratService contratService) {
        this.contratService = contratService;
    }
    @PostMapping
    @MyLog
    public Contrat add(@RequestBody Contrat contrat){
        return contratService.add(contrat);
    }
    @GetMapping("/{id}")
    @Cachable
    @MyLog
    @SecuredByAspect(roles = {"ADMIN"})
    public Contrat get(@PathVariable Long id){
        return contratService.getById(id);
    }
    @GetMapping("/cin/{cin}")
    @Cachable
    @MyLog
    public List<Contrat> get(@PathVariable String cin){
        return contratService.getByCin(cin);
    }
}

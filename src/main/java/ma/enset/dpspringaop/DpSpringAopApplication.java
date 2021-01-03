package ma.enset.dpspringaop;

import ma.enset.dpspringaop.aspect.AppContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
public class DpSpringAopApplication {

    public static void main(String[] args) {
        AppContext.authenticateUser("root","1234",new String[]{"ADMIN"});
        SpringApplication.run(DpSpringAopApplication.class, args);
    }

    @Bean
    CommandLineRunner start(ContratService contratService){
        return args -> {
            contratService.add(new Contrat(null,"imad",new Date(),2000,"imad","gm2000"));
            contratService.add(new Contrat(null,"fatima",new Date(),3000,"fatima","gm3000"));
            contratService.add(new Contrat(null,"oumayma",new Date(),4000,"oumayma","gm4000"));
            contratService.add(new Contrat(null,"mohamed",new Date(),5000,"mohamed","gm5000"));
            System.out.println(contratService.getById(1L));
            System.out.println(contratService.getById(2L));
        };
    }
}

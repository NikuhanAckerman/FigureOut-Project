package com.project.figureout;

import com.project.figureout.model.Gender;
import com.project.figureout.repository.GenderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

@Configuration
public class AppInitializer implements CommandLineRunner {

    // enable DELETE and PUT requests:
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }


    @Autowired
    private GenderRepository genderRepository;

    @Override
    public void run(String... args) throws Exception {
        if (genderRepository.count() == 0) {
            System.out.println("Populando tabela de gêneros...");
            Gender male = new Gender("Masculino");
            Gender female = new Gender("Feminino");
            Gender other = new Gender("Outro");

            genderRepository.save(male);
            genderRepository.save(female);
            genderRepository.save(other);

            System.out.println("Tabela de gêneros populada.");
        }
    }



}

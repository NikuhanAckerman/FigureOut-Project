package com.project.figureout;

import com.project.figureout.model.*;
import com.project.figureout.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

@Configuration
public class AppInitializer implements CommandLineRunner {

    // enable DELETE and PUT requests:
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Autowired
    private GenderRepository genderRepository;

    @Autowired
    private CreditCardBrandRepository creditCardBrandRepository;

    @Autowired
    private StateRepository stateRepository;

    @Autowired
    private CountryRepository countryRepository;

    @Autowired
    private PricingGroupRepository pricingGroupRepository;

    @Override
    public void run(String... args) throws Exception {
        if (genderRepository.count() == 0) {
            System.out.println("Populando tabela de gêneros...");

            ArrayList<Gender> genderList = new ArrayList<>();

            Collections.addAll(genderList,
                    new Gender("Masculino"),
                    new Gender("Feminino"),
                    new Gender("Outro")
            );

            genderRepository.saveAll(genderList);

            System.out.println("Tabela de gêneros populada.");
        }

        if(creditCardBrandRepository.count() == 0) {
            System.out.println("Populando tabela de bandeira de cartão de crédito...");

            ArrayList<CreditCardBrand> creditCardBrandList = new ArrayList<>();

            Collections.addAll(creditCardBrandList,
                    new CreditCardBrand("Visa"),
                    new CreditCardBrand("MasterCard"),
                    new CreditCardBrand("American Express"),
                    new CreditCardBrand("Elo")
            );

            creditCardBrandRepository.saveAll(creditCardBrandList);

            System.out.println("Tabela de bandeira de cartão de crédito populada.");
        }

        if(stateRepository.count() == 0) {
            System.out.println("Populando tabela de estados...");

            ArrayList<State> stateList = new ArrayList<>();

            Collections.addAll(stateList,
                    new State("Acre"),
                    new State("Alagoas"),
                    new State("Amazonas"),
                    new State("Amapá"),
                    new State("Bahia"),
                    new State("Ceará"),
                    new State("Distrito Federal"),
                    new State("Espírito Santo"),
                    new State("Goiás"),
                    new State("Maranhão"),
                    new State("Minas Gerais"),
                    new State("Mato Grosso do Sul"),
                    new State("Mato Grosso"),
                    new State("Pará"),
                    new State("Paraíba"),
                    new State("Pernambuco"),
                    new State("Piauí"),
                    new State("Paraná"),
                    new State("Rio de Janeiro"),
                    new State("Rio Grande do Norte"),
                    new State("Rondônia"),
                    new State("Roraima"),
                    new State("Rio Grande do Sul"),
                    new State("Santa Catarina"),
                    new State("Sergipe"),
                    new State("São Paulo"),
                    new State("Tocantins")
            );

            stateRepository.saveAll(stateList);

            System.out.println("Tabela de estados populada.");
        }

        if(countryRepository.count() == 0) {
            System.out.println("Populando tabela de países...");

            ArrayList<Country> countryList = new ArrayList<>();

            Collections.addAll(countryList,
                    new Country("Brasil")
            );

            countryRepository.saveAll(countryList);

            System.out.println("Tabela de países populada.");
        }

        if(pricingGroupRepository.count() == 0) {
            System.out.println("Populando tabela de grupos de precificação...");

            ArrayList<PricingGroup> pricingGroupList = new ArrayList<>();

            Collections.addAll(pricingGroupList,
                    new PricingGroup("Ouro", 20.0),
                    new PricingGroup("Prata", 15.0),
                    new PricingGroup("Ferro", 10.0),
                    new PricingGroup("Bronze", 5.0)
            );

            pricingGroupRepository.saveAll(pricingGroupList);

            System.out.println("Tabela de grupos de precificação populada.");
        }

    }

}

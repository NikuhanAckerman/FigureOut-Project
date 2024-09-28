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

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private SupplierRepository supplierRepository;

    @Autowired
    private PromotionalCouponRepository promotionalCouponRepository;

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
                    new State("Acre", 15.0),
                    new State("Alagoas", 15.0),
                    new State("Amazonas", 15.0),
                    new State("Amapá", 15.0),
                    new State("Bahia", 15.0),
                    new State("Ceará", 15.0),
                    new State("Distrito Federal", 15.0),
                    new State("Espírito Santo", 15.0),
                    new State("Goiás", 15.0),
                    new State("Maranhão", 15.0),
                    new State("Minas Gerais", 10.0),
                    new State("Mato Grosso do Sul", 10.0),
                    new State("Mato Grosso", 10.0),
                    new State("Pará", 10.0),
                    new State("Paraíba", 10.0),
                    new State("Pernambuco", 10.0),
                    new State("Piauí", 15.0),
                    new State("Paraná", 10.0),
                    new State("Rio de Janeiro", 5.0),
                    new State("Rio Grande do Norte", 15.0),
                    new State("Rondônia", 15.0),
                    new State("Roraima", 15.0),
                    new State("Rio Grande do Sul", 10.0),
                    new State("Santa Catarina", 10.0),
                    new State("Sergipe", 15.0),
                    new State("São Paulo", 5.0),
                    new State("Tocantins", 15.0)
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

        if(categoryRepository.count() == 0) {
            System.out.println("Populando tabela de categorias...");

            ArrayList<Category> categoryList = new ArrayList<>();

            Collections.addAll(categoryList,
                    new Category("Chaveiro"),
                    new Category("Pré-pintado"),
                    new Category("Model Kit"),
                    new Category("Nendoroid"),
                    new Category("Pelúcia"),
                    new Category("Amiibo"),
                    new Category("Figura de ação"),
                    new Category("1/4"),
                    new Category("1/6"),
                    new Category("1/7"),
                    new Category("1/8"),
                    new Category("1/16")
            );

            categoryRepository.saveAll(categoryList);

            System.out.println("Tabela de categorias populada.");
        }

        if(supplierRepository.count() == 0) {
            System.out.println("Populando tabela de fornecedores...");

            ArrayList<Supplier> supplierList = new ArrayList<>();

            Collections.addAll(supplierList,
                    new Supplier("Fornecedor A"),
                    new Supplier("Fornecedor B"),
                    new Supplier("Fornecedor C"),
                    new Supplier("Fornecedor D")
            );

            supplierRepository.saveAll(supplierList);

            System.out.println("Tabela de fornecedores populada.");
        }

        if(promotionalCouponRepository.count() == 0) {
            System.out.println("Populando tabela de cupons promocionais...");

            ArrayList<PromotionalCoupon> promotionalCouponList = new ArrayList<>();

            Collections.addAll(promotionalCouponList,
                    new PromotionalCoupon("FIGUREOUT95", 95.0)
            );

            promotionalCouponRepository.saveAll(promotionalCouponList);

            System.out.println("Tabela de cupons promocionais populada.");
        }

    }

}

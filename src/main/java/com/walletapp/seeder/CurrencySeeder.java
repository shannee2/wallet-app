package com.walletapp.seeder;

import com.walletapp.model.user.User;
import com.walletapp.model.currency.Currency;
import com.walletapp.model.currency.CurrencyType;
import com.walletapp.repository.CurrencyRepository;
import com.walletapp.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class CurrencySeeder {

    @Bean
    CommandLineRunner initCurrencies(CurrencyRepository currencyRepository) {
        return args -> {
            if (currencyRepository.count() == 0) {
                currencyRepository.save(new Currency(CurrencyType.USD, 1.0));
                currencyRepository.save(new Currency(CurrencyType.INR, 83.0));
                currencyRepository.save(new Currency(CurrencyType.EUR, 0.92));
                currencyRepository.save(new Currency(CurrencyType.GBP, 0.79));
                System.out.println("Default currencies inserted.");
            } else {
                System.out.println("Currencies already exist.");
            }
        };
    }


//    @Bean
//    public CommandLineRunner seedAdminUser(UserRepository userRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            if (userRepository.findByUsername("admin").isEmpty()) {
//                User admin = new User("admin", passwordEncoder.encode("admin"));
//                userRepository.save(admin);
//                System.out.println("Admin user created: username=admin, password=admin");
//            }else{
//                System.out.println("Admin already exist in db");
//            }
//        };
//    }
}

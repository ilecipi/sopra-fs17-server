package ch.uzh.ifi.seal.soprafs17;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.AddStoneToShipRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.SailShipRule;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.AddStoneToShipValidator;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.SailShipValidator;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@ComponentScan
@EnableAutoConfiguration
public class Application {

    @Autowired
    static RuleBook ruleBook;
    @Autowired
    static ValidatorManager validatorManager;

    public static void main(String[] args) {

        SpringApplication.run(Application.class, args);
        ruleBook.addRule(new AddStoneToShipRule());
        validatorManager.addValidator(new AddStoneToShipValidator());
        ruleBook.addRule(new SailShipRule());
        validatorManager.addValidator(new SailShipValidator());
    }


    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**").allowedOrigins("*") //This accepts only GET, POST, and few other methods by default (excluding PUT).
                        .allowedMethods("GET", "PUT", "POST", "OPTIONS", "DELETE"); //Allows specific methods to be used.
                // otherwise add more as need arises
            }

        };
    }
}

package ch.uzh.ifi.seal.soprafs17;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.AddStoneToShipRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.GetStoneRule;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.RuleBook;
import ch.uzh.ifi.seal.soprafs17.service.RuleEngine.SailShipRule;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.AddStoneToShipValidator;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.GetStoneValidator;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.SailShipValidator;
import ch.uzh.ifi.seal.soprafs17.service.ValidatorEngine.ValidatorManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.persistence.PersistenceContext;

@ComponentScan
@EnableAutoConfiguration
public class Application {


//    static RuleBook ruleBook;
//
//    static ValidatorManager validatorManager;

    public static void main(String[] args) {
//        RuleBook ruleBook=new RuleBook();
//        ValidatorManager validatorManager = new ValidatorManager();
//        ApplicationContext ctx = new AnnotationConfigApplicationContext(PersistenceContext.class);
//        RuleBook ruleBOOK =ctx.getBean(RuleBook.class);
//        RuleBook ruleBook = new RuleBook();
////        ValidatorManager validatorMANAGER = ctx.getBean(ValidatorManager.class);
//        ValidatorManager validatorManager=new ValidatorManager();
//        ruleBook.addRule(new AddStoneToShipRule());
//        ruleBook.addRule(new SailShipRule());
//        ruleBook.addRule(new GetStoneRule());
//        validatorManager.addValidator(new AddStoneToShipValidator());
//        validatorManager.addValidator(new SailShipValidator());
//        validatorManager.addValidator(new GetStoneValidator());
        SpringApplication.run(Application.class, args);

//        System.out.println("ruleBook"+ruleBook.getRules().get(1));
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

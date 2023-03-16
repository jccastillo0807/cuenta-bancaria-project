package co.com.banco.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;

@Configuration
@ComponentScan(basePackages = "co.com.banco.usecase",
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.REGEX, pattern = "^.+UseCase$")
        },
        useDefaultFilters = false)
public class UseCasesConfig {

        /*@Bean
        public MovimientoUseCase getMovimientoUseCase(MovimientoRepository movimientoRepository){
                return new MovimientoUseCase(movimientoRepository);
        }*/
}

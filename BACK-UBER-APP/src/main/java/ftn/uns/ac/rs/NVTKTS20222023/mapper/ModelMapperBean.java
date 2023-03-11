package ftn.uns.ac.rs.NVTKTS20222023.mapper;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperBean {

    @Bean
    public ModelMapper create(){
        return new ModelMapper();
    }

}

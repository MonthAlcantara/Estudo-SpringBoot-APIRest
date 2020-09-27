package io.github.monthalcantara.restspringbootestudo.serialization.converter;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.yaml.YAMLMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;

public class YamlJackson2HttpMessageConverter extends AbstractJackson2HttpMessageConverter {
    public YamlJackson2HttpMessageConverter() {
        super(new YAMLMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL), /* Mesma config do app.properties
                spring.jackson.default-property-inclusion=non_null
                Porém a config no properties só funciona para json e xml. yml tem de ser feito aqui*/
                MediaType.parseMediaType("application/x-yaml"));
    }
}

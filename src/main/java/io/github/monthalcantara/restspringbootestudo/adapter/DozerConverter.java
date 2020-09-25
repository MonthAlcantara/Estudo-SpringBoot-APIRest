package io.github.monthalcantara.restspringbootestudo.adapter;

import com.github.dozermapper.core.DozerBeanMapperBuilder;
import com.github.dozermapper.core.Mapper;

import java.util.ArrayList;
import java.util.List;

public class DozerConverter {

    private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();

    public static <Origin, Destination> Destination parseObject(Origin origin, Class<Destination> destination){
        return mapper.map(origin, destination);
    }

    public static <Origin, Destination> List<Destination> parseListObjects(List<Origin> origin, Class<Destination> destination){
        List<Destination> destinationObjects = new ArrayList<>();
        for(Object o : origin){
            destinationObjects.add(mapper.map(o,destination));
        }
        return destinationObjects;
    }
}

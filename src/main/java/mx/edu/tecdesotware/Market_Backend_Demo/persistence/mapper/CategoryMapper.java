package mx.edu.tecdesotware.Market_Backend_Demo.persistence.mapper;


import mx.edu.tecdesotware.Market_Backend_Demo.domain.Category;
import mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity.Categorias;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface CategoryMapper {

    @Mappings({
            @Mapping(source = "idCategoria", target = "categoryId"),
            @Mapping(source = "descripcion", target = "category"),
            @Mapping(source = "estado", target = "active")

    })
    Category toCategory(Categorias categoria);

    @InheritInverseConfiguration
    @Mapping(target = "Productos", ignore = true)
    Categorias toCategorias(Categorias categorias);



}

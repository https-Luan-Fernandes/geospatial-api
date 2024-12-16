package br.edu.ifpb.geospatialapi.repository;

import br.edu.ifpb.geospatialapi.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {
    @Query(value = "SELECT * FROM cidade WHERE ST_Distance_Sphere(coordenada, POINT(:longitude, :latitude)) <= :raio", nativeQuery = true)
    List<Cidade> findCidadesWithinRadius(@Param("latitude") double latitude,
                                         @Param("longitude") double longitude,
                                         @Param("raio") double raio);

}

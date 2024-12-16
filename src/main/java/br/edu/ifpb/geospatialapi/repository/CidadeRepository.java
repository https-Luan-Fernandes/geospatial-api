package br.edu.ifpb.geospatialapi.repository;

import br.edu.ifpb.geospatialapi.model.Cidade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CidadeRepository extends JpaRepository<Cidade, Long> {

}

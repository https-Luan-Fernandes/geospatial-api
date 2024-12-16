package br.edu.ifpb.geospatialapi.service;

import br.edu.ifpb.geospatialapi.model.Cidade;
import br.edu.ifpb.geospatialapi.repository.CidadeRepository;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    public List<Cidade> getAllCidades() {
        return cidadeRepository.findAll();
    }

    public Cidade createCidade(Cidade cidade) {
        return cidadeRepository.save(cidade);
    }

    public Cidade getCidade(Long id) {
        return cidadeRepository.findById(id).orElse(null);
    }

    public void deleteCidade(Long id){
        cidadeRepository.deleteById(id);
    }

    public boolean verificarCidadeDentroPoligono(Long cidadeId, Polygon poligono) {
        Optional<Cidade> cidadeOpt = cidadeRepository.findById(cidadeId);
        if (cidadeOpt.isPresent()) {
            Cidade cidade = cidadeOpt.get();
            Point coordenadaCidade = cidade.getCoordenada();
            return poligono.contains(coordenadaCidade);
        }
        return false;
    }

    public List<Cidade> buscarCidadesPorRaio(double latitude, double longitude, double raio) {
        return cidadeRepository.findCidadesWithinRadius(latitude, longitude, raio);
    }

}

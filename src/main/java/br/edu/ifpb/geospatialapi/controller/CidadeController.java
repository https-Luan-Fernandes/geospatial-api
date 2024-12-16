package br.edu.ifpb.geospatialapi.controller;

import br.edu.ifpb.geospatialapi.model.Cidade;
import br.edu.ifpb.geospatialapi.service.CidadeService;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.io.WKTReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeService cidadeService;

    @PostMapping
    public ResponseEntity<Cidade> createCidade(@RequestBody Cidade cidade) {
        return ResponseEntity.status(201).body(cidadeService.createCidade(cidade));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cidade> getCidade(@PathVariable Long id) {
        Cidade cidade = cidadeService.getCidade(id);
        if (cidade != null) {
            return ResponseEntity.ok(cidade);
        } else {
            return ResponseEntity.status(404).build();
        }
    }


    @GetMapping
    public ResponseEntity<List<Cidade>> getAllCidades() {
        List<Cidade> cidades = cidadeService.getAllCidades();
        return ResponseEntity.ok(cidades);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Cidade> updateCidade(@PathVariable Long id, @RequestBody Cidade cidade) {
        Cidade cidadeExistente = cidadeService.getCidade(id);
        if (cidadeExistente != null) {
            cidadeExistente.setNome(cidade.getNome());
            cidadeExistente.setCoordenada(cidade.getCoordenada());
            return ResponseEntity.ok(cidadeService.createCidade(cidadeExistente));
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCidade(@PathVariable Long id) {
        Cidade cidadeExistente = cidadeService.getCidade(id);

        if (cidadeExistente != null) {
            cidadeService.deleteCidade(id);
            // Retorna status 204 (No Content) após excluir a cidade
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.status(404).build();
        }
    }

    @PostMapping("/{id}/verificar-dentro-poligono")
    public ResponseEntity<Boolean> verificarCidadeDentroPoligono(@PathVariable Long id, @RequestBody Map<String, String> body) {
        try {
            String wktPoligono = body.get("wktPoligono");  // Extraindo o WKT do corpo da requisição

            if (wktPoligono == null) {

                return ResponseEntity.status(400).body(false);  // Retorna false caso o WKT seja nulo
            }

            // Convertendo o WKT para um objeto Polygon
            WKTReader reader = new WKTReader();
            Polygon poligono = (Polygon) reader.read(wktPoligono);


            // Verifica se a cidade está dentro do polígono
            boolean resultado = cidadeService.verificarCidadeDentroPoligono(id, poligono);
            return ResponseEntity.ok(resultado);

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(400).body(false);  // Retorna 400 caso o polígono ou a cidade sejam inválidos
        } catch (Exception e) {
            return ResponseEntity.status(500).body(false);  // Retorna 500 em caso de erro inesperado
        }
    }

    @GetMapping("/buscar-por-raio")
    public ResponseEntity<List<Cidade>> buscarCidadesPorRaio(
            @RequestParam double latitude,
            @RequestParam double longitude,
            @RequestParam(defaultValue = "1000000") double raio) {  // Raio padrão de 1.000.000 metros (1.000 km)
        try {
            List<Cidade> cidades = cidadeService.buscarCidadesPorRaio(latitude, longitude, raio);
            return ResponseEntity.ok(cidades);
        } catch (Exception e) {
            return ResponseEntity.status(500).build();  // Retorna erro 500 em caso de erro inesperado
        }
    }

}

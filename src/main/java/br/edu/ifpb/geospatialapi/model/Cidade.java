package br.edu.ifpb.geospatialapi.model;

import br.edu.ifpb.geospatialapi.exception.InvalidGeoJsonException;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

import java.util.List;
import java.util.Map;

@Entity
@Table(name = "cidade")
public class Cidade {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nome;

    @Column(columnDefinition = "POINT")
    @JsonSerialize(using = PointSerializer.class)
    private Point coordenada;


    private void validarCoordenada(Double x, Double y) {
        if (x == null || y == null) {
            throw new IllegalArgumentException("Coordenadas 'x' e 'y' não podem ser nulas.");
        }
        if (x < -180 || x > 180 || y < -90 || y > 90) {
            throw new IllegalArgumentException("Coordenadas fora dos limites válidos: x (-180 a 180), y (-90 a 90).");
        }
    }

    @JsonProperty("coordenada")
    public void setCoordenadaFromGeoJson(Map<String, Object> coordenada) {
        final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
        if (coordenada.containsKey("x") && coordenada.containsKey("y")) {
            Double x = (Double) coordenada.get("x");
            Double y = (Double) coordenada.get("y");
            validarCoordenada(x, y);
            this.coordenada = geometryFactory.createPoint(new Coordinate(x, y));
        } else if (coordenada.containsKey("coordinates")) {
            List<Double> coordinates = (List<Double>) coordenada.get("coordinates");
            if (coordinates.size() == 2) {
                validarCoordenada(coordinates.get(0), coordinates.get(1));
                this.coordenada = geometryFactory.createPoint(new Coordinate(coordinates.get(0), coordinates.get(1)));
            } else {
                throw new InvalidGeoJsonException("Formato inválido para a propriedade 'coordinates'. Deve conter dois elementos.");
            }
        } else {
            throw new InvalidGeoJsonException("Formato inválido para a propriedade 'coordenada'");
        }
    }

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Point getCoordenada() {
        return coordenada;
    }

    public void setCoordenada(Point coordenada) {
        this.coordenada = coordenada;
    }
}

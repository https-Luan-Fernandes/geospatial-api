package br.edu.ifpb.geospatialapi.exception;

public class InvalidGeoJsonException extends RuntimeException {
    public InvalidGeoJsonException(String message) {
        super(message);
    }
}

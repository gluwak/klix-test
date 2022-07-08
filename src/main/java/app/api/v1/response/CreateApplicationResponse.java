package app.api.v1.response;

import app.api.v1.domain.Offer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateApplicationResponse{
    public String id;
    public Status status;
    public Offer offer;

    public enum Status {
        DRAFT,
        PROCESSED
    }
}

package app.api.v1.response;

import app.api.v1.domain.UserApplication;

import java.util.List;

public class ApplicationResponse {

    private final List<UserApplication> applications;

    public ApplicationResponse(List<UserApplication> applications) {
        this.applications = applications;
    }


    public List<UserApplication> getApplications() {
        return applications;
    }
}

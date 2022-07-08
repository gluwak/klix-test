package app.api.v1.request;

import java.util.ArrayList;
import java.util.List;

public class OfferRequest {

    private final List<ApplicationData> applicationDataList;

    public OfferRequest(List<ApplicationData> applicationDataList){
        this.applicationDataList = applicationDataList;
    }

    public OfferRequest(){
        this.applicationDataList = new ArrayList<>();
    }

    public List<ApplicationData> getApplicationDataList() {
        return applicationDataList;
    }

    public static class ApplicationData {
        private String applicationId;
        private String vendor;

        public ApplicationData(String applicationId, String vendor) {
            this.applicationId = applicationId;
            this.vendor = vendor;
        }

        public ApplicationData() {

        }

        public String getApplicationId() {
            return applicationId;
        }

        public String getVendor() {
            return vendor;
        }
    }

}

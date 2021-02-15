package com.example.demo.commons;

import io.restassured.specification.RequestSpecification;
import lombok.Data;
import org.json.JSONObject;

@Data
public class ApiBase {

    private String scenarioPath;
    private String requestHeader;
    private String restEndpoint;
    private String responseHeader;
    private int actualStatusCode;
    private RequestSpecification httpRequest;
    private JSONObject requestParams;

    public String getScenarioPath() {

        try {
            if (getRequestHeader() == "application/json") {
                return scenarioPath + "/request.json";
            } else if (getRequestHeader() == "/application/xml") {
                return scenarioPath + "request.xml";
            } else {
                return "Invalid request type has been entered";
            }
        } catch (Exception e) {
            // softAssert.fail();
            return e.getMessage();
        }
    }

}

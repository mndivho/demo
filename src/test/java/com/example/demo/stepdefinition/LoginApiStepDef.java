package com.example.demo.stepdefinition;

import com.example.demo.commons.ApiBase;
import com.relevantcodes.extentreports.LogStatus;
import cucumber.api.PendingException;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.AfterTest;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import static ucar.nc2.util.IO.readFile;

public class LoginApiStepDef extends ApiBase {

    @Given("Wamly rest endpoints are available")
    public void userSendLoginRequest() {
        setScenarioPath("src/main/resources/features/api/add_users");
        setRestEndpoint("https://api.uatwamly.co.za/wamly/v1/add/user");
        setRequestHeader("application/json");


    }

    @Then("^User sends via backend username <\"([^\"]*)\"> and password <\"([^\"]*)\">$")
    public void userSendsViaBackendUsernameAndPassword(String arg0, String arg1) throws Throwable {

    }

    @Test
    public void adding_users() throws Exception {

    }
}
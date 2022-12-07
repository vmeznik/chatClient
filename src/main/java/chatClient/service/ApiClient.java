package chatClient.service;

import chatClient.model.api.ForgotPassword;
import chatClient.model.api.Login;
import chatClient.model.api.Register;
import chatClient.model.api.RequestConfirmation;
import chatClient.utility.Logger;
import jakarta.ws.rs.client.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

public class ApiClient {
    private final Client client;

    public ApiClient() {
        this.client = ClientBuilder.newClient();
    }

    public RequestConfirmation registerRequest(Register register) {
        WebTarget resource = client.target("http://localhost:8080/chatClient/register");

        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        Response response;
        RequestConfirmation requestConfirmation = new RequestConfirmation(false,
                "confirmation was not successful");
        try {
            response = request.post(Entity.entity(register, MediaType.APPLICATION_JSON));
            requestConfirmation = response.readEntity(RequestConfirmation.class);
        } catch (Exception e) {
            Logger.getInstance().log("Error while registering");
            e.printStackTrace();
        }
        return requestConfirmation;
    }

    public RequestConfirmation loginRequest(Login login) {
        WebTarget resource = client.target("http://localhost:8080/chatClient/login");

        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        Response response;
        RequestConfirmation requestConfirmation = new RequestConfirmation(false,
                "confirmation was not successful");
        try {
            response = request.post(Entity.entity(login, MediaType.APPLICATION_JSON));
            requestConfirmation = response.readEntity(RequestConfirmation.class);
        } catch (Exception e) {
            Logger.getInstance().log("Error while logging in");
            e.printStackTrace();
        }
        return requestConfirmation;
    }

    public String forgotPasswordRequest(ForgotPassword forgotPassword) {
        WebTarget resource = client.target("http://localhost:8080/chatClient/forgotPassword");

        Invocation.Builder request = resource.request(MediaType.APPLICATION_JSON);
        request.accept(MediaType.APPLICATION_JSON);
        Response response;
        String password = "";
        try {
            response = request.post(Entity.entity(forgotPassword, MediaType.APPLICATION_JSON));
            password = response.readEntity(String.class);
        } catch (Exception e) {
            Logger.getInstance().log("Error while forgot password request");
            e.printStackTrace();
        }
        return password;
    }

}

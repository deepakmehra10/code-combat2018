package com.knoldus;

import com.knoldus.models.ProjectResource;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;

import javax.ws.rs.core.MediaType;

public class EmailUtil {
    
    public static ClientResponse sendSimpleMessage(ProjectResource projectResource) {
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api", "key-80f073abd0dc65b45061a2cfb24537c1"));
        WebResource webResource = client.resource("https://api.mailgun.net/v3/sandbox2efee424c7ca4b0fb1049408ba4291fd.mailgun.org/messages");
        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "vikas11@gmail.com");
        formData.add("to", String.format("%s <%s>", projectResource.getEmployeeName(), projectResource.getEmployeeId()));
        formData.add("subject", "Project enrollment");
        formData.add("text", String.format("Congratulations %s, you are enrolled in project %s!\n" +
                        "Your designation is %s.\n" +
                        "Your manager email is %s.\n\n" +
                        "Have a good day.",
                projectResource.getEmployeeName(), projectResource.getProjectName(), projectResource.getEmployeeDesignation(),
                projectResource.getManager()));
        return webResource.type(MediaType.APPLICATION_FORM_URLENCODED).
                post(ClientResponse.class, formData);
    }
    
}

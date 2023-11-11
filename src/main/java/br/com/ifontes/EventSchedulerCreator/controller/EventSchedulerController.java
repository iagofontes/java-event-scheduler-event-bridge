package br.com.ifontes.EventSchedulerCreator.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.PutEventsRequest;
import com.amazonaws.services.eventbridge.model.PutEventsRequestEntry;
import com.amazonaws.services.eventbridge.model.PutEventsResult;
import com.amazonaws.services.eventbridge.model.PutRuleRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EventSchedulerController {

    @GetMapping("/agendar")
    public void agendarSchedule() {

        BasicAWSCredentials basicCredential = new BasicAWSCredentials("admin", "admin");
        AmazonEventBridge client = AmazonEventBridgeClient.builder()
//                .withRegion(Regions.SA_EAST_1)
                .withCredentials(new AWSStaticCredentialsProvider(basicCredential))
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:4566", "sa-east-1"))
                .build();

        String details = """
                {
                    "user": "ABC"
                }
                """;

        PutEventsRequestEntry requestEntry = new PutEventsRequestEntry();
        requestEntry.withSource("user-event")
                .withDetailType("user-preferences")
                .withDetail(details);

        PutEventsRequest req = new PutEventsRequest();
        req.withEntries(requestEntry);

        PutEventsResult result = client.putEvents(req);
        System.out.println(result);

//        PutRuleRequest reqR = new PutRuleRequest();
//        reqR.
//        client.putRule()
    }
}

package br.com.ifontes.EventSchedulerCreator.controller;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.eventbridge.AmazonEventBridge;
import com.amazonaws.services.eventbridge.AmazonEventBridgeClient;
import com.amazonaws.services.eventbridge.model.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

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

//        String details = """
//                {
//                    "user": "ABC"
//                }
//                """;
//
//        PutEventsRequestEntry requestEntry = new PutEventsRequestEntry();
//        requestEntry.withSource("user-event")
//                .withDetailType("user-preferences")
//                .withDetail(details);
//
//        PutEventsRequest req = new PutEventsRequest();
//        req.withEntries(requestEntry);
//
//        PutEventsResult result = client.putEvents(req);
//        System.out.println(result);


        PutRuleRequest ruleRequest = new PutRuleRequest();
        ruleRequest.setDescription("teste sqs event rule");
//        ruleRequest.setScheduleExpression("0 34 6 * * *");
//        ruleRequest.setScheduleExpression("cron(41 21 ? * * *)");
        ruleRequest.setScheduleExpression("cron(41 21 ? * * *)");
        ruleRequest.setEventBusName("default");
        ruleRequest.setState("ENABLED");
        ruleRequest.setName("teste-proposta-2");

        PutRuleResult ruleResponse = client.putRule(ruleRequest);
        System.out.println("The ARN of the new rule is "+ ruleResponse.getRuleArn());

        String targetID = java.util.UUID.randomUUID().toString();

//        aws sqs list-queues --endpoint-url http://localhost:4566
//        aws sqs receive-message --queue-url http://localhost:4566/000000000000/teste --endpoint-url http://localhost:4566
//        aws events list-rules --endpoint-url http://localhost:4566
//        arn:aws:sqs:sa-east-1:000000000000:teste
        Target myTarget = new Target();
        myTarget.setArn("arn:aws:sqs:sa-east-1:000000000000:teste");
        myTarget.setId(targetID);

        List<Target> targets = new ArrayList<>();
        targets.add(myTarget);
        PutTargetsRequest request = new PutTargetsRequest();
        request.setEventBusName(null);
        request.setTargets(targets);
        request.setRule(ruleRequest.getName());

        client.putTargets(request);

//                PutRuleRequest.builder()
//                .name(ruleName)
//                .eventBusName("default")
//                .scheduleExpression(cronExpression)
//                .state("ENABLED")
//                .description("A test rule that runs on a schedule created by the Java API")
//                .build();
//        PutRuleRequest reqR = new PutRuleRequest();
//        reqR.
//        client.putRule()
    }
}

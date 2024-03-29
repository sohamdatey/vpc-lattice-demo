package com.myorg;

import java.util.List;

import software.amazon.awscdk.AppProps;
import software.amazon.awscdk.Stack;
import software.amazon.awscdk.services.ec2.SecurityGroup;
import software.amazon.awscdk.services.ec2.SubnetSelection;
import software.amazon.awscdk.services.ec2.Vpc;
import software.amazon.awscdk.services.lambda.Code;
import software.amazon.awscdk.services.lambda.Function;
import software.amazon.awscdk.services.lambda.Runtime;
import software.constructs.Construct;

public class AppStack extends Stack {

    static record AppProps(Vpc consumervpc,Vpc producerVpc) {
    }
    public AppStack(final Construct scope, final String id, VPCStack props) {
        super(scope, id);

        Function.Builder.create(this, "consumer-lambdafunction")
        .runtime(Runtime.NODEJS_20_X) // Specify the runtime
        .vpc(props.consumerVpc)
        .vpcSubnets(SubnetSelection.builder().subnets(props.consumerVpc.getIsolatedSubnets()).build())
        .securityGroups(List.of(props.consumerVpcDefaultSG))
        .handler("index.handler") // Specify the handler (format: <filename>.<handler function>)
        .code(Code.fromAsset("./../consumer-lambda")) // Provide the code directory or ZIP file
        .build();

        Function.Builder.create(this, "producer_lambdafunction")
        .runtime(Runtime.NODEJS_20_X) // Specify the runtime
        .vpc(props.producerVPc)
        .vpcSubnets(SubnetSelection.builder().subnets(props.producerVPc.getIsolatedSubnets()).build())
        .securityGroups(List.of(props.producerVpcDefaultSG))
        .handler("index.handler") // Specify the handler (format: <filename>.<handler function>)
        .code(Code.fromAsset("./../producer-lambda")) // Provide the code directory or ZIP file
        .build();

    }
}

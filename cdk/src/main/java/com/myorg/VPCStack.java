package com.myorg;

import java.util.List;

import org.jetbrains.annotations.NotNull;

import software.amazon.awscdk.Stack;
import software.amazon.awscdk.StackProps;
import software.amazon.awscdk.services.ec2.ISecurityGroup;
import software.amazon.awscdk.services.ec2.Peer;
import software.amazon.awscdk.services.ec2.Port;
import software.amazon.awscdk.services.ec2.SecurityGroup;
// import software.amazon.awscdk.Duration;
// import software.amazon.awscdk.services.sqs.Queue;
import software.amazon.awscdk.services.ec2.SubnetConfiguration;
import software.amazon.awscdk.services.ec2.SubnetType;
import software.amazon.awscdk.services.ec2.Vpc;
import software.constructs.Construct;

public class VPCStack extends Stack {
    Vpc consumerVpc;
    Vpc producerVPc;
    ISecurityGroup consumerVpcDefaultSG;
    ISecurityGroup producerVpcDefaultSG;
    
    public VPCStack(final Construct scope, final String id) {
        this(scope, id, null);
    }

    public VPCStack(final Construct scope, final String id, final StackProps props) {
        super(scope, id);
        
        consumerVpc= Vpc.Builder
        .create(this, id+"-consumerVpc")
        .vpcName("Consumer Vpc")
        .enableDnsHostnames(true)
        .enableDnsSupport(true)
        .createInternetGateway(true)
        .subnetConfiguration(List.of(
            SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .name("PublicSubnet")
                .cidrMask(24)
                .build(),
            SubnetConfiguration.builder()
                .subnetType(SubnetType.PRIVATE_ISOLATED)
                .name("PrivateSubnet")
                .cidrMask(24)
                .build()
        ))
        .availabilityZones(List.of("us-east-1a", "us-east-1b"))
        .build();

        producerVPc=Vpc.Builder
        .create(this, id+"-producerVpc")
        .vpcName("Producer Vpc")
        .enableDnsHostnames(true)
        .enableDnsSupport(true)
        .createInternetGateway(true)
        .subnetConfiguration(List.of(
            SubnetConfiguration.builder()
                .subnetType(SubnetType.PUBLIC)
                .name("PublicSubnet")
                .cidrMask(24)
                .build(),
            SubnetConfiguration.builder()
                .subnetType(SubnetType.PRIVATE_ISOLATED)
                .name("PrivateSubnet")
                .cidrMask(24)
                .build()
        ))
        .availabilityZones(List.of("us-east-1a", "us-east-1b"))
        .build();
        
        consumerVpcDefaultSG = SecurityGroup.fromSecurityGroupId(this, id+"sgs", consumerVpc.getVpcDefaultSecurityGroup());
        consumerVpcDefaultSG.addEgressRule(Peer.anyIpv4(), Port.allTraffic(), "Allow all outbound traffic");
        consumerVpcDefaultSG.addIngressRule(consumerVpcDefaultSG, Port.allTraffic(), "Allow security grp inbound traffic");

        producerVpcDefaultSG = SecurityGroup.fromSecurityGroupId(this, id+"sgs2", producerVPc.getVpcDefaultSecurityGroup());
        producerVpcDefaultSG.addEgressRule(Peer.anyIpv4(), Port.allTraffic(), "Allow all outbound traffic");
        producerVpcDefaultSG.addIngressRule(producerVpcDefaultSG, Port.allTraffic(), "Allow security grp inbound traffic");

    }
}

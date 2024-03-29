package com.myorg;

import software.amazon.awscdk.App;
import software.amazon.awscdk.StackProps;

public class CdkJavaApp {
        public static void main(final String[] args) {
                App app = new App();

                VPCStack stack = new VPCStack(app, "ld-vpcstack", StackProps.builder().build());
                
                AppStack appStack = new AppStack(stack, "app-stack", stack);
                
                appStack.addDependency(stack);
                app.synth();

        }
}

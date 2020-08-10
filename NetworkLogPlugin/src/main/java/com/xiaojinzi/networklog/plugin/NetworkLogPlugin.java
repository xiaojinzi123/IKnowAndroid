package com.xiaojinzi.networklog.plugin;

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class NetworkLogPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseAppModuleExtension appModuleExtension = (BaseAppModuleExtension) project.getProperties().get("android");
        Object networkLogInterceptor = project.findProperty("networkLogInterceptor");
        Object networkLogProcessedInterceptor = project.findProperty("networkLogProcessedInterceptor");
        String networkLogInterceptorStr = null, networkLogProcessedInterceptorStr = null;
        if (networkLogInterceptor instanceof String) {
            networkLogInterceptorStr = (String) networkLogInterceptor;
            if ("".equals(networkLogInterceptorStr)) {
                networkLogInterceptorStr = null;
            }else {
                networkLogInterceptorStr = networkLogInterceptorStr.replace('.', '/');
            }
        }
        if (networkLogProcessedInterceptor instanceof String) {
            networkLogProcessedInterceptorStr = (String) networkLogProcessedInterceptor;
            if ("".equals(networkLogProcessedInterceptorStr)) {
                networkLogProcessedInterceptorStr = null;
            }else {
                networkLogProcessedInterceptorStr = networkLogProcessedInterceptorStr.replace('.', '/');
            }
        }
        appModuleExtension.registerTransform(new OkHttpInterceptorTransform(networkLogInterceptorStr, networkLogProcessedInterceptorStr));
    }

}
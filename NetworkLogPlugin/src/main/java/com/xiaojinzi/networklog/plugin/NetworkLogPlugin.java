package com.xiaojinzi.networklog.plugin;

import com.android.build.gradle.internal.dsl.BaseAppModuleExtension;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class NetworkLogPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {
        BaseAppModuleExtension appModuleExtension = (BaseAppModuleExtension) project.getProperties().get("android");
        appModuleExtension.registerTransform(new OkHttpInterceptorTransform());
    }

}
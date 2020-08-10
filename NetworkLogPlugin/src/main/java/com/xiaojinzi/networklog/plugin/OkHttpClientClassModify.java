package com.xiaojinzi.networklog.plugin;

import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassVisitor;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.Label;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.io.IOException;
import java.io.InputStream;

public class OkHttpClientClassModify implements Opcodes {

    public static byte[] doModify(InputStream inputStream,
                                  String networkLogInterceptorStr, String networkLogProcessedInterceptorStr) throws IOException {

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES | ClassWriter.COMPUTE_MAXS);
        ClassVisitor cv = new ClassVisitor(ASM4, cw) {
            @Override
            public MethodVisitor visitMethod(int access, String name, String desc, String signature, String[] exceptions) {
                // 移除原有的方法
                if ("interceptors".equals(name) && "()Ljava/util/List;".equals(desc)) {
                    return null;
                }
                return super.visitMethod(access, name, desc, signature, exceptions);
            }
        };
        ClassReader cr = new ClassReader(inputStream);
        cr.accept(cv, 0);

        generateInterceptorsMethod(cw, networkLogInterceptorStr, networkLogProcessedInterceptorStr);

        return cw.toByteArray();

    }

    private static void generateInterceptorsMethod(ClassWriter cw,
                                                   String networkLogInterceptorStr, String networkLogProcessedInterceptorStr) {
        // 新增方法
        MethodVisitor interceptorsMethodVisitor = cw.visitMethod(ACC_PUBLIC, "interceptors", "()Ljava/util/List;", null, null);

        interceptorsMethodVisitor.visitCode();
        // 创建 ArrayList list =  new ArrayList(interceptors);
        Label labelNewList = new Label();
        interceptorsMethodVisitor.visitLabel(labelNewList);
        interceptorsMethodVisitor.visitTypeInsn(NEW, "java/util/ArrayList");
        interceptorsMethodVisitor.visitInsn(DUP);
        interceptorsMethodVisitor.visitVarInsn(ALOAD, 0);
        interceptorsMethodVisitor.visitFieldInsn(GETFIELD, "okhttp3/OkHttpClient", "interceptors", "Ljava/util/List;");
        interceptorsMethodVisitor.visitMethodInsn(INVOKESPECIAL, "java/util/ArrayList", "<init>", "(Ljava/util/Collection;)V", false);
        interceptorsMethodVisitor.visitVarInsn(ASTORE, 1);

        if (networkLogInterceptorStr != null) {
            Label labelCreateNetworkLogInterceptor = new Label();
            interceptorsMethodVisitor.visitLabel(labelCreateNetworkLogInterceptor);
            interceptorsMethodVisitor.visitVarInsn(ALOAD, 1);
            interceptorsMethodVisitor.visitInsn(ICONST_0);
            interceptorsMethodVisitor.visitTypeInsn(NEW, networkLogInterceptorStr);
            interceptorsMethodVisitor.visitInsn(DUP);
            interceptorsMethodVisitor.visitMethodInsn(INVOKESPECIAL, networkLogInterceptorStr, "<init>", "()V", false);
            interceptorsMethodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(ILjava/lang/Object;)V", false);
        }

        if (networkLogProcessedInterceptorStr != null) {
            Label labelCreateNetworkLogProcessedInterceptor = new Label();
            interceptorsMethodVisitor.visitLabel(labelCreateNetworkLogProcessedInterceptor);
            interceptorsMethodVisitor.visitVarInsn(ALOAD, 1);
            interceptorsMethodVisitor.visitTypeInsn(NEW, networkLogProcessedInterceptorStr);
            interceptorsMethodVisitor.visitInsn(DUP);
            interceptorsMethodVisitor.visitMethodInsn(INVOKESPECIAL, networkLogProcessedInterceptorStr, "<init>", "()V", false);
            interceptorsMethodVisitor.visitMethodInsn(INVOKEVIRTUAL, "java/util/ArrayList", "add", "(Ljava/lang/Object;)Z", false);
            interceptorsMethodVisitor.visitInsn(POP);
        }

        Label labelReturn = new Label();
        interceptorsMethodVisitor.visitLabel(labelReturn);
        interceptorsMethodVisitor.visitVarInsn(ALOAD, 1);
        interceptorsMethodVisitor.visitInsn(ARETURN);

        interceptorsMethodVisitor.visitEnd();

    }

}

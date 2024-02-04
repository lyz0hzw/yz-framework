package org.learn.framework.util;

import cn.hutool.core.comparator.VersionComparator;
import cn.hutool.core.util.ReflectUtil;
import cn.hutool.log.Log;

import org.objectweb.asm.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.Modifier;

import java.util.Arrays;
import java.util.List;

/**
 * 参数解析工具类
 */
public class ParameterUtil {

    /**
     * Hutool提供的版本比较器，用以判断当前JDK版本是否大于1.8
     * 仅在JDK版本大于1.8的情况下，才可以通过反射获取参数名称
     * 若通过ASM字节码获取参数名称，则需要额外引入别的工具库
     */
    public static final boolean LEGAL_JDK_VERSION = VersionComparator.INSTANCE.compare(System.getProperty("java.version"), "1.8.0") > 0;

    private ParameterUtil() {}

    public static String getName(Parameter parameter){
        if (!LEGAL_JDK_VERSION){
            Log.get().error("JDK版本应该大于1.8并且编译时应带有参数[-parameters]");
            throw new RuntimeException("JDK版本应该大于1.8");
        }else {
            return parameter.getName();
        }
    }

    /**
     * 比较参数类型是否一致
     *
     * @param types    asm的类型({@link Type})
     * @param clazzes  java 类型({@link Class})
     * @return
     */
    private static boolean sameType(Type[] types, Class<?>[] clazzes) {
        // 个数不同
        if (types.length != clazzes.length) {
            return false;
        }

        for (int i = 0; i < types.length; i++) {
            if (!Type.getType(clazzes[i]).equals(types[i])) {
                return false;
            }
        }
        return true;
    }

    /**
     * 利用asm实现通过反射来获取方法的形参名称
     * @param m 方法
     * @return  获得方法形参名
     */
    public static String[] getParameterName(Method m){
        final String[] paramNames = new String[m.getParameterTypes().length];
        // 获取方法所属类的名称
        final String n = m.getDeclaringClass().getName();
        ClassReader cr = null;
        try {
            cr = new ClassReader(n);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        cr.accept(new ClassVisitor(Opcodes.ASM7) {
            @Override
            public MethodVisitor visitMethod(final int access,
                                             final String name, final String desc,
                                             final String signature, final String[] exceptions) {
                // 方法获取方法的参数类型
                final Type[] args = Type.getArgumentTypes(desc);
                // 方法名相同并且参数个数相同
                if (!name.equals(m.getName())
                        || !sameType(args, m.getParameterTypes())) {
                    return super.visitMethod(access, name, desc, signature, exceptions);
                }
                MethodVisitor v = super.visitMethod(access, name, desc, signature, exceptions);
                return new MethodVisitor(Opcodes.ASM7, v) {
                    @Override
                    public void visitLocalVariable(String name, String desc,
                                                   String signature, Label start, Label end, int index) {
                        int i = index - 1;
                        // 如果是静态方法，则第一就是参数
                        // 如果不是静态方法，则第一个是"this"，然后才是方法的参数
                        if (Modifier.isStatic(m.getModifiers())) {
                            i = index;
                        }
                        if (i >= 0 && i < paramNames.length) {
                            paramNames[i] = name;
                        }
                        super.visitLocalVariable(name, desc, signature, start,
                                end, index);
                    }
                };
            }
        }, 0);
        return paramNames;
    }

    public static String[] getNames(Class<?> clazz, String methodName){
        return getNames(ReflectUtil.getMethodByName(clazz, methodName));
    }

    /**
     * 通过反射来获取方法的形参名称，实际应用中需要修改运行参数
     * @param method 方法
     * @return 形参名称列表
     */
    public static String[] getNames(Method method){
        if(!LEGAL_JDK_VERSION){
            Log.get().error("JDK版本应该大于1.8并且编译时应带有参数[-parameters]");
            throw new RuntimeException("JDK版本应该大于1.8");
        }
        Parameter[] parameters = method.getParameters();
        String[] names = new String[parameters.length];
        for (int i = 0; i < parameters.length; i++) {
            names[i] = parameters[i].getName();
        }
        return names;
    }
}

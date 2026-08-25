package com.library.annotation;

import java.lang.reflect.Method;

public class AuditProcessor {

    public static void auditMethod(
            Class<?> clazz,
            String methodName
    ) {

        Method[] methods =
                clazz.getDeclaredMethods();

        for (Method method : methods) {

            if (
                    method.getName()
                            .equals(methodName)
                    &&
                    method.isAnnotationPresent(
                            Auditable.class
                    )
            ) {

                Auditable auditable =
                        method.getAnnotation(
                                Auditable.class
                        );

                System.out.println(
                        "[AUDIT] Action: "
                        + auditable.action()
                );

                System.out.println(
                        "[AUDIT] Method: "
                        + method.getName()
                );
            }
        }
    }
}
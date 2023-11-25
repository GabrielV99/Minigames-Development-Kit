package ro.Gabriel.Misc;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;

public final class Utils {

    public static boolean isMethodPresent(Class<?> clazz, String name) {
        try {
            clazz.getMethod(name);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean isAnnotated(AnnotatedElement clazz, Class<? extends Annotation> annotation) {
        return clazz.isAnnotationPresent(annotation);
    }

    public static <T extends Annotation> T getAnnotation(Class<?> clazz, Class<T> annotation) {
        return clazz.getAnnotation(annotation);
    }

    public static boolean isExtends(Class<?> clazz, Class<?> extension) {
        return clazz != extension && extension.isAssignableFrom(clazz);
    }

    public static double calculateStringArraySimilarity(String[] arr1, String[] arr2) {
        if (arr1 == null || arr2 == null) {
            return 0.0;
        }

        int len1 = arr1.length;
        int len2 = arr2.length;

        if (len1 == 0 || len2 == 0) {
            return 0.0;
        }

        int commonCount = 0;

        /*if(len1 > len2) {
            for(int i = 0; i < len1; i++) {
                for(int j = 0; j < len2; j++) {
                    if(i == j && arr1[i].equals(arr2[j])) {
                        commonCount++;
                        break;
                    }
                }
            }
        } else {
            for(int i = 0; i < len2; i++) {
                for(int j = 0; j < len1; j++) {
                    if(i == j && arr2[i].equals(arr1[j])) {
                        commonCount++;
                        break;
                    }
                }
            }
        }*/
        for (String s1 : arr1) {
            for (String s2 : arr2) {
                if (s1.equals(s2) || s1.startsWith(s2) || s2.startsWith(s1)) {
                    commonCount++;
                    break;
                }
            }
        }

        int total = len1 + len2 - commonCount;

        return (double) commonCount / total * 100;
    }
}
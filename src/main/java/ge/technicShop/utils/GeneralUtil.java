package ge.technicShop.utils;

import org.apache.coyote.BadRequestException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public final class GeneralUtil {

    public static String getGetterName(String fieldName) {
        return "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static String getSetterName(String fieldName) {
        return "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
    }

    public static void checkRequiredProperties(Object objectToCheck, List<String> requiredProperties) throws Exception {
        List<String> errorKeywords = new ArrayList<>();
        for (String property : requiredProperties) {
            boolean requiredPropertyIsPresent = true;
            Object value = null;
            try {
                value = objectToCheck.getClass().getMethod(getGetterName(property)).invoke(objectToCheck);
            } catch (Exception ex) {
                requiredPropertyIsPresent = false;
            }
            if (value == null) {
                requiredPropertyIsPresent = false;
            }
            if (!requiredPropertyIsPresent) {
                errorKeywords.add(property + "_REQUIRED");
            }
        }
        if (!errorKeywords.isEmpty()) {
            throw new BadRequestException(errorKeywords.stream().collect(Collectors.joining(";")));
        }
    }

    private static List<Field> getFieldsUpTo(Class<?> startClass, Class<?> exclusiveParent) {
        List<Field> currentClassFields = new ArrayList<>(Arrays.asList(startClass.getDeclaredFields()));
        Class<?> parentClass = startClass.getSuperclass();
        if (parentClass != null && (exclusiveParent == null || !parentClass.equals(exclusiveParent))) {
            currentClassFields.addAll(getFieldsUpTo(parentClass, exclusiveParent));
        }
        return currentClassFields;
    }

    private static <T1, T2> void copyValue(T1 sourceObject, T2 destinationObject, String propertyName) throws Exception {
        Object sourceValue = sourceObject.getClass().getMethod(getGetterName(propertyName)).invoke(sourceObject);
        Class<?> sourceReturnType = sourceObject.getClass().getMethod(getGetterName(propertyName)).getReturnType();
        Class<?> destinationReturnType = destinationObject.getClass().getMethod(getGetterName(propertyName)).getReturnType();
        if (sourceReturnType.equals(destinationReturnType) && sourceValue != null) {
            destinationObject.getClass().getMethod(getSetterName(propertyName), sourceReturnType).invoke(destinationObject, sourceValue);
        }
    }

    public static <T1, T2> T2 getCopyOf(T1 source, T2 destination, String... excludeProps) throws Exception {
        List<Field> destinationFields = getFieldsUpTo(destination.getClass(), Object.class);
        List<String> destinationFieldNames = destinationFields.stream().map(Field::getName).collect(Collectors.toList());
        List<String> excludeProperties = Arrays.asList(excludeProps);

        for (Field f : getFieldsUpTo(source.getClass(), Object.class)) {
            if (!Modifier.isStatic(f.getModifiers()) && !excludeProperties.contains(f.getName()) && destinationFieldNames.contains(f.getName())) {
                copyValue(source, destination, f.getName());
            }
        }
        return destination;
    }
}
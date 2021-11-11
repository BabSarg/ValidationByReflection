package validation;

import validation.anotation.*;

import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

public class Validator<D> {

    private static final String REGEX = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$";

    public List<String> accepts(D dto) {
        List<String> errors = new ArrayList<>();
        try {
            Class<?> dtoClass = dto.getClass();
            for (Field declaredField : dtoClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Length.class)) {
                    checkLengthValidation(declaredField, errors, dto);
                } else if (declaredField.isAnnotationPresent(Email.class)) {
                    checkEmailValidation(declaredField, errors, dto);
                } else if (declaredField.isAnnotationPresent(Adulthood.class)) {
                    checkAdulthoodValidation(declaredField,errors,dto);
                }else if (declaredField.isAnnotationPresent(Min.class) || declaredField.isAnnotationPresent(Max.class)){
                    if(declaredField.isAnnotationPresent(Min.class)){
                        Min annotation = declaredField.getAnnotation(Min.class);
                        checkMinValidation(declaredField,annotation.value(),errors,dto);
                    }
                    if(declaredField.isAnnotationPresent(Max.class)){
                        Max annotation = declaredField.getAnnotation(Max.class);
                        checkMaxValidation(declaredField,annotation.value(),errors,dto);
                    }
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return errors;
    }

    private void checkLengthValidation(Field field, List<String> errors, D dto) throws IllegalAccessException {
        Length annotation = field.getAnnotation(Length.class);
        field.setAccessible(true);
        String value = (String) field.get(dto);
        if (value.length() < annotation.min() || value.length() > annotation.max()) {
            errors.add(field.getName() + " is invalid");
        }
    }

    private void checkEmailValidation(Field field, List<String> errors, D dto) throws IllegalAccessException {
        field.setAccessible(true);
        String value = (String) field.get(dto);
        if (!value.matches(REGEX)) {
            errors.add(field.getName() + " is invalid");
        }
    }

    private void checkAdulthoodValidation(Field field, List<String> errors, D dto) throws IllegalAccessException {
        field.setAccessible(true);
        LocalDate localDate = (LocalDate) field.get(dto);
        int years = Period.between(localDate, LocalDate.now()).getYears();
        if (years < 18) {
            errors.add(field.getName() + " is invalid");
        }
    }

    private void checkMinValidation(Field field,int minimum,List<String> errors,D dto) throws IllegalAccessException {
        field.setAccessible(true);
        Integer value = (Integer) field.get(dto);
        if(value < minimum){
            errors.add(field.getName() + " value is invalid");
        }
    }

    private void checkMaxValidation(Field field,int maximum,List<String> errors, D dto) throws IllegalAccessException {
        field.setAccessible(true);
        Integer value = (Integer) field.get(dto);
        if (value > maximum){
            errors.add(field.getName() + " value is invalid");
        }
    }

}

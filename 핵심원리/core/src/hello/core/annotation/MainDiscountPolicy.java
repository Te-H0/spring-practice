package hello.core.annotation;



import javax.annotation.meta.TypeQualifier;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@TypeQualifier("mainDiscountPolicy")
public @interface MainDiscountPolicy {
}

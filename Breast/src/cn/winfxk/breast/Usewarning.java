package cn.winfxk.breast;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用时请小心谨慎，否则可能会导致循环报错
 * 
 * @Createdate 2020/05/21 17:38:13
 * @author Winfxk
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Usewarning {
}

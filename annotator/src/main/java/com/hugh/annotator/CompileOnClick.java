package com.hugh.annotator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Hugh on 2019/3/4.
 *
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface CompileOnClick {
    int[] value();
}
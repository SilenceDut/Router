package com.complier;

import com.squareup.javapoet.ClassName;

/**
 * Created by SilenceDut on 2017/1/18 .
 */

public class TypeUtil {
    public static final ClassName MAPCLS = ClassName.get("java.util", "Map");
    public static final ClassName HASHMAPCLS = ClassName.get("java.util", "HashMap");
    public static final ClassName STRINGCLS = ClassName.get("java.lang", "String");
    public static final ClassName METHODTHREADCLS = ClassName.get("com.silencedut.router", "ThreadFinder");

}

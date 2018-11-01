package com.flyppe.flypplemvp.utils;

import com.flyppe.flypplemvp.basic.BaseContract;
import com.flyppe.flypplemvp.basic.BasePresenter;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

/**
 * 这个类搬运自XMVP框架，向阿里的大佬致敬，写的太NB了
 * 原来没有注释，我费了点功夫，读懂每一句，然后尽量加了比较全的注释
 *
 * 主要用来实例化Presenter对象
 * Created by flypple on 2018/8/18.
 */

public class GenericUtils {

    /**
     * 该方法的作用在于找到klass的泛型中，是filterClass的子接口或者和filterClass一致的那个
     * 如果找不到，则返回null
     */
    private static <T> Class<T> getGenericClass(Class<?> klass, Class<?> filterClass) {
        //getGenericSuperclass()获得带有泛型的父类
        //Type是 Java 编程语言中所有类型的公共高级接口。它们包括原始类型、参数化类型、数组类型、类型变量和基本类型。
        Type type = klass.getGenericSuperclass();
        if (type == null || !(type instanceof ParameterizedType)) return null;
        ParameterizedType parameterizedType = (ParameterizedType) type;
        //获取所有的泛型
        Type[] types = parameterizedType.getActualTypeArguments();
        //如果和某个泛型t是filterClass接口的子接口，则返回泛型t
        for (Type t : types) {
            if (isMe(t, filterClass)) {
                return (Class<T>) t;
            }
        }

        return null;
    }

    /**
     * 这个方法的作用是，找到klass类所实现的所有接口中，
     * 身为filterClass接口的子接口的或者和filterClass接口相同的那个接口的class
     *
     * (呼。。。太绕了，如果你看不懂，多看两遍，我觉的我已经解释的很明白了)
     *
     * @return 返回的class是filterClass的子接口或者是filterClass接口本身
     */
    private static Class<?> getViewClass(Class<?> klass, Class<?> filterClass) {
        //遍历klass所实现的所有的接口的class对象
        for (Class c : klass.getInterfaces()) {
            /*
             * 如果filterClass是c的父类，或者filterClass和c本身就是同一个类，则返回klass
             */
            if (filterClass.isAssignableFrom(c)) return klass;
        }
        //如果上面的循环没有返回值，则找到klass代表的类的父类，递归本方法
        return getViewClass(klass.getSuperclass(), filterClass);
    }

    /**
     * 判断aClass和filterClass是不是同一个接口
     */
    private static boolean isMe(Class<?> aClass, Class<?> filterClass) {
        Class<?>[] classes = aClass.getInterfaces();
        for (Class<?> c : classes) {
            return c == filterClass || isMe(c, filterClass);
        }
        return false;
    }

    private static boolean isMe(Type t, Class<?> filterClass) {
        Class<?> aClass = (Class<?>) t;
        return isMe(aClass, filterClass);
    }

    /**
     * 该方法用于实例化Presenter对象，不但实例化了Presenter对象，同时调用init方法初始化了Presenter
     * 直接运用反射，实例化了Model对象，直接实现了Persenter对View和Model的持有
     *
     * 大量运用了java多态的思想，java基础不牢者，要费点功夫理解了
     */
    public static <T> T newPresenter(Object obj) {
        //判断是否实现了BaseContract.View接口
        if (!BaseContract.View.class.isInstance(obj)) {
            throw new RuntimeException("you shall implement BaseContract.View");
        }
        try {
            //拿到obj当前的class
            Class<?> currentClass = obj.getClass();
            //获取obj实现类中属于BaseContract.View子类的那个
            Class<?> viewClass = getViewClass(currentClass, BaseContract.View.class);
            //找到viewClass泛型中属于BaseContract.Presenter子类的那个
            Class<?> presenterClass = getGenericClass(viewClass, BaseContract.Presenter.class);
            //找到presenterClass泛型中属于BaseContract.Model子类的那个
            Class<?> modelClass = getGenericClass(presenterClass, BaseContract.Model.class);
            //通过反射，获取presenter对象
            BasePresenter<?, ?> xBasePresenter = (BasePresenter<?, ?>) presenterClass.newInstance();
            //初始化presenter对象
            xBasePresenter.init(obj, modelClass.newInstance());
            return (T) xBasePresenter;
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("instance of presenter fail\n" +
                "Remind presenter need to extends BasePresenter");
    }
}

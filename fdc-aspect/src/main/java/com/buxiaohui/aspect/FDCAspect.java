/*
 * Copyright (C) 2020 Baidu, Inc. All Rights Reserved.
 */
package com.buxiaohui.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;

import com.buxiaohui.annotation.FDC;
import com.buxiaohui.fdc.FastClickUtils;

import android.text.TextUtils;

@Aspect
public class FDCAspect {
    private static final String TAG = "FDCAspect";
    private static final String POINTCUT_ON_ANNOTATION = "execution(@com.buxiaohui.annotation.FDC * *(..))";
    private static final String POINTCUT_ON_VIEW_CLICK = "execution(* android.view.View.OnClickListener.onClick(..))";

    @Pointcut(POINTCUT_ON_ANNOTATION)
    public void onAnnotationClick() {
    }

    @Around("onAnnotationClick()")
    public void processJoinPoint(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature methodSignature = (MethodSignature) joinPoint.getSignature();
        if (methodSignature == null) {
            joinPoint.proceed();
            return;
        }
        Method method = methodSignature.getMethod();
        if (method == null) {
            joinPoint.proceed();
            return;
        }
        if (!method.isAnnotationPresent(FDC.class)) {
            joinPoint.proceed();
            return;
        }
        FDC fdc = method.getAnnotation(FDC.class);
        String tag = fdc.tag();
        int timeInterval = fdc.timeInterval();
        if (TextUtils.isEmpty(tag)) {
            if (timeInterval <= 0) {
                if (FastClickUtils.isFastClick()) {
                    System.out.println(TAG + "-----method--processJoinPoint---快速点击，fdc：" + fdc);
                    return;
                }
            } else {
                if (FastClickUtils.isFastClick(timeInterval)) {
                    System.out.println(TAG + "-----method--processJoinPoint---快速点击，fdc：" + fdc);
                    return;
                }
            }
        } else {
            if (timeInterval <= 0) {
                if (FastClickUtils.isFastClick(tag)) {
                    System.out.println(TAG + "-----method--processJoinPoint---快速点击，fdc：" + fdc);
                    return;
                }
            } else {
                if (FastClickUtils.isFastClick(tag, timeInterval)) {
                    System.out.println(TAG + "-----method--processJoinPoint---快速点击，fdc：" + fdc);
                    return;
                }
            }
        }
        joinPoint.proceed();
    }
}

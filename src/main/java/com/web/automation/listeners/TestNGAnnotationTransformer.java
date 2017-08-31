package com.web.automation.listeners;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
 
import org.testng.IAnnotationTransformer;
import org.testng.ITest;
import org.testng.annotations.ITestAnnotation;
import org.testng.annotations.Test;
import org.testng.IAnnotationTransformer;

public class TestNGAnnotationTransformer implements IAnnotationTransformer{
	@Override
	public void transform(ITestAnnotation annotation, Class testClass,Constructor testConstructor, Method testMethod) {
		annotation.setTimeOut(2700000);
		//presently set to 45 minutes of timeout
	}
}

/*
We need to add this extra node in the testng xml just before the test

<listeners>
		<listener class-name="com.mobile.equinox.util.TestNGAnnotationTransformer"></listener>
</listeners>
*/
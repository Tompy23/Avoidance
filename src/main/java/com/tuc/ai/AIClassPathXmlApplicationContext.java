
package com.tuc.ai;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class AIClassPathXmlApplicationContext extends ClassPathXmlApplicationContext 
{
    public AIClassPathXmlApplicationContext(String s, Class<?> aClass) throws BeansException 
    {
        super(s, aClass);
    }

    public AIClassPathXmlApplicationContext(String s) throws BeansException 
    {
        super(s);
    }

    public AIClassPathXmlApplicationContext(String[] s) throws BeansException 
    {
        super(s);
    }

    protected void initBeanDefinitionReader(XmlBeanDefinitionReader beanDefinitionReader) 
    {
        beanDefinitionReader.setValidationMode(XmlBeanDefinitionReader.VALIDATION_NONE);
        beanDefinitionReader.setNamespaceAware(true);
    }
}
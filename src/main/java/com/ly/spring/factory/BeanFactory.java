package com.ly.spring.factory;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 工厂类，生产对象（使用反射技术）
 */
public class BeanFactory {

    /**
     * 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
     * 任务二：对外提供获取实例对象的接口（根据id获取）
     */
    private final static Map<String, Object> map = new HashMap<>();  // 存储对象

    static {
        // 任务一：读取解析xml，通过反射技术实例化对象并且存储待用（map集合）
        // 加载xml
        InputStream resourceAsStream = BeanFactory.class.getClassLoader().getResourceAsStream("beans.xml");
        // 解析xml
        SAXReader saxReader = new SAXReader();
        try {
            Document document = saxReader.read(resourceAsStream);
            Element rootElement = document.getRootElement();
            List<Element> beanList = rootElement.selectNodes("//bean");

            for (Element element : beanList) {
                // 处理每个bean元素，获取到该元素的id 和 class 属性
                String id = element.attributeValue("id");
                String clazz = element.attributeValue("class");
                // 通过反射技术实例化对象
                Class<?> aClass = Class.forName(clazz);
                Object o = aClass.newInstance();  // 实例化之后的对象

                // 存储到map中待用
                map.put(id, o);
            }

            // 实例化完成之后维护对象的依赖关系，检查哪些对象需要传值进入，根据它的配置，我们传入相应的值
            // 有property子元素的bean就有传值需求
            List<Element> propertyList = rootElement.selectNodes("//property");
            // 解析property，获取父元素
            for (Element element : propertyList) {
                String name = element.attributeValue("name");
                String ref = element.attributeValue("ref");

                // 找到当前需要被处理依赖关系的bean
                Element parent = element.getParent();

                // 调用父元素对象的反射功能
                String parentId = parent.attributeValue("id");

                // 拿到父类的对象。
                Object parentObject = map.get(parentId);

                // 遍历父对象中的所有方法，找到"set" + name
                Method[] methods = parentObject.getClass().getMethods();

                for (Method method : methods) {
                    // 拿到 set 该属性的方法。
                    if (method.getName().equalsIgnoreCase("set" + name)) {
                        // 这里的 ref 相当于 bean 标签中的 id
                        method.invoke(parentObject, map.get(ref));
                    }
                }

                // 把处理之后的parentObject重新放到map中
                map.put(parentId, parentObject);
            }
            System.out.println("BeanFactory的static代码块执行完毕");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 任务二：对外提供获取实例对象的接口（根据id获取）
    public static Object getBean(String id) {
        return map.get(id);
    }
}

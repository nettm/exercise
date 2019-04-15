package com.nettm.exercise.mybatis;

import net.sf.jsqlparser.JSQLParserException;
import net.sf.jsqlparser.parser.CCJSqlParserUtil;
import net.sf.jsqlparser.statement.Statement;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.defaults.DefaultSqlSession;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Mapper扫描工具，验证是否能通过SQL解析
 */
public class SQLAnalysisScan {

    private static final String ROOT = "/Users/test/exercise";

    private static String errMethod = StringUtils.EMPTY;

    private static Object generate(String id)
        throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        String clzName = StringUtils.substringBeforeLast(id, ".");
        String methodName = StringUtils.substringAfterLast(id, ".");
        if (StringUtils.isBlank(clzName) || StringUtils.isBlank(methodName)) {
            // 方法重复，可以被忽略
            return null;
        }

        // 为了打印异常输出
        errMethod = methodName;
        if (methodName.equalsIgnoreCase("findByCode")) {
            // 为了调试用
            System.out.println();
        }

        Class<?> clz = Class.forName(clzName);
        Map<String, Object> map = new HashMap<>();
        Method[] methods = clz.getMethods();
        for (Method m : methods) {
            if (m.getName().equals(methodName)) {
                Parameter[] ps = m.getParameters();
                if (ps.length == 0) {
                    return null;
                } else if (ps.length == 1
                    && !ps[0].getType().equals(List.class)
                    && !ps[0].getType().equals(Set.class)
                    && !ps[0].getType().equals(Map.class)) {
                    Parameter parameter = ps[0];
                    Param param = parameter.getAnnotation(Param.class);
                    Object obj = generateObj(parameter.getType());
                    if (Objects.isNull(param)) {
                        return obj;
                    } else {
                        map.put(param.value(), obj);
                    }
                    return map;
                } else {
                    for (Parameter p : ps) {
                        map.putAll(generateObj(p));
                    }
                    return map;
                }
            }
        }

        return null;
    }

    private static Map<String, Object> generateObj(Parameter p)
        throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Map<String, Object> map = new HashMap<>();

        Param param = p.getAnnotation(Param.class);
        String name = Objects.isNull(param) ? p.getName() : param.value();

        if (p.getType().equals(List.class)) {
            List<Object> lst = new ArrayList<>();
            ParameterizedType type = (ParameterizedType)p.getParameterizedType();
            Type[] types = type.getActualTypeArguments();
            lst.add(generateObj(types[0]));
            if (Objects.nonNull(param)) {
                map.put(name, lst);
            } else {
                return wrapCollection(lst);
            }
        } else if (p.getType().equals(Set.class)) {
            Set<Object> set = new HashSet<>();
            ParameterizedType type = (ParameterizedType)p.getParameterizedType();
            Type[] types = type.getActualTypeArguments();
            set.add(generateObj(types[0]));
            if (Objects.nonNull(param)) {
                map.put(name, set);
            } else {
                return wrapCollection(set);
            }
        } else {
            map.put(name, generateObj(p.getType()));
        }

        return map;
    }

    private static Object generateObj(Class<?> type)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return generateObject(type.getTypeName());
    }

    private static Object generateObj(Type type)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        return generateObject(type.getTypeName());
    }

    private static Object generateObject(String clz)
        throws IllegalAccessException, InstantiationException, ClassNotFoundException {
        if (clz.equals("java.lang.Long") || clz.equals("long")) {
            return 1L;
        } else if (clz.equals("java.lang.Integer") || clz.equals("int")) {
            return 1;
        } else if (clz.equals("java.lang.Boolean") || clz.equals("boolean")) {
            return true;
        } else if (clz.equals("java.lang.String")) {
            return "abc";
        } else if (clz.equals("java.util.Date")) {
            return new Date();
        } else if (clz.equals("java.math.BigDecimal")) {
            return new BigDecimal(1);
        } else if (clz.equals("java.time.LocalDateTime")) {
            return LocalDateTime.now();
        } else if (clz.equals("java.util.List")) {
            return Collections.emptyList();
        } else {
            return generateObj(clz);
        }

    }

    private static Object generateObj(String clz)
        throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        Class<?> cls = Class.forName(clz);
        Object obj = cls.newInstance();

        Field[] fields = cls.getDeclaredFields();
        for (Field f : fields) {
            if (f.getName().equals("serialVersionUID")) {
                continue;
            }

            f.setAccessible(true);
            if (f.getType().equals(List.class)) {
                f.set(obj, generateList(f));
            } else if (f.getType().equals(Set.class)) {
                f.set(obj, generateSet(f));
            } else {
                f.set(obj, generateObj(f.getType()));
            }

        }

        return obj;
    }

    private static Object generateList(Field f)
        throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        List<Object> lst = new ArrayList<>();
        addField(lst, f);
        return lst;
    }

    private static Object generateSet(Field f)
        throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Set<Object> lst = new HashSet<>();
        addField(lst, f);
        return lst;
    }

    private static void addField(Collection<Object> collection, Field field)
        throws IllegalAccessException, ClassNotFoundException, InstantiationException {
        Type genericType = field.getGenericType();
        if (genericType instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)genericType;
            Class<?> genericClazz = (Class<?>)pt.getActualTypeArguments()[0];
            collection.add(generateObj(genericClazz));
        }
    }

    private static void parse(String resource, Configuration configuration)
        throws IOException, JSQLParserException, InstantiationException, IllegalAccessException,
        ClassNotFoundException {
        try (InputStream inputStream = new FileInputStream(new File(resource))) {
            XMLMapperBuilder builder = new XMLMapperBuilder(inputStream, configuration, resource,
                configuration.getSqlFragments());
            builder.parse();

            Collection<String> mappedStatementSet = configuration.getMappedStatementNames();
            for (String ms : mappedStatementSet) {
                MappedStatement mappedStatement = configuration.getMappedStatement(ms);
                Object parameterObject = generate(ms);
                if (Objects.isNull(parameterObject)) {
                    continue;
                }

                BoundSql boundSql = mappedStatement.getBoundSql(parameterObject);
                analysis(boundSql, mappedStatement.getSqlCommandType());
            }
        } finally {
            ErrorContext.instance().reset();
        }

    }

    private static void analysis(BoundSql boundSql, SqlCommandType sqlCommandType) throws JSQLParserException {
        Statement stmt = CCJSqlParserUtil.parse(boundSql.getSql());
        System.out.println(stmt.toString());
    }

    private static Map<String, Object> wrapCollection(final Object object) {
        if (object instanceof Collection) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<>();
            map.put("collection", object);
            if (object instanceof List) {
                map.put("list", object);
            }
            return map;
        } else if (object != null && object.getClass().isArray()) {
            DefaultSqlSession.StrictMap<Object> map = new DefaultSqlSession.StrictMap<>();
            map.put("array", object);
            return map;
        }
        return (Map)object;
    }

    private static void getScanMappers(String root, List<String> mappers) {
        File dir = new File(root);
        if (dir.exists() && !dir.getName().equals("target") && !dir.getName().equals("classes")) {
            if (dir.isDirectory()) {
                File[] files = dir.listFiles();
                if (Objects.isNull(files)) {
                    return;
                }

                for (File file : files) {
                    getScanMappers(file.getAbsolutePath(), mappers);
                }
            } else {
                if (dir.getName().endsWith("Mapper.xml")) {
                    mappers.add(dir.getAbsolutePath());
                }
            }
        }
    }

    /**
     * 扫描类
     */
    private static void getScanClasses(String root, List<String> classes) {
        File dir = new File(root);
        if (dir.exists() && dir.isDirectory()) {
            if (dir.getName().equals("classes")) {
                classes.add(dir.getAbsolutePath());
            } else {
                File[] files = dir.listFiles();
                if (Objects.isNull(files)) {
                    return;
                }

                for (File file : files) {
                    getScanClasses(file.getAbsolutePath(), classes);
                }
            }

        }
    }

    private static String findExtMapper(List<String> mappers, String mapper) {
        for (String m : mappers) {
            String fileName = StringUtils.substringAfterLast(m, "/");
            if (fileName.equals("Ext" + mapper)) {
                return m;
            }
        }

        return null;
    }

    /**
     * 加载类，但对于依赖不能加载
     */
    private static void loadClass() {
        List<String> classes = new ArrayList<>();
        getScanClasses(ROOT, classes);

        List<URL> urls = new ArrayList<>();
        for (String clz : classes) {
            try {
                File file = new File(clz);
                URL url = file.toURI().toURL();
                urls.add(url);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
        }

        URL[] us = new URL[urls.size()];
        ClassLoader cl = new URLClassLoader(urls.toArray(us), ClassLoader.getSystemClassLoader());
        Thread.currentThread().setContextClassLoader(cl);
    }

    public static void main(String[] args) {
        System.out.println("-----------开始扫描-----------");
        loadClass();

        List<String> mappers = new ArrayList<>();
        getScanMappers(ROOT, mappers);

        List<List<String>> mapperList = new ArrayList<>();
        for (String mapper : mappers) {
            String fileName = StringUtils.substringAfterLast(mapper, "/");
            if (fileName.startsWith("Ext")) {
                continue;
            }

            List<String> lst = new ArrayList<>();
            lst.add(mapper);
            String extMapper = findExtMapper(mappers, fileName);
            if (Objects.nonNull(extMapper)) {
                lst.add(findExtMapper(mappers, fileName));
            }
            mapperList.add(lst);
        }

        for (List<String> lst : mapperList) {
            Configuration configuration = new Configuration();
            for (String mapper : lst) {
                try {
                    parse(mapper, configuration);
                } catch (Exception e) {
                    System.out.println("Error: " + mapper + " : " + errMethod);
                    e.printStackTrace();
                }
            }
        }

        System.out.println("-----------结束扫描-----------");
    }

}
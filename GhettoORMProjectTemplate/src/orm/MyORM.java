package orm;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;

import io.github.lukehutch.fastclasspathscanner.*;

import annotations.*;
import dao.*;

public class MyORM 
{	
	
	HashMap<Class, Class> entityToMapperMap = new HashMap<Class, Class>();
	
	
	public void init() throws Exception
	{
		System.out.println(this);
		// scan all mappers -- @MappedClass
		scanMappers();		
		
		// scan all the entities -- @Entity
		scanEntities();
				
		// create all entity tables
		createTables();

	}


	private void scanMappers() throws ClassNotFoundException 
	{
		// Use FastClasspathScanner to find all classes with MappedClass annotation
		// then scanMapperHelper()
		FastClasspathScanner scanner = new FastClasspathScanner("");
		scanner.matchClassesWithAnnotation(MappedClass.class, c -> scanMapperHelper(c)).scan();
		System.out.println("Scan Mappers Complete");
	}
	
	//helper function for scanMappers()
	// checks if clazz has @Entity
	// if yes, map clazz to mapper class
	private void scanMapperHelper(Class interfaceClass) 
	{
		MappedClass mappedClass = (MappedClass)interfaceClass.getAnnotation(MappedClass.class);
		if (mappedClass.clazz().isAnnotationPresent(Entity.class)) {
			entityToMapperMap.put(mappedClass.clazz(), interfaceClass);
			System.out.println("Mapped " + mappedClass.clazz().getName() + " to " + interfaceClass.getName());
		}else {
			throw new RuntimeException("No @Entity");
		}	
	}
	

	private void scanEntities() throws ClassNotFoundException 
	{
		FastClasspathScanner scanner = new FastClasspathScanner("");
		scanner.matchClassesWithAnnotation(Entity.class, c -> scanEntityHelper(c)).scan();
		System.out.println("Scan Entities Complete");
		
		// use FastClasspathScanner to scan the entity package for @Entity
			// go through each of the fields 
			// check if there is only 1 field with a Column id attribute
			// if more than one field has id throw new RuntimeException("duplicate id=true")
		
		
	}
	
	private void scanEntityHelper(Class entityClass) {
		int numColumnIDs = 0;
		for (Field field : entityClass.getDeclaredFields()) {
			System.out.println(field.getName());
			if (field.isAnnotationPresent(Column.class)) {
				Column c = field.getAnnotation(Column.class);
				if (c.id() == true) {
					numColumnIDs++;
				}
			}
		}
		System.out.println(numColumnIDs);
		if (numColumnIDs > 1) {
			throw new RuntimeException("duplicate id=true");
		}
	}
	
	public Object getMapper(Class clazz)
	{
		// create the proxy object for the mapper class supplied in clazz parameter
		// all proxies will use the supplied DaoInvocationHandler as the InvocationHandler

		return null;
	}
	

	private void createTables()
	{
		// go through all the Mapper classes in the map
			// create a proxy instance for each
			// all these proxies can be casted to BasicMapper
			// run the createTable() method on each of the proxies
		
	}

	

	
	
}

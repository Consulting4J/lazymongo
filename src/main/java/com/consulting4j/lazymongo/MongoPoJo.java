/*
Copyright [2020] [Consulting4J]

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
*/

package com.consulting4j.lazymongo;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Indexes;

public abstract class MongoPoJo {

	private static Map<String, Field> cachedFileds = new ConcurrentHashMap<String, Field>(256, 0.9f);

	private static Map<String, List<Field>> cachedFieldList = new ConcurrentHashMap<String, List<Field>>(128, 0.9f);

	private static MongoDatabase mongoDB;

	static {
		String dbName="mydbName";
		String dbURIString = "mongodb://[localhost]";
		MongoClient dbClient = MongoClients.create(dbURIString);
		mongoDB = dbClient.getDatabase(dbName);
	}

	long id = 0;

	String name = "";

	String _className = this.getClass().getName();

	public MongoPoJo() {
		id = UUID.randomUUID().getMostSignificantBits() & Long.MAX_VALUE;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void insert() {
		MongoCollection<Document> collection = mongoDB.getCollection(this.getClass().getName());
		Document doc = this.toDocument();
		collection.insertOne(doc);
	}

	public void update() {
		MongoCollection<Document> collection = mongoDB.getCollection(this.getClass().getName());
		Bson filter = Filters.eq("id", this.getId());
		Document operation = new Document("$set", this.toDocument());
		collection.updateOne(filter, operation);
	}

	public void delete() {
		MongoCollection<Document> collection = mongoDB.getCollection(this.getClass().getName());
		Bson filter = Filters.eq("id", this.getId());
		collection.findOneAndDelete(filter);
	}

	protected void restore() {

	}

	public static <T extends MongoPoJo> T findFirst(Class<T> t, Bson filter) {
		return findFirst(null, t, filter);
	}

	public static <T extends MongoPoJo> T findFirst(Bson projections, Class<T> t, Bson filter) {
		Document doc = null;
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		if (null != projections)
			doc = collection.find(filter).projection(projections).limit(1).first();
		else {
			doc = collection.find(filter).limit(1).first();
		}

		if (null != doc) {
			return toPoJo(doc);
		} else
			return null;
	}

	public static <T extends MongoPoJo> List<T> findAny(Class<T> t, Bson filter) {
		return findAny(null, t, filter, null);
	}

	public static <T extends MongoPoJo> List<T> findAny(Bson projections, Class<T> t, Bson filter) {
		return findAny(projections, t, filter, null);
	}

	public static <T extends MongoPoJo> List<T> findAny(Class<T> t, Bson filter, Bson orderBy) {
		return findAny(null, t, filter, orderBy);
	}

	public static <T extends MongoPoJo> List<T> findAny(Bson projections, Class<T> t, Bson filter, Bson orderBy) {
		List<T> result = new ArrayList<T>();
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		if (null == orderBy)
			orderBy = new Document("_id", 1);
		FindIterable<Document> resultset = null;
		if (null != projections)
			resultset = collection.find(filter).projection(projections).sort(orderBy);
		else
			resultset = collection.find(filter).sort(orderBy);

		MongoCursor<Document> cursor = resultset.iterator();
		while (cursor.hasNext()) {
			result.add(MongoPoJo.toPoJo(cursor.next()));
		}
		cursor.close();
		return result;
	}

	public static <T extends MongoPoJo> List<T> findAny(Class<T> t, Bson filter, int pageNo, int pageSize) {
		return findAny(null, t, filter, null, pageNo, pageSize);
	}

	public static <T extends MongoPoJo> List<T> findAny(Bson projections, Class<T> t, Bson filter, Bson orderBy, int pageNo, int pageSize) {
		List<T> result = new ArrayList<T>();
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		MongoCursor<Document> cursor;
		if (null == orderBy)
			orderBy = new Document("_id", 1);
		if (null != projections)
			cursor = collection.find(filter).projection(projections).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();
		else
			cursor = collection.find(filter).sort(orderBy).skip((pageNo - 1) * pageSize).limit(pageSize).iterator();

		while (cursor.hasNext()) {
			result.add(MongoPoJo.toPoJo(cursor.next()));
		}
		cursor.close();
		return result;
	}

	public static boolean isPrimaryTypes(Class<?> fieldClass) {

		boolean result = true;

		if (fieldClass.equals(Boolean.class)) {

		} else if (fieldClass.equals(Byte.class)) {

		} else if (fieldClass.equals(Short.class)) {

		} else if (fieldClass.equals(Integer.class)) {

		} else if (fieldClass.equals(Float.class)) {

		} else if (fieldClass.equals(Double.class)) {

		} else if (fieldClass.equals(Long.class)) {

		} else if (fieldClass.equals(String.class)) {

		} else {
			result = false;
		}
		return result;
	}

	public final Object getFieldValue(String fieldName) {

		if (fieldName.contains("this$"))
			return null;

		if ("_id".equals(fieldName)) {
			throw new RuntimeException("filed name '_id' is reserved by MongoDB");
		}
		Field theField = null;
		try {
			theField = cachedFileds.get(fieldName + "@" + this.getClass().getName());
			if (null == theField) {
				try {
					theField = this.getClass().getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
					Class<?> clazz = this.getClass();
					while ((clazz = clazz.getSuperclass()) != null) {
						try {
							theField = clazz.getDeclaredField(fieldName);
							break;
						} catch (NoSuchFieldException ex) {

						}
					}
				}
				cachedFileds.put(fieldName + "@" + this.getClass().getName(), theField);
			}
			theField.setAccessible(true);
			Class<?> fieldType = theField.getType();
			if (java.util.Collection.class.isAssignableFrom(fieldType)) {
				Object[] array = ((Collection<?>) theField.get(this)).toArray();
				List<Object> list = new ArrayList<Object>(array.length);
				if (array.length > 0) {
					Class<?> clazz = array[0].getClass();
					if (MongoPoJo.class.isAssignableFrom(clazz)) {
						for (Object obj : array) {
							list.add(((MongoPoJo) obj).toDocument());
						}
					} else {
						for (Object obj : array) {
							list.add(obj);
						}
					}
				}
				return list;
			}

			else if (MongoPoJo.class.isAssignableFrom(fieldType)) {
				MongoPoJo pojo = (MongoPoJo) theField.get(this);
				if (null != pojo)
					return pojo.toDocument();
				else
					return null;
			}

			else
				return theField.get(this);

		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public final void setFieldValue(String fieldName, Object valueInDB) {

		try {
			Field field = cachedFileds.get(fieldName + "@" + this.getClass().getName());
			if (null == field) {
				try {
					field = this.getClass().getDeclaredField(fieldName);
				} catch (NoSuchFieldException e) {
					Class<?> clazz = this.getClass();
					while ((clazz = clazz.getSuperclass()) != null) {
						try {
							field = clazz.getDeclaredField(fieldName);
							break;
						} catch (NoSuchFieldException ex) {
							//
						}
					}
				}
				if (null != field)
					cachedFileds.put(fieldName + "@" + this.getClass().getName(), field);
				else
					return;
			}

			field.setAccessible(true);
			if (isPrimaryTypes(field.getType())) {
				new PrimaryTypeCodec(this, field, valueInDB).decode();
			} else if (Collection.class.isAssignableFrom(field.getClass())) {
				new CollectionCodec(this, field, valueInDB).decode();
			} else if (Map.class.isAssignableFrom(field.getClass())) {
				new MapCodec(this, field, valueInDB).decode();
			} else if (MongoPoJo.class.isAssignableFrom(field.getClass())) {
				Document doc = (Document) valueInDB;
				field.set(this, toPoJo(doc));
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<Field> getClassFields(Class<?> clazz) {
		String className = clazz.getName();
		List<Field> fieldList = cachedFieldList.get(className);
		if (null != fieldList) {
			return fieldList;
		} else {
			fieldList = new ArrayList<Field>();
			Field[] objFields = clazz.getDeclaredFields();
			for (Field field : objFields) {
				int modifiers = field.getModifiers();
				if ((!Modifier.isStatic(modifiers))) {
					fieldList.add(field);
				}
			}
			while ((clazz = clazz.getSuperclass()) != null) {
				Field[] parentFields = clazz.getDeclaredFields();
				for (Field field : parentFields) {
					int modifiers = field.getModifiers();
					if ((!Modifier.isStatic(modifiers))) {
						fieldList.add(field);
					}
				}
			}
			cachedFieldList.put(className, fieldList);
		}
		return fieldList;
	}

	public Document toDocument() {

		Document document = new Document();
		Class<?> clazz = this.getClass();
		List<Field> fieldList = getClassFields(clazz);
		try {
			String fieldName = null;
			for (Field theField : fieldList) {
				fieldName = theField.getName();
				Object value = getFieldValue(fieldName);
				if (null != value)
					document.put(fieldName, value);
			}
		} catch (Exception e) {
			return null;
		}

		return document;
	}

	public static <T extends MongoPoJo> T toPoJo(Document document, String className) {
		T pojo = null;
		try {
			Class<?> clazz = Class.forName(className);
			if (className.contains("$")) {
				Class<?> parentClazz = Class.forName(className.substring(0, className.indexOf("$")));
				Constructor<?> constructor = clazz.getDeclaredConstructor(parentClazz);
				MongoPoJo parentObject = (MongoPoJo) parentClazz.newInstance();
				pojo = (T) constructor.newInstance(parentObject);
			} else
				pojo = (T) clazz.getDeclaredConstructor().newInstance();

		} catch (Exception e) {
			e.printStackTrace();
		}

		Iterator<String> iterator = document.keySet().iterator();
		String fieldName = null;
		Object value = null;
		while (iterator.hasNext()) {
			fieldName = iterator.next().toString();
			value = document.get(fieldName);
			if (null != value) {
				pojo.setFieldValue(fieldName, value);
			}
		}
		pojo.restore();
		return pojo;
	}

	public static <T extends MongoPoJo> T toPoJo(Document document) {
		String className = document.get("_className").toString();
		return toPoJo(document, className);
	}

	public static <T extends MongoPoJo> void createTextIndex(Class<T> t, String textFiledName) {
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		collection.createIndex(Indexes.text(textFiledName));
	}

	public static <T extends MongoPoJo> void createDecimalIndex(Class<T> t, String filedName, boolean ascending) {
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		if (ascending)
			collection.createIndex(Indexes.ascending(filedName));
		else
			collection.createIndex(Indexes.descending(filedName));
	}

	public static <T extends MongoPoJo> int count(Class<T> t, Bson filter) {
		MongoCollection<Document> collection = mongoDB.getCollection(t.getName());
		return (int) collection.countDocuments(filter);
	}

	public static <T extends MongoPoJo> boolean exist(Class<T> t, Bson filter) {
		return count(t, filter) > 0;
	}

}

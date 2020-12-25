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

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.bson.Document;

class CollectionCodec extends DocumentCodec {

	CollectionCodec(MongoPoJo pojo, Field field, Object value) {
		super(pojo, field, value);
	}

	private final Collection<?> getContainer(Field field) {
		Class<?> clazz = field.getType();
		Collection<?> collection = null;
		try {
			collection = (Collection<?>) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return collection;
	}

	void decode() {
		try {
			List<?> values = (List<?>) value;
			if (values.size() <= 0)
				return;
			Object firstElement = values.get(0);
			if (isPrimaryTypes(firstElement.getClass())) {
				Collection collection = getContainer(field);
				collection.addAll(values);
				field.set(pojo, collection);
			}

			else if (isMap(firstElement.getClass())) {
				Collection collection = getContainer(field);
				collection.addAll(values);
				field.set(pojo, collection);
			}

			else if (isPoJo(firstElement.getClass())) {
				List<? extends MongoPoJo> list = new ArrayList<>();
				for (Object pojo : values) {
					Document doc = (Document) pojo;
					list.add(MongoPoJo.toPoJo(doc));
				}
				Collection collection = getContainer(field);
				collection.addAll(list);
				field.set(pojo, collection);
				list.clear();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

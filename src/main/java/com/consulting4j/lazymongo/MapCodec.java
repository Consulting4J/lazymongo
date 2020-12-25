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
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.bson.Document;

class MapCodec extends DocumentCodec {

	MapCodec(MongoPoJo pojo, Field field, Object value) {
		super(pojo, field, value);
	}

	private final Map<?, ?> getContainer(Field field) {
		Class<?> clazz = field.getType();
		Map<?, ?> map = null;
		try {
			map = (Map<?, ?>) clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return map;
	}

	void decode() {
		try {
			Object[] objs = ((Map<?, ?>) value).values().toArray();
			if (objs.length < 1)
				return;
			Object firstElement = ((Map<?, ?>) value).values().toArray()[0];
			if (isPrimaryTypes(firstElement.getClass())) {
				Map map = new HashMap();
				map.putAll((Document) value);
				field.set(pojo, map);
			} else if (isPoJo(firstElement.getClass())) {
				Document doc = (Document) value;
				Map map = this.getContainer(field);
				Iterator<String> keys = doc.keySet().iterator();
				while (keys.hasNext()) {
					String key = keys.next();
					Document doc2 = (Document) doc.get(key);
					map.put(key, MongoPoJo.toPoJo(doc2));
				}
				field.set(pojo, map);
			} else
				field.set(pojo, value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

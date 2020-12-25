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
import java.util.Map;

abstract class DocumentCodec {

	MongoPoJo pojo = null;
	Field field = null;
	Object value = null;

	DocumentCodec(MongoPoJo pojo, Field field, Object value) {
		this.pojo = pojo;
		this.field = field;
		this.value = value;
	}

	final boolean isPrimaryTypes(Class<?> clazz) {
		return MongoPoJo.isPrimaryTypes(clazz);
	}

	final boolean isMap(Class<?> clazz) {
		if (Map.class.isAssignableFrom(clazz))
			return true;
		else
			return false;
	}

	final boolean isPoJo(Class<?> clzz) {
		if (MongoPoJo.class.isAssignableFrom(clzz))
			return true;
		else
			return false;
	}

	abstract void decode();

}

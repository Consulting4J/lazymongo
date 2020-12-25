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

class PrimaryTypeCodec extends DocumentCodec {

	PrimaryTypeCodec(MongoPoJo pojo, Field field, Object value) {
		super(pojo, field, value);
	}

	void decode() {

		try {
			if (field.getType().equals(Boolean.class)) {
				field.setBoolean(pojo, (Boolean) value);
			} else if (field.getType().equals(Byte.class)) {
				field.setByte(pojo, (Byte) value);
			} else if (field.getType().equals(Short.class)) {
				field.setShort(pojo, (Short) value);
			} else if (field.getType().equals(Integer.class)) {
				field.setInt(pojo, (Integer) value);
			} else if (field.getType().equals(Float.class)) {
				field.setFloat(pojo, (Float) value);
			} else if (field.getType().equals(Double.class)) {
				field.setDouble(pojo, (Double) value);
			} else if (field.getType().equals(Long.class)) {
				field.setLong(pojo, (Long) value);
			} else if (field.getType().equals(String.class)) {
				field.set(pojo, (String) value);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}

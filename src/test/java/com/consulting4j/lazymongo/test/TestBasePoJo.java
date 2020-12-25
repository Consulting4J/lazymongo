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

package com.consulting4j.lazymongo.test;

import java.lang.reflect.Field;
import java.util.List;

import com.consulting4j.lazymongo.MongoPoJo;

public class TestBasePoJo extends MongoPoJo {

	long id = 1;

	@Override
	public boolean equals(Object obj) {
		boolean result = true;
		if (null != obj && obj instanceof MongoPoJo) {
			List<Field> fields = this.getClassFields(this.getClass());
			for (Field field : fields)
				if (!this.getFieldValue(field.getName()).equals(((MongoPoJo) obj).getFieldValue(field.getName()))) {
					result = false;
					break;
				}
		} else {
			result = false;
		}
		return result;
	}
}

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

import static com.mongodb.client.model.Filters.eq;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.consulting4j.lazymongo.MongoPoJo;

public class MongoPoJoTest {

	@Test
	public void testBasicPoJo() {

		BasicPoJo pojo = new BasicPoJo();
		pojo.insert();
		BasicPoJo pojo2 = BasicPoJo.findFirst(BasicPoJo.class, eq("id", 1));
		assertEquals(pojo.field1, pojo2.field1);
		assertEquals(pojo.field2, pojo2.field2);
		assertEquals(pojo.field3, pojo2.field3);
		assertEquals(pojo.field4, pojo2.field4);
		assertEquals(pojo.field5, pojo2.field5);
		assertEquals(pojo.field6, pojo2.field6, 0.00001);
		assertEquals(pojo.field7, pojo2.field7, 0.00001);
		assertEquals(pojo.field8, pojo2.field8);
		assertEquals(pojo.field9, pojo2.field9);
		// pojo.delete();
	}

	@Test
	public void testCollectionPoJo() {
		CollectionPoJo pojo = new CollectionPoJo();
		pojo.insert();
		CollectionPoJo pojo2 = CollectionPoJo.findFirst(CollectionPoJo.class, eq("id", 1));
		assertEquals((long) 1, pojo2.id);
		assertEquals(pojo.field1, pojo2.field1);
		assertEquals(pojo.field2, pojo2.field2);
		assertEquals(pojo.field3, pojo2.field3);
		assertEquals(pojo.field4, pojo2.field4);
		assertEquals(pojo.field5, pojo2.field5);
		assertEquals(pojo.field6, pojo2.field6);
		assertEquals(pojo.field7, pojo2.field7);
		assertEquals(pojo.field8, pojo2.field8);
		assertEquals(pojo.field9, pojo2.field9);
	}

	@Test
	public void testMapPoJo() {
		MapPoJo pojo = new MapPoJo();
		pojo.insert();
		MapPoJo pojo2 = MapPoJo.findFirst(MapPoJo.class, eq("id", 1));
		assertEquals((long) 1, pojo2.id);
		assertEquals(pojo.field1, pojo2.field1);
		assertEquals(pojo.field2, pojo2.field2);
		assertEquals(pojo.field3, pojo2.field3);
		assertEquals(pojo.field4, pojo2.field4);
		assertEquals(pojo.field5, pojo2.field5);
		assertEquals(pojo.field6, pojo2.field6);
		assertEquals(pojo.field7, pojo2.field7);
		assertEquals(pojo.field8, pojo2.field8);
		assertEquals(pojo.field9, pojo2.field9);
	}

	@Test
	public void testEmbeddedPoJo() {
		EmbeddedPoJo pojo = new EmbeddedPoJo();
		pojo.insert();
		EmbeddedPoJo pojo2 = EmbeddedPoJo.findFirst(EmbeddedPoJo.class, eq("id", 1));
		assertEquals((long) 1, pojo2.id);
		assertEquals(pojo.field1, pojo2.field1);
		assertEquals(pojo.field1.field1, pojo2.field1.field1);
		assertEquals(pojo.field1.field2, pojo2.field1.field2);
		assertEquals(pojo.field1.field3, pojo2.field1.field3);
		assertEquals(pojo.field1.field4, pojo2.field1.field4);
		assertEquals(pojo.field1.field5, pojo2.field1.field5);
		assertEquals(pojo.field1.field6, pojo2.field1.field6, 0.00001);
		assertEquals(pojo.field1.field7, pojo2.field1.field7, 0.00001);
		assertEquals(pojo.field1.field8, pojo2.field1.field8);
		assertEquals(pojo.field1.field9, pojo2.field1.field9);

		assertEquals(pojo.field2, pojo2.field2);
		assertEquals(pojo.field2.field1, pojo2.field2.field1);
		assertEquals(pojo.field2.field2, pojo2.field2.field2);
		assertEquals(pojo.field2.field3, pojo2.field2.field3);
		assertEquals(pojo.field2.field4, pojo2.field2.field4);
		assertEquals(pojo.field2.field5, pojo2.field2.field5);
		assertEquals(pojo.field2.field6, pojo2.field2.field6);
		assertEquals(pojo.field2.field7, pojo2.field2.field7);
		assertEquals(pojo.field2.field8, pojo2.field2.field8);
		assertEquals(pojo.field2.field9, pojo2.field2.field9);

		assertEquals(pojo.field3, pojo2.field3);
		assertEquals(pojo.field3.field1, pojo2.field3.field1);
		assertEquals(pojo.field3.field2, pojo2.field3.field2);
		assertEquals(pojo.field3.field3, pojo2.field3.field3);
		assertEquals(pojo.field3.field4, pojo2.field3.field4);
		assertEquals(pojo.field3.field5, pojo2.field3.field5);
		assertEquals(pojo.field3.field6, pojo2.field3.field6);
		assertEquals(pojo.field3.field7, pojo2.field3.field7);
		assertEquals(pojo.field3.field8, pojo2.field3.field8);
		assertEquals(pojo.field3.field9, pojo2.field3.field9);

	}

	@Test
	public void testMixedPoJo() {
		MixedPoJo pojo = new MixedPoJo();
		pojo.insert();
		MixedPoJo pojo2 = MixedPoJo.findFirst(MixedPoJo.class, eq("id", 1));
		assertEquals((long) 1, pojo2.id);
		assertEquals(pojo.field1, pojo2.field1);
		assertEquals(pojo.field2, pojo2.field2);
		assertEquals(pojo.field3, pojo2.field3);
		assertEquals(pojo.field4, pojo2.field4);
		assertEquals(pojo.field5, pojo2.field5);
	}

	public static void testFindPerformance() {
		long start = System.currentTimeMillis();
		for (int i = 0; i < 500000; i++) {
			MongoPoJo.findFirst(BasicPoJo.class, eq("id", i));
		}
		long end = System.currentTimeMillis();
		System.out.println("insert use " + (end - start) + "milliseconds");
	}

	public static void main(String[] args) {
		testFindPerformance();
	}

}

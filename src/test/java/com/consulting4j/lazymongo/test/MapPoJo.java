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

import java.util.HashMap;
import java.util.Hashtable;
import java.util.TreeMap;

public class MapPoJo extends TestBasePoJo {

	HashMap<String, Boolean> field1 = new HashMap<>();
	Hashtable<String, Byte> field2 = new Hashtable<>();
	TreeMap<String, Character> field3 = new TreeMap<>();
	HashMap<String, Short> field4 = new HashMap<>();
	Hashtable<String, Integer> field5 = new Hashtable<>();
	TreeMap<String, Float> field6 = new TreeMap<>();
	HashMap<String, Double> field7 = new HashMap<>();
	Hashtable<String, Long> field8 = new Hashtable<>();
	TreeMap<String, String> field9 = new TreeMap<>();

	public MapPoJo() {
		super();
		field1.put("key1", true);
		field1.put("key2", false);
		field2.put("key1", (byte) 1);
		field2.put("key2", (byte) 2);
		field3.put("key1", (char) 1);
		field3.put("key2", (char) 2);
		field4.put("key1", (short) 1);
		field4.put("key2", (short) 2);
		field5.put("key1", 1);
		field5.put("key2", 2);
		field6.put("key1", 123.456f);
		field6.put("key2", 456.789f);
		field7.put("key1", 123.456);
		field7.put("key2", 456.789);
		field8.put("key1", 123456789l);
		field8.put("key2", 1234567890l);
		field9.put("key1", "value1");
		field9.put("key2", "value2");
	}

}

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

import java.util.ArrayList;
import java.util.Deque;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.TreeSet;
import java.util.Vector;

public class CollectionPoJo extends TestBasePoJo {

	List<Boolean> field1;
	List<Byte> field2;
	List<Character> field3;
	Set<Short> field4;
	Set<Integer> field5;
	Set<Float> field6;
	Queue<Double> field7;
	Queue<Long> field8;
	Deque<String> field9;

	public CollectionPoJo() {
		super();
		field1 = new ArrayList<>();
		field1.add(true);
		field1.add(false);
		field1.add(true);
		field1.add(false);
		field1.add(true);

		field2 = new LinkedList<>();
		field2.add((byte) 1);
		field2.add((byte) 2);
		field2.add((byte) 4);
		field2.add((byte) 8);
		field2.add(Byte.MAX_VALUE);

		field3 = new Vector<>();
		field3.add('1');
		field3.add('2');
		field3.add('4');
		field3.add('8');
		field3.add(Character.MAX_VALUE);

		field4 = new HashSet<>();
		field4.add((short) 1);
		field4.add((short) 2);
		field4.add((short) 4);
		field4.add((short) 8);
		field4.add((short) Short.MAX_VALUE);

		field5 = new LinkedHashSet<>();
		field5.add(1);
		field5.add(2);
		field5.add(4);
		field5.add(8);
		field5.add(Integer.MAX_VALUE);

		field6 = new TreeSet<>();
		field6.add(1.1f);
		field6.add(2.2f);
		field6.add(4.4f);
		field6.add(8.8f);
		field6.add(Float.MAX_VALUE);

		field7 = new LinkedList<>();
		field7.add(1.1);
		field7.add(2.2);
		field7.add(4.4);
		field7.add(8.8);
		field7.add(Double.MAX_VALUE);

		field8 = new LinkedList<>();
		field8.add(10000l);
		field8.add(1000000l);
		field8.add(100000000l);
		field8.add(10000000000l);
		field8.add(Long.MAX_VALUE);

		field9 = new LinkedList<>();
		field9.add("1");
		field9.add("2");
		field9.add("3");
		field9.add("4");
		field9.add("5");
	}

}

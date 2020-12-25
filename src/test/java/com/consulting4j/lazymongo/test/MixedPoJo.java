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

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MixedPoJo extends TestBasePoJo {

	boolean field1 = true;
	String field2 = "lazymongo string";
	List<Integer> field3 = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);
	Map<String, String> field4 = new HashMap<>();
	long field5 = 2 ^ 35 - 1;
}

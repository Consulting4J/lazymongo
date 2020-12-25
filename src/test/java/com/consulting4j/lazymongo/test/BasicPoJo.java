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

public class BasicPoJo extends TestBasePoJo {

	boolean field1 = true;
	byte field2 = (byte) 2;
	char field3 = '3';
	short field4 = (short) 444;
	int field5 = 555;
	float field6 = 123.456f;
	double field7 = 456.789;
	long field8 = 2 ^ 35 - 1;
	String field9 = "lazymongo string";

}

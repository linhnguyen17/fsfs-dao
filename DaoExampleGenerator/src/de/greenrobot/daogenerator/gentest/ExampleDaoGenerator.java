/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

	public static void main(String[] args) throws Exception {
		Schema schema = new Schema(1000, "net.runsystem.dreamcometrue.bean");

		// addNote(schema);
		// addCustomerOrder(schema);
		addCategory(schema);
		// addTask(schema);
		// addToDo(schema);
		addUser(schema);
		new DaoGenerator().generateAll(schema, "../DreamComeTrue/src-gen");
	}

	private static void addCategory(Schema schema) {
		Entity category = schema.addEntity("Categories");
		// category.addIdProperty();
		category.addLongProperty("category_id").notNull().primaryKey();
		category.addStringProperty("category_name").notNull();
		category.addStringProperty("color");
		category.addIntProperty("status");
		category.addDateProperty("created");
		category.addDateProperty("updated");

		Entity task = schema.addEntity("Tasks");
		// task.addIdProperty();
		task.addLongProperty("task_id").primaryKey().notNull();

		task.addLongProperty("user_id");

		Property category_id = task.addLongProperty("category_id").notNull().getProperty();
		task.addToOne(category, category_id);

		ToMany category2Task = category.addToMany(task, category_id);
		category2Task.setName("task_list");

		task.addStringProperty("task_name");

		task.addStringProperty("description");

		task.addStringProperty("image");

		task.addIntProperty("sort");

		task.addStringProperty("todo_status");

		task.addIntProperty("manual_sort");

		task.addStringProperty("event_id");

		task.addDateProperty("start_date").notNull();

		task.addDateProperty("end_date").notNull();

		task.addIntProperty("status").notNull();

		task.addDateProperty("created").notNull();

		task.addDateProperty("updated");

		Entity todo = schema.addEntity("ToDos");
		// todo.addIdProperty();
		todo.addLongProperty("todo_id").primaryKey().notNull();
		Property taskId = todo.addLongProperty("task_id").notNull().getProperty();
		todo.addToOne(task, taskId);

		ToMany task2todo = task.addToMany(todo, taskId);
		task2todo.setName("todo_list");

		todo.addStringProperty("todo_name");
		todo.addStringProperty("event_id");
		todo.addDateProperty("end_date").notNull();
		todo.addIntProperty("status").notNull();
		todo.addDateProperty("created").notNull();
		todo.addDateProperty("updated");
	}

	private static void addUser(Schema schema) {
		Entity note = schema.addEntity("Users");
		note.addIdProperty();
		// note.addStringProperty("user_id").primaryKey().notNull();
		note.addStringProperty("user_name").notNull();
		note.addStringProperty("birthday").notNull();
		note.addIntProperty("age_of_whole_life").notNull();
		note.addStringProperty("relation");
		note.addIntProperty("sort").notNull();
		note.addIntProperty("tutorial_finisfed_flag").notNull();
		note.addIntProperty("status").notNull();
		note.addDateProperty("created").notNull();
		note.addDateProperty("updated");
	}

	private static void addNote(Schema schema) {
		Entity note = schema.addEntity("Note");
		note.addIdProperty();
		note.addStringProperty("text").notNull();
		note.addStringProperty("comment");
		note.addDateProperty("date");
	}

	private static void addCustomerOrder(Schema schema) {
		Entity customer = schema.addEntity("Customer");
		customer.addIdProperty();
		customer.addStringProperty("name").notNull();

		Entity order = schema.addEntity("Order");
		order.setTableName("ORDERS"); // "ORDER" is a reserved keyword
		order.addIdProperty();
		Property orderDate = order.addDateProperty("date").getProperty();
		Property customerId = order.addLongProperty("customerId").notNull().getProperty();
		order.addToOne(customer, customerId);

		ToMany customerToOrders = customer.addToMany(order, customerId);
		customerToOrders.setName("orders");
		customerToOrders.orderAsc(orderDate);
	}

}

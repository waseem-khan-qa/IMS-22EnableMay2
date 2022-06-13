package com.qa.ims.persistence.dao;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.utils.DBUtils;

public class OrderDAOTest {
	private final OrderDAO DAO = new OrderDAO();

	@Before
	public void setup() {
		DBUtils.connect();
		DBUtils.getInstance().init("src/test/resources/sql-schema.sql", "src/test/resources/sql-data.sql");
	}

	@Test
	public void testCreate() {
		final Customer customer = new Customer("jordan", "harrison");
		final Item item = new Item("laptop", 10.0);
		final List<Item> items = new ArrayList<>();
		items.add(item);
		final Order created = new Order(customer, items);
		assertEquals(created, DAO.create(created));
	}

	@Test
	public void testReadAll() {
		final Customer customer = new Customer("jordan", "harrison");
		final Item item = new Item("laptop", 10.0);
		final List<Item> items = new ArrayList<>();
		items.add(item);
		List<Order> expected = new ArrayList<>();
		expected.add(new Order(customer, items));
		assertEquals(expected, DAO.readAll());
	}

	@Test
	public void testReadLatest() {
		final Customer customer = new Customer("jordan", "harrison");
		final Item item = new Item("laptop", 10.0);
		final List<Item> items = new ArrayList<>();
		items.add(item);		
		assertEquals(new Order(customer, items), DAO.readLatest());
	}

	@Test
	public void testRead() {
		final long ID = 1L;
		final Customer customer = new Customer("jordan", "harrison");
		final Item item = new Item("laptop", 10.0);
		final List<Item> items = new ArrayList<>();
		items.add(item);		
		assertEquals(new Order(ID, customer, items), DAO.read(ID));
	}

	@Test
	public void testUpdate() {
		final Customer customer = new Customer("jordan", "harrison");
		final Item item = new Item("laptop", 10.0);
		final List<Item> items = new ArrayList<>();
		items.add(item);		
		final Order updated = new Order(1L, customer, items);
		assertEquals(updated, DAO.update(updated));

	}

	@Test
	public void testDelete() {
		assertEquals(1, DAO.delete(1));
	}

}

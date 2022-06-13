package com.qa.ims.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.qa.ims.persistence.dao.CustomerDAO;
import com.qa.ims.persistence.dao.ItemDAO;
import com.qa.ims.persistence.dao.OrderDAO;
import com.qa.ims.persistence.domain.Order;
import com.qa.ims.persistence.domain.Item;
import com.qa.ims.persistence.domain.Customer;
import com.qa.ims.utils.Utils;

public class OrderController implements CrudController<Order>{
	public static final Logger LOGGER = LogManager.getLogger();

	private OrderDAO orderDAO;
	private CustomerDAO customerDAO = new CustomerDAO();
	private ItemDAO itemDAO = new ItemDAO();
	private Utils utils;

	public OrderController(OrderDAO orderDAO, Utils utils) {
		super();
		this.orderDAO = orderDAO;
		this.utils = utils;
	}

	/**
	 * Reads all orders to the logger
	 */
	@Override
	public List<Order> readAll() {
		List<Order> customers = orderDAO.readAll();
		for (Order customer : customers) {
			LOGGER.info(customer);
		}
		return customers;
	}

	/**
	 * Creates a order by taking in user input
	 */
	@Override
	public Order create() {
		LOGGER.info("Please enter a customer id");
		Long customer_id = utils.getLong();
		Customer customer = customerDAO.read(customer_id);
		if(customer == null) {
			LOGGER.info("Customer Doesn't exists");
			return null;
		}
		LOGGER.info("Please enter number of items");
		Long count = utils.getLong();
		List<Item> items = new ArrayList<Item>();
		for(int i = 1; i <= count; i++) {
			LOGGER.info("Please enter id of item "+i);
			Long id = utils.getLong();
			Item item = itemDAO.read(id);
			if(item == null) {
				LOGGER.info("This item doesn't exists. Enter correct one");
				i--;
			}else {
				items.add(item);
			}
		}
		Order order = orderDAO.create(new Order(customer, items));
		LOGGER.info("Order created");
		return order;
	}

	/**
	 * Updates an existing order by taking in user input
	 */
	@Override
	public Order update() {
		LOGGER.info("Please enter the id of the order you would like to select");
		Long order_id = utils.getLong();
		LOGGER.info("What would you like to do with the order?");
		do {
			LOGGER.info("ADD: Add Item to an order");
			LOGGER.info("DELETE: Delete Item from an order");
			LOGGER.info("COST: Calculate Cost of an order");
			LOGGER.info("RETURN: To return to perform action");
			String action = utils.getString();	
			switch(action.toUpperCase()) {
				case "ADD":
					LOGGER.info("Please enter the id of the item you would like to add");
					Long item_id = utils.getLong();
					Item item = itemDAO.read(item_id);
					if(item != null) {
						orderDAO.addOrderItem(order_id, item_id);
					}else {
						LOGGER.info("Item Doesn't exist.");
					}
					break;
				case "DELETE":
					LOGGER.info("Please enter the id of the item you would like to delete");
					item_id = utils.getLong();
					item = itemDAO.read(item_id);
					if(item != null) {
						orderDAO.deleteOrderItem(order_id, item_id);
					}else {
						LOGGER.info("Item Doesn't exist.");
					}
					break;
				case "COST":
					LOGGER.info("Total Cost of this order is: " + orderDAO.calculateCost(order_id));
					break;
				case "RETURN":
					return null;
				default:
					break;			
			}
		}while(true);
	}

	/**
	 * Deletes an existing customer by the id of the customer
	 * 
	 * @return
	 */
	@Override
	public int delete() {
		LOGGER.info("Please enter the id of the customer you would like to delete");
		Long id = utils.getLong();
		return orderDAO.delete(id);
	}
}

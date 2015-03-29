package hello;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customers")
public class CustomerController {
	private static boolean isFirstTime = true;

	/*
	@RequestMapping("/")
	public String index() {
		return "Greetings from Spring Boot Hello world! @ " + new Date();
	}
	*/

	@Autowired
	JdbcTemplate jdbcTemplate;

	@RequestMapping(method = RequestMethod.GET)
	public String getEmployees() throws Exception {
		if (isFirstTime) {
			System.out.println("Creating tables");
			jdbcTemplate.execute("drop table customers if exists");
			jdbcTemplate
					.execute("create table customers("
							+ "id serial, first_name varchar(255), last_name varchar(255))");

			String[] fullNames = new String[] { "John Woo", "Chandra mouli",
					"Chandra Shekhar", "John Long" };
			for (String fullname : fullNames) {
				String[] name = fullname.split(" ");
				System.out.printf("Inserting customer record for %s %s\n",
						name[0], name[1]);
				jdbcTemplate
						.update("INSERT INTO customers(first_name,last_name) values(?,?)",
								name[0], name[1]);
			}
			isFirstTime = false;
		}

		System.out.println("Querying for all customer records");
		List<Customer> results = jdbcTemplate.query(
				"select id, first_name, last_name from customers",
				new RowMapper<Customer>() {
					@Override
					public Customer mapRow(ResultSet rs, int rowNum)
							throws SQLException {
						return new Customer(rs.getLong("id"), rs
								.getString("first_name"), rs
								.getString("last_name"));
					}
				});

		StringBuilder output = new StringBuilder();
		for (Customer customer : results) {
			output.append(customer);
		}
		System.out.println(output);
		return output.toString();
	}

	@RequestMapping(method = RequestMethod.GET, value = "{name}")
	public String getEmployeeByName(@PathVariable String name) throws Exception {
		System.out
				.println("Querying for customer records where name = " + name);
		List<Customer> results = jdbcTemplate
				.query("select id, first_name, last_name from customers where first_name = ?",
						new Object[] { name }, new RowMapper<Customer>() {
							@Override
							public Customer mapRow(ResultSet rs, int rowNum)
									throws SQLException {
								return new Customer(rs.getLong("id"), rs
										.getString("first_name"), rs
										.getString("last_name"));
							}
						});

		StringBuilder output = new StringBuilder();
		for (Customer customer : results) {
			output.append(customer);
		}
		System.out.println(output);
		return output.toString();
	}
}

package service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import model.Calculation;

@Stateless
@Path("/calc")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CalculationService {
	
	@PersistenceContext(unitName="hello")
	private EntityManager em;

	public int add(int number1, int number2) {
		return number1 + number2;
	}

	public int subtract(int number1, int number2) {
		return number1 - number2;
	}

	public int multiply(int number1, int number2) {
		return number1 * number2;
	}

	public int divide(int number1, int number2) {
		return number1 / number2;
	}
	
	@GET
	public String test() {
		return "Hello World!";
	}
	
	@POST
	public Response calculate(Calculation calculation) {
		int result = 0;
		switch (calculation.getOperation()) {
		case "+":
			result = add(calculation.getNumber1(), calculation.getNumber2());
			break;
		case "-":
			result = subtract(calculation.getNumber1(), calculation.getNumber2());
			break;
		case "*":
			result = multiply(calculation.getNumber1(), calculation.getNumber2());
			break;
		case "/":
			result = divide(calculation.getNumber1(), calculation.getNumber2());
			break;
		default:
			return Response.status(500).build();
		}
		em.persist(calculation);
		return Response.ok("Result: " + result).build();
	}
	
	@GET
	@Path("/calculations")
	public Response getCalculations() {
		Query query = em.createQuery("SELECT c FROM Calculation c");
		return Response.ok(query.getResultList()).build();
	}
	
	
	@GET
	@Path("/hello")
	public String hello() {
		return "Hello World!";
	}
	
	

}

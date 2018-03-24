package controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import model.Operator;
import repository.IncidenceRepository;

@Controller
public class OperatorController {
	
	public static Operator loggedOperator = null;
	
	@Autowired
	private IncidenceRepository repo;
	
}

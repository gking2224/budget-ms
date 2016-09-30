package me.gking2224.budgetms.web.mvc;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.service.BudgetService;
import me.gking2224.common.utils.JsonUtil;

@org.springframework.web.bind.annotation.RestController
public class BudgetController {

    public static final String BUDGETS = "/budgets";
    public static final String BUDGET       = BUDGETS+"/{id}";

    private static Logger logger = LoggerFactory.getLogger(BudgetController.class);

	@Autowired
	BudgetService budgetService;
	
	@Autowired  @Qualifier("longDateTimeFormat") DateTimeFormatter dateTimeFormatter;

	@Autowired
	JsonUtil jsonUtil;

	// get budgets (all)
    @RequestMapping(value=BUDGETS, method=RequestMethod.GET)
    public ResponseEntity<List<Budget>> getAllBudgets(
    ) {
        List<Budget> findAllBudgets = budgetService.findAllBudgets();
        List<Budget> b = findAllBudgets.stream().map(this::enrichType).collect(toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<List<Budget>>(b, headers, HttpStatus.OK);
    }

	// create budget (post to budget)
    @RequestMapping(value=BUDGETS, method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> newBudget(
            @RequestBody Budget budget) {

        Budget b = budgetService.createBudget(budget);
        
        b = enrichType(b);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(b, headers, HttpStatus.OK);
    }

    // update budget (put at budgets)
    @RequestMapping(value=BUDGET, method=RequestMethod.PUT, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> updateBudget(
            @PathVariable("id") final Long id,
            @RequestBody final Budget budget) {
        Long typeId = budget.getId();
        if (typeId == null) budget.setId(id);
        else if (typeId != id)
            throw new IllegalArgumentException("Illegal attempt to change immutable field (id)");
        Budget mt = budgetService.updateBudget(budget);
        mt = enrichType(mt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(mt, headers, HttpStatus.OK);
    }

    // delete budget by id
    @RequestMapping(value=BUDGET, method=RequestMethod.DELETE, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBudget(
            @PathVariable("id") final Long id) {
        logger.debug(BUDGETS);

        budgetService.deleteBudget(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    // get budget by id
    @RequestMapping(value=BUDGET, method=RequestMethod.GET)
    public ResponseEntity<Budget> getBudget(
            @PathVariable("id") final Long id) {
        Budget b = budgetService.findBudgetById(id);
        b = enrichType(b);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(b, HttpStatus.OK);
    }

    private Budget enrichType(Budget b) {
        b.setLocation(BUDGETS + "/"+ b.getId());
        return b;
    }
}

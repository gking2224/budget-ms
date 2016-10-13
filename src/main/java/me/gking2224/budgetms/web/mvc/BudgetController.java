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
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import me.gking2224.budgetms.model.Budget;
import me.gking2224.budgetms.service.BudgetService;
import me.gking2224.common.utils.JsonUtil;
import me.gking2224.common.web.View;

@RestController
@RequestMapping(value="/budgets")
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
    @RequestMapping(value="", method=RequestMethod.GET)
    @JsonView(View.Summary.class)
    public ResponseEntity<List<Budget>> getAllBudgets(
    ) {
        List<Budget> b = budgetService.findAll();
        b = b.stream().map(this::enrichType).collect(toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(APPLICATION_JSON);
        return new ResponseEntity<List<Budget>>(b, headers, HttpStatus.OK);
    }

	// create budget (post to budget)
    @JsonView(View.Detail.class)
    @RequestMapping(value="", method=RequestMethod.POST, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> newBudget(
            @RequestBody Budget budget) {

        Budget b = budgetService.save(budget);
        
        b = enrichType(b);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(b, headers, HttpStatus.OK);
    }
    

    // update budget (put at budgets)
    @JsonView(View.Detail.class)
    @RequestMapping(value="/{id}", method=RequestMethod.PUT, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Budget> updateBudget(
            @PathVariable("id") final Long id,
            @RequestBody final Budget budget) {
        Long typeId = budget.getId();
        if (typeId == null) budget.setId(id);
        else if (typeId.longValue() != id.longValue())
            throw new IllegalArgumentException("Illegal attempt to change immutable field (id)");
        Budget mt = budgetService.update(budget);
        mt = enrichType(mt);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(mt, headers, HttpStatus.OK);
    }

    // delete budget by id
    @RequestMapping(value="/{id}", method=RequestMethod.DELETE, consumes=APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> deleteBudget(
            @PathVariable("id") final Long id) {
        logger.debug(BUDGETS);

        budgetService.delete(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Void>(headers, HttpStatus.OK);
    }

    // get budget by id
    @JsonView(View.Detail.class)
    @RequestMapping(value="/{id}", method=RequestMethod.GET)
    public ResponseEntity<Budget> getBudget(
            @PathVariable("id") final Long id) {
        Budget b = budgetService.findById(id);
        b = enrichType(b);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<Budget>(b, HttpStatus.OK);
    }

    // get budget by project id
    @JsonView(View.Detail.class)
    @RequestMapping(value="/project/{projectId}", method=RequestMethod.GET)
    public ResponseEntity<List<Budget>> getBudgetsByProjectId(
            @PathVariable("projectId") final Long projectId) {
        List<Budget> b = budgetService.findByProjectId(projectId);
        b = b.stream().map(this::enrichType).collect(toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<List<Budget>>(b, HttpStatus.OK);
    }

    // get budget by resource id
    @JsonView(View.Detail.class)
    @RequestMapping(value="/resource/{resourceId}", method=RequestMethod.GET)
    public ResponseEntity<List<Budget>> getBudgetsByResourceId(
            @PathVariable("resourceId") final Long resourceId) {
        List<Budget> b = budgetService.findByResourceId(resourceId);
        b = b.stream().map(this::enrichType).collect(toList());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new ResponseEntity<List<Budget>>(b, HttpStatus.OK);
    }

    private Budget enrichType(Budget b) {
        b.setLocation(String.format("/budgets/%s", b.getId()));
        return b;
    }
}

package br.com.snack.apiservice.steps.food;

import br.com.snack.apiservice.SnackApiServiceApplication;
import br.com.snack.apiservice.SnackApiServiceTestData;
import br.com.snack.apiservice.data.dto.food.IngredientResponse;
import br.com.snack.apiservice.data.entity.food.Ingredient;
import br.com.snack.apiservice.data.repository.IngredientRepository;
import br.com.snack.apiservice.exception.SystemException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@ContextConfiguration(classes = {SnackApiServiceApplication.class})
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class IngredientSteps {

    private final IngredientRepository ingredientRepository;
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    private final SnackApiServiceTestData testData;

    @Autowired
    public IngredientSteps(IngredientRepository ingredientRepository, MockMvc mockMvc, ObjectMapper objectMapper, SnackApiServiceTestData testData) {
        this.ingredientRepository = ingredientRepository;
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
        this.testData = testData;
    }

    @Before
    public void setUp() {
        this.testData.reset();
    }

    @After
    public void wrapUp() {
        reset(ingredientRepository);
    }

    @Given("this are the ingredients currently in the database:")
    public void thisAreTheIngredientsCurrentlyInTheDatabase(List<Ingredient> ingredients) {
        given(this.ingredientRepository.findAll()).willReturn(ingredients);

        ingredients.forEach(ingredient -> given(this.ingredientRepository.findById(eq(ingredient.getId()))).willReturn(Optional.of(ingredient)));
    }

    @When("this user requests ingredients")
    public void thisUserRequestsIngredients() throws Exception {
        MockHttpServletRequestBuilder
                mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/ingredients")
                                      .accept(MediaType.APPLICATION_JSON);


        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @When("this user request ingredient: {string}")
    public void thisUserRequestIngredient(String ingredientId) throws Exception {
        MockHttpServletRequestBuilder
                mockHttpServletRequestBuilder =
                MockMvcRequestBuilders.get("/ingredients/" + ingredientId)
                                      .accept(MediaType.APPLICATION_JSON);

        ResultActions resultActions = mockMvc.perform(mockHttpServletRequestBuilder);

        this.testData.setResultActions(resultActions);
    }

    @Then("the system will return {int} status code")
    public void theSystemWillReturnStatusCode(int status) throws Exception {
        this.testData.getResultActions()
                     .andExpect(status().is(status));
    }

    @And("the service will reply this list of ingredients: {string}")
    public void theServiceWillReplyThisListOfIngredients(String json) throws Exception {
        final TypeReference<List<IngredientResponse>> listTypeReference = new TypeReference<List<IngredientResponse>>() {
        };

        List<IngredientResponse> expectedResponse = objectMapper.readValue(json, listTypeReference);

        MvcResult mvcResult = this.testData.getResultActions()
                                           .andReturn();

        List<IngredientResponse> ingredientsListResponse = objectMapper.readValue(mvcResult.getResponse()
                                                                                           .getContentAsString(), listTypeReference);

        Assert.assertEquals(expectedResponse, ingredientsListResponse);
    }

    @And("the service will reply this ingredient: {string}")
    public void theServiceWillReplyThisIngredient(String json) throws Exception {
        IngredientResponse expectedResponse = objectMapper.readValue(json, IngredientResponse.class);
        MvcResult mvcResult = this.testData.getResultActions()
                                           .andReturn();

        IngredientResponse ingredientResponse = objectMapper.readValue(mvcResult.getResponse()
                                                                                .getContentAsString(), IngredientResponse.class);

        Assert.assertEquals(expectedResponse, ingredientResponse);
    }

    @Given("the database is offline")
    public void theDatabaseIsOffline() {
        given(this.ingredientRepository.findAll()).willThrow(SystemException.class);
        given(this.ingredientRepository.findById(any(UUID.class))).willThrow(SystemException.class);
    }

    @And("the database will be called to list ingredients")
    public void theDatabaseWillBeCalledToListIngredients() {
        then(this.ingredientRepository).should(atLeastOnce())
                                       .findAll();
    }

    @And("the database is called to find ingredient id: {string}")
    public void theDatabaseIsCalledToFindIngredientId(String ingredientId) {
        then(this.ingredientRepository).should(only())
                                       .findById(eq(UUID.fromString(ingredientId)));
    }
}

package ProjectPackage;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

public class RestAssureDemoProject {

    @Test
    public void test_01() {
        given().
                get("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1")
                .then().assertThat().body(matchesJsonSchemaInClasspath("schema.json"))
                .body("success", is(true))
                .body("deck_id", is(notNullValue()))
                .body("shuffled", is(true))
                .body("remaining", is(52));
    }

    @Test
    public void test_02() {
        String deck_id = given().
                get("https://deckofcardsapi.com/api/deck/new/shuffle/?deck_count=1")
                .then().extract().path("deck_id");
        System.out.println(deck_id);
    }

    @Test
    public void test_03() {
        System.out.println(given().get("https://deckofcardsapi.com/api/deck/new/draw/?count=52").getBody().asString());
    }

    @Test
    public void test_04(){
        given().
                get("https://deckofcardsapi.com/api/deck/new/draw/?count=52").
                then().
                assertThat().body("remaining", equalTo(0))
                .body("cards.code", hasItem("8C"))
                .body("cards.size()", is(52));
    }
}

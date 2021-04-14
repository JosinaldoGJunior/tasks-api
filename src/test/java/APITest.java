import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.BeforeClass;
import org.junit.Test;

public class APITest {

    @BeforeClass
    public static void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void deveRetornarTarefa(){
        RestAssured.given()
                .when()
                .get("/todo")
                .then()
                .statusCode(200)
        ;
    }

    @Test
    public void deveCriarTarefa(){
        RestAssured.given()
                .body("  {\"task\":\"Teste via API\", \"dueDate\":\"2022-01-01\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(202)
        ;
    }

    @Test
    public void NaoDeveCriarTarefaComDataPassada(){
        RestAssured.given()
                .body("  {\"task\":\"Teste via API\", \"dueDate\":\"2020-01-01\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(400)
                .body("message", CoreMatchers.is("Due date must not be in past"))

        ;
    }

    @Test
    public void deveRemoverTarefa(){
        Integer id = RestAssured.given()
                .body("  {\"task\":\"Teste de remocao\", \"dueDate\":\"2022-01-01\"}")
                .contentType(ContentType.JSON)
                .when()
                .post("/todo")
                .then()
                .statusCode(201)
                .extract().path("id")
        ;
        RestAssured.given()
                .when()
                    .delete("/todo/"+id)
                .then()
                    .statusCode(204)
                ;
    }

}

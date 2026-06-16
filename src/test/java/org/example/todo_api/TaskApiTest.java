package org.example.todo_api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class TaskApiTest {

    private static Long taskId;

    @BeforeAll
    static void setup() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    @Order(1)
    void createTaskShouldReturn201() {

        String body = """
                {
                  "title": "Learn Spring",
                  "description": "Practice CRUD"
                }
                """;

        Response response = given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/tasks");

        response.then()
                .statusCode(201)
                .body("id", notNullValue())
                .body("title", equalTo("Learn Spring"))
                .body("description", equalTo("Practice CRUD"))
                .body("completed", equalTo(false));

        taskId = response.jsonPath().getLong("id");
    }

    @Test
    @Order(2)
    void getTaskByIdShouldReturnTask() {

        given()
                .pathParam("id", taskId)
                .when()
                .get("/tasks/{id}")
                .then()
                .statusCode(200)
                .body("id", equalTo(taskId.intValue()))
                .body("title", equalTo("Learn Spring"));
    }

    @Test
    @Order(3)
    void getAllTasksShouldReturnTasksList() {

        given()
                .when()
                .get("/tasks")
                .then()
                .statusCode(200)
                .body("$", not(empty()));
    }

    @Test
    @Order(4)
    void updateTaskShouldReturnUpdatedTask() {

        String body = """
                {
                  "title": "Updated Task",
                  "description": "Updated Description"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", taskId)
                .body(body)
                .when()
                .put("/tasks/{id}")
                .then()
                .statusCode(200)
                .body("title", equalTo("Updated Task"))
                .body("description", equalTo("Updated Description"));
    }

    @Test
    @Order(5)
    void getNonExistingTaskShouldReturn404() {

        given()
                .pathParam("id", 999999L)
                .when()
                .get("/tasks/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(6)
    void createTaskWithEmptyTitleShouldReturn400() {

        String body = """
                {
                  "title": "",
                  "description": "Invalid task"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/tasks")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(7)
    void createTaskWithShortTitleShouldReturn400() {

        String body = """
                {
                  "title": "ab",
                  "description": "Invalid task"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .body(body)
                .when()
                .post("/tasks")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(8)
    void updateTaskWithEmptyTitleShouldReturn400() {

        String body = """
                {
                  "title": "",
                  "description": "Invalid update"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", taskId)
                .body(body)
                .when()
                .put("/tasks/{id}")
                .then()
                .statusCode(400);
    }

    @Test
    @Order(9)
    void updateNonExistingTaskShouldReturn404() {

        String body = """
                {
                  "title": "Updated",
                  "description": "Updated"
                }
                """;

        given()
                .contentType(ContentType.JSON)
                .pathParam("id", 999999L)
                .body(body)
                .when()
                .put("/tasks/{id}")
                .then()
                .statusCode(404);
    }

    @Test
    @Order(10)
    void deleteTaskShouldReturn204() {

        given()
                .pathParam("id", taskId)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(204);
    }

    @Test
    @Order(11)
    void deleteNonExistingTaskShouldReturn404() {

        given()
                .pathParam("id", 999999L)
                .when()
                .delete("/tasks/{id}")
                .then()
                .statusCode(404);
    }
}
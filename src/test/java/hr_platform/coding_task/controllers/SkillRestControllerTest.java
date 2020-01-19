package hr_platform.coding_task.controllers;

import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.SkillRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;


public class SkillRestControllerTest extends BaseRestControllerTest {
    @Autowired
    private SkillRepository skillRepository;

    public void initData() {
        skillRepository.save(new Skill(1L, "react"));
    }
    @Test
    public void testGetAllSkills(){
        Response response = given().when().get("/skill").then().statusCode(200).extract().response();
        Skill[] skills = response.getBody().as(Skill[].class);
        assertNotNull(skills);
    }

    @Test
    public void testGetSkillByName(){
        Response response = given().when().get("/skill/react").then().statusCode(200).extract().response();
        Skill[] skills = response.getBody().as(Skill[].class);
        assertNotNull(skills);
    }
    @Test
    public void testAddSkill(){
        Skill request = new Skill(2L, "angular");
        Response response = given().contentType(ContentType.JSON).body(request).when().post("/skill").then().statusCode(200).extract().response();
        Skill skill = response.getBody().as(Skill.class);
        assertNotNull(skill);
    }


}
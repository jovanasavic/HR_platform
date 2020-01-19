package hr_platform.coding_task.controllers;

import hr_platform.coding_task.models.Candidate;
import hr_platform.coding_task.models.Skill;
import hr_platform.coding_task.repositories.CandidateRepository;
import hr_platform.coding_task.repositories.SkillRepository;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertNotNull;

public class CandidateRestControllerTest extends  BaseRestControllerTest{

    @Autowired
    private SkillRepository skillRepository;

    @Autowired
    private CandidateRepository candidateRepository;

    public void initData() {
        Skill skill = new Skill(1L, "angular");
        Skill skill2 = new Skill(2L, "react");
        Set<Skill> skillSet = new HashSet<>();
        skillSet.add(skill);
        Candidate candidate = new Candidate(1L, "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        Candidate newCandidate = new Candidate(2L, "marko", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", skillSet);
        skillRepository.save(skill);
        skillRepository.save(skill2);
        candidateRepository.save(candidate);
        candidateRepository.save(newCandidate);
    }
    @Test
    public void testGetCandidateByName(){
        Response response = given().when().get("/candidate/milica").then().statusCode(200).extract().response();
        Candidate[] candidates = response.getBody().as(Candidate[].class);
        assertNotNull(candidates);
    }
    @Test
    public void testGetAllCandidates(){
        Response response = given().when().get("/candidate").then().statusCode(200).extract().response();
        Candidate[] candidates = response.getBody().as(Candidate[].class);
        assertNotNull(candidates);
    }
    @Test
    public void testAddCandidate(){
        Candidate request = new Candidate( "milica", LocalDate.of(1990, 12, 31), "+381 616266521", "milica@gmail.com", new HashSet<>());
        Response response = given().contentType(ContentType.JSON).body(request).when().post("/candidate").then().statusCode(200).extract().response();
        Candidate candidate = response.getBody().as(Candidate.class);
        assertNotNull(candidate);
    }

    @Test
    public void testUpdateCandidateSkill(){
        Response response = given().when().put("/candidate/1/updateSkills/2").then().statusCode(200).extract().response();
        Candidate candidate = response.getBody().as(Candidate.class);
        assertNotNull(candidate);
    }

    @Test
    public void testDeleteCandidateSkill(){
        Response response = given().when().put("/candidate/1/skill/1").then().statusCode(200).extract().response();
        Candidate candidate = response.getBody().as(Candidate.class);
        assertNotNull(candidate);
    }

    @Test
    public void testGetCandidatesBySkill(){
        Response response = given().when().get("/candidate/skill/angular").then().statusCode(200).extract().response();
        Candidate[] candidates = response.getBody().as(Candidate[].class);
        assertNotNull(candidates);
    }




}
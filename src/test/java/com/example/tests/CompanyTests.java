package com.example.tests;

import com.example.utils.HttpClient;
import com.example.utils.DatabaseUtils;
import io.qameta.allure.Description;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CompanyTests {

    @BeforeEach
    public void setUp() {

        System.out.println("Setting up before each test");
    }

    @AfterEach
    public void tearDown() {

        System.out.println("Tearing down after each test");
    }

    @Test
    @Description("Проверить, что список компаний фильтруется по параметру active")
    public void testFilterCompaniesByActive() throws IOException {
        String response = HttpClient.get("companies?active=true");
        System.out.println("Response: " + response);

        assertTrue(response.contains("\"active\":true"), "Фильтрация по активным компаниям не работает");
    }

    @Test
    @Description("Проверить создание сотрудника в несуществующей компании")
    public void testCreateEmployeeInNonExistentCompany() throws IOException {
        String json = "{ \"name\": \"Test Employee\", \"companyId\": 99999 }";
        String response = HttpClient.post("employees", json);
        System.out.println("Response: " + response);

        assertTrue(response.contains("Company not found"), "Создание сотрудника в несуществующей компании не должно быть успешным");
    }

    @Test
    @Description("Проверить, что неактивный сотрудник не отображается в списке")
    public void testInactiveEmployeeNotDisplayed() throws IOException {
        String response = HttpClient.get("employees?active=false");
        System.out.println("Response: " + response);

        assertTrue(!response.contains("\"active\":true"), "Неактивный сотрудник отображается в списке");
    }

    @Test
    @Description("Проверить, что у удаленной компании проставляется в БД поле deletedAt")
    public void testDeletedCompanyHasDeletedAtField() throws IOException {
        String json = "{ \"name\": \"Test Company\", \"active\": true }";
        String createResponse = HttpClient.post("companies", json);
        String companyId = extractCompanyId(createResponse);
        HttpClient.post("companies/" + companyId + "/delete", "");
        boolean isDeleted = DatabaseUtils.isCompanyDeleted(companyId);
        System.out.println("Company deleted status: " + isDeleted);

        assertTrue(isDeleted, "Поле deletedAt не установлено для удаленной компании");
    }

    private String extractCompanyId(String response) {

        return "extracted_company_id";
    }
}


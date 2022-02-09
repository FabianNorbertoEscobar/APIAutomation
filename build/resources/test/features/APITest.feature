Feature: Ejemplo de Request para Udemy

@API
Scenario: Prueba GET al endpoint.
    Given I send a GET request to the https://api.github.com URI
    Then I get a 200 status code

Scenario: Validar la cantidad de entradas en la respúesta.
    Given I send a GET request to th https://jsonplaceholder.typicode.com URI
    Then I validate there are 10 items on the /users endpoint


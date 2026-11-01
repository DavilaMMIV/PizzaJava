package xyz.diego.holamundospring01;

import java.net.URI;
import java.net.http.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.beans.factory.annotation.Autowired;
import jakarta.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class Holamundospring01ApplicationTests {
    @LocalServerPort
    int port;
    @Autowired
    EntityManager entityManager;
    final HttpClient client = HttpClient.newHttpClient();

    @ParameterizedTest
    @ValueSource(strings = {"pizzas", "tamanos", "bebidas", "clientes"})
    void crudCompleto(String recurso) throws Exception {
        String datos = recurso.equals("clientes")
                ? "{\"nombre\":\"Ana\",\"telefono\":\"5512345678\",\"correo\":\"ana@example.com\",\"direccion\":\"Calle 1\"}"
                : "{\"nombre\":\"Original\",\"descripcion\":\"Descripcion\",\"precio\":120}";
        String base = "/api/" + recurso;
        assertEquals(400, request("POST", base, "{}").statusCode());
        var creado = request("POST", base, datos);
        assertEquals(201, creado.statusCode(), creado.body());
        String location = creado.headers().firstValue("Location").orElseThrow();
        int id = Integer.parseInt(location.substring(location.lastIndexOf('/') + 1));
        assertEquals(200, request("GET", location, null).statusCode());
        assertTrue(request("GET", "/api/v1/" + recurso, null).body().contains("\"id\":" + id));
        String actualizado = datos.replace("Original", "Actualizado").replace("Ana", "Actualizado");
        var respuesta = request("PUT", location, actualizado);
        assertEquals(200, respuesta.statusCode());
        assertTrue(respuesta.body().contains("Actualizado"));
        assertTrue(request("GET", location, null).body().contains("Actualizado"));
        assertEquals(204, request("DELETE", location, null).statusCode());
        assertEquals(404, request("GET", location, null).statusCode());
        assertEquals("[]", request("GET", base, null).body());
        assertEquals(404, request("PUT", location, datos).statusCode());
        assertEquals(404, request("DELETE", location, null).statusCode());
        assertEquals(Boolean.FALSE, entityManager.createNativeQuery(
                "select activo from " + recurso + " where id = :id", Boolean.class)
                .setParameter("id", id).getSingleResult());
    }

    private HttpResponse<String> request(String method, String path, String body) throws Exception {
        var request = HttpRequest.newBuilder(URI.create("http://localhost:" + port + path))
                .header("Content-Type", "application/json")
                .method(method, body == null ? HttpRequest.BodyPublishers.noBody()
                        : HttpRequest.BodyPublishers.ofString(body)).build();
        return client.send(request, HttpResponse.BodyHandlers.ofString());
    }
}

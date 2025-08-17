package br.hendrew.goldenraspberryapi;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

@SpringBootTest
@ActiveProfiles("test")
class GoldenRaspberryApiApplicationTests {

    @Test
    void testMainMethod() {
        assertDoesNotThrow(() -> GoldenRaspberryApiApplication.main(new String[]{}));
    }

}

package com.example.gameBackend.domain;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerTest {

    private static final Validator VALIDATOR =
            Validation.byDefaultProvider()
                    .configure()
                    .messageInterpolator(new ParameterMessageInterpolator())
                    .buildValidatorFactory()
                    .getValidator();

    @Test
    public void testPlayerValidation_1() {
        Player player = new Player(-1f, -1);  // Set invalid values

        Set<ConstraintViolation<Player>> violations = VALIDATOR.validate(player);

        assertFalse(violations.isEmpty());  // Expect validation errors

        for (ConstraintViolation<Player> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testPlayerValidation_2() {
        Player player = new Player(1000f, 1000);  // Set invalid values

        Set<ConstraintViolation<Player>> violations = VALIDATOR.validate(player);

        assertFalse(violations.isEmpty());  // Expect validation errors

        for (ConstraintViolation<Player> violation : violations) {
            System.out.println(violation.getMessage());
        }
    }

    @Test
    public void testPlayerValidation_3() {
        Player player = new Player(10f, 10);  // Set valid values

        Set<ConstraintViolation<Player>> violations = VALIDATOR.validate(player);

        assertTrue(violations.isEmpty());  // Do not expect validation errors
    }

}

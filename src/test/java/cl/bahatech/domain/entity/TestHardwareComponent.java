package cl.bahatech.domain.entity;


import cl.bahatech.domain.exception.InvalidHardwareComponentBrandException;
import cl.bahatech.domain.exception.InvalidHardwareComponentModelException;
import cl.bahatech.domain.exception.InvalidHardwareComponentQuantityException;
import cl.bahatech.domain.exception.InvalidHardwareComponentSetupException;
import cl.bahatech.domain.exception.InvalidHardwareComponentTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestHardwareComponent {

    private HardwareComponent component;
    private Setup validSetup;
    private ComponentType validType;

    @BeforeEach
    void setUp() {
        component = new HardwareComponent();
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "password123");
        validSetup = new Setup(1L, "Gaming Rig", "High end specs", user);
        validType = new ComponentType(1L, "GPU", "Graphics processing unit");
    }

    @Test
    void shouldThrowInvalidHardwareComponentTypeExceptionWhenTypeIsNull() {

        ComponentType nullType = null;

        InvalidHardwareComponentTypeException exception = assertThrows(
                InvalidHardwareComponentTypeException.class,
                () -> component.validateType(nullType));

        assertEquals("Component must be assigned to a valid type", exception.getMessage());
    }

    @Test
    void shouldValidateTypeSuccessfully() {

        assertDoesNotThrow(() -> component.validateType(validType));
    }

    @Test
    void shouldThrowInvalidHardwareComponentBrandExceptionWhenBrandIsNull() {

        String nullBrand = null;

        InvalidHardwareComponentBrandException exception = assertThrows(
                InvalidHardwareComponentBrandException.class,
                () -> component.validateBrand(nullBrand));

        assertEquals("Invalid component brand", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidHardwareComponentBrandExceptionWhenBrandIsBlank() {

        String blankBrand = "    ";

        InvalidHardwareComponentBrandException exception = assertThrows(
                InvalidHardwareComponentBrandException.class,
                () -> component.validateBrand(blankBrand));

        assertEquals("Invalid component brand", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"NVIDIA", "AMD", "Intel", "Corsair"})
    void shouldValidateBrandSuccessfully(String brand) {

        assertDoesNotThrow(() -> component.validateBrand(brand));
    }

    @Test
    void shouldThrowInvalidHardwareComponentModelExceptionWhenModelIsNull() {

        String nullModel = null;

        InvalidHardwareComponentModelException exception = assertThrows(
                InvalidHardwareComponentModelException.class,
                () -> component.validateModel(nullModel));

        assertEquals("Invalid component model", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidHardwareComponentModelExceptionWhenModelIsBlank() {

        String blankModel = "   ";

        InvalidHardwareComponentModelException exception = assertThrows(
                InvalidHardwareComponentModelException.class,
                () -> component.validateModel(blankModel));

        assertEquals("Invalid component model", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"RTX 4090", "Ryzen 9 7950X", "Vengeance LPX 32GB"})
    void shouldValidateModelSuccessfully(String model) {

        assertDoesNotThrow(() -> component.validateModel(model));
    }

    @Test
    void shouldThrowInvalidHardwareComponentQuantityExceptionWhenQuantityIsNull() {

        Integer nullQuantity = null;

        InvalidHardwareComponentQuantityException exception = assertThrows(
                InvalidHardwareComponentQuantityException.class,
                () -> component.validateQuantity(nullQuantity));

        assertEquals("Quantity cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50})
    void shouldThrowInvalidHardwareComponentQuantityExceptionWhenQuantityIsZeroOrLess(Integer invalidQuantity) {

        InvalidHardwareComponentQuantityException exception = assertThrows(
                InvalidHardwareComponentQuantityException.class,
                () -> component.validateQuantity(invalidQuantity));

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 100})
    void shouldValidateQuantitySuccessfully(Integer validQuantity) {

        assertDoesNotThrow(() -> component.validateQuantity(validQuantity));
    }

    @Test
    void shouldThrowInvalidHardwareComponentSetupExceptionWhenSetupIsNull() {

        Setup nullSetup = null;

        InvalidHardwareComponentSetupException exception = assertThrows(
                InvalidHardwareComponentSetupException.class,
                () -> component.validateSetup(nullSetup));

        assertEquals("Component must be assigned to a valid setup", exception.getMessage());
    }

    @Test
    void shouldValidateSetupSuccessfully() {

        assertDoesNotThrow(() -> component.validateSetup(validSetup));
    }

    @Test
    void shouldCreateHardwareComponentWithConstructor() {

        Long id = 1L;
        String brand = "NVIDIA";
        String model = "RTX 4090";
        Integer quantity = 1;

        HardwareComponent newComponent = new HardwareComponent(id, validType, brand, model, quantity, validSetup);

        assertAll(
                () -> assertEquals(id, newComponent.getId()),
                () -> assertEquals(validType, newComponent.getType()),
                () -> assertEquals(brand, newComponent.getBrand()),
                () -> assertEquals(model, newComponent.getModel()),
                () -> assertEquals(quantity, newComponent.getQuantity()),
                () -> assertEquals(validSetup, newComponent.getSetup())
        );
    }

    @Test
    void shouldCreateHardwareComponentWithEmptyConstructor() {

        Long id = 2L;
        ComponentType type = new ComponentType(2L, "RAM", "Random access memory");
        String brand = "Corsair";
        String model = "32GB DDR5";
        Integer quantity = 2;

        component.setId(id);
        component.setType(type);
        component.setBrand(brand);
        component.setModel(model);
        component.setQuantity(quantity);
        component.setSetup(validSetup);

        assertAll(
                () -> assertEquals(id, component.getId()),
                () -> assertEquals(type, component.getType()),
                () -> assertEquals(brand, component.getBrand()),
                () -> assertEquals(model, component.getModel()),
                () -> assertEquals(quantity, component.getQuantity()),
                () -> assertEquals(validSetup, component.getSetup())
        );
    }
}

package hardwarecomponent;

import domain.HardwareComponent;
import domain.RigUser;
import domain.Setup;
import exception.hardwarecomponent.InvalidHardwareComponentBrandException;
import exception.hardwarecomponent.InvalidHardwareComponentModelException;
import exception.hardwarecomponent.InvalidHardwareComponentQuantityException;
import exception.hardwarecomponent.InvalidHardwareComponentSetupException;
import exception.hardwarecomponent.InvalidHardwareComponentTypeException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

public class TestHardwareComponent {

    private HardwareComponent component;
    private Setup validSetup;

    @BeforeEach
    void setUp() {
        component = new HardwareComponent();
        RigUser user = new RigUser(1L, "Alejandro", "ale@test.com", "password123");
        validSetup = new Setup(1L, "Gaming Rig", "High end specs", user);
    }

    @Test
    void shouldThrowInvalidHardwareComponentTypeExceptionWhenTypeIsNull() {
        // Arrange
        String nullType = null;

        // Act and Assert
        InvalidHardwareComponentTypeException exception = assertThrows(
                InvalidHardwareComponentTypeException.class,
                () -> component.validateType(nullType));

        assertEquals("Invalid component type", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidHardwareComponentTypeExceptionWhenTypeIsBlank() {
        // Arrange
        String blankType = "   ";

        // Act and Assert
        InvalidHardwareComponentTypeException exception = assertThrows(
                InvalidHardwareComponentTypeException.class,
                () -> component.validateType(blankType));

        assertEquals("Invalid component type", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"GPU", "CPU", "Motherboard", "RAM"})
    void shouldValidateTypeSuccessfully(String type) {
        // Act and Assert
        assertDoesNotThrow(() -> component.validateType(type));
    }

    @Test
    void shouldThrowInvalidHardwareComponentBrandExceptionWhenBrandIsNull() {
        // Arrange
        String nullBrand = null;

        // Act and Assert
        InvalidHardwareComponentBrandException exception = assertThrows(
                InvalidHardwareComponentBrandException.class,
                () -> component.validateBrand(nullBrand));

        assertEquals("Invalid component brand", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidHardwareComponentBrandExceptionWhenBrandIsBlank() {
        // Arrange
        String blankBrand = "    ";

        // Act and Assert
        InvalidHardwareComponentBrandException exception = assertThrows(
                InvalidHardwareComponentBrandException.class,
                () -> component.validateBrand(blankBrand));

        assertEquals("Invalid component brand", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"NVIDIA", "AMD", "Intel", "Corsair"})
    void shouldValidateBrandSuccessfully(String brand) {
        // Act and Assert
        assertDoesNotThrow(() -> component.validateBrand(brand));
    }

    @Test
    void shouldThrowInvalidHardwareComponentModelExceptionWhenModelIsNull() {
        // Arrange
        String nullModel = null;

        // Act and Assert
        InvalidHardwareComponentModelException exception = assertThrows(
                InvalidHardwareComponentModelException.class,
                () -> component.validateModel(nullModel));

        assertEquals("Invalid component model", exception.getMessage());
    }

    @Test
    void shouldThrowInvalidHardwareComponentModelExceptionWhenModelIsBlank() {
        // Arrange
        String blankModel = "   ";

        // Act and Assert
        InvalidHardwareComponentModelException exception = assertThrows(
                InvalidHardwareComponentModelException.class,
                () -> component.validateModel(blankModel));

        assertEquals("Invalid component model", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(strings = {"RTX 4090", "Ryzen 9 7950X", "Vengeance LPX 32GB"})
    void shouldValidateModelSuccessfully(String model) {
        // Act and Assert
        assertDoesNotThrow(() -> component.validateModel(model));
    }

    @Test
    void shouldThrowInvalidHardwareComponentQuantityExceptionWhenQuantityIsNull() {
        // Arrange
        Integer nullQuantity = null;

        // Act and Assert
        InvalidHardwareComponentQuantityException exception = assertThrows(
                InvalidHardwareComponentQuantityException.class,
                () -> component.validateQuantity(nullQuantity));

        assertEquals("Quantity cannot be null", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -50})
    void shouldThrowInvalidHardwareComponentQuantityExceptionWhenQuantityIsZeroOrLess(Integer invalidQuantity) {
        // Act and Assert
        InvalidHardwareComponentQuantityException exception = assertThrows(
                InvalidHardwareComponentQuantityException.class,
                () -> component.validateQuantity(invalidQuantity));

        assertEquals("Quantity must be greater than zero", exception.getMessage());
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 100})
    void shouldValidateQuantitySuccessfully(Integer validQuantity) {
        // Act and Assert
        assertDoesNotThrow(() -> component.validateQuantity(validQuantity));
    }

    @Test
    void shouldThrowInvalidHardwareComponentSetupExceptionWhenSetupIsNull() {
        // Arrange
        Setup nullSetup = null;

        // Act and Assert
        InvalidHardwareComponentSetupException exception = assertThrows(
                InvalidHardwareComponentSetupException.class,
                () -> component.validateSetup(nullSetup));

        assertEquals("Component must be assigned to a valid setup", exception.getMessage());
    }

    @Test
    void shouldValidateSetupSuccessfully() {
        // Act and Assert
        assertDoesNotThrow(() -> component.validateSetup(validSetup));
    }

    @Test
    void shouldCreateHardwareComponentWithConstructor() {
        // Arrange
        Long id = 1L;
        String type = "GPU";
        String brand = "NVIDIA";
        String model = "RTX 4090";
        Integer quantity = 1;

        // Act
        HardwareComponent newComponent = new HardwareComponent(id, type, brand, model, quantity, validSetup);

        // Assert
        assertAll(
                () -> assertEquals(id, newComponent.getId()),
                () -> assertEquals(type, newComponent.getType()),
                () -> assertEquals(brand, newComponent.getBrand()),
                () -> assertEquals(model, newComponent.getModel()),
                () -> assertEquals(quantity, newComponent.getQuantity()),
                () -> assertEquals(validSetup, newComponent.getSetup())
        );
    }

    @Test
    void shouldCreateHardwareComponentWithEmptyConstructor() {
        // Arrange
        Long id = 2L;
        String type = "RAM";
        String brand = "Corsair";
        String model = "32GB DDR5";
        Integer quantity = 2;

        // Act
        component.setId(id);
        component.setType(type);
        component.setBrand(brand);
        component.setModel(model);
        component.setQuantity(quantity);
        component.setSetup(validSetup);

        // Assert
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
package ua.com.alevel.hw2.model.manufacturingmaterial;

import lombok.Getter;
import lombok.Setter;
import java.util.Objects;

@Getter
@Setter
public class ManufacturingMaterial {
    private String material;
    private String color;

    public ManufacturingMaterial(String material, String color) {
        this.material = material;
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ManufacturingMaterial that = (ManufacturingMaterial) o;
        return Objects.equals(material, that.material) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(material, color);
    }

    @Override
    public String toString() {
        return "ManufacturingMaterial{" +
                "material='" + material + '\'' +
                ", color='" + color + '\'' +
                '}';
    }
}

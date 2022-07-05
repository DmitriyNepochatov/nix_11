package ua.com.alevel.hw2.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class Plane
{
    protected String id;
    protected PlaneBrand brand;
    protected String model;
    protected long price;
}

package utils;

import java.math.BigDecimal;

import static java.math.BigDecimal.ROUND_HALF_UP;
import static java.math.BigDecimal.valueOf;

public class TriangleCalculator {

    public BigDecimal calculatePerimeter(BigDecimal A, BigDecimal B, BigDecimal C){
        return A.add(B).add(C);
    }

    public BigDecimal calculateArea(BigDecimal a, BigDecimal b, BigDecimal c){
        double A = a.doubleValue();
        double B = b.doubleValue();
        double C = c.doubleValue();
        double halfPerimeter = (A+B+C)/2;
        return valueOf(Math.sqrt(halfPerimeter * (halfPerimeter - A) * (halfPerimeter - B) * (halfPerimeter - C))).setScale(2, ROUND_HALF_UP);
    }
}

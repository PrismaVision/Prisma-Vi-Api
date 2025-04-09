package com.api.prisma_vi.colors;

import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ColorsService {
    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([0-9A-F]{6}|[0-9A-F]{3})$");

    public void validateHexColor(String hexColor) {
        if (hexColor == null || !HEX_PATTERN.matcher(hexColor.toUpperCase()).matches()) {
            throw new InvalidHexadecimalException("Invalid hexadecimal color code");
        }
    }

    public double calculateLuminance(int red, int green, int blue) {
        return 0.2126 * channel(red) + 0.7152 * channel(green) + 0.0722 * channel(blue);
    }

    private double channel(int c) {
        double cNorm = c / 255.0;
        if (cNorm <= 0.03928) {
            return cNorm / 12.92;
        } else {
            return Math.pow((cNorm + 0.055) / 1.055, 2.4);
        }
    }

}

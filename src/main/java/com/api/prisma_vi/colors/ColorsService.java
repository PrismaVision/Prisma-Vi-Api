package com.api.prisma_vi.colors;

import com.api.prisma_vi.apiError.InvalidHexadecimalException;
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
}

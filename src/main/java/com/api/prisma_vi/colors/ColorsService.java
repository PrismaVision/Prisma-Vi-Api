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

    public int[] hexToRGB(String hex) {
        hex = hex.replace("#", "").toUpperCase();
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0)
                    + hex.charAt(1) + hex.charAt(1)
                    + hex.charAt(2) + hex.charAt(2);
        }
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new int[]{r, g, b};
    }

    public String rgbToHSL(int r, int g, int b) {
        double rNorm = r / 255.0;
        double gNorm = g / 255.0;
        double bNorm = b / 255.0;

        double max = Math.max(rNorm, Math.max(gNorm, bNorm));
        double min = Math.min(rNorm, Math.max(gNorm, bNorm));
        double h, s, l;
        h = s = l = (max + min) / 2;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            double d = max - min;
            s = l > 0.5 ? d / (2.0 - max - min) : d / (max + min);

            if (max == rNorm) {
                h = ((gNorm - bNorm) / d + (gNorm < bNorm ? 6 : 0));
            } else if (max == gNorm) {
                h = ((bNorm - rNorm) / d + 2);
            } else {
                h = ((rNorm - gNorm) / d + 4);
            }

            h /= 6.0;
        }

        return String.format("%d, %d%%, %d%%",
                (int) Math.round(h * 360),
                (int) Math.round(s * 100),
                (int) Math.round(l * 100)
        );
    }

    public double calculateContrast(double luminance1, double luminance2) {
        double l1 = Math.max(luminance1, luminance2);
        double l2 = Math.min(luminance1, luminance2);
        return (l1 + 0.05) / (l2 + 0.05);
    }
}

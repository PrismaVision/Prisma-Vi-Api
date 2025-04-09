package com.api.prisma_vi.color;

import com.api.prisma_vi.utils.apiError.InvalidHexadecimalException;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

@Service
public class ColorService {
    private static final Pattern HEX_PATTERN = Pattern.compile("^#?([0-9A-F]{6}|[0-9A-F]{3})$");
    private static final double MIN_CONTRAST_RATIO = 4.5;

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

    public Rgb hexToRGB(String hex) {
        hex = hex.replace("#", "").toUpperCase();
        if (hex.length() == 3) {
            hex = "" + hex.charAt(0) + hex.charAt(0)
                    + hex.charAt(1) + hex.charAt(1)
                    + hex.charAt(2) + hex.charAt(2);
        }
        int r = Integer.parseInt(hex.substring(0, 2), 16);
        int g = Integer.parseInt(hex.substring(2, 4), 16);
        int b = Integer.parseInt(hex.substring(4, 6), 16);
        return new Rgb(r, g, b);
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

    private double calculateContrast(double luminance1, double luminance2) {
        double l1 = Math.max(luminance1, luminance2);
        double l2 = Math.min(luminance1, luminance2);
        return (l1 + 0.05) / (l2 + 0.05);
    }

    public boolean isAccessibleOnWhite(int r, int g, int b) {
        double colorLuminance = calculateLuminance(r, g, b);
        double whiteLuminance = calculateLuminance(255, 255, 255); // sempre 1.0
        double contrast = calculateContrast(colorLuminance, whiteLuminance);
        return contrast >= MIN_CONTRAST_RATIO;
    }

    public boolean isAccessibleOnBlack(int r, int g, int b) {
        double colorLuminance = calculateLuminance(r, g, b);
        double blackLuminance = calculateLuminance(0, 0, 0); // sempre 0.0
        double contrast = calculateContrast(colorLuminance, blackLuminance);
        return contrast >= MIN_CONTRAST_RATIO;
    }

    private String rgbToHex(int r, int g, int b) {
        return String.format("#%02X%02X%02X", r, g, b);
    }

    private int[] hslToRgb(double h, double s, double l) {
        double c = (1 - Math.abs(2 * l - 1)) * s;
        double x = c * (1 - Math.abs((h / 60) % 2 - 1));
        double m = l - c / 2;

        double r = 0, g = 0, b = 0;

        if (h < 60) {
            r = c; g = x; b = 0;
        } else if (h < 120) {
            r = x; g = c; b = 0;
        } else if (h < 180) {
            r = 0; g = c; b = x;
        } else if (h < 240) {
            r = 0; g = x; b = c;
        } else if (h < 300) {
            r = x; g = 0; b = c;
        } else {
            r = c; g = 0; b = x;
        }

        return new int[]{
                (int) Math.round((r + m) * 255),
                (int) Math.round((g + m) * 255),
                (int) Math.round((b + m) * 255)
        };
    }

    private double[] rgbToHslArray(Rgb rgb) {
        double rNorm = rgb.getRed() / 255.0, gNorm = rgb.getGreen() / 255.0, bNorm = rgb.getBlue() / 255.0;
        double max = Math.max(rNorm, Math.max(gNorm, bNorm));
        double min = Math.min(rNorm, Math.min(gNorm, bNorm));
        double h, s, l = (max + min) / 2.0;

        if (max == min) {
            h = s = 0; // achromatic
        } else {
            double d = max - min;
            s = l > 0.5 ? d / (2.0 - max - min) : d / (max + min);

            if (max == rNorm) {
                h = (gNorm - bNorm) / d + (gNorm < bNorm ? 6 : 0);
            } else if (max == gNorm) {
                h = (bNorm - rNorm) / d + 2;
            } else {
                h = (rNorm - gNorm) / d + 4;
            }

            h *= 60;
        }

        return new double[]{h, s, l};
    }

    public String getComplementaryColor(String hex) {
        Rgb rgb = hexToRGB(hex);
        double[] hsl = rgbToHslArray(rgb);

        hsl[0] = (hsl[0] + 180) % 360;

        int[] complementaryRGB = hslToRgb(hsl[0], hsl[1], hsl[2]);
        return rgbToHex(complementaryRGB[0], complementaryRGB[1], complementaryRGB[2]);
    }

    public String inferColorCategory(String hex) {
        Rgb rgb = hexToRGB(hex);
        double[] hsl = rgbToHslArray(rgb);
        double hue = hsl[0];

        if ((hue >= 0 && hue <= 30) || (hue >= 330 && hue <= 360)) return "Primária (Vermelho)";
        if (hue >= 40 && hue <= 65) return "Primária (Amarelo)";
        if (hue >= 210 && hue <= 270) return "Primária (Azul)";

        if (hue > 30 && hue < 40) return "Secundária (Laranja)";
        if (hue >= 90 && hue <= 150) return "Secundária (Verde)";
        if (hue >= 270 && hue < 330) return "Secundária (Roxo)";

        return "Terciária";
    }

}

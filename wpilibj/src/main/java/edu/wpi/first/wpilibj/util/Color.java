// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package edu.wpi.first.wpilibj.util;

import edu.wpi.first.math.MathUtil;
import java.util.Objects;

/**
 * Represents colors.
 *
 * <p>Limited to 12 bits of precision.
 */
@SuppressWarnings("MemberName")
public class Color {
  private static final double kPrecision = Math.pow(2, -12);

  public final double red;
  public final double green;
  public final double blue;

  /**
   * Constructs a Color.
   *
   * @param red Red value (0-1)
   * @param green Green value (0-1)
   * @param blue Blue value (0-1)
   */
  public Color(double red, double green, double blue) {
    this.red = roundAndClamp(red);
    this.green = roundAndClamp(green);
    this.blue = roundAndClamp(blue);
  }

  /**
   * Constructs a Color from a Color8Bit.
   *
   * @param color The color
   */
  public Color(Color8Bit color) {
    this(color.red / 255.0, color.green / 255.0, color.blue / 255.0);
  }

  /**
   * Creates a Color from HSV values.
   *
   * @param h The h value [0-180]
   * @param s The s value [0-255]
   * @param v The v value [0-255]
   * @return The color
   */
  @SuppressWarnings("ParameterName")
  public static Color fromHSV(int h, int s, int v) {
    if (s == 0) {
      return new Color(v / 255.0, v / 255.0, v / 255.0);
    }

    final int region = h / 30;
    final int remainder = (h - (region * 30)) * 6;

    final int p = (v * (255 - s)) >> 8;
    final int q = (v * (255 - ((s * remainder) >> 8))) >> 8;
    final int t = (v * (255 - ((s * (255 - remainder)) >> 8))) >> 8;

    switch (region) {
      case 0:
        return new Color(v / 255.0, t / 255.0, p / 255.0);
      case 1:
        return new Color(q / 255.0, v / 255.0, p / 255.0);
      case 2:
        return new Color(p / 255.0, v / 255.0, t / 255.0);
      case 3:
        return new Color(p / 255.0, q / 255.0, v / 255.0);
      case 4:
        return new Color(t / 255.0, p / 255.0, v / 255.0);
      default:
        return new Color(v / 255.0, p / 255.0, q / 255.0);
    }
  }

  @Override
  public boolean equals(Object other) {
    if (this == other) {
      return true;
    }
    if (other == null || getClass() != other.getClass()) {
      return false;
    }

    Color color = (Color) other;
    return Double.compare(color.red, red) == 0
        && Double.compare(color.green, green) == 0
        && Double.compare(color.blue, blue) == 0;
  }

  @Override
  public int hashCode() {
    return Objects.hash(red, green, blue);
  }

  @Override
  public String toString() {
    return "Color{" + "red=" + red + ", green=" + green + ", blue=" + blue + '}';
  }

  private static double roundAndClamp(double value) {
    final var rounded = Math.round((value + kPrecision / 2) / kPrecision) * kPrecision;
    return MathUtil.clamp(rounded, 0.0, 1.0);
  }

  /*
   * FIRST Colors
   */

  /** 0x1560BD. */
  public static final Color kDenim = new Color(0.0823529412, 0.376470589, 0.7411764706);

  /** 0x0066B3. */
  public static final Color kFirstBlue = new Color(0.0, 0.4, 0.7019607844);

  /** 0xED1C24. */
  public static final Color kFirstRed = new Color(0.9294117648, 0.1098039216, 0.1411764706);

  /*
   * Standard Colors
   */

  /** 0xF0F8FF. */
  public static final Color kAliceBlue = new Color(0.9411765f, 0.972549f, 1.0f);

  /** 0xFAEBD7. */
  public static final Color kAntiqueWhite = new Color(0.98039216f, 0.92156863f, 0.84313726f);

  /** 0x00FFFF. */
  public static final Color kAqua = new Color(0.0f, 1.0f, 1.0f);

  /** 0x7FFFD4. */
  public static final Color kAquamarine = new Color(0.49803922f, 1.0f, 0.83137256f);

  /** 0xF0FFFF. */
  public static final Color kAzure = new Color(0.9411765f, 1.0f, 1.0f);

  /** 0xF5F5DC. */
  public static final Color kBeige = new Color(0.9607843f, 0.9607843f, 0.8627451f);

  /** 0xFFE4C4. */
  public static final Color kBisque = new Color(1.0f, 0.89411765f, 0.76862746f);

  /** 0x000000. */
  public static final Color kBlack = new Color(0.0f, 0.0f, 0.0f);

  /** 0xFFEBCD. */
  public static final Color kBlanchedAlmond = new Color(1.0f, 0.92156863f, 0.8039216f);

  /** 0x0000FF. */
  public static final Color kBlue = new Color(0.0f, 0.0f, 1.0f);

  /** 0x8A2BE2. */
  public static final Color kBlueViolet = new Color(0.5411765f, 0.16862746f, 0.8862745f);

  /** 0xA52A2A. */
  public static final Color kBrown = new Color(0.64705884f, 0.16470589f, 0.16470589f);

  /** 0xDEB887. */
  public static final Color kBurlywood = new Color(0.87058824f, 0.72156864f, 0.5294118f);

  /** 0x5F9EA0. */
  public static final Color kCadetBlue = new Color(0.37254903f, 0.61960787f, 0.627451f);

  /** 0x7FFF00. */
  public static final Color kChartreuse = new Color(0.49803922f, 1.0f, 0.0f);

  /** 0xD2691E. */
  public static final Color kChocolate = new Color(0.8235294f, 0.4117647f, 0.11764706f);

  /** 0xFF7F50. */
  public static final Color kCoral = new Color(1.0f, 0.49803922f, 0.3137255f);

  /** 0x6495ED. */
  public static final Color kCornflowerBlue = new Color(0.39215687f, 0.58431375f, 0.92941177f);

  /** 0xFFF8DC. */
  public static final Color kCornsilk = new Color(1.0f, 0.972549f, 0.8627451f);

  /** 0xDC143C. */
  public static final Color kCrimson = new Color(0.8627451f, 0.078431375f, 0.23529412f);

  /** 0x00FFFF. */
  public static final Color kCyan = new Color(0.0f, 1.0f, 1.0f);

  /** 0x00008B. */
  public static final Color kDarkBlue = new Color(0.0f, 0.0f, 0.54509807f);

  /** 0x008B8B. */
  public static final Color kDarkCyan = new Color(0.0f, 0.54509807f, 0.54509807f);

  /** 0xB8860B. */
  public static final Color kDarkGoldenrod = new Color(0.72156864f, 0.5254902f, 0.043137256f);

  /** 0xA9A9A9. */
  public static final Color kDarkGray = new Color(0.6627451f, 0.6627451f, 0.6627451f);

  /** 0x006400. */
  public static final Color kDarkGreen = new Color(0.0f, 0.39215687f, 0.0f);

  /** 0xBDB76B. */
  public static final Color kDarkKhaki = new Color(0.7411765f, 0.7176471f, 0.41960785f);

  /** 0x8B008B. */
  public static final Color kDarkMagenta = new Color(0.54509807f, 0.0f, 0.54509807f);

  /** 0x556B2F. */
  public static final Color kDarkOliveGreen = new Color(0.33333334f, 0.41960785f, 0.18431373f);

  /** 0xFF8C00. */
  public static final Color kDarkOrange = new Color(1.0f, 0.54901963f, 0.0f);

  /** 0x9932CC. */
  public static final Color kDarkOrchid = new Color(0.6f, 0.19607843f, 0.8f);

  /** 0x8B0000. */
  public static final Color kDarkRed = new Color(0.54509807f, 0.0f, 0.0f);

  /** 0xE9967A. */
  public static final Color kDarkSalmon = new Color(0.9137255f, 0.5882353f, 0.47843137f);

  /** 0x8FBC8F. */
  public static final Color kDarkSeaGreen = new Color(0.56078434f, 0.7372549f, 0.56078434f);

  /** 0x483D8B. */
  public static final Color kDarkSlateBlue = new Color(0.28235295f, 0.23921569f, 0.54509807f);

  /** 0x2F4F4F. */
  public static final Color kDarkSlateGray = new Color(0.18431373f, 0.30980393f, 0.30980393f);

  /** 0x00CED1. */
  public static final Color kDarkTurquoise = new Color(0.0f, 0.80784315f, 0.81960785f);

  /** 0x9400D3. */
  public static final Color kDarkViolet = new Color(0.5803922f, 0.0f, 0.827451f);

  /** 0xFF1493. */
  public static final Color kDeepPink = new Color(1.0f, 0.078431375f, 0.5764706f);

  /** 0x00BFFF. */
  public static final Color kDeepSkyBlue = new Color(0.0f, 0.7490196f, 1.0f);

  /** 0x696969. */
  public static final Color kDimGray = new Color(0.4117647f, 0.4117647f, 0.4117647f);

  /** 0x1E90FF. */
  public static final Color kDodgerBlue = new Color(0.11764706f, 0.5647059f, 1.0f);

  /** 0xB22222. */
  public static final Color kFirebrick = new Color(0.69803923f, 0.13333334f, 0.13333334f);

  /** 0xFFFAF0. */
  public static final Color kFloralWhite = new Color(1.0f, 0.98039216f, 0.9411765f);

  /** 0x228B22. */
  public static final Color kForestGreen = new Color(0.13333334f, 0.54509807f, 0.13333334f);

  /** 0xFF00FF. */
  public static final Color kFuchsia = new Color(1.0f, 0.0f, 1.0f);

  /** 0xDCDCDC. */
  public static final Color kGainsboro = new Color(0.8627451f, 0.8627451f, 0.8627451f);

  /** 0xF8F8FF. */
  public static final Color kGhostWhite = new Color(0.972549f, 0.972549f, 1.0f);

  /** 0xFFD700. */
  public static final Color kGold = new Color(1.0f, 0.84313726f, 0.0f);

  /** 0xDAA520. */
  public static final Color kGoldenrod = new Color(0.85490197f, 0.64705884f, 0.1254902f);

  /** 0x808080. */
  public static final Color kGray = new Color(0.5019608f, 0.5019608f, 0.5019608f);

  /** 0x008000. */
  public static final Color kGreen = new Color(0.0f, 0.5019608f, 0.0f);

  /** 0xADFF2F. */
  public static final Color kGreenYellow = new Color(0.6784314f, 1.0f, 0.18431373f);

  /** 0xF0FFF0. */
  public static final Color kHoneydew = new Color(0.9411765f, 1.0f, 0.9411765f);

  /** 0xFF69B4. */
  public static final Color kHotPink = new Color(1.0f, 0.4117647f, 0.7058824f);

  /** 0xCD5C5C. */
  public static final Color kIndianRed = new Color(0.8039216f, 0.36078432f, 0.36078432f);

  /** 0x4B0082. */
  public static final Color kIndigo = new Color(0.29411766f, 0.0f, 0.50980395f);

  /** 0xFFFFF0. */
  public static final Color kIvory = new Color(1.0f, 1.0f, 0.9411765f);

  /** 0xF0E68C. */
  public static final Color kKhaki = new Color(0.9411765f, 0.9019608f, 0.54901963f);

  /** 0xE6E6FA. */
  public static final Color kLavender = new Color(0.9019608f, 0.9019608f, 0.98039216f);

  /** 0xFFF0F5. */
  public static final Color kLavenderBlush = new Color(1.0f, 0.9411765f, 0.9607843f);

  /** 0x7CFC00. */
  public static final Color kLawnGreen = new Color(0.4862745f, 0.9882353f, 0.0f);

  /** 0xFFFACD. */
  public static final Color kLemonChiffon = new Color(1.0f, 0.98039216f, 0.8039216f);

  /** 0xADD8E6. */
  public static final Color kLightBlue = new Color(0.6784314f, 0.84705883f, 0.9019608f);

  /** 0xF08080. */
  public static final Color kLightCoral = new Color(0.9411765f, 0.5019608f, 0.5019608f);

  /** 0xE0FFFF. */
  public static final Color kLightCyan = new Color(0.8784314f, 1.0f, 1.0f);

  /** 0xFAFAD2. */
  public static final Color kLightGoldenrodYellow = new Color(0.98039216f, 0.98039216f, 0.8235294f);

  /** 0xD3D3D3. */
  public static final Color kLightGray = new Color(0.827451f, 0.827451f, 0.827451f);

  /** 0x90EE90. */
  public static final Color kLightGreen = new Color(0.5647059f, 0.93333334f, 0.5647059f);

  /** 0xFFB6C1. */
  public static final Color kLightPink = new Color(1.0f, 0.7137255f, 0.75686276f);

  /** 0xFFA07A. */
  public static final Color kLightSalmon = new Color(1.0f, 0.627451f, 0.47843137f);

  /** 0x20B2AA. */
  public static final Color kLightSeaGreen = new Color(0.1254902f, 0.69803923f, 0.6666667f);

  /** 0x87CEFA. */
  public static final Color kLightSkyBlue = new Color(0.5294118f, 0.80784315f, 0.98039216f);

  /** 0x778899. */
  public static final Color kLightSlateGray = new Color(0.46666667f, 0.53333336f, 0.6f);

  /** 0xB0C4DE. */
  public static final Color kLightSteelBlue = new Color(0.6901961f, 0.76862746f, 0.87058824f);

  /** 0xFFFFE0. */
  public static final Color kLightYellow = new Color(1.0f, 1.0f, 0.8784314f);

  /** 0x00FF00. */
  public static final Color kLime = new Color(0.0f, 1.0f, 0.0f);

  /** 0x32CD32. */
  public static final Color kLimeGreen = new Color(0.19607843f, 0.8039216f, 0.19607843f);

  /** 0xFAF0E6. */
  public static final Color kLinen = new Color(0.98039216f, 0.9411765f, 0.9019608f);

  /** 0xFF00FF. */
  public static final Color kMagenta = new Color(1.0f, 0.0f, 1.0f);

  /** 0x800000. */
  public static final Color kMaroon = new Color(0.5019608f, 0.0f, 0.0f);

  /** 0x66CDAA. */
  public static final Color kMediumAquamarine = new Color(0.4f, 0.8039216f, 0.6666667f);

  /** 0x0000CD. */
  public static final Color kMediumBlue = new Color(0.0f, 0.0f, 0.8039216f);

  /** 0xBA55D3. */
  public static final Color kMediumOrchid = new Color(0.7294118f, 0.33333334f, 0.827451f);

  /** 0x9370DB. */
  public static final Color kMediumPurple = new Color(0.5764706f, 0.4392157f, 0.85882354f);

  /** 0x3CB371. */
  public static final Color kMediumSeaGreen = new Color(0.23529412f, 0.7019608f, 0.44313726f);

  /** 0x7B68EE. */
  public static final Color kMediumSlateBlue = new Color(0.48235294f, 0.40784314f, 0.93333334f);

  /** 0x00FA9A. */
  public static final Color kMediumSpringGreen = new Color(0.0f, 0.98039216f, 0.6039216f);

  /** 0x48D1CC. */
  public static final Color kMediumTurquoise = new Color(0.28235295f, 0.81960785f, 0.8f);

  /** 0xC71585. */
  public static final Color kMediumVioletRed = new Color(0.78039217f, 0.08235294f, 0.52156866f);

  /** 0x191970. */
  public static final Color kMidnightBlue = new Color(0.09803922f, 0.09803922f, 0.4392157f);

  /** 0xF5FFFA. */
  public static final Color kMintcream = new Color(0.9607843f, 1.0f, 0.98039216f);

  /** 0xFFE4E1. */
  public static final Color kMistyRose = new Color(1.0f, 0.89411765f, 0.88235295f);

  /** 0xFFE4B5. */
  public static final Color kMoccasin = new Color(1.0f, 0.89411765f, 0.70980394f);

  /** 0xFFDEAD. */
  public static final Color kNavajoWhite = new Color(1.0f, 0.87058824f, 0.6784314f);

  /** 0x000080. */
  public static final Color kNavy = new Color(0.0f, 0.0f, 0.5019608f);

  /** 0xFDF5E6. */
  public static final Color kOldLace = new Color(0.99215686f, 0.9607843f, 0.9019608f);

  /** 0x808000. */
  public static final Color kOlive = new Color(0.5019608f, 0.5019608f, 0.0f);

  /** 0x6B8E23. */
  public static final Color kOliveDrab = new Color(0.41960785f, 0.5568628f, 0.13725491f);

  /** 0xFFA500. */
  public static final Color kOrange = new Color(1.0f, 0.64705884f, 0.0f);

  /** 0xFF4500. */
  public static final Color kOrangeRed = new Color(1.0f, 0.27058825f, 0.0f);

  /** 0xDA70D6. */
  public static final Color kOrchid = new Color(0.85490197f, 0.4392157f, 0.8392157f);

  /** 0xEEE8AA. */
  public static final Color kPaleGoldenrod = new Color(0.93333334f, 0.9098039f, 0.6666667f);

  /** 0x98FB98. */
  public static final Color kPaleGreen = new Color(0.59607846f, 0.9843137f, 0.59607846f);

  /** 0xAFEEEE. */
  public static final Color kPaleTurquoise = new Color(0.6862745f, 0.93333334f, 0.93333334f);

  /** 0xDB7093. */
  public static final Color kPaleVioletRed = new Color(0.85882354f, 0.4392157f, 0.5764706f);

  /** 0xFFEFD5. */
  public static final Color kPapayaWhip = new Color(1.0f, 0.9372549f, 0.8352941f);

  /** 0xFFDAB9. */
  public static final Color kPeachPuff = new Color(1.0f, 0.85490197f, 0.7254902f);

  /** 0xCD853F. */
  public static final Color kPeru = new Color(0.8039216f, 0.52156866f, 0.24705882f);

  /** 0xFFC0CB. */
  public static final Color kPink = new Color(1.0f, 0.7529412f, 0.79607844f);

  /** 0xDDA0DD. */
  public static final Color kPlum = new Color(0.8666667f, 0.627451f, 0.8666667f);

  /** 0xB0E0E6. */
  public static final Color kPowderBlue = new Color(0.6901961f, 0.8784314f, 0.9019608f);

  /** 0x800080. */
  public static final Color kPurple = new Color(0.5019608f, 0.0f, 0.5019608f);

  /** 0xFF0000. */
  public static final Color kRed = new Color(1.0f, 0.0f, 0.0f);

  /** 0xBC8F8F. */
  public static final Color kRosyBrown = new Color(0.7372549f, 0.56078434f, 0.56078434f);

  /** 0x4169E1. */
  public static final Color kRoyalBlue = new Color(0.25490198f, 0.4117647f, 0.88235295f);

  /** 0x8B4513. */
  public static final Color kSaddleBrown = new Color(0.54509807f, 0.27058825f, 0.07450981f);

  /** 0xFA8072. */
  public static final Color kSalmon = new Color(0.98039216f, 0.5019608f, 0.44705883f);

  /** 0xF4A460. */
  public static final Color kSandyBrown = new Color(0.95686275f, 0.6431373f, 0.3764706f);

  /** 0x2E8B57. */
  public static final Color kSeaGreen = new Color(0.18039216f, 0.54509807f, 0.34117648f);

  /** 0xFFF5EE. */
  public static final Color kSeashell = new Color(1.0f, 0.9607843f, 0.93333334f);

  /** 0xA0522D. */
  public static final Color kSienna = new Color(0.627451f, 0.32156864f, 0.1764706f);

  /** 0xC0C0C0. */
  public static final Color kSilver = new Color(0.7529412f, 0.7529412f, 0.7529412f);

  /** 0x87CEEB. */
  public static final Color kSkyBlue = new Color(0.5294118f, 0.80784315f, 0.92156863f);

  /** 0x6A5ACD. */
  public static final Color kSlateBlue = new Color(0.41568628f, 0.3529412f, 0.8039216f);

  /** 0x708090. */
  public static final Color kSlateGray = new Color(0.4392157f, 0.5019608f, 0.5647059f);

  /** 0xFFFAFA. */
  public static final Color kSnow = new Color(1.0f, 0.98039216f, 0.98039216f);

  /** 0x00FF7F. */
  public static final Color kSpringGreen = new Color(0.0f, 1.0f, 0.49803922f);

  /** 0x4682B4. */
  public static final Color kSteelBlue = new Color(0.27450982f, 0.50980395f, 0.7058824f);

  /** 0xD2B48C. */
  public static final Color kTan = new Color(0.8235294f, 0.7058824f, 0.54901963f);

  /** 0x008080. */
  public static final Color kTeal = new Color(0.0f, 0.5019608f, 0.5019608f);

  /** 0xD8BFD8. */
  public static final Color kThistle = new Color(0.84705883f, 0.7490196f, 0.84705883f);

  /** 0xFF6347. */
  public static final Color kTomato = new Color(1.0f, 0.3882353f, 0.2784314f);

  /** 0x40E0D0. */
  public static final Color kTurquoise = new Color(0.2509804f, 0.8784314f, 0.8156863f);

  /** 0xEE82EE. */
  public static final Color kViolet = new Color(0.93333334f, 0.50980395f, 0.93333334f);

  /** 0xF5DEB3. */
  public static final Color kWheat = new Color(0.9607843f, 0.87058824f, 0.7019608f);

  /** 0xFFFFFF. */
  public static final Color kWhite = new Color(1.0f, 1.0f, 1.0f);

  /** 0xF5F5F5. */
  public static final Color kWhiteSmoke = new Color(0.9607843f, 0.9607843f, 0.9607843f);

  /** 0xFFFF00. */
  public static final Color kYellow = new Color(1.0f, 1.0f, 0.0f);

  /** 0x9ACD32. */
  public static final Color kYellowGreen = new Color(0.6039216f, 0.8039216f, 0.19607843f);
}

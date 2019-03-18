import java.text.DecimalFormat;

public class Plan {

	private static final DecimalFormat PRECISION_2_FORMATTER = new DecimalFormat(".##");
	private final double basicMonthlyRate;
	private final int includedMinutes;
	private final double ratePerExcessMinute;
	private final double additionalLineRate;
	private final int familyDiscountPerLine = 5;

	public Plan(double basicMonthlyRate, int includedMinutes,
			double ratePerExcessMinute, double additionalLineRate) {
		this.basicMonthlyRate = basicMonthlyRate;
		this.includedMinutes = includedMinutes;
		this.ratePerExcessMinute = ratePerExcessMinute;
		this.additionalLineRate = additionalLineRate;
	}

	public String calculateBillFor(int minutesUsed, int numberOfLines) {
		double addtionalLineCharge = calculateAdditionalLineCharge(numberOfLines);
		return PRECISION_2_FORMATTER
				.format(basicMonthlyRate
						+ calculateExcessUsageCharge(minutesUsed)
						+ addtionalLineCharge);
	}

	private double calculateAdditionalLineCharge(int numberOfLines) {
		int additionalLines = Math.max(numberOfLines - 1, 0);
		return calculateChargeableAdditionalLines(additionalLines)
				+ calculateFamilyDiscount(additionalLines);
	}

	private double calculateChargeableAdditionalLines(int additionalLines) {
		return Math.min(additionalLines, 2) * additionalLineRate;
	}

	private double calculateFamilyDiscount(int additionalLines) {
		int linesEligibleForFamilyDiscount = Math.max(0, additionalLines - 2);
		double familyDiscount = familyDiscountPerLine
				* linesEligibleForFamilyDiscount;
		return familyDiscount;
	}

	private double calculateExcessUsageCharge(int minutesUsed) {
		int chargeableMinutes = Math.max(minutesUsed - includedMinutes, 0);
		double excessUsageCharge = chargeableMinutes * ratePerExcessMinute;
		return excessUsageCharge;
	}

}

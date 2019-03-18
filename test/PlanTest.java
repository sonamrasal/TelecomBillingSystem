import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PlanTest {

	private final Plan GOLD = new Plan(49.95, 1000, 0.45, 14.50);
	private final Plan SILVER = new Plan(29.95, 500, 0.54, 21.50);
	private Plan plan;
	private int numberOfLines;
	private int minutesUsed;

	@Test
	public void noUsageOnlyRentalShouldBePaid() {
		whenPlanIs(GOLD).andMinutesUsedAre(0).thenBillIs(49.95);
	}

	@Test
	public void usageForSingleLineWithinLimitOnlyRentalShouldBePaid() {
		whenPlanIs(GOLD).andMinutesUsedAre(878).thenBillIs(49.95);
	}

	@Test
	public void usageForSingleLineExceedsLimitAddExcessUsageChargeToRentalAmount() {
		whenPlanIs(GOLD).andMinutesUsedAre(1123).thenBillIs(105.3);
	}

	@Test
	public void usageForAdditionalLinesWithinLimitAdditionalLineChargeAddedToRental() {
		whenPlanIs(SILVER).andMinutesUsedAre(44).forNumberOfLines(3)
				.thenBillIs(72.95);
	}

	@Test
	public void usageForAdditionalLinesWithinLimitApplyFamilyDiscountAndAddToRental() {
		whenPlanIs(SILVER).andMinutesUsedAre(44).forNumberOfLines(5)
				.thenBillIs(82.95);
	}

	@Test
	public void usageExceedsLimitForAdditionalLinesWithNoFamilyDiscountApplicable() {
		whenPlanIs(SILVER).andMinutesUsedAre(523).forNumberOfLines(2)
				.thenBillIs(63.87);
	}

	@Test
	public void usageExceedsLimitForAdditionalLinesAndFamilyDiscountApplicable() {
		whenPlanIs(GOLD).andMinutesUsedAre(1123).forNumberOfLines(4)
				.thenBillIs(139.3);
	}

	private PlanTest andMinutesUsedAre(int minutesUsed) {
		this.minutesUsed = minutesUsed;
		return this;
	}

	private PlanTest forNumberOfLines(int numberOfLines) {
		this.numberOfLines = numberOfLines;
		return this;
	}

	private PlanTest whenPlanIs(Plan plan) {
		this.plan = plan;
		return this;
	}

	private void thenBillIs(double expectedBillAmount) {
		assertEquals(String.valueOf(expectedBillAmount),
				plan.calculateBillFor(minutesUsed, numberOfLines));
	}
}

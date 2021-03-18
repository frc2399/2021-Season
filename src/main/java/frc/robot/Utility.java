package frc.robot;

public class Utility {
    public static boolean inRange(double first, double second, double tolerance)
	{
		double upperBound = first + tolerance;
		double lowerBound = first - tolerance;
		
		return (second < upperBound) && (second > lowerBound);
	}
}
package RobotUtilities;

public class PIDController {
    private double kP;  // Proportional constant
    private double kI;  // Integral constant
    private double kD;  // Derivative constant

    private double previousError;
    private double integral;

    public PIDController(double kP, double kI, double kD) {
        this.kP = kP;
        this.kI = kI;
        this.kD = kD;
    }

    public double calculate(double setpoint, double current) {
        double error = setpoint - current;

        // Proportional term
        double proportional = kP * error;

        // Integral term
        integral += error;
        double integralTerm = kI * integral;

        // Derivative term
        double derivative = kD * (error - previousError);

        // Update the previous error
        previousError = error;

        // Calculate the output
        double output = proportional + integralTerm + derivative;

        return output;
    }

    public double calculate(double error) {
        // Proportional term
        double proportional = kP * error;

        // Integral term
        integral += error;
        double integralTerm = kI * integral;

        // Derivative term
        double derivative = kD * (error - previousError);

        // Update the previous error
        previousError = error;

        // Calculate the output
        return proportional + integralTerm + derivative;
    }

    public void reset() {
        previousError = 0;
        integral = 0;
    }

    // Getter and setter methods for constants (if needed)
    // ...

    public static void main(String[] args) {
        // Example usage
        PIDController pidController = new PIDController(0.1, 0.01, 0.05);

        double setpoint = 100.0;
        double current = 50.0;

        for (int i = 0; i < 100; i++) {
            double output = pidController.calculate(setpoint, current);
            // Apply the output to the robot's motors or other actuators
            // ...

            // Simulate some change in the system
            current += 1.0;

            System.out.println("Iteration " + i + ": Output = " + output);
        }
    }
}

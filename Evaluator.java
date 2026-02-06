public class Evaluator {

    // Evaluator details
    private String name;
    private String specialization;

    // Constructor
    public Evaluator(String name, String specialization) {
        this.name = name;
        this.specialization = specialization;
    }

    // --- Getters ---
    public String getName() {
        return name;
    }

    public String getSpecialization() {
        return specialization;
    }

    // toString (For Debugging Purposes)
    @Override
    public String toString() {
        return name + " (" + specialization + ")";
    }
}

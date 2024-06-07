public class CalculusRatiocinator {

    // Enumeration to represent the truth values
    enum TruthValue {
        VRAI, FAUX, JENESAISPAS
    }

    // Function to evaluate a basic affirmation
    public static TruthValue evaluate(String affirmation) {
        switch (affirmation.trim()) {
            case "Lou est beau":
                return TruthValue.VRAI;
            case "Lou est pauvre":
                return TruthValue.FAUX;
            case "Lou est généreux":
                return TruthValue.JENESAISPAS;
            default:
                return TruthValue.JENESAISPAS;
        }
    }

    // Function to evaluate conjunction 'et'
    public static TruthValue et(TruthValue a, TruthValue b) {
        if (a == TruthValue.VRAI && b == TruthValue.VRAI) {
            return TruthValue.VRAI;
        } else {
            return TruthValue.FAUX;
        }
    }

    // Function to evaluate disjunction 'ou'
    public static TruthValue ou(TruthValue a, TruthValue b) {
        if (a == TruthValue.VRAI || b == TruthValue.VRAI) {
            return TruthValue.VRAI;
        } else if (a == TruthValue.FAUX && b == TruthValue.FAUX) {
            return TruthValue.FAUX;
        } else {
            return TruthValue.JENESAISPAS;
        }
    }

    // Function to evaluate implication 'donc'
    public static TruthValue donc(TruthValue a, TruthValue b) {
        if (a == TruthValue.VRAI && b == TruthValue.FAUX) {
            return TruthValue.FAUX;
        } else if (a == TruthValue.VRAI && (b == TruthValue.VRAI || b == TruthValue.JENESAISPAS)) {
            return TruthValue.VRAI;
        } else if (a == TruthValue.FAUX) {
            return TruthValue.VRAI;
        } else {
            return TruthValue.JENESAISPAS;
        }
    }

    // Function to evaluate complex affirmations
    public static TruthValue evaluateAffirmation(String affirmation) {
        if (affirmation.contains(" et ")) {
            String[] parts = affirmation.split(" et ");
            return et(evaluate(parts[0].trim()), evaluate(parts[1].trim()));
        } else if (affirmation.contains(" ou ")) {
            String[] parts = affirmation.split(" ou ");
            return ou(evaluate(parts[0].trim()), evaluate(parts[1].trim()));
        } else if (affirmation.contains(" Donc ")) {
            String[] parts = affirmation.split(" Donc ");
            return donc(evaluate(parts[0].trim()), evaluate(parts[1].trim()));
        } else {
            return evaluate(affirmation.trim());
        }
    }

    public static void main(String[] args) {
        // Example affirmations
        String[] affirmations = {
                "Lou est pauvre et Lou est généreux", // Faux
                "Lou est beau. Donc Lou est pauvre", // JENESAISPAS
                "Lou est pauvre. Donc Lou est généreux", // JENESAISPAS
                "Lou est beau ou Lou est généreux. Donc Lou est pauvre", //VRAI
                "Lou est beau ou Lou est généreux. Donc Lou est pauvre et Lou est pauvre ou Lou est généreux" // FAUX et JENESAISPAS
        };

        for (String affirmation : affirmations) {
            TruthValue result = evaluateComplexAffirmation(affirmation);
            System.out.println(affirmation + " : " + result);
        }
    }

    // Function to evaluate very complex affirmations with nested expressions
    public static TruthValue evaluateComplexAffirmation(String affirmation) {
        // Replace logical conjunctions with unique symbols for easier processing
        affirmation = affirmation.replace(" et ", " & ");
        affirmation = affirmation.replace(" ou ", " | ");
        affirmation = affirmation.replace(" Donc ", " -> ");

        // Evaluate nested expressions
        return evaluateNestedAffirmation(affirmation);
    }

    // Helper function to evaluate nested affirmations
    public static TruthValue evaluateNestedAffirmation(String affirmation) {
        if (affirmation.contains(" & ")) {
            String[] parts = affirmation.split(" & ");
            return et(evaluateNestedAffirmation(parts[0].trim()), evaluateNestedAffirmation(parts[1].trim()));
        } else if (affirmation.contains(" | ")) {
            String[] parts = affirmation.split(" \\| ");
            return ou(evaluateNestedAffirmation(parts[0].trim()), evaluateNestedAffirmation(parts[1].trim()));
        } else if (affirmation.contains(" -> ")) {
            String[] parts = affirmation.split(" -> ");
            return donc(evaluateNestedAffirmation(parts[0].trim()), evaluateNestedAffirmation(parts[1].trim()));
        } else {
            return evaluate(affirmation.trim());
        }
    }
}

package mi.m4x.as;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Assuming Lexer and Token are defined elsewhere in your project
public class Main {

    // Instruction class to hold instruction details
    static class Instruction {
        String name;
        String description;
        int opcode;
        String hexCode;
        String[] operands;
        boolean isArithmetic;
        boolean isLogical;
        boolean isDataTransfer;
        boolean isBranch;
        boolean isJump;
        boolean isStack;

        public Instruction(String name, String description, int opcode, String hexCode, String[] operands,
                           boolean isArithmetic, boolean isLogical, boolean isDataTransfer,
                           boolean isBranch, boolean isJump, boolean isStack) {
            this.name = name;
            this.description = description;
            this.opcode = opcode;
            this.hexCode = hexCode;
            this.operands = operands;
            this.isArithmetic = isArithmetic;
            this.isLogical = isLogical;
            this.isDataTransfer = isDataTransfer;
            this.isBranch = isBranch;
            this.isJump = isJump;
            this.isStack = isStack;
        }
    }

    // Map to hold the instructions
    static Map<String, Instruction> instructionMap = new HashMap<>();

    // Populate the instruction map with available instructions
    static {
        instructionMap.put("NOP", new Instruction("NOP", "No-op", 0, "0", new String[]{}, false, false, false, false, false, false));
        instructionMap.put("ADD", new Instruction("ADD", "Add", 1, "1", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("SUB", new Instruction("SUB", "Subtract", 2, "2", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("MUL", new Instruction("MUL", "Multiply", 3, "3", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("DIV", new Instruction("DIV", "Divide", 4, "4", new String[]{"Dividend register", "Divisor register", "Destination register (quotient)"}, true, false, true, false, false, false));
        instructionMap.put("MOD", new Instruction("MOD", "Modulo", 5, "5", new String[]{"Dividend register", "Divisor register", "Destination register (remainder)"}, true, false, true, false, false, false));
        instructionMap.put("AND", new Instruction("AND", "Bitwise AND", 6, "6", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("OR", new Instruction("OR", "Bitwise OR", 7, "7", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("XOR", new Instruction("XOR", "Bitwise XOR", 8, "8", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("NOT", new Instruction("NOT", "Bitwise NOT", 9, "9", new String[]{"Source register 1", "Source register 2", "Destination register"}, true, false, true, false, false, false));
        instructionMap.put("SHL", new Instruction("SHL", "Shift Left", 10, "A", new String[]{"Source register", "Shift amount", "Destination register"}, true, false, true, false, true, true));
        instructionMap.put("SHR", new Instruction("SHR", "Shift Right", 11, "B", new String[]{"Source register", "Shift amount", "Destination register"}, true, false, true, false, true, true));
        instructionMap.put("LOAD", new Instruction("LOAD", "Load", 12, "C", new String[]{"Memory address", "Unused (Set to 0)", "Destination Register"}, false, false, true, false, false, true));
        instructionMap.put("STORE", new Instruction("STORE", "Store", 13, "D", new String[]{"Memory address", "Unused (Set to 0)", "Destination Register"}, false, false, true, false, false, true));
        instructionMap.put("LDI", new Instruction("LDI", "Load Immediate", 14, "E", new String[]{"Immediate value", "Destination Register"}, false, false, true, false, false, true));
        instructionMap.put("BEQ", new Instruction("BEQ", "Branch if Equal", 15, "F", new String[]{"Source register 1", "Source register 2", "Branch address"}, false, false, false, false, false, false));
        instructionMap.put("BNE", new Instruction("BNE", "Branch if not Equal", 16, "10", new String[]{"Source register 1", "Source register 2", "Branch address"}, false, false, false, false, false, false));
        instructionMap.put("BLT", new Instruction("BLT", "Branch if Less than", 17, "11", new String[]{"Source register 1", "Source register 2", "Branch address"}, false, false, false, false, false, false));
        instructionMap.put("BGT", new Instruction("BGT", "Branch if Greater than", 18, "12", new String[]{"Source register 1", "Source register 2", "Branch address"}, false, false, false, false, false, false));
        instructionMap.put("JMP", new Instruction("JMP", "Jump", 19, "13", new String[]{"Jump Address"}, false, false, false, false, true, false));
        instructionMap.put("CALL", new Instruction("CALL", "Call Subroutine", 20, "14", new String[]{"Subroutine address", "Unused (Set to 0)", "Return address register"}, false, false, false, false, true, true));
        instructionMap.put("RET", new Instruction("RET", "Return", 21, "15", new String[]{"Return address Register", "Unused (Set to 0)", "Unused (Set to 0)"}, false, false, false, false, false, true));
        instructionMap.put("PUSH", new Instruction("PUSH", "Push", 22, "16", new String[]{"Source Register", "Unused (Set to 0)", "Stack pointer register"}, false, false, true, false, false, false));
        instructionMap.put("POP", new Instruction("POP", "Pop", 23, "17", new String[]{"Stack pointer register", "Unused (Set to 0)", "Destination Register"}, false, false, true, false, false, true));
        instructionMap.put("HALT", new Instruction("HALT", "Halt", 24, "18", new String[]{}, false, false, false, false, false, false));
        instructionMap.put("IPT", new Instruction("IPT", "Input", 25, "19", new String[]{"Destination Register", "Device Identifier", "Control/Data Reg"}, false, false, true, false, true, true));
        instructionMap.put("OPT", new Instruction("OPT", "Output", 26, "1A", new String[]{"Destination Register", "Device Identifier", "Control/Data Reg"}, false, false, true, false, true, true));
        instructionMap.put("SLT", new Instruction("SLT", "Set if Less than", 27, "1B", new String[]{"Source Register 1", "Source register 2", "Destination register (set to 1 or 0)"}, false, false, true, false, false, true));
        instructionMap.put("SLE", new Instruction("SLE", "Set if Less or Equal", 28, "1C", new String[]{"Source Register 1", "Source register 2", "Destination register (set to 1 or 0)"}, false, false, true, false, false, true));
        instructionMap.put("SGT", new Instruction("SGT", "Set if Greater", 29, "1D", new String[]{"Source Register 1", "Source register 2", "Destination register (set to 1 or 0)"}, false, false, true, false, false, true));
        instructionMap.put("SGE", new Instruction("SGE", "Set if Greater or Equal", 30, "1E", new String[]{"Source Register 1", "Source register 2", "Destination register (set to 1 or 0)"}, false, false, true, false, false, true));
        instructionMap.put("MOV", new Instruction("MOV", "Move", 31, "1F", new String[]{"Source Register", "Destination Register"}, false, false, false, false, false, true));
    }

    public static void main(String[] args) throws IOException {
        String fileName = "test.asm";

        // Use Lexer to tokenize the input file
        Lexer lexer = new Lexer(fileName);
        List<Token> tokens = lexer.lex();

        // Process each token
        int lineNum = 0;
        for (Token token : tokens) {
            lineNum++;
            String line = token.getValue();

            if (line.isEmpty() || line.startsWith(";")) {
                // Skip empty lines and comments
                continue;
            }

            try {
                parseLine(line);
            } catch (Exception e) {
                System.err.println("Error parsing line " + lineNum + ": " + e.getMessage());
            }
        }
    }

    private static void parseLine(String line) {
        // Tokenize the line by whitespace and commas
        String[] tokens = line.trim().split("\\s+|,\\s*");

        if (tokens.length < 1) {
            throw new RuntimeException("Invalid line format");
        }

        // Get the instruction
        String instructionName = tokens[0];
        Instruction instruction = instructionMap.get(instructionName);

        //FIXME: Operands are not being printed because they are being classified as invalid instructions.
        // They are not instructions but operands and I need to figure out how to handle them. This is super broken.
        if (instruction == null) {
            throw new RuntimeException("Unknown instruction: " + instructionName);
        }

        // Check the number of operands
        int expectedOperands = instruction.operands.length;
        int actualOperands = tokens.length - 1; // Exclude the instruction itself

        if (actualOperands != expectedOperands) {
            throw new RuntimeException("Instruction " + instructionName + " expects " + expectedOperands + " operands but got " + actualOperands);
        }

        // Process the operands
        for (int i = 1; i < tokens.length; i++) {
            String operand = tokens[i];
            String expectedType = instruction.operands[i - 1]; // Get the expected type for the current operand

            // Validate each operand
            if (!isValidOperand(operand, expectedType)) {
                throw new RuntimeException("Invalid operand: " + operand + " for expected type: " + expectedType);
            }
            System.out.println("Operand " + (i) + ": " + operand);
        }
    }


    public static Instruction getInstruction(String mnemonic) {
        return instructionMap.get(mnemonic);
    }

    private static boolean isValidOperand(String operand, String expectedType) {
        // Validate each operand based on its expected type
        switch (expectedType) {
            case "Source register 1":
            case "Source register 2":
            case "Destination register":
            case "Stack pointer register":
            case "Return address Register":
                return operand.matches("R\\d+"); // Example: R1, R2, etc.
            case "Memory address":
            case "Immediate value":
            case "Jump Address":
            case "Branch address":
            case "Device Identifier":
            case "Control/Data Reg":
                return operand.matches("\\d+"); // Example: 0, 1, 255, etc.
            case "Unused (Set to 0)":
                return operand.equals("0"); // Must be exactly "0"
            default:
                return false; // Unknown type
        }
    }
}


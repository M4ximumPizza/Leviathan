package mi.m4x.as;

public class Instruction {
    private String mnemonic;
    private String description;
    private int opcode;
    private int opcodeDecimal;
    private String hexCode;
    private String[] parameters;
    private boolean isMemoryAccess;
    private boolean isControlFlow;
    private boolean isArithmetic;
    private boolean isBitwise;
    private boolean isLogic;
    private boolean isStack;

    public Instruction(String mnemonic, String description, int opcode, String name, int opcodeDecimal, String hexCode,
                       String[] parameters, boolean isMemoryAccess, boolean isControlFlow, boolean isArithmetic,
                       boolean isBitwise, boolean isLogic, boolean isStack) {
        this.mnemonic = mnemonic;
        this.description = description;
        this.opcode = opcode;
        this.opcodeDecimal = opcodeDecimal;
        this.hexCode = hexCode;
        this.parameters = parameters;
        this.isMemoryAccess = isMemoryAccess;
        this.isControlFlow = isControlFlow;
        this.isArithmetic = isArithmetic;
        this.isBitwise = isBitwise;
        this.isLogic = isLogic;
        this.isStack = isStack;
    }

    // Getters
    public String getMnemonic() { return mnemonic; }
    public String getDescription() { return description; }
    public int getOpcode() { return opcode; }
    public int getOpcodeDecimal() { return opcodeDecimal; }
    public String getHexCode() { return hexCode; }
    public String[] getParameters() { return parameters; }
    public boolean isMemoryAccess() { return isMemoryAccess; }
    public boolean isControlFlow() { return isControlFlow; }
    public boolean isArithmetic() { return isArithmetic; }
    public boolean isBitwise() { return isBitwise; }
    public boolean isLogic() { return isLogic; }
    public boolean isStack() { return isStack; }
}

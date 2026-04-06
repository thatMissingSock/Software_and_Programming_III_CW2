package com.novalang.instructions;

import com.novalang.vm.ProgramContext;
import com.novalang.compiler.Translator;

import java.util.Optional;

public record PrintInstr(String label, int rSrc) implements Instruction {
    /**
     * Instruction that prints the value of the register (rSrc)
     * @param label the label associated with this instruction line, empty string if none
     * @param operands the operands array containing rSrc as an index
     */

    // Reflection Factory Constructor
    public PrintInstr(String label, Object... operands) {
        this(label,
                Translator.getRegisterIndex(operands, 0).orElse(0));
        if (!Translator.getRegisterIndex(operands, 0).isPresent() ||
                !Translator.ensureOperandCount(operands, 1)) {
            throw new IllegalArgumentException("Invalid operands for PrintInstr");
        }
    }

    @Override
    public Optional<Integer> execute(ProgramContext context) {
        return context.registers().get(rSrc).map(val -> {
            System.out.println("PC: " + context.pc() + " | PRINT (r" + rSrc + "): " + val);
            return context.pc() + 1;
        });
    }
}
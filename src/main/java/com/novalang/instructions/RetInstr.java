package com.novalang.instructions;

import com.novalang.vm.ProgramContext;
import com.novalang.compiler.Translator;

import java.util.Optional;

public record RetInstr(String label) implements Instruction {
    /**
     * Instruction that returns from a subroutine by popping the return address from the call stack. Additionally, if
     * the call stack is empty, it halts the current thread
     * @param label the label associated with this instruction line, empty string if none
     */

    // Reflection Factory Constructor
    public RetInstr(String label, Object... operands) {
        this(label);
        if (!Translator.ensureOperandCount(operands, 0)) {
            throw new IllegalArgumentException("Invalid operands for RetInstr");
        }
    }

    @Override
    public Optional<Integer> execute(ProgramContext context) {
        if (context.callStack().isEmpty()) {
            return Optional.of(-1);
        }
        return Optional.of(context.callStack().pop());
    }
}
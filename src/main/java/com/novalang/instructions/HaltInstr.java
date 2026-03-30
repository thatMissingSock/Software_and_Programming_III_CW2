package com.novalang.instructions;

import com.novalang.vm.ProgramContext;
import com.novalang.compiler.Translator;

import java.util.Optional;
// TODO: double check this one as it only contains the label, however I am starting to think that it will function correctly as it can use just the string to tell it to stop. I.e. it only needs that car's reg's number to halt.
public record HaltInstr(String label) implements Instruction {

    // Reflection Factory Constructor
    public HaltInstr(String label, Object... operands) {
        this(label);
        if (!Translator.ensureOperandCount(operands, 0)) {
            throw new IllegalArgumentException("Invalid operands for HaltInstr");
        }
    }

    @Override
    public Optional<Integer> execute(ProgramContext context) {
        // Halt the current thread's execution loop
        return Optional.of(-1);
    }
}
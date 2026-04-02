package com.novalang.instructions;

import com.novalang.vm.ProgramContext;
import com.novalang.compiler.Translator;

import java.util.Optional;

// after testing, this is more akin to copying than actually moving registers (much like actual storage). It'll move it
// from one register to another and the other storage will remain the same value untill it gets rewritten
public record MovInstr(String label, int rDest, int rSrc) implements Instruction {

    // Reflection Factory Constructor
    public MovInstr(String label, Object... operands) {
        this(label,
                Translator.getRegisterIndex(operands, 0).orElse(0),
                Translator.getRegisterIndex(operands, 1).orElse(0));
        if (!Translator.getRegisterIndex(operands, 0).isPresent() ||
                !Translator.getRegisterIndex(operands, 1).isPresent() ||
                !Translator.ensureOperandCount(operands, 2)) {
            throw new IllegalArgumentException("Invalid operands for MovInstr");
        }
    }

    @Override
    public Optional<Integer> execute(ProgramContext context) {
        // Copy register[rSrc] into register[rDest]
        return context.registers().get(rSrc).flatMap(val -> {
            if (context.registers().set(rDest, val)) {
                return Optional.of(context.pc() + 1);
            }
            return Optional.empty();
        });
    }
}
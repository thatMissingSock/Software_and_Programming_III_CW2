package com.novalang.instructions;

import com.novalang.vm.ProgramContext;
import com.novalang.compiler.Translator;

import java.util.Optional;


public record AsyncInstr(String label, int targetPC) implements Instruction {
    /**
     * Instruction that starts a new virtual thread beginning execution at the target program counter (targetPC)
     * @param label the label associated with this instruction line, empty string if none
     * @param operands contains the operand for the resolved pc counter as an INT
     */

    // Reflection Factory Constructor
    public AsyncInstr(String label, Object... operands) {
        this(label,
                Translator.getTargetPC(operands, 0).orElse(0)); // targetPC must be resolved by Translator
        if (!Translator.getTargetPC(operands, 0).isPresent() ||
                !Translator.ensureOperandCount(operands, 1)) {
            throw new IllegalArgumentException("Invalid operands for AsyncInstr");
        }
    }

    @Override
    public Optional<Integer> execute(ProgramContext context) {
        // Concurrency: Delegate the task startup to the VM
        context.vm().startAsyncTask(targetPC);
        return Optional.of(context.pc() + 1);
    }
}
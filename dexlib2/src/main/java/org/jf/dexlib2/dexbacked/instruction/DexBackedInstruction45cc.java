/*
 * Copyright 2016, Google Inc.
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are
 * met:
 *
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above
 * copyright notice, this list of conditions and the following disclaimer
 * in the documentation and/or other materials provided with the
 * distribution.
 *     * Neither the name of Google Inc. nor the names of its
 * contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.jf.dexlib2.dexbacked.instruction;



import org.jf.dexlib2.Opcode;
import org.jf.dexlib2.dexbacked.DexBackedDexFile;
import org.jf.dexlib2.dexbacked.reference.DexBackedReference;
import org.jf.dexlib2.iface.UpdateReference;
import org.jf.dexlib2.iface.instruction.formats.Instruction45cc;
import org.jf.dexlib2.iface.reference.Reference;
import org.jf.dexlib2.writer.builder.DexBuilder;
import org.jf.util.NibbleUtils;

public class DexBackedInstruction45cc extends DexBackedInstruction implements Instruction45cc, UpdateReference {
    public DexBackedInstruction45cc(DexBackedDexFile dexFile,
                                    Opcode opcode,
                                    int instructionStart) {
        super(dexFile, opcode, instructionStart);
    }

    @Override
    public int getRegisterCount() {
        return NibbleUtils.extractHighUnsignedNibble(dexFile.readUbyte(instructionStart + 1));
    }

    @Override
    public int getRegisterC() {
        return NibbleUtils.extractLowUnsignedNibble(dexFile.readUbyte(instructionStart + 4));
    }

    @Override
    public int getRegisterD() {
        return NibbleUtils.extractHighUnsignedNibble(dexFile.readUbyte(instructionStart + 4));
    }

    @Override
    public int getRegisterE() {
        return NibbleUtils.extractLowUnsignedNibble(dexFile.readUbyte(instructionStart + 5));
    }

    @Override
    public int getRegisterF() {
        return NibbleUtils.extractHighUnsignedNibble(dexFile.readUbyte(instructionStart + 5));
    }

    @Override
    public int getRegisterG() {
        return NibbleUtils.extractLowUnsignedNibble(dexFile.readUbyte(instructionStart + 1));
    }


    @Override
    public Reference getReference() {
        if (reference != null)
            return reference;
        return DexBackedReference.makeReference(dexFile, opcode.referenceType,
                dexFile.readUshort(instructionStart + 2));
    }

    @Override
    public int getReferenceType() {
        return opcode.referenceType;
    }


    @Override
    public Reference getReference2() {
        if (reference2 != null)
            return reference2;
        return DexBackedReference.makeReference(dexFile, opcode.referenceType2,
                dexFile.readUshort(instructionStart + 3));
    }

    @Override
    public int getReferenceType2() {
        return opcode.referenceType2;
    }

    private Reference reference = null;
    private Reference reference2 = null;

    @Override
    public void updateReference(DexBuilder dexBuilder) {
        this.reference = dexBuilder.internReference(getReference());
        this.reference2 = dexBuilder.internReference(getReference2());
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringgenerator;

import java.io.IOException;

/**
 *
 * @author Jeethu
 */
public class Main {
    
    public static void main(String[] args) throws IOException {
    
        BPSEQVariables bpvar = new BPSEQVariables();
        FileProcessBPSEQ readInput = new FileProcessBPSEQ(bpvar.getBPSEQInputFileName());
        bpvar.setbpseqPartners(readInput.getbpseqPartners());
        bpvar.setbpseqNucleotide(readInput.getbpseqNucleotide());
        bpvar.setBPSEQFileLength(readInput.getFileLength());
        StringGenerator SG = new StringGenerator();
        SG.GenerateOutput(bpvar.getbpseqPartners(), bpvar.getbpseqNucleotide(), bpvar.getBPSEQFileLength());
        
        
    }
    
}

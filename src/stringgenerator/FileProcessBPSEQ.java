/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringgenerator;

import java.io.FileReader;
import java.util.Scanner;

/**
 *
 * @author Jeethu
 */
public class FileProcessBPSEQ {
    
    private String inputFileName, line ;
    private Scanner in = new Scanner(System.in), 
            inputReader, lineReader;
    private int index, partnerOfIndex, FileLength = 0;
    private char nucleotide ;
    private int[][] bpseqPartners = new int[500][2];
    private char[] bpseqNucleotide = new char[500];
    
    public FileProcessBPSEQ(String inputFile)
    {
        OpenInputFile(inputFile);
        ReadFile();
        CloseInputFile();
    }
    private void OpenInputFile(String inputFile) {
        inputFileName = inputFile; 
        try {
            
            inputReader = new Scanner (new FileReader(inputFileName));
        }
        catch(Exception e) {
            System.out.println("The input file \"" + inputFileName + "\" was "
                    + "not found");
            System.out.println(e.toString());
        }
    } 
    
    private void ReadFile() {
        while(inputReader.hasNextLine()) {
            line = inputReader.nextLine();
            lineReader = new Scanner(line);
            
            if (line.startsWith("#")) {
               
            }
            else {
                index = lineReader.nextInt();
                nucleotide = lineReader.next().charAt(0);
                partnerOfIndex = lineReader.nextInt();
                
                bpseqPartners[FileLength][0] = index;
                bpseqPartners[FileLength][1] = partnerOfIndex;
                
                bpseqNucleotide[FileLength] = nucleotide;
                
               /* System.out.println("Index :" +index);
                System.out.println("partnerOfIndex :" +partnerOfIndex); 
                System.out.println("nucleotide :" +bpseqNucleotide[FileLength]); */
                FileLength++;
               // System.out.println("FileLength :" +FileLength);
            }
        }
    }
    
    private void CloseInputFile() {
    if (inputReader != null)
        inputReader.close();  
    }
    
    public int[][] getbpseqPartners(){
        return bpseqPartners;
    }
    
    public char[] getbpseqNucleotide(){
        return bpseqNucleotide;
    }
    
    public int getFileLength(){
        return FileLength;
    }
        
    
     
    
}

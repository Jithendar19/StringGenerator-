/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringgenerator;

/**
 *
 * @author Jeethu
 */
public class BPSEQVariables {
    
    //Variables declaration
    int[][] bpseqPartners = new int[500][2];
    char [] bpseqNucleotide = new char[500];
    private String BPSEQInputFileName = "Input.txt";
    private int BPSEQFileLength ;
    
  //Getters
  public int[][] getbpseqPartners(){
    return bpseqPartners;
    }
  
    public char[] getbpseqNucleotide(){
    return bpseqNucleotide;
    }
  
    public int getBPSEQFileLength(){
        return BPSEQFileLength;
    }
    
  public String getBPSEQInputFileName()
  {
      return BPSEQInputFileName;
  }
  
  //Setters
   public void setbpseqPartners(int[][] bpseqPartners){
    this.bpseqPartners = bpseqPartners;
    }
   
   public void setbpseqNucleotide(char[] bpseqNucleotide)
   {
       this.bpseqNucleotide = bpseqNucleotide;
   }
   
   public void setBPSEQInputFileName( String BPSEQInputFileName)
   {
       this.BPSEQInputFileName = BPSEQInputFileName;
   }
    
    public void setBPSEQFileLength(int BPSEQFileLength)
    {
        this.BPSEQFileLength = BPSEQFileLength;
    }
    
}

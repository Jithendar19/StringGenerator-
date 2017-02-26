/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stringgenerator;


import dk.brics.automaton.Automaton; 
import dk.brics.automaton.State; 
import dk.brics.automaton.Transition; 
import dk.brics.automaton.RegExp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random; 
import java.util.List; 

/**
 *
 * @author Ketan
 */
public class StringGenerator {

    /**
     * @param args the command line arguments
     */
    private static class BPSEQ{
        int seq;
        Character str;
        int index; 
        BPSEQ(int seq,Character str,int index){  
        this.seq=seq;  
        this.str=str;  
        this.index=index;  
    } 
    }
    private final Automaton automaton; 
    private final Random random; 
    public StringGenerator(String regex, Random random) { 
        assert regex != null; 
        assert random != null; 
        this.automaton = new RegExp(regex).toAutomaton(); 
        this.random = random; 
    } 

    private StringGenerator(String regex) {
        this(regex, new Random()); 
    }
    
    public StringGenerator ()
    {
        this.automaton = null;
        this.random = null;
    }
    public String generate() { 
        StringBuilder builder = new StringBuilder(); 
        generate(builder, automaton.getInitialState()); 
        return builder.toString(); 
    } 
 
    private  void generate(StringBuilder builder, State state) { 
        List<Transition> transitions = state.getSortedTransitions(true); 
        if (transitions.isEmpty()) { 
            assert state.isAccept(); 
            return; 
        } 
        int nroptions = state.isAccept() ? transitions.size() : transitions.size() - 1; 
        int option = getRandomInt(0, nroptions, random); 
        if (state.isAccept() && option == 0) {          // 0 is considered stop 
            return; 
        } 
        // Moving on to next transition 
        Transition transition = transitions.get(option - (state.isAccept() ? 1 : 0)); 
        appendChoice(builder, transition); 
        generate(builder, transition.getDest()); 
    } 
    public final static int getRandomInt(int min, int max, Random random) { 
        int dif = max - min; 
        float number = random.nextFloat();              // 0 <= number < 1 
        return min + Math.round(number * dif); 
    }    
    private  void appendChoice(StringBuilder builder, Transition transition) { 
        char c = (char) getRandomInt(transition.getMin(), transition.getMax(), random); 
        builder.append(c); 
    }
    
    static String replace(String str, String pattern, String replace) {
    int start = 0;
    int index = 0;
    StringBuffer result = new StringBuffer();

    while ((index = str.indexOf(pattern, start)) >= 0) {
      result.append(str.substring(start, index));
      result.append(replace);
      start = index + pattern.length();
    }
    result.append(str.substring(start));
    return result.toString();
  }
    static Comparator<BPSEQ> indexComparator = new Comparator<BPSEQ>() {         
      @Override public int compare(BPSEQ jc1, BPSEQ jc2) {             
      return (jc2.seq > jc1.seq ? -1 :                     
              (jc2.seq == jc1.seq ? 0 : 1));           
        }     
    };
     private static class rnaSorter {     
   
        ArrayList<BPSEQ> bpseqList=new ArrayList<BPSEQ>();

        private rnaSorter(ArrayList<BPSEQ> bpseqList) {
           this.bpseqList = bpseqList;   //To change body of generated methods, choose Tools | Templates.
        }
      private  ArrayList<BPSEQ> getSortedIndex() {         
        Collections.sort(bpseqList, indexComparator);         
        return bpseqList;     
        }
     }
     
    public void GenerateOutput (int[][] bpseqPartners, char[] bpseqNucleotide, int BPSEQFileLength) throws IOException {
        // TODO code application logic here

        ArrayList<String> list = new ArrayList<String>();
        rnaSorter rnaSorter; 
        
       ArrayList<BPSEQ> bpseqList=new ArrayList<BPSEQ>();
        for(int i =0; i < BPSEQFileLength; i++)
        {
            bpseqList.add(new BPSEQ(bpseqPartners[i][0],bpseqNucleotide[i], bpseqPartners[i][1] ));
        }
      /*  BPSEQ s1=new BPSEQ(1,'X',5);  
        BPSEQ s2=new BPSEQ(2,'X',0);  
        BPSEQ s3=new BPSEQ(3,'C',8);
        BPSEQ s4=new BPSEQ(4,'Y',0);  
        BPSEQ s5=new BPSEQ(5,'X',1);  
        BPSEQ s6=new BPSEQ(6,'G',0);
        BPSEQ s7=new BPSEQ(7,'Y',0);
        BPSEQ s8=new BPSEQ(8,'X',3);
        //ArrayList<BPSEQ> bpseqList=new ArrayList<BPSEQ>();
        bpseqList.add(s1);
        bpseqList.add(s2);
        bpseqList.add(s3);
        bpseqList.add(s4);
        bpseqList.add(s5);
        bpseqList.add(s6);
        bpseqList.add(s7);
        bpseqList.add(s8); */
        int bpseqLength=bpseqList.size();
        rnaSorter = new rnaSorter(bpseqList);   
        rnaSorter.getSortedIndex();
        
        String bpseqStr = "";
        String epsPosition="";
        int epsCount=0;
        for(int j=0;j<bpseqList.size();j++)
        {
            bpseqStr+=bpseqList.get(j).str;
            if(bpseqList.get(j).str=='Y')
            {
                epsPosition+=Integer.toString(bpseqList.get(j).seq)+",";
                epsCount++;
            }
        }
        
                
        String text = bpseqStr; 
        System.out.println("Actual input String : "+text);
        String newText = replace(text,"Y","(A?|C?|G?|U?){1}");
        String newText1 = replace(newText,"X","(A|C|G|U){1}");
        System.out.println("Converted input String into Regular exprression: "+newText1);
        System.out.println("Possible strings are : ");
        StringGenerator instance = new StringGenerator(newText1); 
         for (int i = 0; i < 10; i++) { 
            String str = instance.generate().trim();
            if(!list.contains(str.trim())) {
                list.add(str.trim());
            }
        }
        int cnt=0;
        ArrayList<ArrayList<BPSEQ>> newbpseqList=new ArrayList<ArrayList<BPSEQ>>();
        ArrayList<ArrayList<ArrayList<BPSEQ>>> finalbpseqList=new ArrayList<ArrayList<ArrayList<BPSEQ>>>();
        BufferedReader in = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\Users\\Alpha\\Documents\\NetBeansProjects\\StringGenerator\\output.txt"));
        for(int j=0;j<list.size();j++)
        {
             //System.out.println(list.get(j));
            ArrayList<BPSEQ> tmpBpseqList=new ArrayList<BPSEQ>();
             if(list.get(j).length()<bpseqLength)
             {
                  
                 String[] pos=epsPosition.split(",");
                 for (int l=0; l<pos.length;l++)
                 {
                     tmpBpseqList=new ArrayList<BPSEQ>();
                     tmpBpseqList.addAll(bpseqList);
                     tmpBpseqList.remove(Integer.parseInt(pos[l])-1);
                    for (int k=0;k<list.get(j).length();k++)
                    {
                        if(tmpBpseqList.get(k).seq<=Integer.parseInt(pos[l])) 
                        {
                            BPSEQ s=new BPSEQ(tmpBpseqList.get(k).seq,list.get(j).charAt(k),(tmpBpseqList.get(k).index == 0) ? 0 : tmpBpseqList.get(k).index-1);  
                            tmpBpseqList.set(k,s);
                        }
                        else
                        {
                            BPSEQ s=new BPSEQ((tmpBpseqList.get(k).seq-1),list.get(j).charAt(k),tmpBpseqList.get(k).index);  
                            tmpBpseqList.set(k,s);
                        }                        
                    }
                    
                 }                 
             }
             else   
             {  
                 tmpBpseqList=new ArrayList<BPSEQ>();
                 tmpBpseqList.addAll(bpseqList);        
                 for (int k=0;k<list.get(j).length();k++)
                 {
                    BPSEQ s=new BPSEQ(tmpBpseqList.get(k).seq,list.get(j).charAt(k),tmpBpseqList.get(k).index);  
                    tmpBpseqList.set(k,s);
                    
                 }              
             }
                newbpseqList.add(j,tmpBpseqList);
                finalbpseqList.add(newbpseqList);
        }
        int NoOfFiles = 1;
        for(int m=0;m<finalbpseqList.size();m++)
        {
              for (int l=0;l<finalbpseqList.get(m).size();l++)
                 {
                   for (int n=0;n<finalbpseqList.get(m).get(l).size();n++)
                    {
                         out.write(finalbpseqList.get(m).get(l).get(n).seq + " " +finalbpseqList.get(m).get(l).get(n).str + " "+finalbpseqList.get(m).get(l).get(n).index);
                         out.newLine();
                    }
                   out.write("#EOF " +(NoOfFiles++)); //Added for marking delimeters in the output file
                   out.newLine();
                 }         
        }
        out.close();
        }
    }
    


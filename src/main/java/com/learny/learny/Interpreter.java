package com.learny.learny;
import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Entity;
import com.textrazor.annotations.NounPhrase;
import com.textrazor.annotations.Relation;
import com.textrazor.annotations.Response;
import com.textrazor.annotations.ScoredCategory;
import com.textrazor.annotations.Sentence;
import com.textrazor.annotations.Topic;
import com.textrazor.annotations.Word;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author nonsense
 */
public class Interpreter {
    private TextRazor tr;
    
    public Interpreter(){
        String key="bca9ddadcc4ee6d68140c264f55ca34c2b08d7630c2a3bc1e6fdefc1";
        tr = new TextRazor(key);
        tr.addExtractor("topics");
        tr.addExtractor("words");
        tr.addExtractor("entities");
        tr.addExtractor("phrases");
        tr.addExtractor("relations");
        tr.addExtractor("dependency-tree");
        
    }
    
    public void analyzeTest(){
        System.out.println("ENTERING ANALYZE");
        //String testString="This is a Premier League Soccer test biology produced Saturday January 28, 2017 at McHacks2k17.";
        //String testString="The chancellor has postponed the sale of the government's final stake in Lloyds Banking Group";
        //String testString="The economic growth of the population of India.";
        /*String testString="You will work in assigned groups from 2008 to 2009. Each group will demonstrate its work, as specified by your instructor. All group members must be present at the demonstration. All members of a group are expected to contribute more or less equally to the project and to be familiar with all of the work of the project.\n" +
"\n" +
"Each team will write a client-server TCP-based system in Java that will allow a person using the client to play game(s) of Mastermind with the computer server.";*/
        //String testString="Martin Luther Zoidberg managed to get apples";
        //String testString="Between 2008 and 2009, you will work in assigned group.";
        //String testString="The bank continued to mislead shareholders in its annual reports of January 2008 and April 2009, both of which identified Sheikh Mansour as the investor.";
        //String testString="Unlike RBS and Lloyds TSB, Barclays narrowly avoided having to request a government bailout late in 2008 after it was rescued by $7bn worth of new investment, most of which came from the gulf states of Qatar and Abu Dhabi.";
        //String testString = "Jhon Shmit died while sitting on the toilet.";
        String testString = "Php a scripting language where as Java is a compiled language."; 
        System.out.println(testString);
        try{
            AnalyzedText at=tr.analyze(testString);
            Response rp=at.getResponse();

            for(Sentence se : rp.getSentences()){
                
                analyzeSentence(se);
                
//                for(Word w : se.getWords()){
//                    System.out.println("OUTER WORD LOOP: "+w.getToken()+" POS: "+w.getPartOfSpeech());
//                    if(w.getNounPhrases() != null){
//                        for(NounPhrase np : w.getNounPhrases()){
//                            for(Word npw : np.getWords()){
//                                if(npw.getPartOfSpeech().equalsIgnoreCase("NNS")){
//                                    System.out.println("NNS: "+npw.getToken());
//                                }
//                            }
//                        }
//                    }
//                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    private String retrieveSentence(Sentence sentence){
        String sent="";
        for(Word word : sentence.getWords()){
            sent+=" "+word.getToken();
        }
        return sent;
    }
    
    private boolean isDate(Entity entity){
        System.out.println("IN ISDATE");
        for(String type : entity.getDBPediaTypes()){
            System.out.println("TYPE: "+type);
            if(type.equalsIgnoreCase("time")){
                return true;
            }
        }
        return false;
    }
    
    /**
     * NOT WORKING 
     * @param dates
     * @return 
     */
    private String returnTimeFrame(List<String> dates){
        String date="";
        if(dates.size() == 2){
            date=dates.get(0)+"-"+dates.get(1);
        }
        return date;
    }
    
    private void retrieveMostRelevantTopic(Response rp){
        
    }
    
    
    /*
    private String retrieveNounPhrase(Word word){
        for(NounPhrase np:word.getNounPhrases()){
            
        }
    }*/

    private void analyzeSentence(Sentence se) {
        
        for(Word w : se.getWords()){
            System.out.println(w.getToken()+":"+w.getPartOfSpeech());
        }
    }
}

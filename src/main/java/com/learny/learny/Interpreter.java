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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 *
 * @author nonsense
 */
public class Interpreter {

    private TextRazor tr;

    public Interpreter() {
        String key = "bca9ddadcc4ee6d68140c264f55ca34c2b08d7630c2a3bc1e6fdefc1";
        tr = new TextRazor(key);
        tr.addExtractor("topics");
        tr.addExtractor("words");
        tr.addExtractor("entities");
        tr.addExtractor("phrases");
        tr.addExtractor("relations");
        tr.addExtractor("dependency-tree");
        List<String> classifier = new ArrayList<>();
        classifier.add("textrazor_mediatopics");
        tr.setClassifiers(classifier);

    }

    public void analyzeTest() {
        System.out.println("ENTERING ANALYZE");
        //String testString="This is a  test produced Saturday January 28, 2017 at McHacks2k17.";
        //String testString="The chancellor has postponed the sale of the government's final stake in Lloyds Banking Group";
        //String testString="The economic growth of the population of India.";
        /*String testString="You will work in assigned groups from 2008 to 2009. Each group will demonstrate its work, as specified by your instructor. All group members must be present at the demonstration. All members of a group are expected to contribute more or less equally to the project and to be familiar with all of the work of the project.\n" +
"\n" +
"Each team will write a client-server TCP-based system in Java that will allow a person using the client to play game(s) of Mastermind with the computer server.";*/
        //String testString="Martin Luther Zoidberg managed to get apples";
        //String testString="Between 2008 and 2009, you will work in assigned group.";
        //String testString="The bank continued to mislead shareholders in its annual reports of January 2008 and April 2009, both of which identified Sheikh Mansour as the investor.";
        //String testString = "Unlike from RBS and Lloyds TSB, Barclays narrowly avoided having to request a government bailout late in 2008 after it was rescued by $7bn worth of new investment, most of which came from the gulf states of Qatar and Abu Dhabi.";
        //String testString="Martin Luther King Jr was a great black man.";
        String testString="Barclays misled shareholders and the public about one of the biggest investments in the bank's history, a BBC Panorama investigation has found.\n" +
"The bank announced in 2008 that Manchester City owner Sheikh Mansour had agreed to invest more than £3bn.\n" +
"But the BBC found that the money, which helped Barclays avoid a bailout by British taxpayers, actually came from the Abu Dhabi government.\n" +
"Barclays said the mistake in its accounts was \"a drafting error\".\n" +
"Unlike RBS and Lloyds TSB, Barclays narrowly avoided having to request a government bailout late in 2008 after it was rescued by £7bn worth of new investment, most of which came from the gulf states of Qatar and Abu Dhabi.\n" +
"Half of the cash was supposed to be coming from Sheikh Mansour.\n" +
"But Barclays has admitted it was told the investor might change shortly before shareholders voted to approve the deal on 24 November 2008.\n" +
"But instead of telling shareholders, the bank remained silent until the change of investor was confirmed a few hours later.\n" +
"\n" +
"The bank said it subsequently provided \"appropriate disclosure\" in three prospectuses that were issued the following day.\n" +
"But the disclosure was buried deep in the small print and said that Sheikh Mansour \"has arranged for his investment…to be funded by an Abu Dhabi governmental investment vehicle, which will become the indirect shareholder\".\n" +
"Barclays still used the phrase \"his investment\", even though it knew Sheikh Mansour was not actually investing in the bank at the time.\n" +
"The bank continued to mislead shareholders in its annual reports of 2008 and 2009, both of which identified Sheikh Mansour as the investor\n" +
"Barclays said the mistake in its accounts was \"simply a drafting error\" and that the information provided in the prospectuses was \"entirely appropriate in all the circumstances\".\n" +
"The bank also said: \"The shareholders meeting had already taken place and there was therefore no need to issue press releases or additional formal communications to shareholders/other market participants.\"\n" +
"\n" +
"Professor Alistair Milne, an expert on financial regulation in the City, said banks are expected to release accurate information about major deals.\n" +
"\"Any discrepancy of that kind is serious because it raises questions in the minds of investors. Every bank is well aware the annual report is a critical document and a huge amount of time and attention is put in to trying to get all the details correct.\"\n" +
"The Abu Dhabi government investment vehicle that provided the funding, International Petroleum Investment Company (IPIC), was not mentioned in any Barclays' announcements until six months after the deal.\n" +
"\n" +
"The chairman of IPIC is Sheikh Mansour. Although the Sheikh did not invest any of his own money in Barclays at the time, a company he controlled was initially issued warrants to buy 758 million shares in the bank.\n" +
"The warrants gave the owner a valuable option to buy the shares at a fixed price at any point over the following five years.\n" +
"If Sheikh Mansour profited personally, then Barclays may have breached anti-corruption laws aimed at preventing payments to government officials.\n" +
"Jeremy Carver, a lawyer for Transparency International, said Barclays may be at fault if government officials benefitted from the deal.\n" +
"\"You have to worry not because Sheikh Mansour may or may not be doing something wrong, you have to worry because you may be doing something wrong as a bank.\n" +
"\"You may be committing a crime, you may be paying a bribe if you have not got it straight as to which capacity the person you are dealing with is acting.\"\n" +
"\n" +
"Barclays issued the warrants to a Jersey company called PCP Gulf Invest 3, which represented the beneficial interests of Sheikh Mansour.\n" +
"Control of the company was then transferred from the Sheikh to IPIC, then from IPIC to an Abu Dhabi official and then eventually back to Sheikh Mansour.\n" +
"It is impossible to tell who benefited from the warrants, because all of these transactions took place offshore.\n" +
"However, the 758 million shares associated with the warrants were bought at significantly below the market price and they now belong to Sheikh Mansour.\n" +
"Barclays said the change in ownership of the offshore company had no bearing on the transaction or required approvals.\n" +
"The bank said in a statement that it had \"repeatedly demonstrated to Panorama why the allegations which have been put to us are not justified\".\n" +
"\"The Board of Barclays took the decision on capital raising in 2008 on the basis of the best interest of shareholder and its other stakeholders, including UK taxpayers,\" it said.\n" +
"\"Barclays performance relative to other UK banks which accepted government funding, especially on key measures such as lending growth, demonstrates unequivocally that it was the correct decision for Barclays, its shareholders, its customers and clients, as well as the UK.\"\n" +
"Neither Sheikh Mansour nor IPIC responded to questions raised by Panorama.\n" +
"In August last year, the UK's Serious Fraud Office said it had started an investigation into commercial arrangements between the bank and Qatar Holding LLC, part of sovereign wealth fund Qatar Investment Authority.";
        //System.out.println(testString);
        try {
            AnalyzedText at = tr.analyze(testString);
            Response rp = at.getResponse();

            ScoredCategory c = rp.getCategories().get(0);

            List<String> dates = new ArrayList<>();
            String temp;
            List<String> properNames = new ArrayList<>();
            for (Sentence se : rp.getSentences()) {
                for (Word w : se.getWords()) {
                    if (w.getNounPhrases().size() != 0) {
                        temp = retrieveNouns(w.getNounPhrases().get(0));
                        if (!temp.isEmpty()) {
                            properNames.add(temp);
                        }
                    }
                }
                List<String> tempList=new ArrayList<>();
                for(int i=0;i<properNames.size();i++){
                    if(!tempList.contains(properNames.get(i))){
                        tempList.add(properNames.get(i));
                    }
                }
                properNames=tempList;
                /*
                Set<String> nounsSet=new HashSet<>();
                nounsSet.addAll(properNames);
                properNames.clear();
                properNames.addAll(nounsSet);
                */
            }
            for(int i=properNames.size()-1;i>-1;i--){
                    System.out.println("NOUNS: "+properNames.get(i));
                }
            
        } catch (Exception e) {
            System.out.println("ERROR -" + e.getMessage() + e.getClass());
        }

    }

    private void testRetrieveNNP(Word np) {
        System.out.println("IN TEST RETRIEVE");
        System.out.println(retrieveProperNoun(np));
    }

    private String retrieveSentence(Sentence sentence) {
        String sent = "";
        for (Word word : sentence.getWords()) {
            sent += " " + word.getToken();
        }
        return sent;
    }

    private boolean isDate(Entity entity) {
        for (String type : entity.getDBPediaTypes()) {
            if (type.equalsIgnoreCase("time")) {
                return true;
            }
        }
        return false;
    }

    /**
     * NOT WORKING
     *
     * @param dates
     * @return
     */
    private String returnTimeFrame(List<String> dates) {
        String date = "";
        if (dates.size() == 2) {
            date = dates.get(0) + "-" + dates.get(1);
        }
        return date;
    }

    /**
     * WORKS Retrieves the category name & removes unnecessary elements.
     *
     * @param category
     * @return
     */
    private String getCategory(String category) {
        String cat = category;
        int trunkIndex = category.length() - 1;
        if (category.contains(">")) {
            trunkIndex = (category.lastIndexOf('>')) + 1;
            cat = category.substring(trunkIndex);
        }
        return cat;
    }

    private boolean isDTorJJ(Word word) {
        String jjRegex = "JJ[S]?\\b";
        if (word.getPartOfSpeech().equals("JJ") || word.getPartOfSpeech().matches(jjRegex)) {
            return true;
        }
        return false;
    }

    private String retrieveNouns(NounPhrase nounPhrase) {
        String nouns = "";
        boolean hasNNP = false;
        List<Word> words = nounPhrase.getWords();
        for (int i = 0; i < words.size(); i++) {
            Word word = words.get(i);
            if (isDTorJJ(word)) {
                words.remove(i);
            }
            if (isProperNoun(word)) {
                hasNNP = true;
            }
        }
        if (hasNNP) {
            for (Word w : words) {
                nouns += w.getToken() + " ";
            }
        }

        return nouns.trim();
        /*
        for(Word s : words){
            System.out.println("WORD: "+s.getToken());
        }*/
        //List<String> nounList=new ArrayList<>();
        //int nnpCtr=0;
        //List<String> nnpIndexes=new ArrayList<>();
        /*
        for(int i=0;i<words.size();i++){
            if(isProperNoun(words.get(i))){
                nnpIndexes.add(i+"");
                nnpCtr++;
            }*///else
        // i++;
        /* System.out.println("NNPCTR: "+nnpCtr+" I:"+i );
        }
        System.out.println("NNPCTR: "+nnpCtr + " WORDSSIZE: "+words.size());
        if(nnpCtr<words.size()){
            System.out.println("IN LOOP");
            String tempNoun="";
            int index1=0;
            int index2=0;
            for(int i=0;i<nnpIndexes.size();i++){
                 index1=Integer.parseInt(nnpIndexes.get(i));
                if((i++) < nnpIndexes.size())
                    index2=Integer.parseInt(nnpIndexes.get(i++));
                System.out.println("VALUE OF INDEX1: "+index1+" AND INDEX2:"+index2);
                //i=index2-1;
                if((index2-index1) > 1){
                    if(!tempNoun.isEmpty()){
                        nounList.add(tempNoun);
                        tempNoun="";
                    }
                    nounList.add(words.get(index1).getToken());
                    nnpIndexes.remove(i);
                }else{
                    tempNoun+= words.get(index1).getToken()+" ";
                    nnpIndexes.remove(i);
                }
                i--;
            }
        }else{
            System.out.println("IN ELSE");
            String noun="";
            for(String index : nnpIndexes){
                noun+=words.get(Integer.parseInt(index))+" ";
            }
            noun=noun.trim();
            nounList.add(noun);
        }
        System.out.println("LEAVING NOUNS: "+nounList.size());
        return nounList;
         */
    }

    private String retrieveProperNoun(Word word) {
        boolean isAllNNP = true;
        String properNoun = "";
        int nnpCtr = 0;
        List<String> nnpIndexes = new ArrayList<>();
        List<Word> words = new ArrayList<>();
        if (isProperNoun(word)) {
            nnpCtr++;
            nnpIndexes.add(word.getPosition() + "");
            if (word.getNounPhrases() == null) {
                properNoun = word.getToken();
            } else {
                NounPhrase np = word.getNounPhrases().get(0);
                words = np.getWords();
                for (Word w : np.getWords()) {
                    if (isProperNoun(w)) {
                        if (!nnpIndexes.contains(w.getPosition() + "")) {
                            nnpCtr++;
                            nnpIndexes.add(w.getPosition() + "");
                        }
                    } else {
                        isAllNNP = false;
                    }
                }
            }
        } else {
            isAllNNP = false;
        }
        System.out.println("END OF METHOD: " + nnpIndexes.size());

        if (isAllNNP) {
            for (String s : nnpIndexes) {
                properNoun += words.get(Integer.parseInt(s)).getToken() + " ";
            }
        } else {
            System.out.println("IT WASNT ALL NNP");
        }
        properNoun = properNoun.substring(0, properNoun.length() - 1);
        return properNoun;
    }

    private boolean isProperNoun(Word word) {
        if (word.getPartOfSpeech().matches("NNP[S]?\\b")) {
            return true;
        }
        return false;
    }

    /*
    private String retrieveProperNoun(Word word) {
        String properNoun = "";
        int nnpCtr=0;
        List<String> nnpIndexes=new ArrayList<>();
        //int primaryWordIndex=word.getPosition();
        if (word.getPartOfSpeech().matches("NNP[S]?\\b")) {
            nnpCtr++;
            nnpIndexes.add(word.getPosition()+"");
            if (word.getNounPhrases() == null) {
                properNoun = word.getToken();
            } else {
                for (NounPhrase np : word.getNounPhrases()) {
                    for (Word npw : np.getWords()) {
                        if(!nnpIndexes.contains(npw.getPosition()+"")){
                            if (npw.getPartOfSpeech().matches("NNP[S]?\\b")) {
                            nnpCtr++;
                            nnpIndexes.add(npw.getPosition()+"");
                        }
                        
                            /*
                            int tempWordIndex=npw.getPosition();
                            if(tempWordIndex == primaryWordIndex -1){
                                properNoun= npw.getToken()+" "+properNoun;
                            }else if(tempWordIndex == primaryWordIndex+1){
                                properNoun+=" "+npw.getToken();
                            }
                            //properNoun+=" "+word.getToken();
                            
                        }
                    }
                    if(nnpCtr>1){
                        System.out.println("SHOWING INDEX LIST WITH SIZE: "+nnpIndexes.size());
                        for(String s : nnpIndexes){
                            System.out.println("INDEX: "+s);
                        }
  
                    }
                }
            }
        }
        return properNoun;
    }*/

 /*
    private String retrieveNounPhrase(Word word){
        for(NounPhrase np:word.getNounPhrases()){
            
        }
    }*/
}

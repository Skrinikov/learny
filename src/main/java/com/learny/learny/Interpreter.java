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
        String testString = "Barclays misled shareholders and the public about one of the biggest investments in the bank's history, a BBC Panorama investigation has found.\n"
                + "The bank announced in 2008 that Manchester City owner Sheikh Mansour had agreed to invest more than £3bn.\n"
                + "But the BBC found that the money, which helped Barclays avoid a bailout by British taxpayers, actually came from the Abu Dhabi government.\n"
                + "Barclays said the mistake in its accounts was \"a drafting error\".\n"
                + "Unlike RBS and Lloyds TSB, Barclays narrowly avoided having to request a government bailout late in 2008 after it was rescued by £7bn worth of new investment, most of which came from the gulf states of Qatar and Abu Dhabi.\n"
                + "Half of the cash was supposed to be coming from Sheikh Mansour.\n"
                + "But Barclays has admitted it was told the investor might change shortly before shareholders voted to approve the deal on 24 November 2008.\n"
                + "But instead of telling shareholders, the bank remained silent until the change of investor was confirmed a few hours later.\n"
                + "\n"
                + "The bank said it subsequently provided \"appropriate disclosure\" in three prospectuses that were issued the following day.\n"
                + "But the disclosure was buried deep in the small print and said that Sheikh Mansour \"has arranged for his investment…to be funded by an Abu Dhabi governmental investment vehicle, which will become the indirect shareholder\".\n"
                + "Barclays still used the phrase \"his investment\", even though it knew Sheikh Mansour was not actually investing in the bank at the time.\n"
                + "The bank continued to mislead shareholders in its annual reports of 2008 and 2009, both of which identified Sheikh Mansour as the investor\n"
                + "Barclays said the mistake in its accounts was \"simply a drafting error\" and that the information provided in the prospectuses was \"entirely appropriate in all the circumstances\".\n"
                + "The bank also said: \"The shareholders meeting had already taken place and there was therefore no need to issue press releases or additional formal communications to shareholders/other market participants.\"\n"
                + "\n"
                + "Professor Alistair Milne, an expert on financial regulation in the City, said banks are expected to release accurate information about major deals.\n"
                + "\"Any discrepancy of that kind is serious because it raises questions in the minds of investors. Every bank is well aware the annual report is a critical document and a huge amount of time and attention is put in to trying to get all the details correct.\"\n"
                + "The Abu Dhabi government investment vehicle that provided the funding, International Petroleum Investment Company (IPIC), was not mentioned in any Barclays' announcements until six months after the deal.\n"
                + "\n"
                + "The chairman of IPIC is Sheikh Mansour. Although the Sheikh did not invest any of his own money in Barclays at the time, a company he controlled was initially issued warrants to buy 758 million shares in the bank.\n"
                + "The warrants gave the owner a valuable option to buy the shares at a fixed price at any point over the following five years.\n"
                + "If Sheikh Mansour profited personally, then Barclays may have breached anti-corruption laws aimed at preventing payments to government officials.\n"
                + "Jeremy Carver, a lawyer for Transparency International, said Barclays may be at fault if government officials benefitted from the deal.\n"
                + "\"You have to worry not because Sheikh Mansour may or may not be doing something wrong, you have to worry because you may be doing something wrong as a bank.\n"
                + "\"You may be committing a crime, you may be paying a bribe if you have not got it straight as to which capacity the person you are dealing with is acting.\"\n"
                + "\n"
                + "Barclays issued the warrants to a Jersey company called PCP Gulf Invest 3, which represented the beneficial interests of Sheikh Mansour.\n"
                + "Control of the company was then transferred from the Sheikh to IPIC, then from IPIC to an Abu Dhabi official and then eventually back to Sheikh Mansour.\n"
                + "It is impossible to tell who benefited from the warrants, because all of these transactions took place offshore.\n"
                + "However, the 758 million shares associated with the warrants were bought at significantly below the market price and they now belong to Sheikh Mansour.\n"
                + "Barclays said the change in ownership of the offshore company had no bearing on the transaction or required approvals.\n"
                + "The bank said in a statement that it had \"repeatedly demonstrated to Panorama why the allegations which have been put to us are not justified\".\n"
                + "\"The Board of Barclays took the decision on capital raising in 2008 on the basis of the best interest of shareholder and its other stakeholders, including UK taxpayers,\" it said.\n"
                + "\"Barclays performance relative to other UK banks which accepted government funding, especially on key measures such as lending growth, demonstrates unequivocally that it was the correct decision for Barclays, its shareholders, its customers and clients, as well as the UK.\"\n"
                + "Neither Sheikh Mansour nor IPIC responded to questions raised by Panorama.\n"
                + "In August last year, the UK's Serious Fraud Office said it had started an investigation into commercial arrangements between the bank and Qatar Holding LLC, part of sovereign wealth fund Qatar Investment Authority.";
        //System.out.println(testString);
        try {
            AnalyzedText at = tr.analyze(testString);
            Response rp = at.getResponse();

            ScoredCategory c = rp.getCategories().get(0);

            List<String> dates = new ArrayList<>();
            String temp;
            List<String> properNames = new ArrayList<>();
            for (Sentence se : rp.getSentences()) {
                analyzeSentence(se);
                /*
                for (Word w : se.getWords()) {
                    if (w.getNounPhrases().size() != 0) {
                        temp = retrieveNouns(w.getNounPhrases().get(0));
                        if (!temp.isEmpty()) {
                            properNames.add(temp);
                        }
                    }
                }
                List<String> tempList = new ArrayList<>();
                for (int i = 0; i < properNames.size(); i++) {
                    if (!tempList.contains(properNames.get(i))) {
                        tempList.add(properNames.get(i));
                    }
                }
                properNames = tempList;
            */}
           /*for(String s : properNames){
               System.out.println("NP: "+s);
           }*/

        } catch (Exception e) {
            System.out.println("ERROR -" + e.getMessage() + e.getClass());
        }

    }

    private String retrieveSentence(Sentence sentence) {
        String sent = "";
        for (Word word : sentence.getWords()) {
            sent += " " + word.getToken();
        }
        return sent;
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
        
        if (word.getPartOfSpeech().equals("DT") 
                || word.getPartOfSpeech().matches(jjRegex)) {
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

    /**
     * Retrives the relevant keywords of the document.
     *
     * @param text String
     * @return List of keywords
     */
    public List<String> getKeyWords(String text) {
        List<String> keywords = new ArrayList<>();

        try {
            AnalyzedText at = tr.analyze(text);
            keywords = getMostRelevantEntities(at.getResponse());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return keywords;
    }

    /**
     * Extract relevant keywords from the input text
     *
     * @param rp Response
     * @return List of keywords
     */
    private List<String> getMostRelevantEntities(Response rp) {
        List<Entity> entities = rp.getEntities();
        List<String> subjects = new ArrayList<>();
        if (entities != null) {
            for (Entity entity : entities) {
                if (entity.getRelevanceScore() > 0.4 
                        && entity.getConfidenceScore() > 2) {
                    if (!listContains(subjects, entity.getMatchedText())) {
                        System.out.println("Entity: " + entity.getMatchedText() 
                                + " - Score: " + entity.getRelevanceScore());
                        subjects.add(entity.getMatchedText());
                    }
                }
            }
        }

        return subjects;

    }

    /**
     * Check if list contains a certain value
     *
     * @param set
     * @param value
     * @return
     */
    private boolean listContains(List<String> set, String value) {
        for (String data : set) {
            if (data.equalsIgnoreCase(value)) {
                return true;
            }
        }
        return false;
    }

    private void analyzeSentence(Sentence se) {
        boolean hasNumber = false;
        boolean hasName = false;

        for (Word w : se.getWords()) {
            if (w.getPartOfSpeech().equals("CD")) {
                hasNumber = true;
            }
            if (w.getPartOfSpeech().equals("NNP")) {
                hasName = true;
            }
        }

        if (hasNumber) {
            createNumberBullet(se);
        }
    }

    /**
     * This methods assumes there were no Proper Names in the sentence
     *
     * @param se
     * @return
     */
    private String createNumberBullet(Sentence se) {
        String bullet = "";

        List<String> subject = new ArrayList<>();
        String tempDate = "";
        boolean addVerb = false;
        int ctr = 0;
        int dateCtr = 0;

        Word verb = null;

        for (Word w : se.getWords()) {
            if (w.getPartOfSpeech().equals("CD")) {
                System.out.println(w.getEntities().get(0).getDBPediaTypes().get(0));
                if (checkWordForDate(se, w.getPosition())) {
                    System.out.println("date" + w.getToken());
                    dateCtr++;
                    tempDate = checkForRange(se, w.getPosition());

                    for (int i = 0; i < subject.size(); i++) {
                        bullet += subject.get(i);
                        if (i + 1 < subject.size()) {
                            bullet += " ";
                        }
                    }
                    subject = new ArrayList<>();
                    if (bullet.trim().length() < 1) {
                        bullet = tempDate + " - ";
                    } else {
                        bullet += ", " + tempDate + " ";
                    }

                } else {
                    addVerb = true;
                }

            } else if (w.getPartOfSpeech().matches("VB[DGNPZ]") && verb == null) {
                verb = w;
            } else {

                if (w.getNounPhrases().size() > 0) {
                    for (Word w2 : w.getNounPhrases().get(0).getWords()) {
                        if (!w2.getPartOfSpeech().equals("DT")) {
                            subject.add(w2.getToken());
                        }
                    }

                    List<String> temp = new ArrayList<>();
                    for (int i = 0; i < subject.size(); i++) {
                        if (!temp.contains(subject.get(i))) {
                            temp.add(subject.get(i));
                        }
                    }
                    subject = temp;

                }

            }

        }

        if (subject.size() > 0) {
            for (int i = 0; i < subject.size(); i++) {
                bullet += subject.get(i);
                if (i + 1 < subject.size()) {
                    bullet += " ";
                }
            }
        }

        if (verb != null && addVerb) {
            bullet += " " + verb.getToken();
        }

        System.out.println(bullet);

        return bullet;
    }

    /**
     * Please work.
     *
     * @param se
     * @return
     */
    private boolean checkWordForDate(Sentence se, int pos) {
        List<Word> words = se.getWords();
        //TODO add TO
        if (pos + 2 == words.size() && words.get(pos).getPartOfSpeech().equals("CD") && words.get(pos).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("Time")) {
            System.out.println("Time");
            return true;
        }
        if (pos >= 1 && words.get(pos - 1).getPartOfSpeech().equals("IN") && pos + 1 < words.size() && words.get(pos + 1).getToken().matches("[,.]")) {
            if (words.get(pos).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("Time")) {
                return true;
            }
        }
        if (pos + 1 < words.size() && words.get(pos).getPartOfSpeech().equals("CD")) {
            if (words.get(pos + 1).getPartOfSpeech().matches("VB[DGNPZ]") || words.get(pos + 1).getPartOfSpeech().equals("TO") || words.get(pos + 1).getPartOfSpeech().equals("IN") || words.get(pos + 1).getPartOfSpeech().equals("CC")) {
                if (words.get(pos + 1).getToken().equals("is") || words.get(pos + 1).getToken().equals("was") || words.get(pos + 1).getToken().equals("will")) {
                    if (words.get(pos + 2).getNounPhrases() != null || words.get(pos + 3).getNounPhrases() != null) {
                        return false;
                    }
                }
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param se
     * @param firstVerbPos
     * @return
     */
    private String checkForRange(Sentence se, int firstVerbPos) {
        System.out.println("Hello");
        List<Word> words = se.getWords();
        if (firstVerbPos + 2 < words.size() && words.get(firstVerbPos + 2).getEntities().size() > 0 && words.get(firstVerbPos + 2).getEntities().get(0).getDBPediaTypes().size() > 0 && words.get(firstVerbPos + 2).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
            return words.get(firstVerbPos).getToken() + " to " + words.get(firstVerbPos + 2).getToken();
        } else {
            if (firstVerbPos + 3 < words.size() && words.get(firstVerbPos + 3).getEntities().size() > 0 && words.get(firstVerbPos + 3).getEntities().get(0).getDBPediaTypes().size() > 0 && words.get(firstVerbPos + 3).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
                return words.get(firstVerbPos).getToken() + " to " + words.get(firstVerbPos + 3).getToken();
            } else {
                if (firstVerbPos + 4 < words.size() && words.get(firstVerbPos + 4).getEntities().size() > 0 && words.get(firstVerbPos + 4).getEntities().get(0).getDBPediaTypes().size() > 0 && words.get(firstVerbPos + 4).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
                    return words.get(firstVerbPos).getToken() + " to " + words.get(firstVerbPos + 4).getToken();
                }
            }
        }
        System.out.println(words.get(firstVerbPos).getToken());
        return words.get(firstVerbPos).getToken();
    }

}

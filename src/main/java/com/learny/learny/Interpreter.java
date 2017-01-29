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

    public void analyzeTest() throws Exception {
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
        String testString = "In 1986, George Bush created a conspiracy theory.";
        System.out.println(testString);
        try {
            AnalyzedText at = tr.analyze(testString);
            Response rp = at.getResponse();

            for (Sentence se : rp.getSentences()) {

                analyzeSentence(se);
            }
        } catch (Exception e) {
            throw new Exception(e.toString());
        }
    }

    private String retrieveSentence(Sentence sentence) {
        String sent = "";
        for (Word word : sentence.getWords()) {
            sent += " " + word.getToken();
        }
        return sent;
    }

    private boolean isDate(Entity entity) {
        System.out.println("IN ISDATE");
        for (String type : entity.getDBPediaTypes()) {
            System.out.println("TYPE: " + type);
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

    private void retrieveMostRelevantTopic(Response rp) {

    }

    /*
    private String retrieveNounPhrase(Word word){
        for(NounPhrase np:word.getNounPhrases()){
            
        }
    }*/
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
        if(pos >= 1 && words.get(pos - 1).getPartOfSpeech().equals("IN") && pos + 1 < words.size() && words.get(pos+1).getToken().matches("[,.]")){
            if(words.get(pos).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("Time"))
                return true;
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

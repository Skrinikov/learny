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
    private int wordOffset;
    private List<String> bullets;

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
        wordOffset = 0;
        bullets = new ArrayList<>();
    }

    public List<String> analyze(String testString) {
        if (testString == null || testString.isEmpty()) {
            List<String> errorList = new ArrayList<>();
            errorList.add("Error - No value inputed");
            return errorList;
        }
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
                 */
            }
            /*for(String s : properNames){
               System.out.println("NP: "+s);
           }*/

        } catch (Exception e) {
            System.out.println("ERROR -" + e.getMessage() + e.getClass());
        }

        return bullets;

    }

    private String retrieveSentence(Sentence sentence) {
        String sent = "";
        for (Word word : sentence.getWords()) {
            sent += " " + word.getToken();
        }
        return sent;
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
        //System.out.println("END OF METHOD: " + nnpIndexes.size());

        if (isAllNNP) {
            for (String s : nnpIndexes) {
                properNoun += words.get(Integer.parseInt(s)).getToken() + " ";
            }
        } else {
            //System.out.println("IT WASNT ALL NNP");
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
            e.printStackTrace();
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
        //System.out.println("Sentence " + se.getWords().size());
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

        String s = temp(se);
        bullets.add(s);

        wordOffset += se.getWords().size();
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
                //System.out.println(w.getEntities().get(0).getDBPediaTypes().get(0));
                if (checkWordForDate(se, w.getPosition() - wordOffset)) {
                    //System.out.println("date" + w.getToken());
                    dateCtr++;
                    tempDate = checkForRange(se, w.getPosition() - wordOffset);

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
                        if (w2.getRelations() == null) {
                            System.out.println("NULL");
                        }
                        if (w2.getRelations() != null && w2.getRelations().size() > 0) {
                            if (w2.getRelations().get(0).getPredicateWords() != null) {
                                System.out.println("HEllo");
                                subject.add(w2.getRelations().get(0).getPredicateWords().get(0).getToken());
                                System.out.println(w2.getRelations().get(0).getPredicateWords().get(0).getToken());

                            }
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

        //System.out.println(bullet);
        return bullet;
    }

    private String temp(Sentence se) {
        String out = "";
        String verb = null;

        List<String> subject = new ArrayList<>();
        List<String> time = new ArrayList<>();
        List<String> what = new ArrayList<>();

        int pos = 0;
        List<Word> words = se.getWords();

        while (pos < words.size()) {
            if (words.get(pos).getNounPhrases() != null && words.get(pos).getNounPhrases().size() > 0) {
                NounPhrase np = words.get(pos).getNounPhrases().get(0);
                String nouns = retrieveNouns(np);
                pos = np.getWords().get(np.getWords().size() - 1).getPosition() - wordOffset;

                if (nouns.length() < 1) {
                    checkIfNpRelevant(np);
                }

                if (nouns.length() > 2) {
                    if (verb == null) {
                        subject.add(nouns);
                    } else {
                        what.add(nouns);
                    }
                }
            } else if (words.get(pos).getPartOfSpeech().equals("CD")) {
                if (isMoney(words.get(pos))) {
                    what.add(words.get(pos).getToken());
                }
                if (isWordATime(words.get(pos))) {
                    time.add(words.get(pos).getToken());
                }
            } else if (words.get(pos).getPartOfSpeech().matches("VB[GD]")) {
                verb = words.get(pos).getToken();
            }
            pos++;
        }

        out = "In" +time.get(0) +  createSubject(subject) + " " + verb + " " + createSubject(what);
        return out;
    }

    private String createSubject(List<String> subj) {
        String subject = "";

        if (subj.size() == 1) {
            return subj.get(0);
        }

        for (int i = 0; i < subj.size(); i++) {
            if (i == subj.size() - 1) {
                subject += "and " + subj.get(i);
            } else {
                subject += subj.get(i) + ", ";
            }
        }

        return subject;
    }

    private boolean isMoney(Word w) {
        List<Entity> es = w.getEntities();
        if (es != null && es.size() > 0) {
            Entity e = es.get(0);
            List<String> d = e.getDBPediaTypes();
            if (d != null && d.size() > 0) {
                if (d.get(0).equalsIgnoreCase("Money")) {
                    return true;
                }
            }
        }
        return false;
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
            //System.out.println("Time");
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

    private String checkForRange(Sentence se, int firstVerbPos) {
        List<Word> words = se.getWords();

        for (int i = 2; i < 4; i++) {
            if (firstVerbPos + i < words.size() && words.get(firstVerbPos + i).getEntities().size() > 0 && words.get(firstVerbPos + i).getEntities().get(0).getDBPediaTypes() != null && words.get(firstVerbPos + i).getEntities().get(0).getDBPediaTypes().size() > 0 && words.get(firstVerbPos + i).getEntities().get(0).getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
                return words.get(firstVerbPos).getToken() + " to " + words.get(firstVerbPos + i).getToken();
            }
        }

        return words.get(firstVerbPos).getToken();
    }

    private boolean isWordATime(Word get) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private String checkIfNpRelevant(NounPhrase np) {
        for (Word w : np.getWords()) {
            if (isMoney(w)) {
                return w.getToken();
            }
        }
        return "";
    }

}

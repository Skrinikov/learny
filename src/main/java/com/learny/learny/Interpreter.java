package com.learny.learny;

import com.textrazor.TextRazor;
import com.textrazor.annotations.AnalyzedText;
import com.textrazor.annotations.Entity;
import com.textrazor.annotations.NounPhrase;
import com.textrazor.annotations.Relation;
import com.textrazor.annotations.RelationParam;
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
        wordOffset = -1;
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
            
            
            //ScoredCategory c = rp.getCategories().get(0);

            List<String> dates = new ArrayList<>();
            String temp;
            List<String> properNames = new ArrayList<>();
            for (Sentence se : rp.getSentences()) {
                analyzeSentence(se);

            }


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
            if (w.getPartOfSpeech().matches("NNP[S]?\\b")) {
                hasName = true;
            }
        }
        if (!hasNumber && hasName) {

        }

        //bullets.add(createNumberBullet(se));
        String s = temp(se);
        bullets.add(s);

        wordOffset += se.getWords().size();
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
            if (words.get(pos).getPartOfSpeech().equals("CD")) {
                if (isMoney(words.get(pos))) {
                    what.add(words.get(pos).getToken());
                }
                if (isWordATime(words.get(pos))) {
                    time.add(words.get(pos).getToken());
                }
            } else if (words.get(pos).getNounPhrases() != null && words.get(pos).getNounPhrases().size() > 0) {
                NounPhrase np = words.get(pos).getNounPhrases().get(0);
                String nouns = retrieveNouns(np);
                pos = np.getWords().get(np.getWords().size() - 1).getPosition() - wordOffset;

                if (nouns.length() < 1) {
                    nouns = checkIfNpRelevant(np);
                }

                if (nouns.length() > 2) {
                    if (verb == null) {
                        subject.add(nouns);
                    } else {
                        what.add(nouns);
                    }
                }
            } else if (words.get(pos).getPartOfSpeech().matches("VB[GD]") ) {
                //Verb
                verb = words.get(pos).getToken();

                // If something like had aggreed.
                if (pos + 1 < words.size() && words.get(pos + 1).getPartOfSpeech().matches("VB[GDN]")) {
                    verb += " " + words.get(pos + 1).getToken();
                    pos++;

                }
                
                // Had to...verb VB
                if (pos + 2 < words.size() && words.get(pos + 2).getPartOfSpeech().matches("VB")) {
                   verb += " to " + words.get(pos + 2).getToken();
                   pos += 2;
                }

                subject.addAll(what);
                what.clear();
            }
            pos++;
        }
        if (time.size() > 0) {
            out = "In " + time.get(0) + ", ";
        }

        out += " " + createSubject(subject) + " " + verb + " " + createSubject(what);
        System.out.println(out);
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
     * Checks if a Word object is categorized as a Time type
     *
     * @param word
     * @return
     */
    private boolean isWordATime(Word word) {
        if (!word.getPartOfSpeech().equals("CD")) {
            return false;
        }
        if(word.getEntities() == null || word.getEntities().size() <= 0)
            return false;
        Entity entity = word.getEntities().get(0);
        if (!entity.getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the word object is a "to", a "preposition subordinate
     * conjunctions", or a "coordinating conjunction".
     *
     * @param word
     * @return
     */
    private boolean isWordTOINCC(Word word) {
        String wordPOS = word.getPartOfSpeech();

        if (wordPOS.equals("TO")) {
            return true;
        }
        if (wordPOS.equals("IN")) {
            return true;
        }
        if (wordPOS.equals("CC")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the inputed word object is "is", "was", or "will".
     *
     * @param word
     * @return
     */
    private boolean isWordIsWasWill(Word word) {
        String wordToken = word.getToken();

        if (wordToken.equalsIgnoreCase("is")) {
            return true;
        }
        if (wordToken.equalsIgnoreCase("was")) {
            return true;
        }
        if (wordToken.equalsIgnoreCase("will")) {
            return true;
        }
        return false;
    }

    /**
     * Checks if the inputed word object is part of a noun phrase.
     *
     * @param word
     * @return
     */
    private boolean doesWordHasNP(Word word) {
        List<NounPhrase> nps = word.getNounPhrases();

        if (nps == null) {
            return false;
        }
        if (nps.size() < 1) {
            return false;
        }
        if(word.getEntities() == null || word.getEntities().size() < 1)
            return false;
        Entity entity = word.getEntities().get(0);
        if (entity.getDBPediaTypes().get(0).equalsIgnoreCase("time")) {
            return false;
        }
        return true;
    }



   
   


    /**
     * Checks if a Word object is categorized as a Time type
     *
     * @param word
     * @return
     */
    
    /**
     * Checks if the inputed word object is part of a noun phrase.
     * @param word
     * @return 
     */
    

    /**
     * Check if the inputed sentence contains a Time type entity.
     *
     * @param se
     * @return
     */
    private boolean checkWordForDate(Sentence se, int pos) {
        List<Word> words = se.getWords();
        Word currWord = words.get(pos);
        Word nextWord = words.get(pos+1);
        
        if ((pos + 2) == words.size()) {
            if (isWordATime(currWord)) {
                return true;
            }
        }
        if ((pos >= 1) && words.get(pos - 1).getPartOfSpeech().equals("IN")
                && (pos + 1 < words.size())
                && nextWord.getToken().matches("[,.]")) {
            if (isWordATime(currWord)) {
                return true;
            }
        }
        if (!((pos + 1) < words.size())) {
            return false;
        }
        if (currWord.getPartOfSpeech().equals("CD")) {
            if (isWordTOINCC(nextWord)) {
                if (isWordIsWasWill(nextWord)) {
                        if(doesWordHasNP(words.get(pos+2))){
                            return false;
                        }
                        if(doesWordHasNP(words.get(pos+3))){
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

    private String checkIfNpRelevant(NounPhrase np) {
        for (int i = 0; i < np.getWords().size(); i++) {
            if (isMoney(np.getWords().get(i))) {
                System.out.println("Yes " + np.getWords().get(i).getToken() + np.getWords().get(i + 1).getToken());
                return np.getWords().get(i).getToken() + np.getWords().get(i + 1).getToken();
            }
        }
        return "";
    }

}

package ZeroFrame.Analysis.SpeechConfiguration;

import edu.cmu.sphinx.jsgf.JSGFGrammar;
import edu.cmu.sphinx.jsgf.JSGFRuleGrammarFactory;
import edu.cmu.sphinx.jsgf.JSGFRuleGrammarManager;
import edu.cmu.sphinx.jsgf.rule.JSGFRule;
import edu.cmu.sphinx.linguist.acoustic.UnitManager;
import edu.cmu.sphinx.linguist.acoustic.tiedstate.Sphinx3Loader;
import edu.cmu.sphinx.linguist.acoustic.tiedstate.TiedStateAcousticModel;
import edu.cmu.sphinx.linguist.dictionary.FastDictionary;
import edu.cmu.sphinx.linguist.flat.FlatLinguist;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;

public class GenericConfiguration extends Sphinx4BaseConfiguration {

    public GenericConfiguration() throws MalformedURLException, URISyntaxException, ClassNotFoundException {
        super();
    }

    protected void initCommon() {
        super.initCommon();

        this.absoluteBeamWidth = -1;
        this.relativeBeamWidth = 1E-80;
        this.wordInsertionProbability = 1E-36;
        this.languageWeight = 8.0f;
    }

    protected void initModels() throws MalformedURLException, URISyntaxException, ClassNotFoundException {

        this.unitManager = new UnitManager();
        
        //I pulled this code directly from the sphinx website, but the following line seems to not be correct
        //I've made an amendment below        
       /* this.modelLoader = new Sphinx3Loader(
                "resource:/TIDIGITS_8gau_13dCep_16k_40mel_130Hz_6800Hz",
                "wd_dependent_phone.500.mdef",
                "wd_dependent_phone.cd_continuous_8gau/",
                logMath,
                unitManager,
                true,
                true,
                39,
                0.0f,
                1e-7f,
                0.0001f,
                true);*/
        
        this.modelLoader = new Sphinx3Loader(
                "resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz",
                "mdef",
                "",
                logMath,
                unitManager,
                0.0f,
                1e-7f,
                0.0001f,
                true);

        this.model = new TiedStateAcousticModel(modelLoader, unitManager, true);

        this.dictionary = new FastDictionary(
                "resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/dict/cmudict.0.6d",
                "resource:/WSJ_8gau_13dCep_16k_40mel_130Hz_6800Hz/noisedict",
                new ArrayList<URL>(),
                false,
                "<sil>",
                false,
                false,
                unitManager);
    }

    protected void initLinguist() throws MalformedURLException, ClassNotFoundException {

        this.grammar = new JSGFGrammar(
                // URL baseURL,
                "file:" + ZeroFrame.Config.grammarFolder,
                logMath, // LogMath logMath,
                "zfgrammar", // String grammarName,
                false, // boolean showGrammar,
                false, // boolean optimizeGrammar,
                false, // boolean addSilenceWords,
                false, // boolean addFillerWords,
                dictionary // Dictionary dictionary
        );

        this.linguist = new FlatLinguist(
                model, // AcousticModel acousticModel,
                logMath, // LogMath logMath,
                grammar, // Grammar grammar,
                unitManager, // UnitManager unitManager,
                wordInsertionProbability, // double wordInsertionProbability,
                1.0, // double silenceInsertionProbability,
                1.0, // double fillerInsertionProbability,
                1.0, // double unitInsertionProbability,
                languageWeight, // float languageWeight,
                false, // boolean dumpGStates,
                false, // boolean showCompilationProgress,
                false, // boolean spreadWordProbabilitiesAcrossPronunciations,
                false, // boolean addOutOfGrammarBranch,
                1.0, // double outOfGrammarBranchProbability,
                1.0, // double phoneInsertionProbability,
                null // AcousticModel phoneLoopAcousticModel
        );
    }

}
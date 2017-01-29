/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.learny.learny;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author nonsense
 */
public class InterpreterTest {
    
    public InterpreterTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of analyzeTest method, of class Interpreter.
     */
    @Test
    public void testAnalyzeTest() throws Exception{
        System.out.println("analyzeTest");
        Interpreter instance = new Interpreter();
        instance.analyze("Barclays said the change in ownership of the offshore company had no bearing on the transaction or required approvals.\n" +
"The bank said in a statement that it had \"repeatedly demonstrated to Panorama why the allegations which have been put to us are not justified\".\n" +
"\"The Board of Barclays took the decision on capital raising in 2008 on the basis of the best interest of shareholder and its other stakeholders, including UK taxpayers,\" it said.\n" +
"\"Barclays performance relative to other UK banks which accepted government funding, especially on key measures such as lending growth, demonstrates unequivocally that it was the correct decision for Barclays, its shareholders, its customers and clients, as well as the UK.\"\n" +
"Neither Sheikh Mansour nor IPIC responded to questions raised by Panorama.\n" +
"In August last year, the UK's Serious Fraud Office said it had started an investigation into commercial arrangements between the bank and Qatar Holding LLC, part of sovereign wealth fund Qatar Investment Authority.");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

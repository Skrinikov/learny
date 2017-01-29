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
        instance.analyze("The bank announced in 2008 that Manchester City owner Sheikh Mansour had agreed to invest more than £3bn.");
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}

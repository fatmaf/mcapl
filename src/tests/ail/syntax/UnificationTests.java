// ----------------------------------------------------------------------------
// Copyright (C) 2013 Louise A. Dennis, and  Michael Fisher 
//
// This file is part of the Agent Infrastructure Layer (AIL)
// 
// The AIL is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 3 of the License, or (at your option) any later version.
// 
// The AIL is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
// Lesser General Public License for more details.
// 
// You should have received a copy of the GNU Lesser General Public
// License along with the AIL; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
// 
// To contact the authors:
// http://www.csc.liv.ac.uk/~lad
//
//----------------------------------------------------------------------------

package ail.syntax;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Collections;

import org.junit.Test;
import org.junit.Assert;

import ail.semantics.AILAgent;
import ail.util.AILexception;

/**
 * Tests of the Unification algorithm.
 * @author louiseadennis
 *
 */
public class UnificationTests {
	
	@Test public void dbNumTest() {
		Literal head = new Literal("head");
		GBelief ghead = new GBelief(head);
		ghead.setEB(new VarTerm("Any"));
		
		Literal head2 = new Literal("head");
		GBelief ghead2 = new GBelief(head2);
		ghead2.setEB(new VarTerm("Any"));
		
		Literal head3 = new Literal("head");
		GBelief ghead3 = new GBelief(head3);
		ghead3.setEB(new VarTerm("Any"));

		Literal body = new Literal("body");
		
		
		
		Rule rule = new Rule(ghead, new GBelief(body));
		Rule ruleC = rule.clone();
		
		ruleC.standardise_apart(ghead2, new Unifier(), Collections.<String>emptyList());
		
		Assert.assertTrue(ghead.toString().equals(ghead3.toString()));
		
		
		
	}
	
	@Test public void stringUnification() {
		Literal already_tried = new Literal("already_tried");
		already_tried.addTerm(new StringTermImpl("plan"));
		
		Literal already_tried_var = new Literal("already_tried");
		already_tried_var.addTerm(new VarTerm("P"));
		GBelief g_atv = new GBelief(already_tried_var);
		g_atv.setEB(new VarTerm("Any"));
		
		GBelief spt = new GBelief(new Literal("some_plan_tried"));
		spt.setEB(new VarTerm("Any"));

		Rule rule = new Rule(spt, g_atv);
		
		AILAgent ag = new AILAgent("agent");
		ag.addBel(already_tried, BeliefBase.TSelf);
		ag.addRule(rule);
		
		Assert.assertTrue(ag.believesyn(new Guard(new GBelief(new Literal("some_plan_tried"))), new Unifier()));
	}
	
	@Test public void composingUnifiers() {
		Unifier u1 = new Unifier();
		VarTerm p = new VarTerm("P");
		VarTerm p1 = new VarTerm("P1");
		u1.unifies(p,  p1);
		
		Unifier u2 = new Unifier();
		Predicate plan = new Predicate("plan");
		u2.unifies(p, plan);
		
		
		ArrayList<String> relevantNames = new ArrayList<String>();
		relevantNames.add("P1");
		
		u1.compose(u2);
		u1.pruneRedundantNames(relevantNames);
		
		p1.apply(u1);
		Assert.assertTrue(p1.equals(plan));
	}
	
	@Test public void standardiseApartPlans() {
		Unifier u1 = new Unifier();
		VarTerm ag = new VarTerm("Ag");
		Literal agent = new Literal("ag3");
		u1.unifies(ag, agent);
		
		VarTerm agp = new VarTerm("Ag");
		Literal my_name = new Literal("my_name");
		my_name.addTerm(agp);
		
		ArrayList<Guard> guards = new ArrayList<Guard>();
		guards.add(new Guard(new GBelief(my_name)));
		ArrayList<Deed> deeds = new ArrayList<Deed>();
		deeds.add(new Deed(new Action("do_something")));
		ArrayList<Deed> prf = new ArrayList<Deed>();
		Plan p = new Plan(new Event(Event.AILAddition, Event.AILBel, new Literal("someevent")), prf, guards, deeds);
		
		Literal agentg = new Literal("ag2");
		Literal my_nameg = new Literal("my_name");
		my_nameg.addTerm(agentg);

		AILAgent a = new AILAgent();
		a.addBel(my_nameg, BeliefBase.TSelf);
		
		ArrayList<Guard> iguards = new ArrayList<Guard>();
		iguards.add(new Guard(new GBelief()));
		ArrayList<Deed> ideeds = new ArrayList<Deed>();
		ideeds.add(new Deed(Deed.Dnpy));
		Intention i = new Intention(new Event(Event.AILAddition, Event.AILBel, new Literal("someevent")), ideeds, iguards, u1);
		a.setIntention(i);
		
		try {
			a.addPlan(p);
		} catch (AILexception e) {
			
		}
		
		Iterator<ApplicablePlan> it = a.getPL().getAllRelevant(new PredicateIndicator("+", new PredicateIndicator("someevent", 0)), a);
		Assert.assertTrue(it.hasNext());
	}
	
}

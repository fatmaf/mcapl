package gwendolen.partialplans.devel.tests;

import ail.mas.DefaultEnvironment;
import ail.syntax.Action;
import ail.syntax.Predicate;
import ail.syntax.Term;
import ail.syntax.Unifier;
import ail.util.AILexception;

public class MoveEnvWithNear_RevList extends DefaultEnvironment {

	String curloc="l1";

	@Override
	public Unifier executeAction(String agName, Action act) throws AILexception {
		if(act.getFunctor().equals("move_to"))
		{
			String newlocs = act.getTerm(0).toString();
			Predicate newloc = new Predicate("at");
			newloc.addTerm(new Predicate(newlocs));
			
			Predicate oldloc = new Predicate("at"); 
			oldloc.addTerm(new Predicate(curloc));
			
			removePercept(agName,oldloc); 
			addPercept(agName,newloc);
			curloc = newlocs;
			
			Predicate nearloc = new Predicate("near"); 
			nearloc.addTerm(new Predicate("l6"));
			//adding the near percept 
			//when we get to l4 we realise that we are "near l6" 
			if(newlocs.equalsIgnoreCase("l4"))
			{
				addPercept(agName,nearloc);
				
			}
			else
			{
				// it might be there already and we might have to remove it 
				// putting this in move because that is what decides it 
				removePercept(agName,nearloc);
			}
			
			
		}
//		else if (act.getFunctor().equals("reverse_list_n"))
//		{
//			Term list_to_reverse = act.getTerm(0); 
//			Term depth = act.getTerm(1);
//			System.out.println(
//					"Reverse list: "+list_to_reverse.toString()
//					+" up to "+depth.toString());
//			
//			
//		}
		Unifier u =super.executeAction(agName, act);
		return u;//super.executeAction(agName, act);
	}
	

}
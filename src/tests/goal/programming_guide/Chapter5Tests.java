package goal.programming_guide;

import org.junit.Test;

import ail.util.AJPF_w_AIL;
import gov.nasa.jpf.util.TypeRef;
import gov.nasa.jpf.util.test.TestJPF;

public class Chapter5Tests extends TestJPF {
	 static final String[] JPF_ARGS = {  "-show" };

	 @Test //----------------------------------------------------------------------
	  public void stackbuilder () {
	    if (verifyNoPropertyViolation(JPF_ARGS)){
	    	String filename =  "/src/examples/goal/programming_guide/chapter5/stackBuilder.ail";
	    	String prop_filename =  "/src/examples/goal/programming_guide/chapter5/stack.psl";
	    	String[] args = new String[3];
	    	args[0] = filename;
	    	args[1] = prop_filename;
	    	args[2] = "1";
	    	AJPF_w_AIL.run(args);
	 	 }
	  }
	 
	 @Test //----------------------------------------------------------------------
	  public void stackbuilderRandom () {
	    if (verifyPropertyViolation(new TypeRef("ajpf.MCAPLListener"), JPF_ARGS)){
	    	String filename =  "/src/examples/goal/programming_guide/chapter5/stackBuilderRandom.ail";
	    	String prop_filename =  "/src/examples/goal/programming_guide/chapter5/stack.psl";
	    	String[] args = new String[3];
	    	args[0] = filename;
	    	args[1] = prop_filename;
	    	args[2] = "2";
	    	AJPF_w_AIL.run(args);
	 	 }
	  }

}
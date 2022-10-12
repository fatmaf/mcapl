
ismember(H, [H | T]).
ismember(H, [X | T]) :-
	ismember(H,T).
	
append([], X, X).
append([ H | T], X , [ H | W ]) :- 
	append(T,X,W).

remove_from_list(Elem,[],[]).
remove_from_list(Elem,[Elem|L],L).
remove_from_list(Elem,[NotElem | L],[NotElem | NL]):-
	remove_from_list(Elem,L,NL).

remove_list_from_list([],L,L).	
remove_list_from_list([ToRemoveH | ToRemoveT ],L,NL):-
	remove_from_list(ToRemoveH,L,NL1),
	remove_list_from_list(ToRemoveT,NL1,NL). 


replaceFirst(_,_, [],[]).
replaceFirst(O,R,[O|T],[R|T]).
replaceFirst(O,R,[H|T],[H|T2]) :- 
			dif(H,O),
			replaceFirst(O,R,T,T2).

%combining the required and preferred specs
combined_specifications(CSpecs):-
	specification("Required",RSpec),
	specification("Preferred",PSpec),
	append(RSpec,PSpec,CSpecs).

%checking if a spec is satisfied 
satisfied(before(Per,Loc)):-
	visited(Per,Tp),
	visited(Loc,Tl),
	[Tp < Tl].
	
satisfied(before(Per,Loc)):-
	visited(Per,Tp),
	\not visited(Loc,Tl).
		
satisfied(before(Loc,Per)):-
	visited(Loc,Tl),
	visited(Per,Tp),
	[Tl < Tp].
	
satisfied(visit(Per)):-
	visited(Per,Tp).
	
satisfied_avoid(avoid(Per)):-
	\not visited(Per,Tp).
	
%removing satisfied specs from list 
filter_satisfied_specs([],[]).
filter_satisfied_specs([OrigSpecsH | OrigSpecsT], FiltSpecs):-
	satisfied(OrigSpecsH),
	filter_satisfied_specs(OrigSpecsT,FiltSpecs).
filter_satisfied_specs([OrigSpecsH | OrigSpecsT], [OrigSpecsH | FiltSpecs]):-
	filter_satisfied_specs(OrigSpecsT,FiltSpecs).

%listing satisfied and unsatisfied specs 
break_specs_satisfied_otherwise([],[],[]).
break_specs_satisfied_otherwise([OrigSpecsH | OrigSpecsT], [OrigSpecsH | OtherSatSpecs], Otherwise):-
	satisfied(OrigSpecsH),
	break_specs_satisfied_otherwise(OrigSpecsT,OtherSatSpecs,Otherwise).
break_specs_satisfied_otherwise([OrigSpecsH | OrigSpecsT],  OtherSatSpecs, [OrigSpecsH | Otherwise]):-
	break_specs_satisfied_otherwise(OrigSpecsT,OtherSatSpecs,Otherwise).

	
%when we're all done we'll only have avoid specs 
%so we can check if all we're left with is avoid specs 
%and just say we're done 
avoid_specs_only([]).
avoid_specs_only([avoid(X) | T]):-
	avoid_specs_only(T).

%checking if a spec is violated 
%have to avoid a location
violated(Per,avoid(Per)).

%if we were to visit per before loc
%and we've already visited loc
%and we haven't visited per 
%then we're going to violate this
violated(Per,before(Per,Loc)):-
	visited(Loc,Tloc),
	~visited(Per,Tper).
%if we have visited per before then
%we're not likely to violate the spec 

%if we were to visit loc before per 
%and we haven't visited loc 
%then we'll violate this 
violated(Per,before(Loc,Per)):-
	~visited(Loc,Tloc).
	
	

%getting all violated specs
violated_specifications(Per,[],[]).
	
violated_specifications(Per,[OrigSpecsH | OrigSpecsT],[OrigSpecsH | ViolSpecT]):-
	%so we get all the violated specifications 
	violated(Per,OrigSpecsH),
	violated_specifications(Per,OrigSpecsT,ViolSpecT).
	
violated_specifications(Per,[OrigSpecsH | OrigSpecsT], ViolSpecT):-
	violated_specifications(Per,OrigSpecsT,ViolSpecT).

%find the first spec we have a plan for 
first_spec_with_plan([],[],[]).

first_spec_with_plan([H | T],H,AllPlans):-
	plans(H,AllPlans).

first_spec_with_plan([H | T],Spec,Res):-
	first_spec_with_plan(T,Spec,Res).
	
%remove all specs from list that have plans except specified spec 
has_plans(Spec):-
	plans(Spec,Plans).

remove_plan_specs([],[],[]).
remove_plan_specs([HSL | TSL],OtherSpecs,[HSL | PlanSpecs] ):-
	has_plans(HSL),
	remove_plan_specs(TSL,OtherSpecs,PlanSpecs).
remove_plan_specs([HSL | TSL],[HSL | OtherSpecs],PlanSpecs ):-
	remove_plan_specs(TSL,OtherSpecs,PlanSpecs).

%find the first spec that has a plan and return that list of plans
first_spec_with_plan(Spec,AllSpecPlans):-

	updated_specifications("Required",RSpec),
	updated_specifications("Preferred",PSpec),
	%combine these 
	append(RSpec,PSpec,CSpec),
	
	%find the first spec we have a plan for 
	first_spec_with_plan(CSpec,Spec,AllSpecPlans).

%get a required spec without any of the specs we have plans for 
%spec - is the spec the planset is for (see first spec with plan)
%NewRNoPlanSpec is the updated req spec 
%NewRPlanSpec is the list of all the removed specs from req spec 
%we need the above for pspec_modified
rspec_with_no_otherplanspecs(Spec,NewRNoPlanSpec,NewRPlanSpec):-

	updated_specifications("Required",RSpec),
	remove_plan_specs(RSpec,RNoPlanSpec,RPlanSpec),
	remove_from_list(Spec,RPlanSpec,NewRPlanSpec),
	append([Spec],RNoPlanSpec,NewRNoPlanSpec).

%get an updated preferred spec
%the input is the list of specs from rspec we removed 
%cuz we had plans for them 
%weakening spec is the spec we get by tacking things on 
%and essentially not caring 
pspec_modified(NewRPlanSpec,WeakeningSpec):-

	updated_specifications("Preferred",PSpec),
	remove_plan_specs(PSpec,PNoPlanSpec,PPlanSpec),
	append(PNoPlanSpec,PPlanSpec,NewPPlanSpec),
	append(NewPPlanSpec,NewRPlanSpec,WeakeningSpec).

%helper stuff for plans 
get_plans_for_spec(S,[],[]).

get_plans_for_spec(S, [plan(X,Ind,Actions,HP) | TP], [plan(X,Ind,Actions,HP) | Res]) :-
	ismember(S,HP),
	get_plans_for_spec(S,TP,Res).  
	
get_plans_for_spec(S,[plan(X,Ind,Actions,HP) | TP], Res) :-
	get_plans_for_spec(S,TP,Res).

%getting plans without weakening 	
get_plans_for_speclist_no_weakening([],Plans,Plans).	
 
get_plans_for_speclist_no_weakening([H|T],Plans,PossiblePlans):-
	get_plans_for_spec(H,Plans,PlanSet),
	get_plans_for_speclist_no_weakening(T,PlanSet,PossiblePlans).

%more helper things 
check_plans_for_spec(L,[],L).
check_plans_for_spec(L,NL,NL).

%getting plans with weakening 
get_plans_for_speclist_weakening([],Plans,Plans).	
 
get_plans_for_speclist_weakening([H|T],Plans,PossiblePlans):-
	get_plans_for_spec(H,Plans,PlanSet),
	check_plans_for_spec(Plans,PlanSet,NewPlanSet),
	get_plans_for_speclist_weakening(T,NewPlanSet,PossiblePlans).
			

%finding a plan 
plan_exists(CurrentLoc,Goal,PlanIndex,PlanActions,
			PlanAnnots,[plan(CurrentLoc,PlanIndex,PlanActions,PlanAnnots) | T]).


plan_exists(CurrentLoc,Goal,PlanIndex,PlanActions,PlanAnnots,[H | T]):-
	plan_exists(CurrentLoc,Goal,PlanIndex,PlanActions,PlanAnnots,T).
	
plan_exists(Goal,Source,PlanIndex,PlanActions,PlanAnnots):-
	plans(Goal,L),
	plan_exists(Source,Goal,PlanIndex,PlanActions,PlanAnnots,L).


check_length(L,Len):-
	length(L,Len).
	
%updating plans 
%
updated_plans(PlanIndex,L,NewPlanAnnots,NewL):-
	replaceFirst(plan(Source,PlanIndex,PlanActions,PlanAnnots),plan(Source,PlanIndex,PlanActions,NewPlanAnnots),L,NewL).
	

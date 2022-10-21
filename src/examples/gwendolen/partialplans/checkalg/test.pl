specification("Required",[visit(l1),visit(l3),visit(l5)]).
specification("Preferred",[before(l1,l3),before(l1,l5),before(l3,l5),avoid(l6)]).


%//helpers
%//ismember
ismember(H, [H | T]).
ismember(H, [X | T]) :-
	ismember(H,T).

%//append(L1,L2,L1+L2)
append([],L2,L2).
append([HL1 | TL1],L2,[HL1 | L12]):-
    append(TL1,L2,L12).


%//unique
getunique([],[]).
getunique([H | T],Ul):-
    ismember(H,T),
    getunique(T,Ul).
getunique([H | T], [H | Ul]):-
    getunique(T,Ul).
%//combine specifications
combined_specifications(CSpecs):-
    specification("Required",RSpec),
    specification("Preferred",PSpec),
    append(RSpec,PSpec,CSpecs).

%// step 1
strip_locations_from_specs_wrapper(CSpecs,Locs):-
    strip_locations_from_specs(CSpecs,LocsNotUnique),
    getunique(LocsNotUnique,Locs).
strip_locations_from_spec(visit(Loc),[Loc]).
strip_locations_from_spec(avoid(Loc),[Loc]).
strip_locations_from_spec(before(Loc1,Loc2),Locs):-
    append([Loc1],[],Locst),
    append([Loc2],Locst,Locs).
strip_locations_from_specs([],[]).
strip_locations_from_specs([H | T],LocsN):-
    strip_locations_from_spec(H,SpecLocs),
    strip_locations_from_specs(T,Locs),
    append(SpecLocs,Locs,LocsN).
%%// strip locs from specs
%strip_locations_from_specs([],[]).
%strip_locations_from_specs([visit(Loc) | T],Locs):-
%    strip_locations_from_specs(T,Locs).
%strip_locations_from_specs([avoid(Loc) | T],[Loc | Locs]):-
%    strip_locations_from_specs(T,Locs).
%strip_locations_from_specs([before(Loc1,Loc2) | T],[Loc1 | Locs2]):-
%    strip_locations_from_specs(T,Locs),
%    append([Loc2],Locs,Locs2).

teststuff(Locs):-
    strip_locations_from_specs([before(l1,l2)],LocsP),
    getunique(LocsP,Locs).

parse_before(before([B1,B2]),B1,B2).
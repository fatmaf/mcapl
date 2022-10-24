update_visit_time_from_time(StartTime,TimeIndex,[],[]).
update_visit_time_from_time(StartTime,StartTime,[visit(Loc,StartTime) | Tail],[visit(Loc,NewTimeIndex) | Rest]):-
    NewTimeIndex is StartTime + 1,
    update_visit_time_from_time(StartTime,NewTimeIndex,Tail,Rest).
update_visit_time_from_time(StartTime,StartTime,[visit(Loc,TimeIndex) | Tail],[visit(Loc,TimeIndex) | Rest]):-
    update_visit_time_from_time(StartTime,StartTime,Tail,Rest).
update_visit_time_from_time(StartTime,TimeIndex,[visit(Loc,TimeIndex)| Tail],[visit(Loc,NewTimeIndex) | Rest]):-
     NewTimeIndex is TimeIndex + 1,
    update_visit_time_from_time(StartTime,TimeIndex,Tail,Rest).



dtmc

 module jpfModel
state : [0 ..76] init 0;
auavevade: bool init false;
auavland: bool init false;
pcollision: bool init false;
[] state = 0 -> 1.0:(state'=1) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 1 -> 1.0:(state'=2) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 2 -> 1.0:(state'=3) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 3 -> 1.0:(state'=4) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 4 -> 1.0:(state'=5) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 5 -> 1.0:(state'=6) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 6 -> 1.0:(state'=7) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 7 -> 1.0:(state'=8) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 8 -> 1.0:(state'=9) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 9 -> 1.0:(state'=10) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 10 -> 1.0:(state'=11) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 11 -> 1.0:(state'=12) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 12 -> 1.0:(state'=13) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 13 -> 1.0:(state'=14) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 14 -> 1.0:(state'=15) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 15 -> 1.0:(state'=16) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 16 -> 1.0:(state'=17) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 17 -> 1.0:(state'=18) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 18 -> 1.0:(state'=19) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 19 -> 1.0:(state'=20) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 20 -> 1.0:(state'=21) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 21 -> 1.0:(state'=22) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 22 -> 1.0:(state'=23) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 23 -> 1.0:(state'=24) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 24 -> 1.0:(state'=25) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 25 -> 1.0:(state'=26) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 26 -> 1.0:(state'=27) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 27 -> 1.0:(state'=28) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 28 -> 1.0:(state'=29) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 29 -> 1.0:(state'=30) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 30 -> 1.0:(state'=31) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 31 -> 1.0:(state'=32) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 32 -> 1.0:(state'=33) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 33 -> 1.0:(state'=34) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 34 -> 1.0:(state'=35) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 35 -> 1.0:(state'=36) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 36 -> 1.0:(state'=37) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 37 -> 1.0:(state'=38) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 38 -> 1.0:(state'=39) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 39 -> 1.0:(state'=40) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 40 -> 1.0:(state'=41) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 41 -> 1.0:(state'=42) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 42 -> 1.0:(state'=43) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 43 -> 1.0:(state'=44) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 44 -> 1.0:(state'=45) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 45 -> 1.0:(state'=46) & (auavevade'= false) & (auavland'= false) & (pcollision'= false);
[] state = 46 -> 1.0:(state'=47) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 47 -> 1.0:(state'=48) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 48 -> 1.0:(state'=49) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 49 -> 1.0:(state'=50) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 50 -> 1.0:(state'=51) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 51 -> 1.0:(state'=52) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 52 -> 1.0:(state'=53) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 53 -> 1.0:(state'=54) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 54 -> 1.0:(state'=55) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 55 -> 1.0:(state'=56) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 56 -> 1.0:(state'=57) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 57 -> 1.0:(state'=58) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 58 -> 1.0:(state'=59) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 59 -> 1.0:(state'=60) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 60 -> 1.0:(state'=61) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 61 -> 1.0:(state'=62) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 62 -> 1.0:(state'=63) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 63 -> 1.0:(state'=64) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 64 -> 1.0:(state'=65) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 65 -> 1.0:(state'=66) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 66 -> 1.0:(state'=67) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 67 -> 1.0:(state'=68) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 68 -> 1.0:(state'=69) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 69 -> 1.0:(state'=70) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 70 -> 1.0:(state'=71) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 71 -> 1.0:(state'=72) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 72 -> 1.0:(state'=73) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 73 -> 1.0:(state'=74) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 74 -> 1.0:(state'=75) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
[] state = 75 -> 1.0:(state'=76) & (auavevade'= false) & (auavland'= true) & (pcollision'= false);
endmodule
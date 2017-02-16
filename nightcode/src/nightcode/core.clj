(ns nightcode.core
  (:gen-class))

; Aufgabe 1.2
; a) Definieren Sie die Sequenz, die alle ganzen Zahlen von 100 bis -100
;    in absteigender Reihenfolge beinhaltet.
(def myseq (reverse (range -100 101)))
myseq

; b) Definieren Sie die Sequenz, die alle Quadratzahlen 
;    zwischen 0 bis 1000 beinhaltet.
(def square (fn [x] (* x x)))
(def lower1000 (filter (fn [x] (< x 1000))))
(transduce lower1000 conj (map square (range 1 50)))

; c) Definieren Sie die Sequenz, die alle Zahlen von 0 bis 1000 beinhaltet,
;    die nicht durch 3 teilbar sind.
(def mymod (filter (fn [x] (not= 0 (mod x 3)))))
(transduce mymod conj (range 0 1001))

; d) Definieren Sie die Sequenz, die alle Tupel [n, m] von ganzen Zahlen 
;    beinhaltet fur die gilt: 0 < n < 1000 ^ n2 < m ^ m ist minimal. 
;    Die Sequenz beginnt also mit [1,2], [2,5], [3,10], ...
(def getTuple (fn [x] [x (inc(* x x))]))
(getTuple 3)

(map getTuple (range 1001))

; e) Definieren Sie die Sequenz, die alle Zahlen mit genau 5 Ziffern
;    (dargestellt als 5 Tupel), die ein Palindrom sind enthalt, 
;    es soll auerdem gelten, dass die Ziern bis zur mittleren Zahl strikt 
;    aufsteigend sind. [0 2 3 2 0] ist also Teil der Sequenz, aber 
;    nicht [0 3 2 3 0] oder [1 1 1].











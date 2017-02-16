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

(def sevenAdder (fn [x] [(+ x 7) 9 19]))
(sevenAdder 243)

; e) Definieren Sie die Sequenz, die alle Zahlen mit genau 5 Ziffern
;    (dargestellt als 5 Tupel), die ein Palindrom sind enthalt, 
;    es soll auerdem gelten, dass die Ziern bis zur mittleren Zahl strikt 
;    aufsteigend sind. [0 2 3 2 0] ist also Teil der Sequenz, aber 
;    nicht [0 3 2 3 0] oder [1 1 1].
(for [x (range 0 10)
      y (range 0 10)
      z (range 0 10) :when (< x y z)] [x y z y x]) 

; Aufgabe 1.3 (Fibonacci)
; a) Schreiben Sie eine Funktion, die eine ganze Zahl z als Eingabe
; bekommt und eine Sequenz mit den ersten z Fibbonaccizahlen erzeugt.

; playing around
(def fibo (fn [x] 7))
(fibo 7711992)

; playing
(defn positive-numbers 
  ([] (positive-numbers 0))
  ([n] (lazy-seq (cons n (positive-numbers (inc n))))))

(take 5 (positive-numbers))

; gogo
(def fibonacci
  ((fn fib [a b]
     (lazy-seq (cons a (fib b (+ a b)))))
   0 1))

(take 20 fibonacci)


(def something
  ( (fn bla [a b c]
      (lazy-seq (cons a (bla b c (inc c)))))
    0 1 2))
(take 9 something)

(def bullshit(lazy-seq (cons 777 [1 2 3])))
(take 5 bullshit)


; Aufgabe 1.4
; remove duplicates
(defn remove-duplicates [x] (distinct x))
(remove-duplicates [])
(remove-duplicates nil)
(remove-duplicates [1 2 1])
(remove-duplicates [1 2 3 "dd" 2 :a 3])

(def isDuplicate? (fn [vec x] (contains? vec x)))
; for each element in vector
;   if is not duplicate
;        add to result list
(def my-dupli (fn [vec] (for [x vec :when (isDuplicate? vec x)] x)))
(my-dupli [1 2 3 4 4 5])

(for [x [0 1 2 3 4 5]
      :let [y (* x 3)]
      :when (even? y)]
  y)



(isDuplicate? [1 2 3] 2)
(def justreturn (fn [x] x))
(def isd (fn [vec] (filter (fn [x] (contains? vec x)) (map justreturn vec))))
(isd [1 2 3 3 4 5 6])

(first (drop 3 [1 2 3 4 5]))

(my-remove 2)

(def rem-dup (fn [vec] (map)))
(filter (check [ 1 2 3 ] 2))







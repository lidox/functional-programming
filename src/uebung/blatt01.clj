 (ns blatt01)

 ;; Let's get ready toooo RUMBLE!!!

 ;; Aufgabe 1 (Truthiness)

(if 0 :truthy :falsey)

(if nil :truthy :flasey)

;; http://clojure.org/api/cheatsheet
(println
  (map (fn [x] (if x :truthy :falsey)) [0
                                        false   ;; falsey
                                        :false
                                        nil     ;; falsey
                                        []
                                        ()
                                        {}
                                        #{}
                                        ""
                                        ]))

;; Aufgabe 1.2
(def beispiel-seq (range -100 101))
(println beispiel-seq)

(def desc-seq (reverse (range -100 101)))
(println desc-seq)

(println "Aufgabe 1.2 b) Quadratzahlen: 1² 2² 3² 4² 5² 6² ... x², solange x² < 1000")
(defn squere [x] (* x x))
(def squere-num (filter (fn [x] (< x 1001)) (map squere (range 0 101))))
(println squere-num)


;;c) Definieren Sie die Sequenz, die alle Zahlen von 0 bis 1000 beinhaltet,
;; die nicht durch 3 teilbar sind.
(println "Aufgabe 1.2 c)")
(def teilbar
  (remove (fn [x] (zero? (mod x 3))) (range 0 1001) ))
(println teilbar)

;; alternativ
(def nichtdurch3teilbar (filter (fn [x] (not= 0 (mod x 3)))(range 1 1001)))

;; d) Definieren Sie die Sequenz, die alle Tupel [n, m] von ganzen Zahlen beinhaltet für die gilt:
;; 0 < n < 1000 ∧ n^2 < m ∧ m ist minimal. Die Sequenz beginnt also mit [1,2], [2,5], [3,10], ...
(println "Aufgabe 1.2 d)")
(def getSeq (for [x (range 1 1000)] [x (inc (* x x))]))

;; e) Definieren Sie die Sequenz, die alle Zahlen mit genau 5 Ziffern (dargestellt als 5 Tupel), die
;; ein Palindrom sind enthält, es soll außerdem gelten, dass die Ziffern bis zur mittleren Zahl
;; strikt aufsteigend sind. [0 2 3 2 0] ist also Teil der Sequenz, aber nicht [0 3 2 3 0] oder [1 1 1 1 1].
(def palindromTuple (for [x (range 0 10)
                          y (range 0 10)
                          z (range 0 10) :when (< x y z)]
                      [x y z y x]))

;; Aufgabe 1.3
;; a) Schreiben Sie eine Funktion, die eine ganze Zahl z als Eingabe bekommt
;; und eine Sequenz mit den ersten z Fibbonaccizahlen erzeugt.
(reduce (fn [x empty] (conj x (+ (get x (- (count x) 1)) (get x (- (count x) 2)))))
        [0 1] (range 7))









































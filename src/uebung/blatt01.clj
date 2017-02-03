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


;; d) Definieren Sie die Sequenz, die alle Tupel [n, m] von ganzen Zahlen beinhaltet für die gilt:
;; 0 < n < 1000 ∧ n 2 < m ∧ m ist minimal. Die Sequenz beginnt also mit [1,2], [2,5], [3,10], ...
(println "Aufgabe 1.2 d)")




































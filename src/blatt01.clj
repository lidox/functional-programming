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
(defn my-filter [x] (< x 1001))
(def squere-num (filter (fn [x] (< x 1001)) (map squere (range 0 101))))
(println squere-num)

(defn a [x] (* x x))
;;(fn [x (* x x)])

(defn say [text] (println text))
(say "muhaha")
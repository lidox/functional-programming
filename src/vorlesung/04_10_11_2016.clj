(ns repl.core
  (:gen-class))


(set! *print-length* 20)

(comment 
  ;; ---------------------------------------------------------
  ;; WARMUP
  ;; ---------------------------------------------------------

  ;; Collections
  ;; - Listen sind als singly linked List implementiert
  ;; - Vektoren, Sets und Maps als Array Mapped Hash Tries
  ;; Wichtig für die Laufzeitcharakteristik: Structural Sharing

  ;; Seq ist eine Abstraktion, die meisten Funktionen,
  ;; die mit Coll
  ;; geben ein seq zurück.

  (map inc [1 2 3])
  ;;        ^
  ;;        seqable

  ;; => (2 3 4)
  ;;     ^
  ;;    seq

  ;; Wenn man eine Collection zu einem seq macht, verliert sie
  ;; ihre Laufzeiteigenschaften (Ausnahme: Listen)

  ;; Lazyness
  ;; Vorsicht bei Fragen nach der Länge einer unendlichen Sequenz

  ;; Auch wichtig: Niemals den Kopf festhalten bei grossen Listen
  ;; Don't hang (onto) your head
  ;; Lose your head

  ;; NICHT AUSFÜHREN!
  (let [r (range 1e9)] [(first r) (last r)]) 
  (let [r (range 1e9)] [(last r) (first r)]) 

  ;; Das Binding an r ist das Problem.

  ;; Im ersten Fall ist (last r) der letzte Ausdruck, d.h., das
  ;; Binding wird nicht mehr gebraucht und Werte werden durch
  ;; die GC abgeräumt.

  ;; Im zweiten Fall existiert das Binding noch weil (first r)
  ;; noch folgt deswegen muss die gesamte Collection im Speicher
  ;; gehalten werden


  ;; ---------------------------------------------------------
  ;; Nützliche Funktionen aus der Standard-Bibliothek
  ;; ---------------------------------------------------------

  ;; Haben wir schon gesehen: get, conj, cons, assoc dissoc
  ;; und einige HOF: map, filter, mapcat, reduce

  (mapv inc [1 3 4])

  ;; Funktionen mit Maps:

  ({:a 1, :b 2} :b)
  
  ;; Maps sind Funktionen, die einen Schlüssel bekommen und diesen in sich selber nachschlagen

  (:a {:a 1 :b 2})
  ;;Keywords sind Funktionen, die eine Assoziative Datenstruktur bekommen und sich selber in dieser nachschlagen



  ;; Sequenz der Schlüssel/Werte (effizient)
  (keys {:a 1 :b 2 "foo" 3 nil 4})
  (vals {:a 1 :b 2 "foo" 3 nil 4})

  ;; for expression (ähnlich: Python list comprehension)
  ;; (for [bindings] body)


  (for [user ["Itchy" "Scratchy"]]
    (str user " for president!"))

  (for [x [1 2 3 4]
        y [1 2 3 4]] [x :a y])

  (for [x [1 2 3 4]
        y [1 2 3 4]
        :when (< y x)] [:tuple x y])

  ;; (let [bindings] body)
  (let [user ["Itchy" "Scratchy"]]
    (str user " for president!"))


  ;; Quizz: Was gibt diese Funktion aus?
  (let [r (range 10)]
    (for [a r
          b r
          c r :when (and (even? a) (= a b c))]
      [a b c]))


  ;; contains?
  (contains? #{3 4 5 6} 3)
  (contains? {3 4, 5 6} 3)
  (contains? [1 2 3] 2)
  (contains? [3 4 5] 3)
  (contains? [3 4 5] 1)

  ;; Vorsicht bei contains? und Vektoren




  ;; Äquivalenz

  (= [1 2 3] '(1 2 3))
  (= (keys {1 2, 3 4}) [1 3])
  






  ;; Hilfreiche Funktionen auf Collections

  ;; meine Highlights

  (frequencies [1 3 2 4 6 1 3 6 1 3 2 2 1])

  (group-by first [[1 2] [1 4] [2 1] [2 2] [4 2]])
  (group-by (fn [v] (apply + v)) [[1 2] [1 4] [2 1] [2 2] [4 2]])

  (partition 2 (range 13))
  (partition 3 2 (range 13))

  (partition 3 3 (repeat :a) (range 13))
  (partition 3 3 [] (range 13))

  ;; Die Üblichen Kandidaten

  (reverse [1 2 3])
  (concat [1 2 3] [4 5 6])
  (count [1 1 1 1])

  (interleave [1 1 1 1] [2 2 2 2] [3 3 3 3])
  (interleave [1 1 1 1] [2 2])
  (interpose "1" [2 2 2 2 2 2])

  (repeat 5 :a)

  (take 4 (range 1000))
  (take 5 (repeat "a"))
  (take 10 (repeat "a"))

  (drop 5 (range 14))
  (take-while neg? (range -10 10000))
  (drop-while neg? (range -10 10))


  (last [1 2 3])
  (butlast [1 2 3])

  (first [1 2 3])
  (second [1 2 3])
  (nth [1 2 3] 2)
  (nth (range 1000) 33)

  (take 20 (cycle [1 2 3])))

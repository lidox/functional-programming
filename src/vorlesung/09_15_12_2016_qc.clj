(ns repl.core
  (:require [clojure.test.check :as tc]
            [clojure.test.check.generators :as gen]
            [clojure.test.check.properties :as prop]
            [clojure.test :as t]))

(defn my-sort [coll] (seq (into (sorted-set) coll)))
(defn my-sort2 [coll] (into [] (into (sorted-set) coll)))
;; PORT 4004

(comment


  ;; Transients: Wir vermeiden mutable Objects, aber ...

  ;; Wenn das innerhalb einer Funktion gekapselt ist, ist es kein
  ;; Problem.
  ;; Konstruktion von grossen Vektoren, Sets, etc.

  (-> #{} (conj 1) (conj 2) (conj 3) (conj 4) (disj 3))

  (-> #{} transient (conj! 1) (conj! 2) (conj! 3) (conj! 4) (disj! 3) persistent!)

  (type (persistent! (transient #{})))

  (-> #{} transient)

  (def a #{1 3})

  ;; nicht machen!
  (def b (transient a))

  a

  (conj! b 6)

  a

  (def c  (persistent! b))

  [a b c]

  ;; Richtige Anwendung von transients: Immer innerhalb einer Funktion
  ;; generieren und am Ende persistent! aufrufen

  (defn foo [n]
    (loop [i 0 v (transient #{})]
      (if (< i n)
        (recur (inc i) (conj! v (keyword (str i))))
        (persistent! v))))

  (foo 100)


  ;; Noch richtiger: gar nicht benutzen!


  ;; Angenommen ihr hättet transients in Clojure eingebaut. Wie würdet
  ;; ihr sicherstellen, dass eure Implementierung korrekt ist?

  ;; Welche Tests würdet ihr schreiben?
























  ;; test.check
  ;; Randomisierter Input + Spezifikation der Funktion

  gen/nat

  (gen/sample gen/nat)

  (gen/sample gen/nat 20)

  (gen/sample gen/boolean)
  (gen/sample gen/char-ascii)
  (gen/sample gen/keyword)
  (gen/sample gen/int)
  (gen/sample gen/any)
  (gen/sample gen/any-printable) ;; no bell!
  (gen/sample gen/string 20)
  (gen/sample gen/string-ascii 20)
  (gen/sample gen/string-alphanumeric 20)

  (gen/sample (gen/choose 100 250))
  (gen/sample (gen/elements [-1 2 -3 4]))
  (gen/sample (gen/return 3))

  ;; Composed
  (gen/sample (gen/vector gen/boolean))
  (gen/sample (gen/vector gen/boolean 3))
  (gen/sample (gen/tuple gen/int gen/boolean gen/nat))
  (gen/sample (gen/map gen/keyword (gen/map gen/nat gen/int)))

  (nth (gen/sample (gen/map gen/keyword (gen/map gen/nat gen/int))) 4)

  ;; Filter
  (gen/sample (gen/vector gen/boolean))
  (gen/sample (gen/not-empty (gen/vector gen/boolean)))
  (gen/sample gen/nat 30)
  (gen/sample (gen/such-that even? gen/nat) 30)

  (gen/sample (gen/such-that (fn [n] (< n 0)) gen/nat 100))


  
  ;; Higher order Generatoren

  (gen/sample (gen/fmap str gen/int))

  (gen/sample
   (gen/fmap (fn [[x y z :as c]]
               [x y z (count (filter identity c))])
             (gen/vector gen/boolean 3)))



  (let [x (gen/sample
           (gen/frequency [[70 (gen/return :kopf)]
                           [30 (gen/return :zahl)]]) 1000)]
    (frequencies x))

  (let [x (gen/sample
           (gen/frequency [[20 (gen/return :kopf)]
                           [20 (gen/return :zahl)]]) 1000)]
    (frequencies x))

  (gen/sample (gen/elements [1 2 3]))
  (gen/sample (gen/one-of [gen/int gen/boolean]))



  ;; Komplexere Generatoren
  ;; Ein Tupel bestehend aus einem Vektor und einem Element aus diesem Vektor

  (gen/sample (gen/fmap (fn [v] [v (rand-nth v)]) (gen/not-empty (gen/vector gen/int))))
  
  
  
  (def vector-gen (gen/not-empty (gen/vector gen/int)))
  (gen/sample vector-gen)

  (defn tuple-from-vector-gen [v]
    (gen/tuple (gen/return v)
               (gen/elements v)))
  
  (gen/sample (tuple-from-vector-gen [1 2 3]))


  (def complex-gen (gen/bind vector-gen tuple-from-vector-gen))
  (gen/sample complex-gen 20)


  
  
  ;; Was hat das nun mit Testing zu tun?







  ;; Idee von Quickcheck: Spezifiziere die Relation zwischen Input und
  ;; Output als Prädikat. Wirf zufällige Eingaben in die Funktion und
  ;; checke den Output.


  
  (t/are [x y] (= x y)
    (my-sort [1]) [1]
    (my-sort [1 2]) (my-sort [2 1])
    (my-sort [1 3 2 4 5]) (my-sort [5 1 3 2 4]))


  (t/are [x y] (not= x y)
    (my-sort [1]) [3])

  

  ;; Reicht das an test-cases?

  (def vectors-of-numbers (gen/vector gen/int))

  (gen/sample vectors-of-numbers)

  ;; Wir benutzen ein Orakel, also eine Implementierung die korrekt
  ;; ist. Das ist ziemlich nützlich, wenn man eine alte (korrekte aber
  ;; möglicherweise nicht optimale) Implementierung ersetzen will


  (sorted? [2 1])
  (sorted? [1 2])

  ;; WTF?

  (doc sorted?)









  
  

  (defn sortiert? [v]
    (= (sort v) v))

  (sortiert? [])
  (sortiert? [1 2])
  (sortiert? [2 1])



  ;; Das Ergebnis einer sort Funktion sollte sortiert sein:
  (def sortiert-prop (prop/for-all [data vectors-of-numbers]
                                   (sortiert? (my-sort data))))


  (tc/quick-check 100 sortiert-prop)


  ;; Es gibt ein Macro um quickchek in einen clojure.test Testcase umzuwandeln
  ;; (defspec qs-sorted 100 sortiert-prop)

  (my-sort [])

  (t/is (= (my-sort2 []) []))

  (def sortiert-prop (prop/for-all [data vectors-of-numbers]
                                   (sortiert? (my-sort2 data))))
  

  (tc/quick-check 100 sortiert-prop)
  (tc/quick-check 1000 sortiert-prop)


  ;; Noch eine Eigenschaft: Alle ursprünglichen Werte sollten erhalten
  ;; werden

  (defn permutation? [v1 v2]
    (= (frequencies v1) (frequencies v2)))


  (def permutation-prop
    (prop/for-all [data vectors-of-numbers]
                  (permutation? data (my-sort2 data))))



  (def r (tc/quick-check 10 permutation-prop :seed 1422980091656))

  (:fail r)

  r

  (-> r :shrunk :smallest)


  (t/is (= (my-sort2 [0 0]) [0 0]))



  ;; Zurück zum Transient Beispiel

  ;; Wenn wir eine Sequenz haben (-> #{} (conj 1) (conj 2) (disj 0))
  ;; Dann können wir die Performace erhöhen, indem wir
  ;; 1) Als Erstes transient aufruft
  ;; 2) conj durch conj! und disj durch disj! ersetzt
  ;; 3) Am Ende persistent! aufruft

  (defn transient? [x]
    (instance? clojure.lang.ITransientCollection x))

  ;; Wir können transient und persistent! öfter aufrufen, aber nur
  ;; paarweise. transient ... persistent! ... transient ...
  ;; persistent! ...
  ;; Aber nicht transient ... transient ... persistent! ...
  ;; presistent!

  ;; Wir könnten einen Generator schreiben, der ein Paar aus zwei
  ;; Programmen generiert. Ein Programm ist normaler Code, das Andere
  ;; beinhaltet korrekt gesetzte transien/persistent! Paare

  ;; Das ist zu kompliziert. Stattdessen schreiben wir einen Generator,
  ;; der Sequenzen von Aktionen erzeugt und einen kleinen Interpreter,
  ;; der diese in korrekte Calls übersetzt.

  ;; Aktionen haben die Form [:conj Zahl], [:disj Zahl], [:trans] oder
  ;; [:pers]

  (def gen-mods
    (gen/not-empty
     (gen/vector
      (gen/one-of
       [(gen/elements [[:trans] [:pers]])
        (gen/tuple (gen/elements [:conj :disj]) gen/int)]))))

  (gen/sample (gen/elements [[:trans] [:pers]]))
  (gen/sample (gen/tuple (gen/elements [:conj :disj]) gen/int))

  (nth (gen/sample gen-mods) 4)

  (defn run-action [c [f & [arg]]]
    (condp = [(transient? c) f]
      [true   :conj]          (conj! c arg)
      [false  :conj]          (conj c arg)
      [true   :disj]          (disj! c arg)
      [false  :disj]          (disj c arg)
      [true   :trans]         c
      [false  :trans]         (transient c)
      [true   :pers]          (persistent! c)
      [false  :pers]          c))

  (run-action #{} [:conj 2])
  (run-action #{} [:trans])
  (persistent! (run-action (transient #{1}) [:disj 1]))

  (defn reduce-actions [coll actions]
    (reduce run-action coll actions))


  (reduce-actions #{} [[:conj 3]])
  (reduce-actions #{} [[:conj 2] [:trans] [:conj 4] [:trans] [:pers]])
  (reduce-actions #{} [[:conj 2] [:trans] [:conj 4] [:trans] [:trans]])

  (defn apply-actions [coll actions]
    (let [applied (reduce-actions coll actions)]
      (if (transient? applied)
        (persistent! applied)
        applied)))


  (apply-actions #{} [[:conj 2] [:trans] [:conj 4] [:trans] [:trans]])


  (defn filter-actions [actions]
    (filter (fn [[a & args]]
              (#{:conj :disj} a))
            actions))

  (filter-actions [[:conj 2] [:trans] [:conj 4] [:trans] [:trans]])

  ;; Was machen wir?
  ;; 1) Generiere Actions mit :trans und :pers
  ;; 2) Lasse die Actions auf dem leeren Set laufen.
  ;;    Falsche Sequenzen werden vom Interpreter automatisch repariert
  ;; 3) Filtere die Actions und lasse sie auf dem leeren Set laufen
  ;; 4) Die Resultate müssen übereinstimmen

  (def transient-property
    (prop/for-all
     [a gen-mods]
     (= (apply-actions #{} a)
        (apply-actions #{} (filter-actions a)))))


  (tc/quick-check 100 transient-property)
  (tc/quick-check 10000 transient-property)

  (def r (tc/quick-check 400 transient-property :seed 1422983037254))

  (:fail r)
  (count (first  (:fail r)))

  (def full-seq (-> r :shrunk :smallest first))

  full-seq
  ;; Keine Aktion kann man weglassen ohne, das der Fehler
  ;; verschwindet.

  (let [le-seq [[:conj -49] [:conj 48] [:trans] [:disj -49] [:pers]]]
    (= (apply-actions #{} le-seq)
       (apply-actions #{} (filter-actions le-seq))))

  (= (apply-actions #{} full-seq)
     (apply-actions #{} (filter-actions full-seq)))

  ;; Was ist denn genau nicht gleich?

  (let [le-seq [[:conj -49] [:conj 48] [:trans] [:disj -49] [:pers]]]
    (t/is  (= (apply-actions #{} full-seq)
              (apply-actions #{} (filter-actions full-seq)))))





  (.hashCode 47)
  (.hashCode -48)



  ;; Wer von euch hatte den Testcase?





  *clojure-version*




  ;; Bug: http://dev.clojure.org/jira/browse/CLJ-1285
  ;; Wurde tatsächlich mit dieser Methode gefunden!
  ;; https://groups.google.com/forum/#!msg/clojure-dev/HvppNjEH5Qc/1wZ-6qE7nWgJ


  ;; Es gibt eine Spezialbibliothek für das Testen von Datenstruktur-Implementierungen
  ;; https://github.com/ztellman/collection-check







  )

(ns repl.core
  (:gen-class))


;; besonders wichtig, wenn wir mit unendlichen Datenstrukturen arbeiten
(set! *print-length* 20)

(def nat (range))
nat


(comment
  ;; ---------------------------------------------------------
  ;; WARMUP
  ;; ---------------------------------------------------------


  ;; Letzte Woche gesehen

  ;; skalare Datentypen (Ausschnitt)
  (type "Ich bin ein String")
  nil
  5
  5/3 ;; Literal, kein Infix Operator
  (/ 5 3)
  (str "hallo" \space "welt")
  (type 9223372036854775808)
  (type :keyword)
  *clojure-version*

  inc

  ;; Collection-Datentypen
  (list 1 2 3)
  '(1 2 3) ;; Quote

  (type '(1 2 3))

  [1 2 3]
  (type [1 2 3])

  #{1 2 3}
  (type #{1 2 3})

  ;; conj
  (conj [1 2 3] :a)
  (conj '(1 2 3) :a)
  (conj #{1 2 3} :a)


  {:a 1 :b 2 [{}] 3}
  (type {:a 1 :b 2 [] 3})
  (assoc {:a 1} :b 2 :p 2)

  (get {:a 1 :b 2} :a)
  (get {:a 1 :b 2} :c)
  (get {:a 1 :b 2} :c 42)

  ;; Sequentielle Sicht auf Maps
  (map identity {:a 1 :b 2 :c 3})
  (conj {} [:a :b])

  ;; map: Wende eine Funktion auf alle Elemente einer Collection an
  (map inc [1 2 3 4])
  (type (map inc [1 2 3 4])) ;; mehr dazu später!

  ;; def / defn

  (def a 1)

  a

  (defn square [x] (* x x))
  (def square (fn [x] (* x x)))
  (square 3)

  (if :foo 1 2)
  (if nil 1 2)
  (if-not :foo 1 2)

  (defn disp [x]
    (cond
      (= x 1) :eins
      (< x 2) :einsb
      (= 2 x) :zwei
      :ansonsten :viele))

  (disp 0)
  (disp 1)
  (disp 9)

  (range 10)
  (range 10 20)
  (range 1 100 10)

  ;; woah! unendliche Listen
  (range)
  (nth (range) 4756)

  (source empty?)

  ;; FizzBuzz Kata

  (defn divisible? [n m]
    (= 0 (mod n m)))

  (defn replace-fb [n]
    (cond
      (divisible? n 15) :fizzbuzz
      (= 0 (mod n 5)) :buzz
      (= 0 (mod n 3)) :fizz
      :sonst n))

  (defn fizzbuzz [n]
    (map replace-fb (range 1 (inc n))))

  (fizzbuzz 15)


  ;; ---------------------------------------------------------
  ;; Funktionen / Higher Order Functions
  ;; ---------------------------------------------------------


  ;; Def: Higher Order Function - Funktion, die eine Funktion als Parameter bekommt oder zurückgibt

  ;; fn als param

  (defn evaluate1 [f v] (f (f v)))

  (evaluate1 inc 12)
  (evaluate1 dec 12)

  ;; fn als wert
  (defn mk-adder [n]
    (fn [x] (+ x n)))

  (def foo (mk-adder 17))

  (foo 4)


  ;; Beispiele: map filter mapcat reduce

  (map inc (range 2 7))
  (map #(* % 2) (range))
  (map + [1 2 3] [3 4 5] [4 6])

  (filter (fn [x] (< 2 x 6)) [1 2 3 4 5 6])

  (defn plusminus [e] [(dec e) e (inc e)])
  (plusminus 5)
  (map plusminus (range 5 10))
  (mapcat plusminus (range 5 10))

  ;; Die Eingabecollection hat 7 Elemente, wieviele Elemente hat die Ausgabecollection?
  ;; map
  ;; filter
  ;; mapcat













  (mapcat vector [1 2 3])
  (mapcat (constantly []) [1 2 3])


  ;; Reduce, die Mutter aller Collection Funktionen
  ;; (reduce funktion startwert collection)

  (reduce * 1 (range 2 7))
  (reduce * (range 2 7))


  ;; Reduce-Function
  ;; Zweistellige Funktion:
  ;;  - erstes Argument ist das bisherige Teilergebnis (Akkumulator)
  ;;  - zweites Argument ist der nächste Wert der Collection
  ;; Der Initialwert ist der erste Wert für den Akkumulator

  (defn squaresum-reduce-fn [akku elem]
    (+ akku (* elem elem)))

  (reduce squaresum-reduce-fn 0 [1 2 3 4])

  ;; Ohne Startwert
  (reduce squaresum-reduce-fn [1 2 3 4])
  (reduce squaresum-reduce-fn [2 3 4])
  (reduce squaresum-reduce-fn [3])
  (reduce squaresum-reduce-fn [])

  (doc reduce)


  ;; reduce kann map implementieren !!!
  ;; (und auch filter)

  (defn reduce-function-gen [f]
    (fn [akku element] (concat akku [(f element)])))
  (defn mymap [f c]
    (reduce (reduce-function-gen f) (list) c))

  (mymap dec [1 2 3])
  


  ;; Was ist eigentlich der Ausgabetyp von map?

  (type (map second {:a 1 :b 2 :c 3}))
  (type (map inc [1 2 3]))
  (type (map inc #{9 2 1}))

  ;; (Lazy)Seqs

  ;; SLIDES



  ;; ---------------------------------------------------------
  ;; Lazyness Selbst gemacht
  ;; ---------------------------------------------------------


  ;; Range mit Datenstrukturen

  (defn ranje [n]
    {:first n
     :rest (fn [] (ranje (inc n)))})

  (ranje 0)

  (defn head [r]
    (:first r))

  (head (ranje 5))

  (defn tail [r]
    ((:rest r)))

  (head (tail  (tail (ranje 0))))


  ;; Range a la Houdini

  (defn ranje2 [n]
    (fn [e] (if e n (ranje2 (inc n)))))

  (defn head2 [r]
    (r true))

  (defn tail2 [r]
    (r false))

  (head2 (tail2 (tail2 (ranje2 0))))



  ;; Was man beachten muss:
  ;;   + Keine Seiteneffekte

  (def lz  (map (fn [e] (println :doh) (inc e)) (range 0 100)))

  (nth lz 4)
  (nth lz 4)
  (nth lz 31)
  (nth lz 32)

  ;;   + Bestimmte Fragen bei unendlichen Sequenzen vermeinden:
  ;;      + Was ist das letzte Element?
  ;;      + Wie lang ist die Sequenz?

  ;;   + Aufpassen bei (str ...)
  (str (map inc [1 2 3]))

  (into [] (map inc [1 2 3]))
  
  ;; Abhilfe:
  (reduce str (map inc [1 2 3]))
  (apply str (map inc [1 2 3]))

  (loop [x 1]
    (println x)
    (if (= 2 x) :ende (recur (inc x))))

 


  )

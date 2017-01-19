 (ns blatt8)

 ;; Aufgabe 2
(defn calc'
  ([a] ))

(defmacro calc [bindings & body]
  '(let ~(vec bindings)))


 ;; AUFGABE 3
  ;; DSL (meist heißt das macro) für DFA implementieren
  ;; welches problem gibt es, wenn man es als DFA implementiert

  ;; z4 ist deklariert aber ungebunden
(defn dfa-state [action delta]
  (fn [s]
    (if (seg s)
      #((delta (first s)) (rest s))
    action)))

(defn dfa-match [z s]
  (trampoline z s))

(declare z0, z1, z2, z3, z4)

(def z0 (dfa-state :accept {\0 z0 \1 z1} ))
(def z1 (dfa-state :accept {\0 z2 \1 z3} ))
(def z2 (dfa-state :accept {\0 z4 \1 z0} ))
(def z3 (dfa-state :accept {\0 z1 \1 z2} ))
(def z4 (dfa-state :accept {\0 z3 \1 z4} ))

(defn dfa-match' [z s]
  (dfa-match z (drop 2 s)))

(mac)

 ;; warum löst das macro das problem?
 ;; A: code wird genommen und in eine funktion umgeschrieben
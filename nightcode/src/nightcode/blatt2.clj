(ns nightcode.core
  (:gen-class))

; Aufgabe 2.1
; a) Geben Sie eine Funktion an, die den maximalen Wert seiner Argumente bestimmt
(def getMax (fn [vec] (reduce max vec)))
(getMax [1 2 4 5 3 66 33 74 224 63 735])

(defn max-value [& x] (apply max x)) ;;alternativ auch "reduce" anstatt "apply"
(max-value 3 42 1336 12.5)

; b) Geben Sie eine Funktion an, die die erste langste Sequenz ihrer 
;    Argumente bestimmt. Verschachtelungen sollen hier nicht betrachtet werden.
;    (longest [] [:a :b 12] [[1 2 3 4 5 6]])
;    => [:a :b 12]

; for each item
;    get max count
(defn longest [& x] (apply max-key count x))
;              ^
;        mulpiple values
(longest [] [:a :b 12 2] [[1 2 3 4 5 6]] [1 2 2])

; c) Geben Sie eine Funktion an, die die maximale Lange ihrer Argumente bestimmt. 
;    Verschachtelungen sollen auch hier nicht betrachtet werden.
;    (max-length [] [:a :b 12] [[1 2 3 4 5 6]])
;    => 3
(def max-length (fn [& x] (apply max (map count x))))
(max-length [] [:a :b 12] [[1 2 3 4 5 6]])




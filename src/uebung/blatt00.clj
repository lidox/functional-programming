 (ns blatt00)

; Aufgabe 0.3 (REPL-Plugin)
(def fib-seq
  (lazy-cat [0 1] (map + (rest fib-seq) fib-seq)))

(take 10 fib-seq)

; Buch Aufgabe: Write a function that takes a number and adds 100 to it.
(def adder "adds 100 to x"  (fn [x] (+ 100 x)))

; dec-maker
; Write a function, dec-maker , that works exactly like
; the function inc-maker except with subtraction:
(defn dec-maker
  "Create a custom decrementor"
  [dec-by]
  #(- %1 dec-by))

(def dec3 (dec-maker 3))
(dec3 10)
; => 7

; 4. Write a function, mapset , that works like map
; except the return value is a set:
(into #{} (map inc [1 1 2 2]))
; => #{2 3}

(def liste #(1 2 3 4))








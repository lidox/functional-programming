 (ns blatt00)

;; Aufgabe 0.3 (REPL-Plugin)
(def fib-seq
  (lazy-cat [0 1] (map + (rest fib-seq) fib-seq)))

(take 10 fib-seq)
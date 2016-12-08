 (ns ziegen-problem)


;; ziegenproblem
(defn experiment1 []
  "entscheidet sich um"
  (let [prize (int (rand 3))
        choice (int (rand 3))]
    (= prize choice)))

(defn experiment2 []
  "entscheidet sich immer um"
  (let [prize (int (rand 3))
        choice (int (rand 3))]
    (not= prize choice)))

(defn run [n f]
  (frequencies (take n (repeatedly f))))

(run 100000 experiment1)
(run 100000 experiment2)


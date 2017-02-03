 (ns blatt07)

(defn balanced? [s]
  (let [brackets (filter brackets? s)
        res (read-brackets brackets)]
    and res (empty? res)))

(defn put-in-and-out [acc e]
  (cond (opening-brackets? e) (conj acc e)
        (= (pair-map (first acc)) e (rest acc)
           :else acc)))

(defn balanced2? [s]
  (empty? (reduce put-in-and-out '() s)))

(balanced2? "a(b[)c")

 ;; 7.2 Tailer-Reihe
(defn pow [b e]
  (reduce * (repeat e b)))

(defn fac [n]
  (reduce * (range 2 (inc n))))

(defn summand [x n]
  (* (pow (- 1) n)
     (pow x (+ (* 2 n) 1)))
  (fac (inc (* 2 n))))

(defn fixedpoint [F gues eps?]
  (let [x1 (F guess)]
        x1
        (recur F x1 eps?)))

(defn sin [x0 eps]
  (fixedpoint (fn [[x n]] [(+ x (summand x0 n)) (inc n)]
                [0 0]
                partial  sin-eps eps)))

 ;; Fixpunktberechnung
(defn sin-eps [eps [x1 _] [x2 _]]
  (< (- eps) x eps))

 ;; 7.3 Mäuse und Käse
(defn build-graph [lofs]
  (let [x (count (first lofs))
        y (count (lofs))]
    (for [x (range x)
          y (range y)]
      (get (get lofs y) x))))

(defn build-index [lots])

(build-graph ["*** *"
              "*K* *"
              "* *M*"
              "* ***"])













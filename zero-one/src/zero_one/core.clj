(ns zero-one.core)

(defn foo
  "I don't do a whole lot."
  [x]
  (println x "Hello, World!"))


;; Aufgabe 1.4 - Duplikate entfernen
(def remove-duplicates distinct)
(remove-duplicates [1 2 3 "dd" 2 2 1 1 :a 3])

(defn remove-duplicates2 [c]
  (loop [r c
         seen #{}
         so-far []]
    (if (empty? r)
          so-far
          (recur (rest r)
          (conj seen (first r))
            (if (contains? seen (first r))
              so-far
              (conj so-far (first r)))))))
(remove-duplicates2 [1 2 3 "dd" 2 2 1 1 :a 3])

(defn remove-duplicates3 [v]
  (remove nil?
          (for [i (range 0 (count v))]
            (let [j (nth v i)]
              (if (some #(= j %)
                        (take (dec i) v))
                nil
                j)))))
(remove-duplicates3 [1 2 3 "dd" 2 2 1 1 :a 3])
        
    
;; Feature von seq: wenn uebergebene Liste leer ist --> gibt 'nil' zurueck 
(seq [1 2 3])

;; Vorlesung Woche 3

;; Collections: schneller zugriff vorne
(list 123)

;; Type
(type '(1 2 3))

;; Vector ~ so wie arrayList, gut zum Einfügen von daten von hinten
[1 2 3]
(type [1 2 3])


;;
(conj [1 2 3] :a)

;; Map
{:a 1 :b 2 [{}] 3}
(type {:a 1 :b 2 [{}] 3})

;; get value by key
(get {:a 1 :b 2 [{}] 3} :a)

;; map function: Wende eine funktion auf alle elemente der collection
(map inc [1 2 3 4])

;; def / defn ~ quasi globale variable, nur auf top level ebene benutzen!!! zum dinge definieren
(def a 1)

;; funktion definiert
(def square (fn [x] (* x x)))
(square 3)

;; if
(if :foo 1 2)
(if nil 1 2)

;; conditions
(defn disp [x]
  (cond
    (= x 1) :eins
    (< x 2) :einsh
    ))
(disp 1)

;; FizzBuzz Kata
(defn replace-fb [n]
  (cond
    (= 0 (mod n 15)) :fizzbuzz
    (= 0 (mod n 5)) :buzz
    (= 0 (mod n 3)) :fizz
    :sonst n))

(defn fizzbuzz [n]
  map( replace-fb (range 1 (inc n))))

(fizzbuzz 15)

;; higher order functions: zwei mal inc ausführen
(defn evaluate1 [f v] (f (f v)))

(evaluate1 inc 12)

;; filter
(filter (fn [x] (< 2 x 6 )) [1 2 3 4 5 6 7])

;; AUsgabetypen von map
(type (map second {:a 1 :b 2 :c 3}))


;; Was ist eine LazySeq? -> TUPEL




































































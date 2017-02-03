(ns repl.core)

(use 'clojure.repl)
(use 'clojure.walk)

;; Dokumentation
(doc doc)
(doc source)
(doc apropos)

;; Suchen
(apropos "map")

;; Sourcecode lesen
(source map)

;; nützliche Uebersicht fuer alles moegliche:
;; http://clojure.org/api/cheatsheet

;; Null

nil
(type nil)

;; nil ist exakt das Selbe wie null in Java!
(- nil)
(pst)

;; NullPointerException sieht man sehr selten in reinem Clojure code.

;; ---------- Strings

"foo"

(type "foo")
;; Clojure Strings sind Java Strings

"hallo
welt"

;; ---------- Characters

\f

(type \f)

(str "foo" \f)

(str \n)
;; Das ist etwas ungewohnt, insbesondere, weil \n in Strings die "normale" Bedeutung hat

(str "Hallo" "Welt")

(str "Hallo" \space "Welt")

(str "Hallo" \newline "Welt")

\u03bb

(println \u03bb "Kalkül" "ftw!")


;; ---------- Numbers

3
(type 3)

0x33

012

2r1011

7r666

36rCrazy
;; Literalsyntax für alle Basen <= 36
;; Frage: Warum 36?

(type 36rCRAZY)

(type 9223372036854775807)
(type 9223372036854775808)

;; Literale werden automatisch in einen passenden Typ gelesen

3N
(type 3N)
;; Erzwingen von BigInt

(inc 4)
(inc 9223372036854775807)

(+ 9223372036854775807 1)

(inc' 9223372036854775807)

(*' 9223372036854775807 9223372036854775807)

;; Auto-Promotion

(type *1)
(type (*' 9223372036854775807 9223372036854775807))
;; *1 ist das letzte Ergebnis in der Repl

(type *)
;; ---

3.141592653589793
(type 3.141592653589793)

3.141592653589793238

;; Fließkommazahlen werden nicht in einen passenden Typ gelesen

3.141592653589793238M
(type 3.141592653589793238M)

;; Erzwingen von beliebiger Genauigkeit mit M

22/7
(type 22/7)

(/ (inc 32) 7)
(inc 32)/7 ;; geht nicht!

(double (/ 22 7))
(type (/ 22 7))

2/6
8/2
(type 8/2)

;; Vorsicht! Ratio kann langsam sein!

;; ---------- Names

;; Zwei Arten: Symbols und Keywords

*clojure-version*
(type *clojure-version*)

;; Symbols sind wie Bezeichner in anderen Sprachen, sie stehen für etwas

inc

+'

blah!

(*clojure-version*)

'(+ 1 2)

;; Quote verhindert die Aufloesung des Symbols

;; In Düsseldorf ist ein ü

(type (quote *clojure-version*))

foo
(quote foo)
(quote foo!)
(quote foo?)
(quote Foo.)
(quote .foo)

'blah!
(quote blah!)

;; ' ist Reader-Syntax für quote

:foo
(type :foo)

;; Keywords fangen mit einem : an, sie stehen für sich selber
;; Vielseitige Verwendung:
;; - Schlüssel in maps
;; - Keywords in APIs
;; - Wo man in Java Stringkonstanten benutzen würde

;; ---------- Regex

#"([0-9]{4})/([0-9]{2})/([0-9]{2})"
(type #"([0-9]{4})/([0-9]{2})/([0-9]{2})")

;; Literalsyntax für Javas Pattern
(re-seq #"A(.*?)B" "ACBAAABBBBBSSSSYYYAJHDHGHJ")

;; ---------- Kollektionen

(list 1 :zwei 3)

'(1,2,3)

(type ,,,, '(1,2,3))
(type '(1 2 3))

[1 2 ,,3,,,4,,,]
(type [str, 2, :foo, \space])

(nth [1 2 3] 2)
(conj [1 2 3] 1)
(conj '(1 2 3) 1)

(.hashCode [1 2 3])

(vector 1 2 3)

{:name "Bendisposto", :vorname "Jens" :alter :uHu}

{[1] \n [] \l :foo 1}

(get {[1] \n [] \l } '())

(= '() [])
(type '())
(type [])

(type {:key1 "foo", 2 9})

(def a {:foo 1})
a
(assoc a :bar 2)
(assoc {:foo 1} :bar 2)
(assoc {:foo 1} :bar 2 :baz 3 :boing 4)

{:kaboom 1 :kaboom 2}
{:kaboom 1 :kaboom 1}

(assoc {:kaboom 1} :kaboom 2)

#{2 3 5 7 11}
(type #{str, 2, :foo, \space})

#{1 1 1}


(def n [{:name "Bendisposto", :vorname "Jens" :alter :uHu :lottozahlen [1 3 5 15 21 44]}
        {:name "Witulski", :vorname "John" :alter :bivi}])

(get n 1)
(get (get n 1) :name)

;; Maps und Vektoren sind Funktionen, die einen Key bekommen und den Key in sich selber nachschlagen
;; Bei Vektoren sind die Keys die Positionen
(n 1)
((n 1) :name)

(:name (n 0))

(get-in n [1 :name])

;; ---------- Call

(+ 1 2 3 4 5 6 7)


(+ (* 3 4) 11 112)

;; Frage: Was ist ein "vernünftiger" Wert für (*) und (+)
(*)
(+)

;; ---------- apply
(+ [1 2 3])
(apply + [1 2 3])

(apply + [1 [2 2]])
(apply (partial apply +) [1 [2 2]])



;; + ist eine Funktion, die mehreren Argumente nimmt. Man kann aber keine Kollection übergeben.
;; apply "entpackt" das letzte Argument
;; (apply + [1 3]) -> (+ 1 3)
;; (apply + 1 2 [3 4 5]) -> (+ 1 2 3 4 5)


;; ---------- Definition

pi
(def pi 3)
pi

(def π 3.141592653589793238M)
(* 2 π)

;; ---------- Immutability

(def v [1 2 3])
v
(into '()  (concat v [4 5 6]))
(into nil (concat v [4 5 6]))
v

;; Alle Funktionen, die Kollektionen "modifizieren",
;; geben eine neue Kollektion zurück, die ursprüngliche
;; Version bleibt erhalten.

;; ---------- Funktionen

;; λx.x+3
(fn [x] (+ 3 x))

(def square (fn [x] (* x x)))

(def sum-square (fn [x y] (+ (square x) (square y))))

;; defn ist syntaktischer Zucker
(defn sq2 [x] (* x x))
(square 15)
(sq2 15)

(macroexpand-1 '(defn sq2 [x] (* x x)))

;; Funktionsauswertung
;; Mechanismus: beta reduction

;; Um eine Anwendung auszuwerten
;; 1) werte das erste Element aus um die Funktion zu erhalten
;; 2) werte die restlichen Elemente aus um die Argumente zu erhalten
;; 3) wende die Funktion auf die Argumente an
;;     - kopiere den Funktionskörper substituiere dabei die formalen
;;       parameter durch die Operanden
;;     - werte den resultierenden neuen Körper aus

;; Beispiel Summe der Quadratzahlen

(sum-square 3 4)

(+ (square 3) (square 4))
(clojure.core/+ (square 3) (square 4))
(+ (* 3 3) (square 4))
(+ 9 (square 4))
(+ 9 (* 4 4))
(+ 9 16)
25

;; ---------- Control structures

(if true 1 2)
(if true (println 1) (println 2))
(if false 1 2)

(if 1 2 3)
(if :doh 2 3)
(if nil 1 2)

;; Falsey sind nil und false, alles andere ist truthy
(when false 1)

(cond
  (= 1 2) 1
  ( < 1 2) 2
  :else 3)

(cond
  (= 1 2) 1
  ( > 1 2) 2
  :otherwise 3)

(macroexpand-all '(if 1 2 3))

(macroexpand-all '(cond (= 1 2) 2
                        (= 2 2) 4))


(macroexpand-all  '(if-not false 1 2))

;; if-not und cond sind syntaktischer Zucker

;; ---------- fac

(defn ! [n]
  (if (= 1 n)
    1
    (* n (! (dec n)))))

(! 10)

(! 30)

;; * gibt einen Overflow, wenn das Ergebnis nicht mehr in den Long Typ passt.

(defn ! [n]
  (if (= 1 n)
    n
    (*' n (! (dec n)))))

(! 30)

(! 10000)

;; Rekursive Aufrufe produzieren Frames auf dem Stack und irgendwann sind es zu viele.

(defn ! [n]
  (reduce *' (range 1 (inc n))))

;; reduce ist eine Weise das Problem zu reparieren, range hatten wir schon gesehen 

(time  (let [x (! 10000)] :ok))


;; Higher order Functions: map, filter, reduce

(map inc [2 3 4 5 6])
(map + [1 2 3] [3 4 5] [2 3])

;; map bekommt eine Funktion f und eine Sequenz von Werten x1 ... xn und berechnet die Sequenz f(x1) ... f(xn)


;; filter bekommt eine Funktion p? und eine Sequenz x1..xn.
;; es wird eine Sequenz aller xi produziert, für die (p? xi) eine Wert liefert, der truthy ist
(filter (fn [x] (< 2 x 6)) [1 2 3 4 5 6])

;; Was macht remove? 
(remove (fn [x] (< 2 x 6)) [1 2 3 4 5 6])


;; reduce, die Mutter aller HOFs

;; reduce bekommt eine Funktion f der Struktur (fn [akkumulator element] ...),
;; einen Start-wert a für den Akkumulator und eine Sequenz x1..xn. 
;; Berechnet wird: (f ... (f (f a x1) x2) ... xn) 

(reduce + 1 [2 3 4 5 6]) ;; (+  (+ (+ 1 2) 3) 4 ...)

;; (reduce reduce-funktion start-wert sequenz)

(reduce * (range 2 7))

(reduce + 0 (range 2 7))
(reduce + (range 2 7))

(reduce conj [] [1 2 3])
(reduce conj '() [1 2 3])


(take 10 (iterate inc 1))

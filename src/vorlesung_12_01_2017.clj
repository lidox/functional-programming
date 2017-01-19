 (ns vorlesung-12-01-2017)

;; THEMA: MACROS
;; macros -> Ändern der Sprache, damit sie zum Problem passt, statt das Problem auf den Code anzupassen (in den code questschen)
;; Macros kann man gut nutzen und die Sprache anzupassen. Bei JAVA dauert es Jahre, bis Änderugnen akzeptiert werden mit hunderten Zeilen Code
;; In Clojure reichen wenige Zeilen Code mit Macros. Validierung geht schneller.

;; Achtung: Macros werden zur Laufzeit evaluiert/ zur Kompliezeit berechnet. Gefahr: es können große Bytecodes zur laufzeit generiert werden

(defmacro m [] (println :m))
(defn f [x] (println (inc x)) (m) (println :done))

(f 1) ;; m verschwindet --> also mit Quote ausführen: '(m)

;; Klausuraufgabe: sollten wissen dass es nicht der erste Wert

;; macros können dafür verwendet werden, um syntaktischen Zucker zu erzeugen. SIeht besser aus und ist einfacher zu lesen
;; bei Berechnungen zur Kompilezeit ist es auch sinnvoll

;; macros haben 2 parameter, die von clujure auftomatisch gesetzt werden: &form und &env
;; normale macros machen transformationen auf oberster ebene

;; es gibt noch deepWalking macros
;; gehen rein in den Code und können damit vieles unmögliche, möglich machen
;; transfomation auf innerer EBene möglich
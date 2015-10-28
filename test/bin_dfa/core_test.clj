(ns bin-dfa.core-test
  (:require [clojure.test :refer :all]
            [bin-dfa.core :refer :all]))

(deftest run-machine-test
  (letfn [(accepting [state] (or (= state "AD") (= state "ABD")))
          (not-accepting [state] (not (accepting state)))
          (all-inputs [length] (if (zero? length)
              '(())
              (apply concat (for [x [0 1]] (map (partial cons x) (all-inputs (- length 1)))))))
          (suffix [sequence] (if (= (count sequence) 3) sequence (suffix (rest sequence))))
          (should-accept [sequence] (let [s (suffix sequence)]
              (or (= '(0 1 0) s) (= '(0 1 1) s))))]
    (testing "run-machine"
      (testing "accept strings ending in 010 or 011"
        (let [machine (process-machine
              '((A AB A) (AB AB AC) (AC ABD AD) (ABD AB AC) (AD AB A)))]
          (doseq [input (all-inputs 8)] (let [result (run-machine machine "A" input)]
              (is (= (accepting result) (should-accept input)) input))))))))